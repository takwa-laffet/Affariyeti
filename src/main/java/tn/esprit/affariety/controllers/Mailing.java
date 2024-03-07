package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affariety.services.LivraisonService;

import java.io.File;
import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class Mailing {

    @FXML
    private TextField emailTF;

    @FXML
    private TextField subjectTF;

    @FXML
    private TextArea messageTF;
    @FXML
    private Button attachFileButton;

    private String attachedFilePath;
    private LivraisonService livraisonService = new LivraisonService();
    @FXML
    void attachFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach File");
        Stage stage = (Stage) attachFileButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            attachedFilePath = selectedFile.getAbsolutePath();
        }
    }


    @FXML
    void envoyer(ActionEvent event) {
        String email = emailTF.getText();
        String subject = subjectTF.getText();
        String message = messageTF.getText();

        if (isValidEmail(email)) {
            if (!subject.isEmpty() && !message.isEmpty()) {
                livraisonService.envoyerEmailRecuperation(email, subject, message, attachedFilePath);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Email sent successfully.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Attention", "Please enter the subject and message.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid email address.");
        }
    }

    private boolean isValidEmail(String email) {
        // Ajoutez ici une logique de validation d'adresse e-mail, si nécessaire.
        return email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void retour(ActionEvent event)throws IOException {

        try {
            Parent root = load(getClass().getResource("/tn/esprit/gestion/Afficherlivraison.fxml"));
            attachedFilePath = null;
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de l'interface Afficherlivraison : " + e.getMessage());
        }
    }


    }

