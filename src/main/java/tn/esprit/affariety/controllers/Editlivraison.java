package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.affariety.models.Depot;
import tn.esprit.affariety.models.Livraison;
import tn.esprit.affariety.services.DepotService;
import tn.esprit.affariety.services.LivraisonService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Editlivraison {
    public static int iddepot_static;
    public static String statuslivraison_static;
    public static Date datelivraison_static;
    public static Timestamp datecommande_static;
    public static String adresselivraison_static;
    @FXML
    private TextField adresselivraison;

    @FXML
    private DatePicker datelivraison;

    @FXML
    private ComboBox<String> statuslivraison;

    @FXML
    private ComboBox<Depot> depotComboBox;
    static float latitude;
    static float longitude;

    public static int id;
    private LivraisonService livraisonService = new LivraisonService(); // Initialize livraisonService
    private Livraison livraison;
    private Depot depot;
    public void initData(Livraison livraison) {
        this.livraison = livraison;
        adresselivraison.setText(livraison.getAdresselivraison());
        datelivraison.setValue(livraison.getDatecommande().toLocalDateTime().toLocalDate());
        statuslivraison.getItems().addAll("En attente", "En cours", "livree", "Annulee");
        statuslivraison.setValue(livraison.getStatuslivraison());


        // Fetch the depots and populate the ComboBox
        populateDepotComboBox();

        // Select the appropriate depot based on its ID
        int iddepot = livraison.getIddepot();
        selectDepotById(iddepot);
    }

    private void selectDepotById(int depotId) {
        for (Depot depot : depotComboBox.getItems()) {
            if (depot.getIddepot() == depotId) {
                depotComboBox.getSelectionModel().select(depot);
                break;
            }
        }
    }

    private void populateDepotComboBox() {
        DepotService depotService = new DepotService();
        ObservableList<Depot> depots = FXCollections.observableArrayList();

        try {
            // Fetch the depots from your data source
            List<Depot> retrievedDepots = depotService.recuperer();
            depots.addAll(retrievedDepots);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la récupération des dépôts : " + e.getMessage());
        }

        depotComboBox.setItems(depots);
        depotComboBox.setConverter(new StringConverter<Depot>() {
            @Override
            public String toString(Depot depot) {
                // Convert Depot object to display its name
                return depot != null ? depot.getNomdepot() : "";
            }

            @Override
            public Depot fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void modifier(ActionEvent event) {
        try {
            // Update the Livraison object with the new values from UI components

            livraison.setAdresselivraison(adresselivraison.getText());
            livraison.setDatelivraison(Date.valueOf(datelivraison.getValue()));
            livraison.setStatuslivraison(statuslivraison.getValue());
            livraison.setIddepot(depotComboBox.getSelectionModel().getSelectedItem().getIddepot());
            livraison.setLatitude(Editlivraison.latitude);
            livraison.setLongitude(Editlivraison.longitude);

           //  Call the modifier method from LivraisonService to update the Livraison
            livraisonService.modifier(livraison);
            Livraison livraison = new Livraison();


            // Show a success message

            showAlert("Modification", "Livraison modifiée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la modification de la livraison : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void saveCoords(String latitude, String longitude) {
        Editlivraison.latitude =Float.parseFloat(latitude);
        Editlivraison.longitude =Float.parseFloat(longitude);
        System.out.println("LANG LAT FROM CONTROLLER BORNE"+latitude+"  "+latitude);
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
}