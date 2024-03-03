package com.example.affariyetii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavafx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainJavafx.class.getResource("/Chatboot.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene =new Scene(root);
        stage.setTitle("AFFARIYETI");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}