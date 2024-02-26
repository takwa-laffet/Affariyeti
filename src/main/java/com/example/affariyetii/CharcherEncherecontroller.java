package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CharcherEncherecontroller implements Initializable {
    @FXML
    private Text titleText;

    @FXML
    private HBox productBox;

    @FXML
    private TextField chercher;

    private final EnchereService enchereService = new EnchereService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherTousLesAuctions();
    }

    private void afficherTousLesAuctions() {
        List<Enchere> topSaleAuctions = enchereService.getTopSaleAuctions();
        displayAuctions(topSaleAuctions);
    }

    private void displayAuctions(List<Enchere> auctions) {
        productBox.getChildren().clear(); // Clear the existing items

        for (Enchere enchere : auctions) {
            ImageView imageView = new ImageView(enchere.getImage());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Text itemNameText = new Text(enchere.getNom_enchere());
            itemNameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            Text itemPriceText = new Text("Montant initial: " + enchere.getMontantInitial() + " dt");

            VBox textContainer = new VBox(itemNameText, itemPriceText);
            textContainer.setAlignment(Pos.CENTER);

            VBox contentContainer = new VBox(imageView, textContainer);
            contentContainer.setAlignment(Pos.CENTER);

            // Add zoom effect on mouse hover
            contentContainer.setOnMouseEntered(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), contentContainer);
                scaleTransition.setToX(1.2);
                scaleTransition.setToY(1.2);
                scaleTransition.play();
            });

            // Remove zoom effect on mouse exit
            contentContainer.setOnMouseExited(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), contentContainer);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                scaleTransition.play();
            });

            imageView.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnchereDetailView.fxml"));
                    BorderPane root = loader.load();

                    AuctionDetailController controller = loader.getController();
                    controller.initialize(enchere);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to open auction detail view.");
                }
            });

            productBox.getChildren().add(contentContainer);
        }
    }

    private void showAlert(String error, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void chercher(ActionEvent actionEvent) {
        String name = chercher.getText();
        List<Enchere> topSaleAuctions;

        if (name.isEmpty()) {
            topSaleAuctions = enchereService.getTopSaleAuctions();
        } else {
            topSaleAuctions = enchereService.chercher(name);
        }

        displayAuctions(topSaleAuctions);
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
}
