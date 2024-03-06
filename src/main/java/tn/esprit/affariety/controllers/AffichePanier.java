package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.models.Panier;
import tn.esprit.affariety.services.ArticleService;
import tn.esprit.affariety.services.PanierService;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AffichePanier {

    @FXML
    private VBox panierItems;
    @FXML
    private Label totalLabel;

    private final PanierService panierService = new PanierService();

    @FXML
    void initialize() {
        afficherArticlesDuPanier();
    }

    private void afficherArticlesDuPanier() {
        try {
            List<Panier> panierList = panierService.recuperer();
            for (Panier panier : panierList) {
                ajouterArticleAuPanier(panier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur de récupération des données du panier depuis la base de données
        }
        float totalAchat = 0;
        for (Node node : panierItems.getChildren()) {
            if (node instanceof Label) {
                Label articleLabel = (Label) node;
                // Analyser le texte du Label pour extraire le prix de l'article
                String[] parts = articleLabel.getText().split(", Prix: ");
                if (parts.length == 2) { // S'assurer que le Label est au format attendu
                    float prixArticle = Float.parseFloat(parts[1]);
                    // Ajouter le prix de l'article au total des achats
                    totalAchat += prixArticle;
                }
            }
        }

        // Mettre à jour l'affichage du total
        totalLabel.setText(String.format("%.2f", totalAchat));

    }

    private void ajouterArticleAuPanier(Panier panier) {
        Label articleLabel = new Label();
        articleLabel.setText("Nom: " + panier.getNom_article() + ", Prix: " + panier.getPrix());
        panierItems.getChildren().add(articleLabel);
    }

    @FXML
    void validerAchat() {
        // Initialiser le total des achats
        float totalAchat = 0;
        for (Node node : panierItems.getChildren()) {
            if (node instanceof Label) {
                Label articleLabel = (Label) node;
                // Analyser le texte du Label pour extraire le prix de l'article
                String[] parts = articleLabel.getText().split(", Prix: ");
                if (parts.length == 2) { // S'assurer que le Label est au format attendu
                    float prixArticle = Float.parseFloat(parts[1]);
                    // Ajouter le prix de l'article au total des achats
                    totalAchat += prixArticle;
                }
            }
        }

        // Mettre à jour l'affichage du total
        totalLabel.setText(String.format("%.2f", totalAchat));

        // Mettre à jour les stocks dans la base de données et vider le panier
        try {
            for (Node node : panierItems.getChildren()) {
                if (node instanceof Label) {
                    Label articleLabel = (Label) node;
                    // Analyser le texte du Label pour extraire le nom de l'article
                    String[] parts = articleLabel.getText().split(", Prix: ");
                    if (parts.length == 2) { // S'assurer que le Label est au format attendu
                        String nomArticle = parts[0].substring(5); // Supprimer "Nom: " du début

                        // Mettre à jour la quantité en stock dans la base de données
                        panierService.updateStock(nomArticle, 1); // Supposons que chaque article ait une quantité de 1 pour cet exemple
                    }
                }
            }
            // Vider le panier
            panierItems.getChildren().clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation de commande");
            alert.setHeaderText(null);
            alert.setContentText("Votre commande a été validée avec succès!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur de mise à jour de la base de données
        }
    }

    @FXML
    void filtrer(ActionEvent event) {
        // Créer une liste pour stocker les informations sur les articles du panier
        List<Label> articlesPanier = new ArrayList<>();

        // Ajouter tous les Labels du panierItems à la liste
        for (Node node : panierItems.getChildren()) {
            if (node instanceof Label) {
                articlesPanier.add((Label) node);
            }
        }

        // Trier la liste en fonction du prix de chaque article
        Collections.sort(articlesPanier, Comparator.comparingDouble(this::getPrixFromLabel));

        // Mettre à jour l'affichage du panier avec les articles triés
        panierItems.getChildren().clear();
        panierItems.getChildren().addAll(articlesPanier);
    }

    private double getPrixFromLabel(Label label) {
        String[] parts = label.getText().split(", Prix: ");
        if (parts.length == 2) {
            return Double.parseDouble(parts[1]);
        }
        return 0;
    }

    @FXML
    void viderPanier() {
        panierItems.getChildren().clear();
    }
    @FXML
    public void pdf(ActionEvent actionEvent) throws IOException {
        try {
            ArticleService articleService=new ArticleService();
            List<Article> articles = articleService.getAllArticles();
            // Créez un nouveau document PDF
            PDDocument document = new PDDocument();

            // Créez une page dans le document
            PDPage page = new PDPage();
            document.addPage(page);

            // Obtenez le contenu de la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Écrivez du texte dans le document
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            float totalAchat = 0;
            for (Article article : articles) {
                totalAchat += article.getPrix();
            }

            for (Article article : articles) {
                String ligne = "Nom : " + article.getNomArticle() +
                        "     Prix : " + article.getPrix();

                contentStream.showText(ligne);
                contentStream.newLine();
                contentStream.newLineAtOffset(0, -17);


            }
            contentStream.showText("Total des achats : " + totalAchat);
            contentStream.newLine();
            contentStream.endText();

            // Fermez le contenu de la page
            contentStream.close();

            // Enregistrez le document PDF
            String outputPath = "C:/Users/user/Desktop/pidev/pidev.pdf";
            File file = new File(outputPath);
            document.save(file);

            // Fermez le document
            document.close();

            System.out.println("PDF généré avec succès.");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handlePDFGeneration(ActionEvent actionEvent) {
    }
}