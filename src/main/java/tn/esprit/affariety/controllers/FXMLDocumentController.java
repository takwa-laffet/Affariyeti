package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane cardHolder;
    @FXML
    private Button categoryid;
    @FXML
    private AnchorPane idsidebar;
    private VBox labelContainer;
    private VBox articlesContainer;

    ObservableList<CustomerCard> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            /*idsidebar.getChildren().add(labelContainer);

            Button button = new Button("Afficher les labels");
            button.setOnAction(event -> {
                Label additionalLabel = new Label("Nouveau label");
                labelContainer.getChildren().add(additionalLabel);
            });

            labelContainer.getChildren().add(button);*/

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void panier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AffichePanier.fxml"));
        try {
            cardHolder.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
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

    public void listCategories(ActionEvent event) {
        CategorieService categorieService = new CategorieService();
        try {
            List<Categorie> categories = categorieService.recuperer();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Catégories disponibles");
            alert.setHeaderText(null);


            StringBuilder contentText = new StringBuilder();
            for (Categorie categorie : categories) {
                contentText.append(categorie).append("\n");
            }

            alert.setContentText(contentText.toString());
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ProduitUser.fxml"));
            Parent root = loader.load();

            ProduitUser produitUser = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void modifierProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ModifierUser.fxml"));
            Parent root = loader.load();

            ModifierUser modifierUser = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
