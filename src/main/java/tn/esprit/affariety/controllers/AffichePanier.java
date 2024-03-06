package tn.esprit.affariety.controllers;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.layout.element.Paragraph;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.*;
import tn.esprit.affariety.models.Panier;
import tn.esprit.affariety.services.PanierService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.affariety.models.Panier;
import tn.esprit.affariety.services.PanierService;


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
            articleLabel.setText("Nom: " + panier.getNom_article() + ", Prix: " + panier.getPrix()  );
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



        // Parcourir les nœuds enfants du conteneur panierItems
        for (Node node : panierItems.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane panierItemPane = (AnchorPane) node;

                // Récupérer les informations sur l'article à partir des étiquettes dans le panneau
                Label nomLabel = (Label) panierItemPane.getChildren().get(0);
                Label prixLabel = (Label) panierItemPane.getChildren().get(1);
                Label quantiteLabel = (Label) panierItemPane.getChildren().get(2);

                String nomArticle = nomLabel.getText();
                float prixArticle = Float.parseFloat(prixLabel.getText());
                int quantiteArticle = Integer.parseInt(quantiteLabel.getText());

                // Calculer le total pour cet article et l'ajouter au total des achats
                float totalArticle = prixArticle * quantiteArticle;
                totalAchat += totalArticle;
            }
        }

        // Mettre à jour l'affichage du total
        totalLabel.setText(String.format("%.2f", totalAchat));

        // Mettre à jour les stocks dans la base de données et vider le panier
        try {
            for (Node node : panierItems.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane panierItemPane = (AnchorPane) node;

                   //  Récupérer les informations sur l'article à partir des étiquettes dans le panneau
                   Label nomLabel = (Label) panierItemPane.getChildren().get(0);
                    Label prixLabel = (Label) panierItemPane.getChildren().get(1);
                    Label quantiteLabel = (Label) panierItemPane.getChildren().get(2);

                    String nomArticle = nomLabel.getText();
                    float prixArticle = Float.parseFloat(prixLabel.getText());
                   int quantiteArticle = Integer.parseInt(quantiteLabel.getText());

                    // Mettre à jour la quantité en stock dans la base de données
                   panierService.updateStock(nomArticle, quantiteArticle);
                }
            }
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
    void handlePDFGeneration(ActionEvent event) {
        try {
            // Créer un nouvel objet PdfWriter pour écrire dans le fichier "panier.pdf"
            PdfWriter writer = new PdfWriter("panier.pdf");

            // Créer un nouvel objet PdfDocument associé au PdfWriter
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Créer un nouvel objet Document associé au PdfDocument
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

            for (Node node : panierItems.getChildren()) {
                if (node instanceof Label) {
                    Label articleLabel = (Label) node;
                    // Analyser le texte du Label pour extraire les informations sur l'article
                    String[] parts = articleLabel.getText().split(", Prix: ");
                    if (parts.length == 2) {
                        String nomArticle = parts[0].substring(5);
                        float prixArticle = Float.parseFloat(parts[1]);

                        // Ajouter les informations sur l'article au document PDF
                        document.add(new Paragraph("Nom: " + nomArticle + ", Prix: " + prixArticle));
                    }
                }
            }

            // Fermer le document
            document.close();

            // Afficher un message de confirmation
            System.out.println("PDF généré avec succès !");
        } catch (IOException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void filtrer(ActionEvent event) {
        // Créer une liste pour stocker les informations sur les articles du panier
        List<AnchorPane> articlesPanier = new ArrayList<>();

        // Ajouter tous les AnchorPane du panierItems à la liste
        for (Node node : panierItems.getChildren()) {
            if (node instanceof AnchorPane) {
                articlesPanier.add((AnchorPane) node);
            }
        }

        // Trier la liste en fonction du prix de chaque article
        Collections.sort(articlesPanier, new Comparator<AnchorPane>() {
            @Override
            public int compare(AnchorPane article1, AnchorPane article2) {
                // Récupérer les prix des articles
                float prix1 = Float.parseFloat(((Label) article1.getChildren().get(0)).getText().substring(5)); // Supprimer "Nom: " du début
                float prix2 = Float.parseFloat(((Label) article2.getChildren().get(0)).getText().substring(5)); // Supprimer "Nom: " du début

                // Comparer les prix
                if (prix1 < prix2) {
                    return -1;
                } else if (prix1 > prix2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        // Mettre à jour l'affichage du panier avec les articles triés
        panierItems.getChildren().clear();
        panierItems.getChildren().addAll(articlesPanier);
    }
    @FXML
    void viderPanier() {
        panierItems.getChildren().clear();
    }
}






