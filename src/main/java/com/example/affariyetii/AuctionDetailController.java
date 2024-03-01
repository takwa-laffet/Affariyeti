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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SearchTicket.fxml"));
            Parent root = loader.load();

            SearchTicketController controller = loader.getController();
          Enchere enchere = new Enchere();
            controller.initData(enchere); // Pass auction details to the search ticket controller

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open search ticket interface.");
        }
    }

    private void showAlert(String error, String s) {
    }
    @FXML
    private void deleteAction() {
        Enchere enchere = new Enchere(); // Remove this line
        EnchereService enchereService = new EnchereService();
        enchereService.supprimer(enchere.getEnchereId()); // Pass the correct ID to delete
    }

}
