package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.services.ArticleService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficheArticle {

    @FXML
    private VBox articlesContainer;

    private final ArticleService articleService = new ArticleService();

    @FXML
    private void initialize() {
        // Récupérer tous les articles depuis la base de données
        List<Article> articles = articleService.getAllArticles();

        // Pour chaque article, créer une carte et l'ajouter à la VBox
        for (Article article : articles) {
            addCard(article);
        }

    }

    private void addCard(Article article) {
        try {
            // Charger le fichier FXML de la carte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/carte.fxml"));
            AnchorPane card = loader.load();

            // Récupérer le contrôleur de la carte
            Carte carteController = loader.getController();

            // Configurer les données de l'article dans la carte
            carteController.setData(article);

            // Ajouter la carte à la VBox principale
            articlesContainer.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void panier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AffichePanier.fxml"));
        try {
            articlesContainer.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
        }


    }
}
