package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.affariety.models.Depot;
import tn.esprit.affariety.models.Livraison;
import tn.esprit.affariety.services.DepotService;
import tn.esprit.affariety.services.LivraisonService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


import static javafx.fxml.FXMLLoader.load;

public class Ajouterlivraison {


    @FXML
    private AnchorPane GESTIONDELIVRAISON;

    @FXML
    private TextField adresselivraison;

    static float latitude;
    static float longitude;

    @FXML
    private DatePicker datelivraison;

    @FXML
    private ComboBox<String> statuslivraison;

    @FXML
    private AnchorPane AnchorPaneajout;

    @FXML
    private ComboBox<Depot> depotComboBox;


    private LivraisonService livraisonService = new LivraisonService();


    @FXML
    void initialize() {
        statuslivraison.getItems().addAll("En attente", "En cours", "Livree", "Annulee");
        populateDepotComboBox();

        String pattern = "dd-MM-yyyy";
        datelivraison.setPromptText(pattern.toLowerCase());
        datelivraison.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
    }


    private void populateDepotComboBox() {
        DepotService depotService = new DepotService();
        ObservableList<Depot> depots = FXCollections.observableArrayList();

        try {
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
                return (depot != null) ? depot.getNomdepot() : "";
            }

            @Override
            public Depot fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void ajouterLivraison(ActionEvent event) throws SQLException {
        if(latitude==0||longitude==0)
        {
            showAlert("Erreur","Tu dois selectionner la localisation avec le carte.");
            return;
        }
        // Validation of input fields
        if (adresselivraison.getText().isEmpty() || datelivraison.getValue() == null ||
                statuslivraison.getValue() == null || depotComboBox.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        Livraison livraison = new Livraison();


        Depot selectedDepot = depotComboBox.getValue();
        livraison.setIddepot(selectedDepot.getIddepot());

        Livraison liv = new Livraison(adresselivraison.getText(), Timestamp.valueOf(LocalDateTime.now()),
                Date.valueOf(datelivraison.getValue()), statuslivraison.getValue().toString(), selectedDepot.getIddepot(), latitude, longitude);
        //livraisonService.ajouter(liv);
        // Set longitude and latitude


        try {
            livraisonService.ajouter(liv);
            showAlert("Succès", "Livraison ajoutée avec succès.");
            String number = "25377666";
            String message = "Votre livraison  " +statuslivraison.getValue().toString() + " est effectuée le "+ datelivraison.getValue().toString();
            SmsSender.sendSms(number, message);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de l'ajout de la livraison : " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);

        // Ajouter un bouton personnalisé
        ButtonType voirListeButton = new ButtonType("Voir la liste", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().add(voirListeButton);

        Optional<ButtonType> result = alert.showAndWait();

        // Vérifier si l'utilisateur a cliqué sur le bouton "Voir la liste"
        if (result.isPresent() && result.get() == voirListeButton) {
            // Naviguer vers AfficherLivraison.fxml ici
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Afficherlivraison.fxml"));
                Parent root = fxmlLoader.load();
                // Vous pouvez récupérer le contrôleur de la vue ici si nécessaire
                // AfficherLivraisonController controller = fxmlLoader.getController();
                // Faire des opérations supplémentaires si nécessaire

                // Créer la scène et afficher la nouvelle fenêtre
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la navigation vers Afficherlivraison.fxml : " + e.getMessage());
            }
        }
    }


    private void clearFields() {
        adresselivraison.clear();
        datelivraison.setValue(null);
        statuslivraison.getSelectionModel().clearSelection();
        depotComboBox.getSelectionModel().clearSelection();

    }

    @FXML
    public void toaffiche(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/Afficherlivraison.fxml"));
            Parent root = loader.load();

            Afficherlivraison ajouterlivraison = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Afficherlivraison : " + e.getMessage());
        }
    }

    @FXML
    public void todepot(ActionEvent event) {
        try {
            Parent root = load(getClass().getResource("/tn/esprit/gestion/Ajouterdepot.fxml"));
            AnchorPaneajout.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterdepot : " + e.getMessage());
        }
    }

    public void showWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL resourceUrl = getClass().getClassLoader().getResource("tn/esprit/gestion/webview.fxml");
            if (resourceUrl == null) {
                throw new IOException("FXML file not found");
            }
            fxmlLoader.setLocation(resourceUrl);
            fxmlLoader.setController(this); // Set the controller
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void showWarningAlert(String warning, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(warning);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSucessAlert(String success, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(success);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void openmap(ActionEvent actionEvent) throws IOException {
        tn.esprit.affariety.controllers.MapController m=new MapController();
        m.showWindow();
        String latitude = "latitude";
        String longitude = "longitude";


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/webvieww.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savecoords(String latitude, String longitude) {

        Ajouterlivraison.latitude =Float.parseFloat(latitude);
        Ajouterlivraison.longitude =Float.parseFloat(longitude);
        System.out.println("LANG LAT FROM CONTROLLER Livraison"+latitude+"  "+latitude);
    }




}

