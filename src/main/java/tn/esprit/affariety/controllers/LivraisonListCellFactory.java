package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.affariety.models.Livraison;

import java.io.IOException;

public class LivraisonListCellFactory extends ListCell<Livraison> {

    @FXML
    private HBox root;

    @FXML
    private VBox infoBox;

    @FXML
    private ImageView qrCodeImage;

    @FXML
    private Button detailsButton;



    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/controllers/LivraisonListCellFactory.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void updateItem(Livraison item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Populate your labels and other information
            // ...

            // Set the QR code image
            // ...

            // Set up the details button
            detailsButton.setOnAction(event -> {
                // Implement your action when the button is clicked
                // For example, open a new window, show more details, etc.
                System.out.println("Details button clicked for Livraison ID: " + item.getId());
            });

            setGraphic(root);
        }
    }
}
