package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.affariety.Cellule.CategorieCell;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.services.CategorieService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InterfaceCategorie {
    @FXML
    private ListView<Categorie> listView;
    @FXML
    private TextField nomTF;

    @FXML
    void initialize() {
        CategorieService categorieService = new CategorieService();
        try {
            List<Categorie> categories = categorieService.recuperer();
            listView.setCellFactory(param -> new CategorieCell());
            ObservableList<Categorie> observableList = FXCollections.observableList(categories);
            listView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Récupérer le client sélectionné
                Categorie selectedCategorie = listView.getSelectionModel().getSelectedItem();

                // Afficher les informations du client dans les champs de texte
                nomTF.setText(selectedCategorie.getNom_c());
            }
        });

    }

    public void ajouterCategorie(ActionEvent event) {
        CategorieService categorieService = new CategorieService();
        Categorie categorie = new Categorie();
        categorie.setNom_c(nomTF.getText());
        try {
            categorieService.ajouter(categorie);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Catégorie ajoutée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        initialize();
    }

    public void modifierCategorie(ActionEvent event) {
        CategorieService categorieService = new CategorieService();

        // Retrieve the selected category from the ListView
        Categorie selectedCategorie = listView.getSelectionModel().getSelectedItem();

        // Check if a category is selected
        if (selectedCategorie != null) {
            // Set the name from the text field to the selected category
            selectedCategorie.setNom_c(nomTF.getText());

            try {
                // Print debug information
                System.out.println("Updating category with id_c: " + selectedCategorie.getId_c() + ", new nom_c: " + selectedCategorie.getNom_c());

                // Call the modifier method with the selected category
                categorieService.modifier(selectedCategorie);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succes");
                alert.setContentText("Catégorie modifiée");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setContentText("Veuillez sélectionner une catégorie à modifier.");
            alert.showAndWait();
        }
    }


    public void supprimerCategorie(ActionEvent event) {

        CategorieService categorieService = new CategorieService();
        Categorie categorie = new Categorie();
        categorie.setNom_c(nomTF.getText());
        try {
            categorieService.supprimer(categorie.getNom_c());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Catégorie supprimée");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        initialize();

    }

    @FXML
    void addProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/InterfaceProduit.fxml"));
            Parent root = loader.load();

            InterfaceProduit interfaceProduit = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void clearFields (ActionEvent event){
        nomTF.clear();
    }

    public void Total(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ghada.fxml"));
            Parent root = loader.load();

            ghada ghada = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Statistiques(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/Statistiques.fxml"));
            Parent root = loader.load();

             Statistiques statistiques = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

