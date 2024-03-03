package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardAdmin {

    //@FXML
    //private LineChart<?, ?> lineChart;

    //@FXML
    //private PieChart pieChart;

    @FXML
    private Label total_c;

    @FXML
    private Label total_p;

    @FXML
    private Connection connection;

    @FXML
    public void initialize() {
        // Appeler les méthodes pour mettre à jour les statistiques lors de l'initialisation du contrôleur
        totalCategories();
        totalProducts();
    }

    @FXML
    public void totalCategories() {
        String query = "SELECT COUNT(*) FROM categorie";
        connection = MyDataBase.getInstance().getConnection();
        int countData = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countData = resultSet.getInt("COUNT(*)");
            }
            total_c.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void totalProducts() {
        String query = "SELECT COUNT(*) FROM produit";
        connection = MyDataBase.getInstance().getConnection();
        int countData = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countData = resultSet.getInt("COUNT(*)");
            }
            total_p.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
