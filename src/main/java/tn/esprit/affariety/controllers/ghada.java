package tn.esprit.affariety.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.affariety.utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

public class ghada extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);

        PieChart chart = createPieChart();
        ((Group) scene.getRoot()).getChildren().add(chart);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private PieChart piechart;

    @FXML
    private Label total_c;

    @FXML
    private Label total_p;

    @FXML
    private Connection connection;

    @FXML
    public void initialize() {
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
    public static PieChart createPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));

        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");

        return chart;
    }
    public static PieChart createDynamicPieChart(ObservableList<PieChart.Data> pieChartData, String title) {
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle(title);

        return chart;
    }



        public static void order() {
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Grapefruit", 13),
                            new PieChart.Data("Oranges", 25),
                            new PieChart.Data("Plums", 10),
                            new PieChart.Data("Pears", 22),
                            new PieChart.Data("Apples", 30));

            PieChart chart = createDynamicPieChart(pieChartData, "Ordered Fruits");

            // You can customize the ordering logic here if needed

            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setWidth(500);
            stage.setHeight(500);

            ((Group) scene.getRoot()).getChildren().add(chart);

            stage.setScene(scene);
            stage.show();
        }

    public void retournerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/InterfaceCategorie.fxml"));
            Parent root = loader.load();

            InterfaceCategorie interfaceCategorie = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}






