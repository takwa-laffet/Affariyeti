package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import tn.esprit.affariety.models.Commentaire;
import tn.esprit.affariety.models.Publication;
import tn.esprit.affariety.services.CommentaireService;
import tn.esprit.affariety.services.PublicationService;

import java.sql.SQLException;
import java.util.Optional;

public class ModifCom {
    @FXML
    private TextArea commentTextArea;

    private Commentaire commentaire;
    private GestionCom gestionComController; // Reference to UserComments


    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
        // Remplir les champs textuels avec les valeurs actuelles de la publication
        commentTextArea.setText(commentaire.getContenu());
       }
    public void initData(int commentaireId, GestionCom gestionComController) {
        this.gestionComController = gestionComController; // Assigner la référence à gestionComController

        CommentaireService commentaireService = new CommentaireService();
        try {
            // Récupérer le commentaire à partir de son identifiant
            Commentaire commentaire = commentaireService.getCommentsById(commentaireId);
            // Vérifier si le commentaire existe
            if (commentaire != null) {
                // Définir le commentaire dans le contrôleur
                setCommentaire(commentaire);
            } else {
                // Afficher une alerte si le commentaire n'est pas trouvé
                afficherAlerte("Commentaire introuvable", "Le commentaire avec l'ID " + commentaireId + " n'existe pas.");
            }
        } catch (SQLException e) {
            // En cas d'erreur lors de la récupération du commentaire
            afficherAlerte("Erreur de récupération", "Une erreur s'est produite lors de la récupération du commentaire : " + e.getMessage());
        }
    }
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
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
                        gestionComController.updateCommentaire(commentaire);
                        // Call the method in UserComments to update comments after modification
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
