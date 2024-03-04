package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.models.Raiting;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.services.RaitingService;


import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsProduits implements Initializable {

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
    @FXML
    private Rating raitingid;
    private ProduitService produitsService = new ProduitService();

    @FXML

    private int prouitsid;
private  Produit produit;

    public DetailsProduits(int productId) {
        this.prouitsid = productId;
    }
    public void setProduit(int produit) {
        this.prouitsid = produit;
    }
    public int getId(){
        return  this.prouitsid;
    }
    public DetailsProduits() {

    }

    public void initializeDetails() {
        try {
            System.out.println(this.prouitsid);
            Produit produit = produitsService.getProduitById(this.prouitsid);

            if (produit != null) {
                System.out.println(getId());
                Prix.setText(String.valueOf(produit.getPrix_p()));
                File file = new File("C://Users/Lenovo/IdeaProjects/Affariety/" + produit.getImage_p());
                System.out.println(produit.getImage_p());
                String imageUrl = file.toURI().toURL().toString();

                idImage.setImage(new Image(imageUrl));
                idNom.setText(produit.getNom_p());
                idcategory.setText(produit.getCategorie().getNom_c());
                iddescription.setText(produit.getDescription_p());
            } else {
                System.err.println("Produit is null for ID: " + this.prouitsid);
            }


            RaitingService raitingService = new RaitingService();
            double averageRating = raitingService.getmoyenneraiting(this.prouitsid);


            raitingid.setRating( averageRating);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleRatingClick(javafx.scene.input.MouseEvent mouseEvent) {
        double userRating = raitingid.getRating();

        System.out.println(userRating);
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Voulez-vous vraiment soumettre ce rating ?");
        confirmationDialog.setContentText("Votre rating : " + userRating);


        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);


        if (result == ButtonType.OK) {
            try {
                RaitingService raitingService=new RaitingService();
                Raiting userRatingcontrollers = new Raiting();
                userRatingcontrollers.setUser_id(111);
                userRatingcontrollers.setProduct_id(this.prouitsid);
                userRatingcontrollers.setValue((int) userRating);


                raitingService.addRating(userRatingcontrollers);

            } catch (NumberFormatException e) {

                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDetails();
    }


}

