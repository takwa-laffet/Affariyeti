package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.ProduitService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane cardHolder;
    @FXML
    private Button categoryid;
    @FXML
    private AnchorPane idsidebar;
    private VBox labelContainer;

    ObservableList<CustomerCard> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ProduitService getallproduits = new ProduitService();

        try {
            for (int i = 0; i < getallproduits.recuperer().size(); i++) {
                System.out.println(getallproduits.recuperer().get(i).getImage_p());

                list.add(new CustomerCard(getallproduits.recuperer().get(i).getId_p(),
                        getallproduits.recuperer().get(i).getNom_p(),
                        getallproduits.recuperer().get(i).getPrix_p(),
                        getallproduits.recuperer().get(i).getImage_p()));
                System.out.println(list.get(i));
            }

            int count = 0;
            int maxCardsPerRow = 3;

            for (int i = 0; i < getallproduits.recuperer().size(); i += maxCardsPerRow) {
                for (int j = 0; j < maxCardsPerRow && (i + j) < getallproduits.recuperer().size(); j++) {
                    cardHolder.add(list.get(count), j, i / maxCardsPerRow);
                    count++;
                }
            }


            VBox labelContainer = new VBox(10);
            labelContainer.setStyle("-fx-padding: 250 50 0 0;");

            // Ajoutez le labelContainer à idsidebar
            idsidebar.getChildren().add(labelContainer);

            Button button = new Button("Afficher les labels");
            button.setOnAction(event -> {
                Label additionalLabel = new Label("Nouveau label");
                labelContainer.getChildren().add(additionalLabel);
            });

            labelContainer.getChildren().add(button);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onSearch() {
        int count = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                cardHolder.add(list.get(count), j, i);
                count++;
            }
        }
    }
}
