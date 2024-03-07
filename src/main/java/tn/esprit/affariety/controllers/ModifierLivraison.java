package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Livraison;
import tn.esprit.affariety.test.HelloApplication;

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

    static float latitude;
    static float longitude;

    private Livraison livraisonToModify;

    public void setLivraison(int id, String adresselivraison, LocalDate datelivraison, LocalDateTime dateCommande, String statuslivraison, String adresse,float latitude,float longitude) {
        idlivraison.setText(String.valueOf(id)); // Convert id to string
        this.adresselivraison.setText(adresselivraison);
        this.datelivraison.setValue(datelivraison);
        this.datecommande.setValue(dateCommande.toLocalDate());
        this.status.setValue(statuslivraison);
        this.adresse.setText(adresse);
        this.latitude=latitude;
        this.longitude=longitude;

    }

    public void openmap(ActionEvent actionEvent) throws IOException {
        tn.esprit.affariety.controllers.MapController m=new MapController();
        m.showWindow();
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((HelloApplication.class.getResource("/webvieww.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("localiser la livraison");
        stage.setScene(scene);
        stage.show();
    }

    public void saveCoords(String latitude, String longitude) {
        ModifierLivraison.latitude =Float.parseFloat(latitude);
        ModifierLivraison.longitude =Float.parseFloat(longitude);
        System.out.println("LANG LAT FROM CONTROLLER BORNE"+latitude+"  "+latitude);
    }



    public void savecoords(String latitude, String longitude) {
    }

    public void Modifier(ActionEvent event) {
    }

}
