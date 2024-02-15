package tn.esprit.gestion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.services.LivraisonService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

    private Livraison livraisonToModify;

    public void setLivraison(int id, String adresselivraison, LocalDate datelivraison, LocalDateTime dateCommande, String statuslivraison, String adresse) {
        idlivraison.setText(String.valueOf(id)); // Convert id to string
        this.adresselivraison.setText(adresselivraison);
        this.datelivraison.setValue(datelivraison);
        this.datecommande.setValue(dateCommande.toLocalDate());
        this.status.setValue(statuslivraison);
        this.adresse.setText(adresse);
    }


}

