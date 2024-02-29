package tn.esprit.affarietygui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.File;
import java.sql.SQLException;

public class UserModifPub {
    private String photoPath;
    private UserAffichePub userAffichePub; // Référence vers le contrôleur de UserAffichePub

    @FXML
    private Label cheminphoto;

    @FXML
    private TextField pubTF;
    private Publication publication;
    private final PublicationService publicationService = new PublicationService();

    @FXML
    void ajouter_photo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo");
        // Filtres pour les fichiers image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Enregistrer le chemin de la photo sélectionnée
            photoPath = selectedFile.getAbsolutePath();
            cheminphoto.setText(photoPath);
        }
    }

    @FXML
    void confirmermodification(ActionEvent event) {
        // Récupérer les nouvelles données depuis les champs
        String nouveauContenu = pubTF.getText();
        // Récupérer d'autres champs si nécessaire

        // Mettre à jour la publication avec les nouvelles données
        publication.setContenu(nouveauContenu);

        String nvphoto = cheminphoto.getText();
        publication.setPhoto(nvphoto);
        // Mettre à jour d'autres champs si nécessaire

        // Créer une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de modification");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir modifier cette publication ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // L'utilisateur a cliqué sur OK, procéder à la modification
                try {
                    publicationService.modifier(publication);
                    // Fermer la fenêtre après la modification réussie
                    cheminphoto.getScene().getWindow().hide();

                    // Rafraîchir l'affichage des publications dans UserAffichePub
                    userAffichePub.initialize();
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérer l'exception
                }
            }
        });
    }

    public void initData(Publication publication, UserAffichePub userAffichePub) {
        this.publication = publication;
        this.userAffichePub = userAffichePub; // Initialiser la référence à UserAffichePub
        // Afficher les détails de la publication dans les champs correspondants
        pubTF.setText(publication.getContenu());
        // Afficher le chemin de la photo si nécessaire
        cheminphoto.setText(publication.getPhoto());
        // Autres champs à initialiser si nécessaire
    }
}
