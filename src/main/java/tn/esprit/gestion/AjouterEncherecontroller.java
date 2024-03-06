package tn.esprit.gestion;

import tn.esprit.gestion.models.Enchere;
import tn.esprit.gestion.models.Ticket;
import tn.esprit.gestion.services.Chat;
import tn.esprit.gestion.services.EnchereService;
import tn.esprit.gestion.services.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterEncherecontroller implements Initializable {

    @FXML
    private TextField nomEnchereTextField;

    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private TextField heureDebutTextField;

    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private TextField heureFinTextField;
    @FXML
    private TextField montantInitialTextField;

    @FXML
    private ToggleButton image;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private EnchereService enchereService;



    @FXML
    void ajouterEnchere(ActionEvent event) {
        EnchereService enchereService = new EnchereService();
        Enchere enchere = new Enchere();
        String nom = nomField.getText();
        String prenom = prenomField.getText();

        // Vérifier si les champs obligatoires sont remplis
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || nomEnchereTextField.getText().isEmpty() || dateDebutPicker.getValue() == null || heureDebutTextField.getText().isEmpty() || dateFinPicker.getValue() == null || heureFinTextField.getText().isEmpty() || montantInitialTextField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Vérifier le format de la date
        try {
            // Tentative de conversion de la date en format String
            enchere.setDateDebut(dateDebutPicker.getValue().toString());
            enchere.setDateFin(dateFinPicker.getValue().toString());
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Format de date invalide", "Veuillez entrer des dates valides.");
            return;
        }

        // Vérifier le montant initial
        try {
            // Tentative de conversion du montant initial en double
            Double.parseDouble(montantInitialTextField.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Montant initial invalide", "Veuillez entrer un montant initial valide.");
            return;
        }

        // Vérifier les valeurs numériques positives si nécessaire
        if (Double.parseDouble(montantInitialTextField.getText()) <= 0) {
            showAlert(AlertType.ERROR, "Montant initial invalide", "Le montant initial doit être supérieur à zéro.");
            return;
        }

        enchere.setNom_enchere(nomEnchereTextField.getText());
        String contenu = nomEnchereTextField.getText();
        // Utiliser le service Chat pour détecter les mots haineux dans le contenu de la publication
        Chat chat = new Chat();
        String resultatDetection = chat.badword(contenu);

        enchere.setIdclcreree(enchereService.getUserIdByNomAndPrenom(nom, prenom));

        // Check if dateDebutPicker's value is not null before accessing it
        if (dateDebutPicker.getValue() != null) {
            enchere.setDateDebut(dateDebutPicker.getValue().toString());
        }

        enchere.setHeured(heureDebutTextField.getText());

        // Check if dateFinPicker's value is not null before accessing it
        if (dateFinPicker.getValue() != null) {
            enchere.setDateFin(dateFinPicker.getValue().toString());
        }
        if (dateDebutPicker.getValue().isAfter(dateFinPicker.getValue())) {
            showAlert(AlertType.ERROR, "Erreur", "La date de fin doit être après la date de début.");
            return;
        }

        enchere.setHeuref(heureFinTextField.getText());
        enchere.setMontantInitial(montantInitialTextField.getText());
        enchere.setMontant_final(montantInitialTextField.getText());
        enchere.setImage(image.getText()); // Set the image URL
        if (resultatDetection.equals("1"))
        { Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Le contenu de la nom Enchere contient un discours haineux. Veuillez le modifier.");
        alert.showAndWait();
    } else if (resultatDetection.equals("0")) {
        try {
            enchereService.ajouter(enchere);
            showAlert(AlertType.INFORMATION, "Enchere Ajoutée", "L'enchere a été ajoutée avec succès.");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout de l'enchere : " + e.getMessage());
        }
        int enchereId = enchereService.rechercherIdParNom(nomEnchereTextField.getText());

        // Create TicketService instance to add tickets
        TicketService ticketService = new TicketService();
        // Add the specified number of tickets for the given Enchere ID
        for (int i = 0; i < 10; i++) {
            Ticket ticket = new Ticket();
            ticket.setEnchereId(enchereId);
            ticket.setPrix(10);
            // You may set other properties of the ticket if needed
            ticketService.ajouter(ticket);
        }

        System.out.println(" tickets added successfully for the auction: ");
    }
    }

    @FXML
    void annuler(ActionEvent event) {
        nomEnchereTextField.clear();
        dateDebutPicker.setValue(null);
        heureDebutTextField.clear();
        dateFinPicker.setValue(null);
        heureFinTextField.clear();
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
        {            e.printStackTrace(); }
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
        {e.printStackTrace();}
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
    }
    @FXML
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
