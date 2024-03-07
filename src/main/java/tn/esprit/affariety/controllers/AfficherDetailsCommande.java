package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import tn.esprit.affariety.Cellule.DetailCommaandeCell;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.models.DetailsCommande;
import tn.esprit.affariety.services.DetailsCommandeService;

import java.sql.SQLException;
import java.util.List;

public class AfficherDetailsCommande {

    @FXML
    private ListView<DetailsCommande> lvdCmd;

    private Commande selectedCommand;

    private DetailsCommandeService detailsCommandeService;

    // Méthode pour initialiser les données de la commande sélectionnée
    public void initData(Commande selectedCommand) {
        this.selectedCommand = selectedCommand;

        // Assurez-vous que le service est initialisé
        if (detailsCommandeService == null) {
            detailsCommandeService = new DetailsCommandeService();
        }

        // Vérifiez si une commande est sélectionnée
        if (selectedCommand != null) {
            try {
                // Récupérez les détails de la commande à partir du service
                List<DetailsCommande> detailsCommandes = detailsCommandeService.recupererC(selectedCommand);
                lvdCmd.setCellFactory(param -> new DetailCommaandeCell());

                // Créez une ObservableList à partir de la liste récupérée
                ObservableList<DetailsCommande> detailsObservableList = FXCollections.observableArrayList(detailsCommandes);

                // Définissez les éléments pour la ListView
                lvdCmd.setItems(detailsObservableList);
            } catch (SQLException e) {
                // Gérer les exceptions SQL
                e.printStackTrace();
            }
        } else {
            // Gérer le cas où aucune commande n'est sélectionnée
            lvdCmd.setItems(FXCollections.emptyObservableList());
        }
    }

    // Méthode pour gérer l'action de bouton "Retour" par exemple
}