package tn.esprit.gestion.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomepageController {

    @FXML


    public void goToAjouterLivraison(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouterlivraison.fxml"));
        Scene scene = new Scene(loader.load());

        // Assuming you have a controller for Afficherlivraison.fxml, you can get it like this:
        Ajouterlivraison controller = loader.getController();

        // If needed, you can perform additional initialization on the controller here

        // Access the primaryStage from the event
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    }

