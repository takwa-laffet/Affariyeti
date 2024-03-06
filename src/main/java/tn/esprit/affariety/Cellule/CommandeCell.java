package tn.esprit.affariety.Cellule;

import javafx.scene.control.ListCell;
import tn.esprit.affariety.models.Commande;
import javafx.scene.control.Label;

public class CommandeCell extends ListCell<Commande> {
    @Override
protected void updateItem(Commande commande, boolean empty) {
    super.updateItem(commande, empty);

    if (empty || commande == null) {
        setText(null);
        setGraphic(null);
    } else {
        // Création du label pour afficher les détails de la commande
        Label label = new Label();
        label.setStyle("-fx-font-weight: bold;-fx-text-fill: black;"); // Appliquer un style au label
        label.setWrapText(true); // Permettre le retour à la ligne automatique

        // Construction de la chaîne de caractères avec les détails de la commande
        StringBuilder sb = new StringBuilder();
        //  sb.append("ID: ").append(client.getId_c()).append("\n")
        sb.append("Commande Client: ").append(commande.getCmd_client()).append("\n")
                .append("Status: ").append(commande.getEtat()).append("\n")
                .append("Date: ").append(commande.getCmd_date()).append("\n");



        // Définition du texte du label
        label.setText(sb.toString());

        setGraphic(label);
    }
}
}
