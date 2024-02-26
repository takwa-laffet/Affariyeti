package com.example.affariyetii;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AjouterEncherecontroller implements Initializable {

    @FXML
    private TextField nomEnchereTextField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField montantInitialTextField;

    @FXML
    private ToggleButton image;

    private EnchereService enchereService;

    @FXML
    void ajouterEnchere(ActionEvent event) {
        EnchereService enchereService = new EnchereService();
        Enchere enchere = new Enchere();
        enchere.setNom_enchere(nomEnchereTextField.getText());
        enchere.setDateDebut(dateDebutPicker.getValue().toString());
        enchere.setDateFin(dateFinPicker.getValue().toString());
        enchere.setMontantInitial(montantInitialTextField.getText());
        enchere.setImage(image.getText()); // Set the image URL
        try {

            enchereService.ajouter(enchere);
            showAlert(AlertType.INFORMATION, "Enchere Ajoutée", "L'enchere a été ajoutée avec succès.");
        }catch (Exception e){
            showAlert(AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout de l'enchere : " + e.getMessage());

        }
    }

    @FXML
    void annuler(ActionEvent event) {
        nomEnchereTextField.clear();
        dateDebutPicker.setValue(null);
        dateFinPicker.setValue(null);
        montantInitialTextField.clear();
        image.setSelected(false);   }

    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un Fichier");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imageUrl = selectedFile.toURI().toString(); // Get the URL of the selected file
            image.setText(imageUrl);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image = new ToggleButton(); // Initialize the ToggleButton

    }
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class AcheterTicketcontroller {
    }

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
