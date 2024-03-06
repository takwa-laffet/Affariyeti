package tn.esprit.affarietygui.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.models.User;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistique {

    @FXML
    private ListView<String> ListPie;

    @FXML
    private PieChart pubPielike;
    @FXML
    private PieChart pubPiedislike;
    @FXML
    private LineChart<String, Number>linechartpub;
    private PublicationService publicationService = new PublicationService();
    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    public void initialize() {
        try {
            afficherLineChart();
            afficherBarChart();
            afficherPieChartlike();
            afficherPieChartdislike();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherBarChart() throws SQLException {
        List<Publication> publications = publicationService.recuperer();

        // Create a map to store the count of publications for each user
        Map<String, Integer> publicationCountByUser = new HashMap<>();

        // Count the number of publications for each user
        for (Publication publication : publications) {
            String userName = publication.getUser().getNom() + " " + publication.getUser().getPrenom();
            publicationCountByUser.put(userName, publicationCountByUser.getOrDefault(userName, 0) + 1);
        }

        // Create series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre de publications par utilisateur");

        // Add data points to the series
        for (Map.Entry<String, Integer> entry : publicationCountByUser.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Set the bar chart data
        barchart.getData().add(series);
    }

    private void afficherLineChart() throws SQLException {
        List<Publication> publications = publicationService.recuperer();

        // Create a map to store the count of publications for each date
        Map<LocalDate, Integer> publicationCountByDate = new HashMap<>();

        // Count the number of publications for each date
        for (Publication publication : publications) {
            LocalDate date = publication.getDate_pub().toLocalDateTime().toLocalDate();
            publicationCountByDate.put(date, publicationCountByDate.getOrDefault(date, 0) + 1);
        }

        // Create series for the line chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("nombre des publications par date");

        // Add data points to the series
        for (Map.Entry<LocalDate, Integer> entry : publicationCountByDate.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        // Set the line chart data
        linechartpub.getData().add(series);
    }

    private void afficherPieChartlike() throws SQLException {
        List<Publication> publications = publicationService.recuperer();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Publication publication : publications) {
            String nb_likes = String.valueOf(publication.getNb_likes());
            boolean nb_likesexist = false;

            // Vérifier si le nombre de likes existe déjà dans le PieChart
            for (PieChart.Data data : pieChartData) {
                if (data.getName().equals(nb_likes)) {
                    nb_likesexist = true;
                    data.setPieValue(data.getPieValue() + 1);
                    break;
                }
            }

            // Si le nombre de likes n'existe pas, l'ajouter dans le PieChart
            if (!nb_likesexist) {
                pieChartData.add(new PieChart.Data(nb_likes, 1));
            }
        }

        pubPielike.setData(pieChartData);
    }

    private void afficherPieChartdislike() throws SQLException {
        List<Publication> publications = publicationService.recuperer();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Publication publication : publications) {
            String nb_dislikes = String.valueOf(publication.getNb_dislike());
            boolean nb_likesexist = false;

            // Vérifier si le nombre de likes existe déjà dans le PieChart
            for (PieChart.Data data : pieChartData) {
                if (data.getName().equals(nb_dislikes)) {
                    nb_likesexist = true;
                    data.setPieValue(data.getPieValue() + 1);
                    break;
                }
            }

            // Si le nombre de likes n'existe pas, l'ajouter dans le PieChart
            if (!nb_likesexist) {
                pieChartData.add(new PieChart.Data(nb_dislikes, 1));
            }
        }

        pubPiedislike.setData(pieChartData);
    }

    public void retourbtn(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/afficher_pub.fxml"));
            Parent root = loader.load();

            // Obtenir la scène depuis le bouton source
            Scene scene = ((Node) actionEvent.getSource()).getScene();

            // Obtenir la fenêtre actuelle et la fermer
            Stage stage = (Stage) scene.getWindow();
            stage.close();

            // Afficher la nouvelle interface utilisateur dans une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception en affichant la trace
        }
    }

    public void DashboardCommentaires(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/StatCom.fxml"));
            Parent root = loader.load();

            // Obtenir la scène depuis le bouton source
            Scene scene = ((Node) actionEvent.getSource()).getScene();

            // Obtenir la fenêtre actuelle et la fermer
            Stage stage = (Stage) scene.getWindow();
            stage.close();

            // Afficher la nouvelle interface utilisateur dans une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception en affichant la trace
        }
    }
}
