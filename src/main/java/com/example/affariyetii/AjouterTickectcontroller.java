package com.example.affariyetii;

import com.example.affariyetii.models.Ticket;
import com.example.affariyetii.services.EnchereService;
import com.example.affariyetii.services.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterTickectcontroller {

    @FXML
    private TextField nomEnchereTextField;

    @FXML
    private TextField nombreTicketsTextField;

    @FXML
    public void ajouterTickets() {
        String nomEnchere = nomEnchereTextField.getText();
        int nombreTickets = Integer.parseInt(nombreTicketsTextField.getText());

        // Retrieve the Enchere ID based on the Enchere name
        EnchereService enchereService = new EnchereService();
        int enchereId = enchereService.rechercherIdParNom(nomEnchere);

        // Create TicketService instance to add tickets
        TicketService ticketService = new TicketService();

        // Add the specified number of tickets for the given Enchere ID
        for (int i = 0; i < nombreTickets; i++) {
            Ticket ticket = new Ticket();
            ticket.setEnchereId(enchereId);
            ticket.setPrix(10);
            // You may set other properties of the ticket if needed
            ticketService.ajouter(ticket);
        }

        System.out.println(nombreTickets + " tickets added successfully for the auction: " + nomEnchere);
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
