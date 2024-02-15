package tn.esprit.gestion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.services.DepotService;
import tn.esprit.gestion.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ModifyDepot {

    @FXML
    private TextField iddepot;

    @FXML
    private TextField nomdepot;

    @FXML
    private TextField adressedepot;

    @FXML
    private Button modifyButton;

    private DepotService depotService;

    private int depotId;

    public void initData(Depot depot) {
        iddepot.setText(String.valueOf(depot.getIddepot()));
        depotId = depot.getIddepot();
        nomdepot.setText(depot.getNomdepot());
        adressedepot.setText(depot.getAdresse());
    }

    @FXML
    private void initialize() {
        depotService = new DepotService();
        modifyButton.setOnAction(event -> modifyDepot());
    }

    @FXML
    private void modifyDepot() {
        try {
            // Contrôles de saisie
            if (nomdepot.getText().isEmpty() || adressedepot.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            // Récupération des valeurs modifiées
            String newNomDepot = nomdepot.getText();
            String newAdresseDepot = adressedepot.getText();

            // Création de l'objet Depot modifié
            Depot modifiedDepot = new Depot();
            modifiedDepot.setIddepot(depotId);
            modifiedDepot.setNomdepot(newNomDepot);
            modifiedDepot.setAdresse(newAdresseDepot);

            // Appel à la méthode de modification du service
            depotService.modifier(modifiedDepot);

            // Affichage d'une alerte de succès
            showAlert("Succès", "Le dépôt a été modifié avec succès.");

            // Fermez la fenêtre ModifyDepot ou gérez comme nécessaire
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez ou enregistrez l'exception
            showAlert("Erreur", "Erreur lors de la modification du dépôt : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void ToAffiche(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AfficheDepot.fxml"));
        try {
            nomdepot.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }}
