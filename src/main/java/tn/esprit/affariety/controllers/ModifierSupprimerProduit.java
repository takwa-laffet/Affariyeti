package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierSupprimerProduit {
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField idTF;

    @FXML
    private TextField nom_cTF;


    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;

    @FXML
    void modifierProduit(ActionEvent event) {
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        Categorie c = new Categorie();
        CategorieService cs = new CategorieService();
        try {
            c=cs.getCategoryById(Integer.parseInt(nom_cTF.getText()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        produit.setId_p(Integer.parseInt(idTF.getText()));
        produit.setNom_p(nomTF.getText());
        produit.setDescription_p(descriptionTF.getText());
        produit.setPrix_p(Float.parseFloat(prixTF.getText()));
        produit.setCategorie(c);
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

    }

    @FXML
    void supprimerProduit(ActionEvent event) {
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        produit.setId_p(Integer.parseInt(idTF.getText()));
        try {
            produitService.supprimer(produit.getNom_p());
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Produit supprimé");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }
    @FXML

    public void trouverProduit(ActionEvent actionEvent) {
        int idProduit = Integer.parseInt(idTF.getText());
        //String nomProduit = nomTF.getText();
        ProduitService produitService = new ProduitService();
        try {
            Produit produitTrouve = produitService.getProduitById(idProduit);
            //Produit produitTrouve = produitService.getProduitByName(nomProduit);


            if (produitTrouve != null) {
                System.out.println("Produit trouvé : " + produitTrouve);
                idTF.setText(String.valueOf(produitTrouve.getId_p()));
                nom_cTF.setText(String.valueOf(produitTrouve.getCategorie().getNom_c()));
                nomTF.setText(produitTrouve.getNom_p());
                descriptionTF.setText(produitTrouve.getDescription_p());
                prixTF.setText(String.valueOf(produitTrouve.getPrix_p()));
            } else {
                System.out.println("Aucun produit trouvé avec le nom : " + produitTrouve);
            }
        } catch (SQLException e) {
            // Gérer l'exception SQLException
            e.printStackTrace();
        }
    }


    /*@FXML
     void trouverProduit(ActionEvent event) {
        try {
            int idProduit = Integer.parseInt(idTF.getText());
            ProduitService produitService = new ProduitService();
            Produit produitTrouve = produitService.getProduitById(idProduit);

            if (produitTrouve != null) {
                System.out.println("Produit trouveé : " + produitTrouve.getNom_p());
                nomTF.setText(produitTrouve.getNom_p());
                descriptionTF.setText(produitTrouve.getDescription_p());
            } else {
                System.out.println("Aucun produit trouvé avec l'ID : " + idProduit);
            }
        } catch  (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }*/
    @FXML
    void afficherProduit(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AfficherProduit.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
