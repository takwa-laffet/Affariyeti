package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCategorie {
    @FXML
    private ResourceBundle resources;

    @FXML
    private ListView<Categorie> listView;

    @FXML
    void initialize() {
        CategorieService categorieService = new CategorieService();
        try {
            List<Categorie> categories = categorieService.recuperer();
            ObservableList<Categorie> observableList = FXCollections.observableList(categories);
            listView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    void retournerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ModifierSupprimerCategorie.fxml"));
            Parent root = loader.load();

            ModifierSupprimerCategorie modifierSupprimerCategorie = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ajouterCategorie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/AjouterCategorie.fxml"));
            Parent root = loader.load();

            AjouterCategorie ajouterCategorie = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
