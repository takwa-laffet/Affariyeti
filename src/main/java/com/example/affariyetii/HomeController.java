package com.example.affariyetii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    void openAjouterEnchere(ActionEvent event) throws IOException {
        // Load the Ajouter Enchere interface
     try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEnchere.fxml"));
         Parent root = loader.load();
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.show();

     }catch (IOException e)
     {            e.printStackTrace();


     }
    }

    @FXML
    void openChercherEnchere(ActionEvent event) throws IOException {
        // Load the Chercher Enchere interface
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/ChercherEnchere.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();
        }
    }

    @FXML
    void openModifierEnchere(ActionEvent event) throws IOException {
        // Load the Chercher Enchere interface
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/ModifierEnchere.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();


        }
    }

    @FXML
    void openAjouterTickect(ActionEvent event) throws IOException {
        // Load the Chercher Enchere interface
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/AjouterTickect.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();


        }
    }
    @FXML
    void openTicket(ActionEvent event) throws IOException {
        // Load the Chercher Enchere interface
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/AfficherTicketclient.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();


        }
    }@FXML
    void openAcher(ActionEvent event) throws IOException {
        // Load the Chercher Enchere interface
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/AcheterTickect.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {            e.printStackTrace();


        }
    }
}
