package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;


import javax.mail.internet.AddressException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProduit {
    @FXML
    private ResourceBundle resources;
    //@FXML
    //private TextField nomTF;

    @FXML
    private ListView<Produit> listView;
    @FXML
    private TextField descriptionTF;



    @FXML
    private TextField nomTF;

    @FXML
    private TextField nom_cTF;

    @FXML
    private TextField prixTF;


    @FXML
    void initialize() {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.recuperer();
            ObservableList<Produit> observableList = FXCollections.observableList(produits);
            listView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérer le client sélectionné
                Produit selectedProduit = listView.getSelectionModel().getSelectedItem();

                // Afficher les informations du client dans les champs de texte
                nomTF.setText(selectedProduit.getNom_p());
                nom_cTF.setText(String.valueOf(selectedProduit.getCategorie().getNom_c()));
                descriptionTF.setText(selectedProduit.getDescription_p());
                prixTF.setText(String.valueOf(selectedProduit.getPrix_p()));
                
            }
        });

    }
    @FXML
    void retournerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ModifierSupprimerProduit.fxml"));
            Parent root = loader.load();

            ModifierSupprimerProduit modifierSupprimerProduit = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    /*void ajouterProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/AjouterProduit.fxml"));
            Parent root = loader.load();

            AjouterProduit ajouterProduit = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    void ajouterProduit(ActionEvent event){
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        Categorie c = new Categorie();
        CategorieService cs = new CategorieService();
        try {
            c=cs.getCategoryByName(nom_cTF.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        produit.setNom_p(nomTF.getText());
        produit.setDescription_p(descriptionTF.getText());
        produit.setPrix_p(Float.parseFloat(prixTF.getText()));
        produit.setCategorie(c);
        try {
            produitService.ajouter(produit);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Produit ajoutée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    public void modifierProduit(ActionEvent actionEvent) {
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        Categorie c = new Categorie();
        CategorieService cs = new CategorieService();
        try {
            c=cs.getCategoryByName(nom_cTF.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //produit.setId_p(Integer.parseInt(idTF.getText()));
        produit.setNom_p(nomTF.getText());
        produit.setCategorie(c);
        produit.setDescription_p(descriptionTF.getText());
        produit.setPrix_p(Float.parseFloat(prixTF.getText()));

        try {
            produitService.modifier(produit);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Produit modifiée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        initialize();
    }
}
