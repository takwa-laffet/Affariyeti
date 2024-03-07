package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.services.CommandeService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;


public class AjouterCommande {
    @FXML
    private TextField cmd_clientTF;
    @FXML
    private TextField etatTF;

    @FXML
    void afficherCommande(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AfficherCommande.fxml"));
        try {
            cmd_clientTF.getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void ajouterCommande(ActionEvent event) {
        // Vérifier si les champs de saisie sont vides
        if (cmd_clientTF.getText().isEmpty() || etatTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Vérifier si le champ cmd_clientTF contient un nombre négatif
        try {
            int cmdClient = Integer.parseInt(cmd_clientTF.getText());
            if (cmdClient < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un numéro de commande valide (positif).");
            alert.showAndWait();
            return;
        }

        // Vérifier si le champ etatTF contient un entier
        try {
            Integer.parseInt(etatTF.getText());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ 'État' doit contenir une chaîne de caractères, pas un entier.");
            alert.showAndWait();
            return;
        } catch (NumberFormatException e) {
            // Le champ etatTF contient bien une chaîne de caractères
        }

        // Maintenant, récupérer les valeurs des champs après la validation
        int cmdClient = Integer.parseInt(cmd_clientTF.getText());
        String etat = etatTF.getText();

        // Ajouter la commande seulement si les champs de saisie sont valides et remplis
        CommandeService commandeService = new CommandeService();
        Commande c = new Commande();
        c.setCmd_client(cmdClient);
        c.setEtat(etat);
        try {
            commandeService.ajouter(c);
            cmd_clientTF.setText("");
            etatTF.setText("");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Commande ajoutée avec succès.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de l'ajout de la commande : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void category(ActionEvent event) {

    }

    @FXML
    void commande(ActionEvent event) {

    }

    @FXML
    void demande(ActionEvent event) {

    }

    @FXML
    void details(ActionEvent event) {

    }

    @FXML
    void enchere(ActionEvent event) {

    }

    @FXML
    void livraison(ActionEvent event) {

    }

    @FXML
    void modifierCommande(ActionEvent event) {

    }

    @FXML
    void returnto(ActionEvent event) {

    }

    @FXML
    void supprimerCommande(ActionEvent event) {

    }

    @FXML
    void utilisateur(ActionEvent event) {

    }
}
