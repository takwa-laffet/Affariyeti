package com.example.affariyetii;

import com.example.affariyetii.models.TicketPaiment;
import com.example.affariyetii.services.TicketPaimentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AfficherTicketscontroller{

    private TicketPaimentService ticketPaimentService;

    public AfficherTicketscontroller(TicketPaimentService ticketPaimentService) {
        this.ticketPaimentService = ticketPaimentService;
    }

    @FXML
    private TextField nomEnchereTextField;

    @FXML
    private TextField clientIdTextField;

    @FXML
    private Label resultLabel;

    @FXML
    private void afficherTicketPaiment() {
        String nomEnchere = nomEnchereTextField.getText();
        int clientId = Integer.parseInt(clientIdTextField.getText());

        // Call the service method to retrieve tickets based on the provided parameters
        try {
            ticketPaimentService.reuperer(nomEnchere, clientId);
            // Display the result in the label or handle it as needed
            resultLabel.setText("Tickets fetched successfully.");
        } catch (Exception e) {
            resultLabel.setText("Error fetching tickets: " + e.getMessage());
        }
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
