package com.example.test.gui;

import com.example.test.services.GestionUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DashboardAdmin {
@FXML
private Button add;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private PieChart pieChart;
    public void initialize() {
        GestionUser userService = new GestionUser();
        // Call getUs
        // qerDataByDate() from UserService
        Map<java.sql.Date, Integer> userDataByDate = userService.getUserDataByDate();

        // Create a series for the line chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Add data points to the series
        for (Map.Entry<java.sql.Date, Integer> entry : userDataByDate.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        pieChart.getData().clear();

        // Add data points to the pie chart
        Map<String, Integer> userDataByStatus = userService.getUserDataByStatus();
        for (Map.Entry<String, Integer> entry : userDataByStatus.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart.getData().add(slice);
        }
        // Set the series data to the line chart
        lineChart.getData().add(series);
    }

    // Method to get user data by date (you need to implement this)
    private Map<Date, Integer> getUserDataByDate() {
        // This is just a placeholder, you need to implement this method
        return new HashMap<>();
    }



    @FXML
    public void openChefList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/AffichageCcp.fxml"));
        Parent root = loader.load();
        AffichageCcp addController = loader.getController(); // Get the controller instance associated with the FXML
         // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @FXML
    public void addChefList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/codePromoList.fxml"));
        Parent root = loader.load();
        CodePromoList addController = loader.getController(); // Get the controller instance associated with the FXML
        // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    public void addNut()throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/UsersListt.fxml"));
        Parent root = loader.load();
        UsersList addController = loader.getController(); // Get the controller instance associated with the FXML
        // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();

    }

    @FXML
    public void openNutList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/UsersList.fxml"));
        Parent root = loader.load();
        UsersList addController = loader.getController(); // Get the controller instance associated with the FXML
        // Call initData on the correct controller instance
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();

    }

    @FXML
    public void BackToLogin() {try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/login.fxml"));
        Parent profileInterface = loader.load();

        // Get the controller instance

        // Initialize data using the controller's method

        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);

        // Close the current stage (assuming loginButton is accessible from here)
        Stage currentStage = (Stage) lineChart.getScene().getWindow();
        currentStage.close();

        // Show the profile stage
        profileStage.show();
    }catch (Exception e ){
        System.out.println(e.getMessage());
    }
    }
}
