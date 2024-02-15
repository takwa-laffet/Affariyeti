package tn.esprit.gestion.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.services.DepotService;
import tn.esprit.gestion.services.LivraisonService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ajouterlivraison {

    public ComboBox<Depot> depotComboBox;
    @FXML
    private AnchorPane GESTIONDELIVRAISON;

    @FXML
    private TextField adresselivraison;

    @FXML
    private TextField datecommande;

    @FXML
    private DatePicker datelivraison;

    @FXML
    private ComboBox<String> statuslivraison;

    @FXML
    private AnchorPane AnchorPaneajout;


    // Your other methods...

    @FXML
    void initialize() {
        // Initialize the ComboBox with the predefined values
        statuslivraison.getItems().addAll("En attente", "En cours", "livree", "Annulee");
        populateDepotComboBox();

        // Set the default date format for the DatePicker
        String pattern = "dd-MM-yyyy";
        datelivraison.setPromptText(pattern.toLowerCase());
        datelivraison.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
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
                // Implement this method if needed
                return null;
            }
        });
    }
    @FXML
    void ajouterLivraison(ActionEvent event) {
        LivraisonService livraisonService = new LivraisonService();
        Livraison livraison = new Livraison();

        // Vérification des champs obligatoires
        if (adresselivraison.getText().isEmpty() || datelivraison.getValue() == null || statuslivraison.getValue() == null || depotComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode si les champs obligatoires ne sont pas remplis
        }

        livraison.setAdresselivraison(adresselivraison.getText());

        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);

        livraison.setDatecommande(Timestamp.valueOf(currentDateTime));

        String dateLivraisonStr = datelivraison.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        livraison.setDatelivraison(Date.valueOf(LocalDate.parse(dateLivraisonStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        livraison.setStatuslivraison(statuslivraison.getValue());

        // Set the selected depot's iddepot
        Depot selectedDepot = depotComboBox.getValue();
        livraison.setIddepot(selectedDepot.getIddepot());

        try {
            livraisonService.ajouter(livraison);

            // Show a success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Livraison ajoutée avec succès.");
            successAlert.showAndWait();

            // Clear the input fields
            adresselivraison.setText("");
            datelivraison.setValue(null);
            statuslivraison.setValue("");

        } catch (SQLException e) {
            // Handle the exception, show an error alert or log the error
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Erreur lors de l'ajout de la livraison : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void toaffiche(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Afficherlivraison.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneajout.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Afficherlivraison : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    public void todepot(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Ajouterdepot.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneajout.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterdepot : " + e.getMessage());
        }
    }
}
