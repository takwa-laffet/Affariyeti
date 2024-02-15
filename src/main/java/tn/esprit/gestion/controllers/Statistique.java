package tn.esprit.gestion.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.services.LivraisonService;

import java.sql.SQLException;
import java.util.List;

public class Statistique {

    @FXML
    private ListView<String> ListPie; // Utilisez String pour ListView au lieu de ?

    @FXML
    private PieChart adressePie;

    private LivraisonService livraisonService = new LivraisonService();

    @FXML
    public void initialize() {
        try {
            afficherListeAdresse();
            afficherPieChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherListeAdresse() throws SQLException {
        List<Livraison> livraisons = livraisonService.recuperer();
        ObservableList<String> adresses = FXCollections.observableArrayList();

        for (Livraison livraison : livraisons) {
            adresses.add(livraison.getAdresselivraison());
        }

        ListPie.setItems(adresses);
    }

    private void afficherPieChart() throws SQLException {
        List<Livraison> livraisons = livraisonService.recuperer();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Livraison livraison : livraisons) {
            String adresse = livraison.getAdresselivraison();
            boolean adresseExistante = false;

            // Vérifier si l'adresse existe déjà dans le PieChart
            for (PieChart.Data data : pieChartData) {
                if (data.getName().equals(adresse)) {
                    adresseExistante = true;
                    data.setPieValue(data.getPieValue() + 1);
                    break;
                }
            }

            // Si l'adresse n'existe pas, l'ajouter dans le PieChart
            if (!adresseExistante) {
                pieChartData.add(new PieChart.Data(adresse, 1));
            }
        }

        adressePie.setData(pieChartData);
    }
}
