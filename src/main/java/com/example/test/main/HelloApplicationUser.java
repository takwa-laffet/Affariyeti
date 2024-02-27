package com.example.test.main;

import com.example.test.gui.EditCategorieCodePromo;
import com.example.test.services.GestionCategorieCodePromo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplicationUser extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GestionCategorieCodePromo gs = new GestionCategorieCodePromo();

// FXMLLoader for the EditCategorieCodePromo.fxml
        FXMLLoader loginLoader = new FXMLLoader(HelloApplicationUser.class.getResource("/com/example/test/EditCategorieCodePromo.fxml"));
        Parent loginRoot = loginLoader.load();

// Get the controller from the FXMLLoader
        EditCategorieCodePromo ecController = loginLoader.getController();

// Send data to the controller
        ecController.initData(gs.findById(2));

// Create the scene with the loaded root
        Scene loginScene = new Scene(loginRoot, 800, 400);

// Set up the stage
        stage.setTitle("Edit Categorie Code Promo");
        stage.setScene(loginScene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();


    }
}



