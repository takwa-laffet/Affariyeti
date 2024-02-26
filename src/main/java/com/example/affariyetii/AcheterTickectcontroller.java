package com.example.affariyetii;

import com.example.affariyetii.models.TicketPaiment;
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

public class AcheterTickectcontroller {

    @FXML
    private TextField ticketIdTextField;

    @FXML
    private TextField clientIdTextField;

    @FXML
    private TextField enchereIdTextField;

    private final TicketPaimentService ticketPaimentService = new TicketPaimentService();

    @FXML
    void ajouterTicketPaiment() {
        try {
            int ticketId = Integer.parseInt(ticketIdTextField.getText());
            int clientId = Integer.parseInt(clientIdTextField.getText());
            int enchereId = Integer.parseInt(enchereIdTextField.getText());

            TicketPaiment ticketPaiment = new TicketPaiment(ticketId, clientId, enchereId);
            ticketPaimentService.ajouter(ticketPaiment);

            showAlert("Ticket Paiment Added Successfully!", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Please enter valid numeric values for Ticket ID, Client ID, and Enchere ID.", Alert.AlertType.ERROR);
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
    void openTicket(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherTicketclient.fxml"));
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