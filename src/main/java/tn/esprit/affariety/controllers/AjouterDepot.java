package tn.esprit.affariety.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Depot;
import tn.esprit.affariety.services.DepotService;

public class AjouterDepot {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AVIS;

    @FXML
    private AnchorPane AnchrorPanedepot;

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
    private Label adresse;

    @FXML
    private TextField nomdepot;
    @FXML
    private TextField adressedepot;

    @FXML
    void ajoutdepot(ActionEvent event) {
        String nomDepotText = nomdepot.getText();
        String adresseText = adressedepot.getText(); // Utilisez adressedepot au lieu de adresse

        // Vérifier si le champ du nom du dépôt n'est pas vide
        if (nomDepotText.isEmpty()) {
            showAlert("Champ Vide", "Le nom du dépôt ne peut pas être vide.");
            return;
        }

        // Vérifier si le nom du dépôt contient uniquement des lettres et des espaces
        if (!nomDepotText.matches("^[a-zA-Z\\s]+$")) {
            showAlert("Saisie Incorrecte", "Le nom du dépôt doit contenir uniquement des lettres et des espaces.");
            return;
        }

        // Vérifier si le champ de l'adresse n'est pas vide
        if (adresseText.isEmpty() || adresseText.equals("Adresse")) {
            showAlert("Champ Vide", "L'adresse du dépôt ne peut pas être vide.");
            return;
        }

        try {
            // Créer un objet Depot avec les données fournies
            Depot nouveauDepot = new Depot();
            nouveauDepot.setNomdepot(nomDepotText);
            nouveauDepot.setAdresse(adresseText); // Définissez l'adresse dans l'objet Depot

            // Appeler le service pour ajouter le dépôt
            DepotService depotService = new DepotService();
            depotService.ajouter(nouveauDepot);

            // Afficher une alerte de succès
            showAlert("Succès", "Le dépôt a été ajouté avec succès.");

            // Réinitialiser le champ du nom du dépôt et de l'adresse
            nomdepot.clear();
            adressedepot.clear();

        } catch (SQLException e) {
            // En cas d'erreur SQL, afficher une alerte d'erreur
            showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du dépôt.");
            e.printStackTrace();
        }
    }

    private void showAlert(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    // Méthode pour afficher une alerte


    @FXML
    void initialize() {
       DepotService depotService = new DepotService();
    }
// hedhy takhdem ?



        @FXML
        void navigateToAfficherDepot(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Afficherdepot.fxml"));
                Parent root = fxmlLoader.load();
                // Get the controller associated with the loaded FXML
                Afficherdepot afficherDepotController = fxmlLoader.getController();

                // Set the content of the AnchorPane named "AnchrorPanedepot" to the loaded content
                AnchrorPanedepot.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors du chargement de l'interface Afficherlivraison : " + e.getMessage());
            }
        }


}
