package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;
import com.example.affariyetii.services.TicketPaimentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AcheterTickectcontroller {
    @FXML
    private TextField nomField; // TextField for entering nom

    @FXML
    private TextField prenomField;

    @FXML
    private TextField encherenomTextField;

    private final TicketPaimentService ticketPaimentService = new TicketPaimentService();
private EnchereService enchereService = new EnchereService();
private     Enchere enchere = new Enchere();

    @FXML
    void ajouterTicketPaiment() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String encherenom = encherenomTextField.getText();
        try {
            int clientId = enchereService.getUserIdByNomAndPrenom(nom, prenom);
            int enchereId = enchereService.rechercherIdParNom(encherenom);
            if (enchereId == -1) {
                showAlert("No enchere found for the provided nom.", Alert.AlertType.ERROR);
                return;
            }
            List<Integer> unlinkedTicketIds = ticketPaimentService.getUnlinkedTicketIds();
            if (unlinkedTicketIds.isEmpty()) {
                showAlert("No unlinked tickets found.", Alert.AlertType.ERROR);
                return;
            }
            int ticketId = unlinkedTicketIds.get(0);


            ticketPaimentService.ajouterTicketPaiment(ticketId, clientId, enchereId);

            showAlert("Ticket Payment Added Successfully!", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Please enter valid values for nom prenom and enchere nom.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void openAjouterEnchere(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEnchere.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void openModifierEnchere(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ModifierEnchere.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void openAjouterTickect(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterTickect.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void openAcher(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AcheterTickect.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void openChercherEnchere(ActionEvent event) throws IOException {
        // Load the Chercher Enchere interface
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/ChercherEnchere.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();


        }
    }

}