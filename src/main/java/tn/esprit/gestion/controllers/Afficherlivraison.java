package tn.esprit.gestion.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.services.DepotService;
import tn.esprit.gestion.services.LivraisonService;

public class Afficherlivraison {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchtext;

    @FXML
    private Button AVIS;

    @FXML
    private AnchorPane AnchorPaneaff;

    @FXML
    private Button COMMANDES;

    @FXML
    private Button ENCHERE;

    @FXML
    private Button LIVRAISON;

    @FXML
    private ListView<Livraison> LVcmd;

    @FXML
    private Button PRODUITS;

    @FXML
    private Button USER;

    @FXML
    private Button versModifier;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/DD");
    private DepotService depotService = new DepotService();

    @FXML
    void initialize() {
        LivraisonService livraisonService = new LivraisonService();
        try {
            List<Livraison> livraisons = livraisonService.recuperer();
            ObservableList<Livraison> livraisonDataList = FXCollections.observableArrayList(livraisons);

            LVcmd.setItems(livraisonDataList);

            // Add listener to search text field
            searchtext.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    // Filter livraisons based on addresselivraison using streams
                    List<Livraison> result = livraisonService.recuperer().stream()
                            .filter(livraison -> livraison.getAdresselivraison().toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());

                    // Clear the ListView before adding new items
                    LVcmd.getItems().clear();
                    LVcmd.getItems().addAll(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Une erreur s'est produite lors de la recherche : " + e.getMessage());
                }
            });

            // Define the updateItem method for the ListView
            LVcmd.setCellFactory(param -> new ListCell<Livraison>() {
                @Override
                protected void updateItem(Livraison item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        String dateCommandeText = (item.getDatecommande() != null) ?
                                dateFormatter.format(item.getDatecommande().toLocalDateTime()) : "";

                        String datelivraisonText = (item.getDatelivraison() != null) ?
                                dateFormatter.format(item.getDatelivraison().toLocalDate()) : "";

                        try {
                            DepotService depotService = new DepotService();
                            Depot depot = depotService.getById(item.getIddepot());
                            String depotName = (depot != null) ? depot.getNomdepot() : "";

                            // Add titles as the first row
                            if (getIndex() == 0) {
                                String columnTitles = String.format("%-35s %-40s %-40s %-30s %-30s  ", "Adresselivraison", "Datecommande", "Datelivraison", "Statuslivraison", "Depot");
                                setText(columnTitles);
                                setStyle("-fx-background-color: rgba(132, 151, 219, 0.9375);");
                            } else {
                                setText(String.format("%-35s %-39s %-39s %-39s %-39s ",
                                        item.getAdresselivraison(),
                                        dateCommandeText,
                                        datelivraisonText,
                                        item.getStatuslivraison(),
                                        depotName
                                ));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void trier(ActionEvent event) throws SQLException {
        try {
            LivraisonService livraisonService = new LivraisonService();
            List<Livraison> livraisons = livraisonService.tri();

            ObservableList<Livraison> livraisonsObservable = livraisons.stream()
                    .sorted(Comparator.comparing(Livraison::getDatelivraison))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            LVcmd.setItems(livraisonsObservable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Ajouterlivraison.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        LivraisonService livraisonService = new LivraisonService();

        try {
            Livraison selectedLivraison = LVcmd.getSelectionModel().getSelectedItem();

            if (selectedLivraison == null) {
                showAlert("Avertissement", "Veuillez sélectionner une livraison à supprimer.");
                return;
            }

            int id = selectedLivraison.getId();

            if (livraisonService.idExists(id)) {
                livraisonService.supprimer(id);
                LVcmd.getItems().remove(selectedLivraison);
                showAlert("Suppression", "Livraison a été supprimée avec succès.");
            } else {
                showAlert("Avertissement", "L'ID spécifié n'existe pas dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la suppression de la livraison : " + e.getMessage());
        }
    }

    @FXML
    void ondepot(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Ajouterdepot.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterlivraison : " + e.getMessage());
        }
    }

    @FXML
    void searchLivraison(ActionEvent event) {
        // This method is no longer used as we update the search results dynamically
    }

    @FXML
    void VersModifier(ActionEvent event) {
        Livraison selectedLivraison = LVcmd.getSelectionModel().getSelectedItem();

        if (selectedLivraison != null) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Editlivraison.fxml"));
                Parent root = fxmlLoader.load();
                Editlivraison editController = fxmlLoader.getController();
                editController.initData(selectedLivraison);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors du chargement de l'interface Edit : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner une livraison à modifier.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void toStat(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Statistique.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterlivraison : " + e.getMessage());
        }


    }
}
