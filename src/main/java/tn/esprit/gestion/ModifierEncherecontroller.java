package tn.esprit.gestion;

import tn.esprit.gestion.models.Enchere;
import tn.esprit.gestion.services.EnchereService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

import java.io.File;

public class ModifierEncherecontroller {

        @FXML
        private TextField nomEnchereTextField;

        @FXML
        private TextField nouvelleImageTextField;

        @FXML
        private TextField nouvelleDateDebutTextField;

        @FXML
        private TextField nouvelleDateFinTextField;

        @FXML
        private TextField nouveauMontantInitialTextField;

        @FXML
        private ToggleButton image;
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
        @FXML
        void modifierEnchere(ActionEvent event) {
                EnchereService enchere = new EnchereService();
                int id = enchere.rechercherIdParNom(nomEnchereTextField.getText());
                String nomEnchere = nomEnchereTextField.getText();
                String nouvelleImage = image.getText();
                String nouvelleDateDebut = nouvelleDateDebutTextField.getText();
                String nouvelleDateFin = nouvelleDateFinTextField.getText();
                String nouveauMontantInitial = nouveauMontantInitialTextField.getText();

                EnchereService enchereService = new EnchereService();
                if (id == enchereService.rechercherIdParNom(nomEnchere)) {
                        enchereService = new EnchereService();
                        Enchere newEnchere = new Enchere();
                        newEnchere.setImage(nouvelleImage);
                        newEnchere.setDateDebut(nouvelleDateDebut);
                        newEnchere.setDateFin(nouvelleDateFin);
                        newEnchere.setMontantInitial(nouveauMontantInitial);
                        enchereService.modifierEnchere(id, newEnchere);
                }
                else {
                        System.out.println("Enchere not found");
                }

        }
}
