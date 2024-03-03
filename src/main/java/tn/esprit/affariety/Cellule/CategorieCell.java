package tn.esprit.affariety.Cellule;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import tn.esprit.affariety.models.Categorie;

public class CategorieCell extends ListCell<Categorie> {
    @Override
    protected void updateItem(Categorie categorie, boolean empty) {
        super.updateItem(categorie, empty);

        if (empty || categorie == null) {
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
            sb.append("Nom Catégorie: ").append(categorie.getNom_c()).append("\n");


            // Définition du texte du label
            label.setText(sb.toString());

            setGraphic(label);
        }
    }
}
