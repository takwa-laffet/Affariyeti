package tn.esprit.gestion.controllers;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tn.esprit.gestion.models.Depot;

public class DepotListCell extends ListCell<Depot> {

    @Override
    protected void updateItem(Depot depot, boolean empty) {
        super.updateItem(depot, empty);

        if (empty || depot == null) {
            setText(null);
            setGraphic(null);
            setBackground(null);
        } else {
            // Create an ImageView and set its properties
            ImageView imageView = new ImageView(new Image("/patron.png"));
            imageView.setFitWidth(50); // Set the width of the image
            imageView.setFitHeight(50); // Set the height of the image

            // Create a VBox to hold both the image and the text
            VBox vBox = new VBox(5); // 5 is the spacing between image and text
            vBox.getChildren().addAll(imageView, createText(depot));

            // Set the VBox as the graphic for the cell
            setGraphic(vBox);
            setStyle("-fx-padding: 10;");
            setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        }
    }

    private Node createText(Depot depot) {
        return new Text("Nom: " + depot.getNomdepot() + "\nAdresse: " + depot.getAdresse());
    }
}
