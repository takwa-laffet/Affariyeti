package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.services.CommandeService;

import java.sql.SQLException;

public class ModifierCommande {
    @FXML
    private TextField cmd_clientTF;

    @FXML
    private TextField etatTF;

    private Commande commande; // Stocker la commande sélectionnée

    // Méthode pour définir la commande à modifier
    public void setCommande(Commande commande) {
        this.commande = commande;
        // Remplir les champs textuels avec les valeurs actuelles de la commande
        cmd_clientTF.setText(String.valueOf(commande.getCmd_client()));
        etatTF.setText(commande.getEtat());
    }
    public Commande getCommandeModifiee() {
        // Mettre à jour les attributs de la commande avec les valeurs des champs textuels
        commande.setCmd_client(Integer.parseInt(cmd_clientTF.getText()));
        commande.setEtat(etatTF.getText());
        return commande;
    }

    @FXML
    void confirmerModification(ActionEvent event) {
        // Vérifier si une commande est sélectionnée
        if (commande == null) {
            afficherAlerte("Aucune commande sélectionnée", "Veuillez sélectionner une commande à modifier.");
            return;
        }

        // Récupérer les nouvelles valeurs depuis les champs textuels
        int nouveauCmdClient = Integer.parseInt(cmd_clientTF.getText());
        String nouvelEtat = etatTF.getText();

        // Mettre à jour les attributs de la commande sélectionnée
        commande.setCmd_client(nouveauCmdClient);
        commande.setEtat(nouvelEtat);

        // Appeler le service pour modifier la commande dans la base de données
        CommandeService commandeService = new CommandeService();
        try {
            commandeService.modifier(commande);
            // Afficher une confirmation
            afficherAlerte("Commande modifiée", "La commande a été modifiée avec succès.");
        } catch (Exception e) {
            // En cas d'erreur lors de la modification
            afficherAlerte("Erreur lors de la modification", "Une erreur s'est produite lors de la modification de la commande : " + e.getMessage());
        }
    }




    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

   public void initData(int commandId) {

            CommandeService commandeService = new CommandeService();
            try {
                // Récupérer la commande à partir de son identifiant
                Commande commande = commandeService.recupererParId(commandId);
                // Vérifier si la commande existe
                if (commande != null) {
                    // Définir la commande dans le contrôleur
                    setCommande(commande);
                    cmd_clientTF.setText(String.valueOf(commande.getCmd_client()));
                    etatTF.setText(commande.getEtat());
                } else {
                    // Afficher une alerte si la commande n'est pas trouvée
                    afficherAlerte("Commande introuvable", "La commande avec l'ID " + commandId + " n'existe pas.");
                }
            } catch (SQLException e) {
                // En cas d'erreur lors de la récupération de la commande
                afficherAlerte("Erreur de récupération", "Une erreur s'est produite lors de la récupération de la commande : " + e.getMessage());
            }
        }

    }

