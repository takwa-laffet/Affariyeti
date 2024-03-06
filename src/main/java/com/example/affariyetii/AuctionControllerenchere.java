package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class AuctionControllerenchere {
    @FXML
    private ImageView auctionImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label Datedebut;

    @FXML
    private Label Datefin;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField finalAmountField;

    // Method to initialize the controller
    @FXML
    public void initialize() {
        // Fetch auction data from the database
        EnchereService enchereService = new EnchereService();
        List<Enchere> encheres = enchereService.reuperer();
        double finalAmount = Double.parseDouble(encheres.get(0).getMontantInitial());
        // For simplicity, assume only one auction is retrieved
        if (!encheres.isEmpty()) {
            Enchere enchere = encheres.get(0);

            // Set values to JavaFX components
            nameLabel.setText(enchere.getNom_enchere());
            Datedebut.setText(enchere.getDateDebut());
            Datefin.setText(enchere.getDateFin());
            priceLabel.setText(enchere.getMontantInitial());

            // Set image to ImageView (assuming you have a method to convert image path to Image)
            String imagePath = enchere.getImage(); // Assuming you have a method to get image path from database
            Image image = new Image(imagePath);
            auctionImageView.setImage(image);
        }
    }

    // function to update the final amount of the auction montantinitial intiall finalamount=montant initial
    @FXML
    public void updateFinalAmount() {
        EnchereService enchereService = new EnchereService();
        Enchere encheres = new Enchere();
        String montant = finalAmountField.getText();
        double newFinalAmount;
        if (!montant.isEmpty() && Double.parseDouble(montant) > 100) {
            try {

                newFinalAmount = Double.parseDouble(montant);

                if (newFinalAmount > Double.parseDouble(encheres.getMontantInitial())) {

                    newFinalAmount = Double.parseDouble(encheres.getMontant_final()) + newFinalAmount;
                    encheres.setMontant_final(String.valueOf(newFinalAmount));
                    enchereService.modifierMontantEnchere(encheres.getEnchereId(), newFinalAmount, encheres.getIdclenchere());

                }
                if (montant.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ajouter un montant > 100");
                }
                if (Double.parseDouble(montant) < 100) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("le mnimum est 100");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //ajouter montant 100
    public void ajouter100() {
        EnchereService enchereService = new EnchereService();
        Enchere encheres = new Enchere(); // Initialize encheres properly
        double montant = 100; // Change to double since you're adding to a double value
        double newFinalAmount;
        if (encheres.getMontant_final() != null) { // Check if montant_final is not null
            newFinalAmount = Double.parseDouble(encheres.getMontant_final()) + montant;
            enchereService.modifierMontantEnchere(encheres.getEnchereId(), newFinalAmount, encheres.getIdclenchere());
        }
    }
}
