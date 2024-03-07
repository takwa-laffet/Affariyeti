package tn.esprit.affariety.Cellule;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import tn.esprit.affariety.models.Produit;

public class ProduitCell extends ListCell<Produit> {

    @Override
    protected void updateItem(Produit produit, boolean empty) {
        super.updateItem(produit, empty);

        if (empty || produit == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Création du label pour afficher les détails de la commande
            Label label = new Label();
            label.setStyle("-fx-font-weight: bold;-fx-text-fill: black;"); // Appliquer un style au label
            label.setWrapText(true); // Permettre le retour à la ligne automatique

            // Construction de la chaîne de caractères avec les détails de la commande
            StringBuilder sb = new StringBuilder();
            ImageView imageView = new ImageView(produit.getImage_p());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            sb.append("Nom Catégorie: ").append(produit.getCategorie().getNom_c()).append("\n")
                    .append("Nom Produit: ").append(produit.getNom_p()).append("\n")
                    .append("Description: ").append(produit.getDescription_p()).append("\n")
                    .append("Prix: ").append(produit.getPrix_p()).append("\n")
                    .append("C://Users/Lenovo/IdeaProjects/Affariety/"+imageView);



            // Définition du texte du label
            label.setText(sb.toString());

            setGraphic(label);
        }
    }
}