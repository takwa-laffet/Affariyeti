package tn.esprit.gestion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.test.HelloApplication;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModifierLivraison {

    @FXML
    private TextField idlivraison;

    @FXML
    private TextField adresselivraison;

    @FXML
    private DatePicker datecommande;

    @FXML
    private DatePicker datelivraison;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TextField adresse;

    @FXML
    private TextField nomdepot;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    private Livraison livraisonToModify;

    public void setLivraison(int id, String adresselivraison, LocalDate datelivraison, LocalDateTime dateCommande, String statuslivraison, String adresse,float latitude,float longitude) {
        idlivraison.setText(String.valueOf(id)); // Convert id to string
        this.adresselivraison.setText(adresselivraison);
        this.datelivraison.setValue(datelivraison);
        this.datecommande.setValue(dateCommande.toLocalDate());
        this.status.setValue(statuslivraison);
        this.adresse.setText(adresse);
        this.latitude.setText(String.valueOf(latitude));
        this.longitude.setText(String.valueOf(longitude));
    }

    public void saveCoords(String latitude, String longitude) {
        this.latitude.setText(latitude);
        this.longitude.setText(longitude);
        System.out.println("LAT LONG FROM CONTROLLER livraison " + longitude + "  " + latitude);
    }



    public void savecoords(String latitude, String longitude) {
    }

    public void Modifier(ActionEvent event) {
    }
}
