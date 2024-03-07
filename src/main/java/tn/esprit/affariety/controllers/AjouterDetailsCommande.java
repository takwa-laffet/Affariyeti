package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.affariety.models.DetailsCommande;
import tn.esprit.affariety.services.DetailsCommandeService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterDetailsCommande {
    @FXML
    private ComboBox<Integer> id_cmdTF;

    @FXML
    private TextField nom_articleTF;

    @FXML
    private TextField num_articleTF;

    @FXML
    private TextField prix_unitaireTF;

    @FXML
    private TextField quantiteTF;

    @FXML
    private TextField sous_totalTF;
@FXML
    void afficherDetailsCommande(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AfficherDetailsCommande.fxml"));
        try {
            nom_articleTF.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
        }



    }

    @FXML
    void ajouterDetailsCommande(ActionEvent event) {
        // Vérifier si les champs de saisie sont vides
        if (num_articleTF.getText().isEmpty() || nom_articleTF.getText().isEmpty() ||
                quantiteTF.getText().isEmpty() || prix_unitaireTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Vérifier si le champ num_articleTF contient un nombre négatif
        try {
            int numArticle = Integer.parseInt(num_articleTF.getText());
            if (numArticle < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un numéro d'article valide (positif).");
            alert.showAndWait();
            return;
        }

        // Maintenant, récupérer les valeurs des champs après la validation
        int numArticle = Integer.parseInt(num_articleTF.getText());
        String nomArticle = nom_articleTF.getText();
        int quantite = Integer.parseInt(quantiteTF.getText());
        float prixUnitaire = Float.parseFloat(prix_unitaireTF.getText());

        // Calculer le sous-total
        float sousTotal = quantite * prixUnitaire;
        sous_totalTF.setText(String.valueOf(sousTotal));

        // Ajouter les détails de commande seulement si les champs de saisie sont valides et remplis
        DetailsCommandeService detailsCommandeService = new DetailsCommandeService();
        DetailsCommande dc = new DetailsCommande();
        dc.setNum_article(numArticle);
        dc.setNom_article(nomArticle);
        dc.setQuantite(quantite);
        dc.setPrix_unitaire(prixUnitaire);
        dc.setSous_totale(sousTotal);
        try {
            detailsCommandeService.ajouter(dc);
            num_articleTF.setText("");
            nom_articleTF.setText("");
            quantiteTF.setText("");
            prix_unitaireTF.setText("");
            sous_totalTF.setText("");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Détails de commande ajoutés avec succès.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de l'ajout des détails de commande : " + e.getMessage());
            alert.showAndWait();
        }
    }


    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    // Méthode pour vider les champs après l'ajout
    private void viderChamps() {
        num_articleTF.clear();
        nom_articleTF.clear();
        quantiteTF.clear();
        prix_unitaireTF.clear();
        sous_totalTF.clear();
    }
    public void initialize() {
        // Remplir le ComboBox avec les IDs de commande lors de l'initialisation
        try {
         List<Integer> idsCommande = new DetailsCommandeService().recupererIdsCommande();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(idsCommande);
        id_cmdTF.setItems(observableList);
        } catch (SQLException e) {
          e.printStackTrace(); // Gérer les erreurs
        }
        }
    }




