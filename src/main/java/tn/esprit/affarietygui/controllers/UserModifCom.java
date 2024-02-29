package tn.esprit.affarietygui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import tn.esprit.affarietygui.models.Commentaire; // Importer la classe Commentaire
import tn.esprit.affarietygui.services.CommentaireService;

import java.sql.SQLException;
import java.util.Optional;

public class UserModifCom {
    @FXML
    private TextArea commentTextArea;

    private Commentaire commentaire;
    private UserComments userCommentsController; // Reference to UserComments

    // Method to pass the comment object and a reference to UserComments
    public void initData(Commentaire commentaire, UserComments userCommentsController) {
        this.commentaire = commentaire;
        this.userCommentsController = userCommentsController;
        commentTextArea.setText(commentaire.getContenu());
    }

    @FXML
    public void ModifierCommentaire(ActionEvent actionEvent) {
        if (commentaire != null) {
            String newContent = commentTextArea.getText();
            if (!newContent.isEmpty()) {
                // Afficher une boîte de dialogue de confirmation
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Voulez-vous vraiment modifier ce commentaire ?");

                // Obtenir la réponse de l'utilisateur depuis la boîte de dialogue
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si l'utilisateur clique sur OK, modifier le commentaire
                    commentaire.setContenu(newContent);

                    // Mettre à jour le commentaire dans la base de données
                    CommentaireService commentaireService = new CommentaireService();
                    try {
                        commentaireService.modifier(commentaire);

                        // Afficher un message de confirmation
                        System.out.println("Commentaire modifié avec succès !");

                        // Call the method in UserComments to update comments after modification
                        userCommentsController.updateCommentaire(commentaire);

                        // Fermer la fenêtre de modification du commentaire
                        commentTextArea.getScene().getWindow().hide();
                    } catch (SQLException e) {
                        e.printStackTrace(); // Gérer l'exception correctement
                    }
                }
            } else {
                // Afficher un message d'erreur si le contenu du commentaire est vide
                System.out.println("Veuillez entrer un nouveau contenu pour le commentaire.");
            }
        } else {
            // Afficher un message d'erreur si aucun commentaire n'est sélectionné
            System.out.println("Aucun commentaire sélectionné.");
        }}
}
