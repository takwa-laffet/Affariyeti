package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.TicketPaimentService;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class AfficherTicketscontroller  {


   @FXML
        private Text titleText;

        @FXML
        private HBox productBox;
        @FXML
        private TextField id;
        @FXML
        private TextField chercher;
private final TicketPaimentService ticketPaimentService = new TicketPaimentService();


        @FXML
        private void afficherTousLesAuctions() {
            List<Enchere> topSaleAuctions = ticketPaimentService.getEnchereIdsByClientId(Integer.parseInt(id.getText()));
            displayAuctions(topSaleAuctions);
        }

        private void displayAuctions(List<Enchere> auctions) {
            productBox.getChildren().clear(); // Clear the existing items

            for (Enchere enchere : auctions) {
                ImageView imageView = new ImageView(enchere.getImage());
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                enchere.getEnchereId();
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
