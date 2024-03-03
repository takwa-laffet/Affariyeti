package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.services.ProduitsService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsProduits extends Initializable {

    @FXML
    private Label Prix;
    @FXML
    private ImageView idImage;
    @FXML
    private Label idNom;
    @FXML
    private Label idcategory;
    @FXML
    private Label iddescription;
    @FXML
    private Label label;

    private ProduitService produitsService = new ProduitService();

    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Produit produit = produitsService.getProduitById(produitId);


        Prix.setText(String.valueOf(produit.getPrix_p()));
        idImage.setImage(new Image(produit.getImage_p()));
        idNom.setText(produit.getNom_p());
        idcategory.setText(produit.getCategorie().getNom_c());
        iddescription.setText(produit.getDescription_p());
    }
}
