package tn.esprit.affarietygui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.PublicationService;
import tn.esprit.affarietygui.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Ajouterpub {
    @FXML
    private Label cheminphoto;
    @FXML
    private TextField idclientTF;

    @FXML
    private TextField pubTF;
    private String photoPath;


    @FXML
    void afficher_pub(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affarietygui/UserAffichePub.fxml"));
        try {
            pubTF.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

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
        }}

    @FXML
    void ajouter_pub(ActionEvent event) {
        // Vérifier si les champs sont vides
        if (idclientTF.getText().isEmpty() || pubTF.getText().isEmpty() || cheminphoto.getText().isEmpty()) {
            System.err.println("Veuillez remplir tous les champs.");
            return; // Sortir de la méthode si un champ est vide
        }

        // Récupérer les valeurs depuis les champs de l'interface utilisateur
        int idClient = Integer.parseInt(idclientTF.getText());
        String contenu = pubTF.getText();
        String cheminPhoto = cheminphoto.getText(); // Assurez-vous que la label cheminphoto contient le chemin de la photo sélectionnée

        // Créer une instance de Publication avec les valeurs récupérées
        Publication nouvellePublication = new Publication();
        nouvellePublication.setId_client(idClient);
        nouvellePublication.setContenu(contenu);
        nouvellePublication.setPhoto(cheminPhoto);
        nouvellePublication.setNb_likes(0); // Mettre nb_likes à 0
        nouvellePublication.setNb_dislike(0); // Mettre nb_dislike à 0

        // Créer une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de publication");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir publier cette publication ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Utiliser PublicationService pour ajouter la nouvelle publication à la base de données
                PublicationService publicationService = new PublicationService();
                try {
                    publicationService.ajouter(nouvellePublication);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Publié");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La publication a été ajoutée avec succès !");
                    successAlert.showAndWait();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de l'ajout de la publication : " + e.getMessage());
                }
            }
        });}
}



















/*
package tn.esprit.affarietygui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affarietygui.models.Publication_Client;
import tn.esprit.affarietygui.services.PublicationClientService;
import tn.esprit.affarietygui.models.Reaction;
import tn.esprit.affarietygui.services.ReactionService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Ajouterpub {
    @FXML
    private Label cheminphoto;
    @FXML
    private TextField idclientTF;

    @FXML
    private TextField pubTF;
    private String photoPath;


    @FXML
    void afficher_pub(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/PublicationClient.fxml"));
        try {
            pubTF.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

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
    void ajouter_pub(ActionEvent event) {
        // Récupérer les valeurs depuis les champs de l'interface utilisateur
        int idClient = Integer.parseInt(idclientTF.getText());
        String contenu = pubTF.getText();
        String cheminPhoto = cheminphoto.getText(); // Assurez-vous que la label cheminphoto contient le chemin de la photo sélectionnée

        // Créer une instance de Publication_Client avec les valeurs récupérées
        Publication_Client nouvellePublication = new Publication_Client();
        nouvellePublication.setId_client(idClient);
        nouvellePublication.setContenu(contenu);
        nouvellePublication.setPhoto(cheminPhoto);

        // Utiliser PublicationClientService pour ajouter la nouvelle publication à la base de données
        PublicationClientService publicationService = new PublicationClientService();
        ReactionService reactionService = new ReactionService();
        try {
            // Ajouter la nouvelle publication
            int idPub = publicationService.ajouter(nouvellePublication);
            System.out.println("Publication ajoutée avec succès !");

            // Récupérer l'ID du client associé à la publication
            int idclient = nouvellePublication.getId_client();
            System.out.println(idclient);
            // Maintenant, créer une réaction avec l'ID de la publication et du client
            Reaction nouvelleReaction = new Reaction();
            nouvelleReaction.setId_pub(idPub);
            nouvelleReaction.setId_client(idclient);
            nouvelleReaction.setNb_like(0);
            nouvelleReaction.setNb_dislike(0);
            reactionService.ajouter(nouvelleReaction);
            System.out.println("Réaction ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la publication : " + e.getMessage());
        }
    }
}
*/