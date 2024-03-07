package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import tn.esprit.affariety.models.Commentaire;
import tn.esprit.affariety.services.CommentaireService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

public class StatCom{
    @FXML
    private BarChart<String, Integer> barchartCom;
    @FXML
    private LineChart<String, Integer> linechartcom;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart PieCom;

    private CommentaireService commentaireService;

    @FXML
    public void initialize() {
        commentaireService = new CommentaireService();
        displayCommentCounts();
        displayCommentCountsPerDate();
        displayCommentCountsPerUserAndPublication();
    }

    private void displayCommentCounts() {
        try {
            // Retrieve comment counts per user
            Map<String, Integer> commentCountsPerUser = commentaireService.getCommentCountsPerUser();

            // Populate the BarChart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            for (Map.Entry<String, Integer> entry : commentCountsPerUser.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barchartCom.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void displayCommentCountsPerDate() {
        CommentaireService commentaireService = new CommentaireService();

        try {
            Map<Timestamp, Integer> commentCountsPerDate = commentaireService.getCommentCountsPerDate();

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            for (Map.Entry<Timestamp, Integer> entry : commentCountsPerDate.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }
            linechartcom.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void displayCommentCountsPerUserAndPublication() {
        try {
            // Récupérer les comptes de commentaires par utilisateur et par publication
            Map<String, Integer> commentCountsPerUserAndPublication = commentaireService.getCommentCountsPerUserAndPublication();

            // Peupler le PieChart
            for (Map.Entry<String, Integer> entry : commentCountsPerUserAndPublication.entrySet()) {
                PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
                PieCom.getData().add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
