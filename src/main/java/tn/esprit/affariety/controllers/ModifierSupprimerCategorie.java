package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierSupprimerCategorie {

    @FXML
    private TextField idTF;

    @FXML
    private TextField nomTF;

    @FXML
    void modifierCategorie(ActionEvent event) {
        CategorieService categorieService = new CategorieService();
        Categorie categorie = new Categorie();
        categorie.setId_c(Integer.parseInt(idTF.getText()));
        categorie.setNom_c(nomTF.getText());
        try {
            categorieService.modifier(categorie);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Catégorie modifiée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }

    @FXML
    void supprimerCategorie(ActionEvent event) {
        CategorieService categorieService = new CategorieService();
        Categorie categorie = new Categorie();
        categorie.setId_c(Integer.parseInt(idTF.getText()));
        try {
            categorieService.supprimer(categorie.getNom_c());
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Catégorie supprimée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }

    @FXML
    void trouverCategorie(ActionEvent event) {
        try {
            int idCategorie = Integer.parseInt(idTF.getText());
            CategorieService categorieService = new CategorieService();
            Categorie categorieTrouvee = categorieService.getCategoryById(idCategorie);

            if (categorieTrouvee != null) {
                System.out.println("Categorie trouvee : " + categorieTrouvee.getNom_c());
                nomTF.setText(categorieTrouvee.getNom_c());
            } else {
                System.out.println("Aucune catégorie trouvée avec l'ID : " + idCategorie);
            }
        } catch  (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void naviguerCategorie(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AfficherCategorie.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
