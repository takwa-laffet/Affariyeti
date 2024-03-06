package tn.esprit.gestion;
import tn.esprit.gestion.models.Enchere;
import tn.esprit.gestion.services.EnchereService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class AuctionControllerclientcontroller {
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

    // Method to initialize the controller
    @FXML
    public void initialize() {
        // Fetch auction data from the database
        EnchereService enchereService = new EnchereService();
        List<Enchere> encheres = enchereService.reuperer();

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
}

