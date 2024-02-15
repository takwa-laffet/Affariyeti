package tn.esprit.gestion.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.services.DepotService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Afficherdepot {

    @FXML
    private ListView<Depot> depotListView;

    public Button modifier;
    public Button supprimer;

    private DepotService depotService;

    @FXML
    void initialize() {
        depotService = new DepotService();
        refreshDepotList();


    }

    @FXML
    private void modifyDepot() {
        Depot selectedDepot = depotListView.getSelectionModel().getSelectedItem();
        if (selectedDepot != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/ModifyDepot.fxml"));

                Parent root = loader.load();
                ModifyDepot modifyDepot = loader.getController();
                modifyDepot.initData(selectedDepot);
                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors du chargement de l'interface Edit : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un depot à modifier.");
        }
    }

    @FXML
    private void supprimerDepot() {
        Depot selectedDepot = depotListView.getSelectionModel().getSelectedItem();
        if (selectedDepot != null) {
            try {
                depotService.supprimer(selectedDepot.getIddepot());
                refreshDepotList();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la suppression du depot : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un depot à supprimer.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshDepotList() {
        try {
            List<Depot> depots = depotService.recuperer();
            depotListView.getItems().clear();
            depotListView.getItems().addAll(depots);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
