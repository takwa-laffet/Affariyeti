package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.models.Ticket;
import com.example.affariyetii.services.TicketService;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class SearchTicketController {
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TableView<Ticket> ticketTableView; // Assuming you have a TableView to display tickets

    private Enchere enchere;

    public void initData(Enchere enchere) {
        this.enchere = enchere;
    }

    @FXML
    private void searchTickets() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();

        // Call a method in your service to search for tickets based on name and surname
        TicketService ticketService = new TicketService();
        List<Ticket> tickets = ticketService.searchTicketsByNomAndPrenom(nom, prenom);

        // Populate the TableView with the search results
        ticketTableView.getItems().setAll(tickets);
    }
}
