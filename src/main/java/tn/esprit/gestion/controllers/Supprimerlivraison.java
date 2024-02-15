package tn.esprit.gestion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.gestion.services.LivraisonService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Supprimerlivraison {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AVIS;

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
    private Button supprimer;

    @FXML
    private TextField supprimerTF;
    @FXML
    private AnchorPane AnchorPanesupp;

    private LivraisonService livraisonService;

    @FXML
    void initialize() {
        livraisonService = new LivraisonService();
    }

    @FXML
    void supprimer(ActionEvent event) {
        try {
            String inputText = supprimerTF.getText().trim();

            if (inputText.isEmpty()) {
                // Show an alert for empty input
                showAlert("Avertissement", "Veuillez saisir un ID à supprimer.");
                return;
            }

            int id = Integer.parseInt(inputText);

            // Check if the ID exists before attempting to delete
            if (livraisonService.idExists(id)) {
                livraisonService.supprimer(id);
                showAlert("Suppression", "Livraison a été supprimée avec succès.");
            } else {
                // Show an alert if the ID doesn't exist
                showAlert("Avertissement", "L'ID spécifié n'existe pas dans la base de données.");
            }

        } catch (NumberFormatException e) {
            // Show an alert for invalid integer input
            showAlert("SUPPRESSION", "L'ID doit être un entier valide.");
        } catch (SQLException e) {
            // Log the exception to help diagnose the issue
            e.printStackTrace();

            // Show an alert for database-related errors
            showAlert("Erreur", "Erreur lors de la suppression de la livraison : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }


    @FXML
    void retour(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Afficherlivraison.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPanesupp.getChildren().setAll(root);

    }
}