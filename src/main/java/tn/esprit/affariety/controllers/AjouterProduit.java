package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.test.HelloApplication;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterProduit {
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;

    @FXML
    void afficherProduit(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AfficherProduit.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    /*@FXML
    void ajouterProduit(ActionEvent event) {
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        Categorie c = new Categorie();
        CategorieService cs = new CategorieService();
        try {
            c=cs.getCategoryById(Integer.parseInt(idTF.getText()));
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
        } catch (SQLException | AddressException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }*/
    @FXML
    void retournerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/AjouterCategorie.fxml"));
            Parent root = loader.load();

            AjouterCategorie ajouterCategorie = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
