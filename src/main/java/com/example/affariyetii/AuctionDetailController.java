package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AuctionDetailController {
    @FXML
    private ImageView auctionImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label Datedebut;
    @FXML
    private Label Datefin;

    public void initialize(Enchere enchere) {
        auctionImageView.setImage(new Image(enchere.getImage()));
        nameLabel.setText(enchere.getNom_enchere());
        Datedebut.setText("Date debut: " + enchere.getDateDebut());
        Datefin.setText("Date fin: " + enchere.getDateFin());
        priceLabel.setText("Montant initial: " + enchere.getMontantInitial() + " dt");
    }

    @FXML
    private void participateAction() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/SearchTicket.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();


        }
    }

    private void showAlert(String error, String s) {
    }
    @FXML
    private void deleteAction() {
        EnchereService enchereService = new EnchereService();
        List<Enchere> allEncheres = enchereService.reuperer(); // Retrieve all auctions
        List<String> ticketpEnchereIds = enchereService.getTicketpEnchereIds(); // Retrieve IDs of auctions in ticketp

        for (Enchere enchere : allEncheres) {
            String enchereId = String.valueOf(enchere.getEnchereId());
            if (!ticketpEnchereIds.contains(enchereId)) { // If the auction ID is not in ticketp
                enchereService.supprimer(Integer.parseInt(enchereId)); // Delete the auction
            }
        }

    }
}