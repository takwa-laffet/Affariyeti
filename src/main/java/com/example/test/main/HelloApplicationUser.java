package com.example.test.main;

import com.example.test.gui.AffichageCcp;
import com.example.test.gui.EditCategorieCodePromo;
import com.example.test.models.CategorieCodePromo;
import com.example.test.models.Discount;
import com.example.test.services.GestionCategorieCodePromo;
import com.example.test.services.GestionCodePromo;
import com.example.test.services.GestionDiscount;
import com.example.test.services.GestionUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplicationUser extends Application {
    @Override

    public void start(Stage stage) throws IOException, SQLException {
        GestionUser us = new GestionUser();
        GestionCategorieCodePromo cc = new GestionCategorieCodePromo();
        GestionCodePromo cp = new GestionCodePromo();
        GestionDiscount gs = new GestionDiscount();
        Discount d = new Discount();
        System.out.println(cp.findById(32));
        d.setUser(us.findById(111));
        d.setCodePromo(cp.findById(32));
        d.setIdD(1);
        CategorieCodePromo c = cc.findById(9);

            //gs.delete(d);



// FXMLLoader for the EditCategorieCodePromo.fxml
        FXMLLoader loginLoader = new FXMLLoader(HelloApplicationUser.class.getResource("/com/example/test/login.fxml"));
        Parent loginRoot = loginLoader.load();

// Get the controller from the FXMLLoader
       // AffichageCcp ecController = loginLoader.getController();

// Send data to the controller
       // ecController.initData();

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



