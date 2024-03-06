package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.utils.MyDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

    public class Carte implements Initializable {

        @FXML
        private Button addButton;

        @FXML
        private AnchorPane cardForm;

        @FXML
        private Label productTypeFLabel;

        @FXML
        private Label productPriceLabel;

        @FXML
        private Label productDescriptionLabel;

        @FXML
        private ImageView productImageView;

        @FXML
        private Spinner<Integer> quantitySpinner;


        private Image image;
        private String imageUrl;


        private Article article;
        private int productId;
        private Connection connection;
        private Alert alert;

        public Carte() {
            connection = MyDatabase.getInstance().getConnection();
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }

        public void setData(Article article) {
            this.article = article;

            productId = article.getId();
            productTypeFLabel.setText(article.getNomArticle());
            productPriceLabel.setText(String.valueOf(article.getPrix()));
            productDescriptionLabel.setText(article.getCategorie());
        }
        @FXML
        public void add(javafx.event.ActionEvent event) {
            if (article == null) {
                // Vérifiez si un article est sélectionné avant d'ajouter au panier
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un article.");
                alert.showAndWait();
                return;
            }

            try {
                String insertData = "INSERT INTO panier (nom_article, prix) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertData);
                preparedStatement.setString(1, article.getNomArticle());
                preparedStatement.setFloat(2, article.getPrix());
                preparedStatement.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Produit ajouté avec succès !");
                alert.showAndWait();
            } catch (SQLException e) {
                // Gérez les exceptions SQL
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de l'ajout du produit au panier.");
                alert.showAndWait();
                e.printStackTrace();
            }
        }


    }





