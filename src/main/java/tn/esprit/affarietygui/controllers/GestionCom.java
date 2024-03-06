package tn.esprit.affarietygui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.affarietygui.models.Commentaire;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.CommentaireService;
import tn.esprit.affarietygui.services.PublicationService;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class GestionCom {

    @FXML
    private Button AVIS;

    @FXML
    private AnchorPane AnchorPaneaff;

    @FXML
    private Button COMMANDES;

    @FXML
    private Button ENCHERE;

    @FXML
    private Button LIVRAISON;

    @FXML
    private Button PRODUITS;

    @FXML
    private Button USER;

    @FXML
    private ListView<Commentaire> lvcom;

    private Publication publicationSelectionnee; // Ajout de la variable pour stocker la publication sélectionnée

    public void afficherCommentaires(List<Commentaire> commentaires, Publication publication) {
        ObservableList<Commentaire> commentaireDataList = FXCollections.observableArrayList(commentaires);
        lvcom.setItems(commentaireDataList);
        lvcom.setCellFactory(new CommentaireCellFactory());
        publicationSelectionnee = publication; // Assignation de la publication sélectionnée
    }

    public void Modifier(ActionEvent actionEvent) {
        Commentaire selectedCom = lvcom.getSelectionModel().getSelectedItem();
        if (selectedCom == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune publication sélectionnée");
            alert.setContentText("Veuillez sélectionner une publication à modifier.");
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier commentaire");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce commentaire  ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier commentaire");
            dialog.setHeaderText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/ModifCom.fxml"));
            try {
                Parent root = loader.load();
                ModifCom controller = loader.getController();
                controller.initData(selectedCom.getId_com(), this); // Passer la référence à GestionCom
                dialog.getDialogPane().setContent(root);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> dialogResult = dialog.showAndWait();
        }
    }


    public void supprimerCom(ActionEvent actionEvent) {
        Commentaire selectedCommentaire = lvcom.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer ce commentaire ?");

            // Ajouter les boutons OK et Annuler au dialog
            confirmationDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CommentaireService commentaireService = new CommentaireService();
                    try {
                        commentaireService.supprimer(selectedCommentaire.getId_com());
                        lvcom.getItems().remove(selectedCommentaire); // Supprimer le commentaire de la liste affichée
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de la suppression du commentaire: " + e.getMessage());
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun commentaire sélectionné");
            alert.setContentText("Veuillez sélectionner un commentaire à supprimer.");
            alert.showAndWait();
        }
    }

    public void updateCommentaire(Commentaire commentaire) {
        // Refresh the comments view
        CommentaireService commentaireService = new CommentaireService();
        try {
            List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
            afficherCommentaires(commentaires, publicationSelectionnee);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }



    private static class CommentaireCellFactory implements Callback<ListView<Commentaire>, ListCell<Commentaire>> {
        @Override
        public ListCell<Commentaire> call(ListView<Commentaire> param) {
            return new ListCell<Commentaire>() {
                @Override
                protected void updateItem(Commentaire commentaire, boolean empty) {
                    super.updateItem(commentaire, empty);
                    if (empty || commentaire == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Créer une VBox pour contenir le commentaire et la date avec le séparateur
                        VBox vbox = new VBox();
                        vbox.setSpacing(20); // Espace entre chaque élément

                        // Ajouter le contenu du commentaire
                        Separator separator = new Separator();
                        String publicationText ="\nUser: " + commentaire.getUser().getPrenom() + " " + commentaire.getUser().getNom() + "\nContenu: " + commentaire.getContenu() +
                                "\nDate: " + formatDate(commentaire.getDate_com());
                        vbox.getChildren().addAll(new Label(publicationText), separator);

                        // Définir la VBox comme élément de la cellule
                        setGraphic(vbox);
                    }
                }

                private String formatDate(Timestamp timestamp) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    return sdf.format(timestamp);
                }
            };
        }
    }
}
