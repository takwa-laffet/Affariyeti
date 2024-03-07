package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.affariety.Cellule.CommandeCell;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.services.CommandeService;
import tn.esprit.affariety.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AfficherCommande {


    @FXML
    private ListView<Commande> LVcmd; // Assurez-vous que le type correspond à ce que vous souhaitez afficher

    @FXML
    void initialize() {
        CommandeService commandeService = new CommandeService();
        try {
            List<Commande> commandes = commandeService.recuperer();
            ObservableList<Commande> commandeDataList = FXCollections.observableArrayList(commandes);

            // Définir la cellule personnalisée
            LVcmd.setCellFactory(param -> new CommandeCell()); // Utiliser la classe CommandeCell

            // Ajouter les données des commandes
            LVcmd.getItems().addAll(commandeDataList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        }



    @FXML
    void modifierCommande(ActionEvent event) {
        // Récupérer l'élément sélectionné dans la ListView
        Commande selectedCommand = LVcmd.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (selectedCommand == null ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune commande sélectionnée");
            alert.setContentText("Veuillez sélectionner une commande à modifier.");
            alert.showAndWait();
            return; // Sortir de la méthode si aucune commande n'est sélectionnée
        }

        // Extraire l'ID de la commande sélectionnée
        int commandId = selectedCommand.getId();

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier la commande");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette commande ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Ouvrir une boîte de dialogue de modification de la commande
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la commande");
            dialog.setHeaderText(null);

            // Charger le fichier FXML de la boîte de dialogue de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ModifierCommande.fxml"));
            try {
                dialog.getDialogPane().setContent(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Passer l'ID de la commande sélectionnée au contrôleur de la boîte de dialogue de modification
            ModifierCommande controller = loader.getController();
            controller.initData(commandId);

            // Ajouter les boutons "Confirmer" et "Annuler" à la boîte de dialogue
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> dialogResult = dialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                // Récupérer la commande modifiée du contrôleur ModifierCommande
                Commande commandeModifiee = controller.getCommandeModifiee();
                // Mettre à jour la commande dans la liste des commandes
                int index = LVcmd.getItems().indexOf(selectedCommand);
                if (index != -1) {
                    LVcmd.getItems().set(index, commandeModifiee);
                }
            }
        }
    }



    @FXML
    void supprimerCommande(ActionEvent event) {
        CommandeService commandeService = new CommandeService();
        try {
            // Récupérer l'élément sélectionné dans la ListView
            Commande selectedCommand = LVcmd.getSelectionModel().getSelectedItem();

            if (selectedCommand == null) {
                showAlert("Aucune commande sélectionnée", Alert.AlertType.WARNING);
                return; // Sortir de la méthode si aucune commande n'est sélectionnée
            }

            // Récupérer l'ID de la commande à partir de la chaîne sélectionnée
            int id = selectedCommand.getId();
            if (commandeService.idExists(id)) {
                // Demander confirmation à l'utilisateur
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmer la suppression");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette commande ?");

                // Attendre la réponse de l'utilisateur
                Optional<ButtonType> result = confirmationAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // L'utilisateur a confirmé la suppression, appeler le service pour supprimer la commande
                    try {
                        commandeService.supprimer(id);
                        // Rafraîchir la liste des commandes après la suppression
                        LVcmd.getItems().remove(selectedCommand);
                        // Afficher une confirmation
                        showAlert("Commande supprimée", Alert.AlertType.INFORMATION);
                    } catch (SQLException e) {
                        // En cas d'erreur lors de la suppression
                        showAlert("Erreur lors de la suppression : " + e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            } else {
                showAlert("Commande n'existe pas", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            showAlert("Erreur lors de la suppression : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Méthode utilitaire pour afficher les alertes
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void details(ActionEvent event) {
        Commande selectedCommand = LVcmd.getSelectionModel().getSelectedItem();
        if (selectedCommand == null) {
            showAlert("Aucune commande sélectionnée", Alert.AlertType.WARNING);
            return;
        }

        // Charger la vue AfficherDetailsCommande.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/AfficherDetailsCommande.fxml"));
        try {
            // Charger la vue et obtenir le contrôleur associé
            Parent root = fxmlLoader.load();
            AfficherDetailsCommande controller = fxmlLoader.getController();

            // Passer les détails de la commande sélectionnée au contrôleur de la vue AfficherDetailsCommande
            controller.initData(selectedCommand);

            // Afficher la vue dans une nouvelle fenêtre ou une nouvelle scène
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void returnto(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affariety/AjouterCommande.fxml"));
        try {
            LVcmd    .getScene().setRoot(fxmlLoader.load());
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }



    }





