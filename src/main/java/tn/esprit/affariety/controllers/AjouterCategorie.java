package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCategorie {
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField nomTF;

    @FXML
    void afficherCategorie(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AfficherCategorie.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void ajouterCategorie(ActionEvent event) {
        CategorieService categorieService = new CategorieService();
        Categorie categorie = new Categorie();
        categorie.setNom_c(nomTF.getText());
        try {
            categorieService.ajouter(categorie);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Catégorie ajoutée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }
    @FXML
    void ajouterProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/AjouterProduit.fxml"));
            Parent root = loader.load();

            AjouterProduit ajouterProduit = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
