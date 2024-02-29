package tn.esprit.affarietygui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affarietygui.models.Publication;
import javafx.scene.control.Alert;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.File;
import java.sql.SQLException;

public class ModifierPub {

    @FXML
    private Label cheminphoto;

    @FXML
    private TextField id_client;

    @FXML
    private TextField pubTF;
    private String photoPath;
private Publication publication;
    public void setPublication(Publication publication) {
        this.publication = publication;
        // Remplir les champs textuels avec les valeurs actuelles de la publication
        id_client.setText(String.valueOf(publication.getId_client()));
        pubTF.setText(publication.getContenu());
        cheminphoto.setText(publication.getPhoto());}
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
            photoPath= selectedFile.getAbsolutePath();
            cheminphoto.setText(photoPath);
        }
    }


    @FXML
    void confirmermodification(ActionEvent event) {
        if (publication == null) {
            afficherAlerte("Aucune publication sélectionnée", "Veuillez sélectionner une publication à modifier.");
            return;
        }

        // Récupérer les nouvelles valeurs depuis les champs textuels
        int nvidclient = Integer.parseInt(id_client.getText());
        String nvcontenue = pubTF.getText();
        String path = cheminphoto.getText();

        // Mettre à jour les attributs de la commande sélectionnée
        publication.setId_client(nvidclient);
        publication.setContenu(nvcontenue);
        publication.setPhoto(path);

        // Appeler le service pour modifier la commande dans la base de données
        PublicationService publicationService = new PublicationService();
        try {
            publicationService.modifier(publication);
            // Afficher une confirmation
            afficherAlerte("Publication modifiée", "La publication a été modifiée avec succès.");

            // Update the ListView
            Stage stage = (Stage) pubTF.getScene().getWindow();
            AfficherPub afficherPubController = (AfficherPub) stage.getUserData();
            afficherPubController.initialize(); // Reload the data
            pubTF.getScene().getWindow().hide();
        } catch (Exception e) {
            // En cas d'erreur lors de la modification
            afficherAlerte("Erreur lors de la modification", "Une erreur s'est produite lors de la modification de la commande : " + e.getMessage());
        }
    }
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void initData(int publicationId) {

        PublicationService publicationService = new PublicationService();
        try {
            // Récupérer la commande à partir de son identifiant
            Publication publication = publicationService.getPublicationById(publicationId);
            // Vérifier si la commande existe
            if (publication != null) {
                // Définir la commande dans le contrôleur
                setPublication(publication);
            } else {
                // Afficher une alerte si la commande n'est pas trouvée
                afficherAlerte("Publication introuvable", "La publication avec l'ID " + publicationId + " n'existe pas.");
            }
        } catch (SQLException e) {
            // En cas d'erreur lors de la récupération de la commande
            afficherAlerte("Erreur de récupération", "Une erreur s'est produite lors de la récupération de la publication : " + e.getMessage());
        }
    }

}
