package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatistiqueAdmine {

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    // Inject your EnchereService or any appropriate service here
    private EnchereService enchereService = new EnchereService();

    public StatistiqueAdmine() {
        // Initialize your service here
        enchereService = new EnchereService();
    }

    @FXML
    public void initialize() {
        // Retrieve data from your service
        List<Enchere> encheres = enchereService.reuperer();

        // Prepare data for the chart
        Map<String, Integer> auctionCountsByStartDate = new HashMap<>();
        for (Enchere enchere : encheres) {
            String startDate = enchere.getDateDebut();
            auctionCountsByStartDate.put(startDate, auctionCountsByStartDate.getOrDefault(startDate, 0) + 1);
        }

        // Create a series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Populate the series with data
        for (Map.Entry<String, Integer> entry : auctionCountsByStartDate.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add the series to the chart
        stackedBarChart.getData().add(series);
    }
}
