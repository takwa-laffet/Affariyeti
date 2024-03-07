package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Livraison;
import tn.esprit.affariety.services.LivraisonService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javafx.fxml.FXMLLoader.load;

public class Statistique {

    @FXML
    private ListView<String> AdresseList;

    @FXML
    private PieChart AdressePie;

    @FXML
    private ListView<String> DateList;

    @FXML
    private LineChart<String, Integer> LineDate; // Note: Assuming the x-axis is of type String

    private LivraisonService livraisonService;

    public Statistique() {
        this.livraisonService = new LivraisonService();
    }

    @FXML
    public void initialize() {
        try {
            displayAdresseList();
            displayAdressePieChart();
            displayDateList();
            displayLineChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAdresseList() throws SQLException {
        List<Livraison> livraisons = livraisonService.recuperer();
        ObservableList<String> addresses = FXCollections.observableArrayList(
                livraisons.stream().map(Livraison::getAdresselivraison).collect(Collectors.toList())
        );
        AdresseList.setItems(addresses);
    }

    private void displayAdressePieChart() throws SQLException {
        List<Livraison> livraisons = livraisonService.recuperer();
        Map<String, Integer> addressCountMap = new HashMap<>();

        for (Livraison livraison : livraisons) {
            String address = livraison.getAdresselivraison();
            addressCountMap.put(address, addressCountMap.getOrDefault(address, 0) + 1);
        }

        ObservableList<PieChart.Data> pieChartData = addressCountMap.entrySet().stream()
                .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        AdressePie.setData(pieChartData);
    }

    private void displayDateList() throws SQLException {
        List<Livraison> livraisons = livraisonService.recuperer();
        ObservableList<String> dateList = FXCollections.observableArrayList(
                livraisons.stream()
                        .map(livraison -> livraison.getDatelivraison().toString())
                        .collect(Collectors.toList())
        );
        DateList.setItems(dateList);
    }

    private void displayLineChart() throws SQLException {
        List<Livraison> livraisons = livraisonService.tri(); // Assuming tri() sorts by 'datelivraison'

        ObservableList<XYChart.Series<String, Integer>> lineChartData = FXCollections.observableArrayList();

        Map<String, Integer> dateCountMap = new HashMap<>();

        for (Livraison livraison : livraisons) {
            String date = livraison.getDatelivraison().toString();
            dateCountMap.put(date, dateCountMap.getOrDefault(date, 0) + 1);
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Livraisons per day");

        for (Map.Entry<String, Integer> entry : dateCountMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        lineChartData.add(series);
        LineDate.setData(lineChartData);
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = load(getClass().getResource("/tn/esprit/gestion/Ajouterlivraison.fxml"));
            LineDate = null;
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de l'interface Afficherlivraison : " + e.getMessage());
        }


    }

    private void showAlert(Alert.AlertType alertType, String erreur, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(erreur);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
