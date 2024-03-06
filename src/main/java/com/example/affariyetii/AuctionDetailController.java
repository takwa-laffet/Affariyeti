package com.example.affariyetii;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.services.EnchereService;
import com.example.affariyetii.services.SocialMediaSharing;
import com.example.affariyetii.services.TicketPaimentService;
import com.example.affariyetii.services.TicketService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AuctionDetailController {
    @FXML
    private ImageView auctionImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

     @FXML
     private VBox Vbox;
    @FXML
    private Label Datedebut;
    @FXML
    private Label Datefin;

    public void initialize(Enchere enchere) {
        auctionImageView.setImage(new Image(enchere.getImage()));
        nameLabel.setText(enchere.getNom_enchere());
        Datedebut.setText("Date debut: " + enchere.getDateDebut());
        Datefin.setText("Date fin: " + enchere.getDateFin());
        priceLabel.setText("Montant initial: " + enchere.getMontantInitial() + " dt");
    }

    @FXML
    private void participateAction() {
    }
    private void showAlert(String error, String s) {
    }
    @FXML
    private void deleteAction() {
        Enchere enchere = new Enchere();
        EnchereService enchereService = new EnchereService();
        TicketService ticketService = new TicketService();
        TicketPaimentService ticketPaimentService = new TicketPaimentService();
        int id= enchereService.rechercherIdParNom(nameLabel.getText());

       if (ticketPaimentService.getEnchereIdinTicketp(id) == id){
           showAlert("error","Enchere est deja payé");
       }
       else{
           ticketService.supprimer(id);
           enchereService.supprimer(id);
       }

    }
    public void shareOnSocialMedia() {
        Enchere enchere = new Enchere();
        enchere.setNom_enchere(nameLabel.getText()); // Obtenez le nom de l'enchère à partir de votre Label
        enchere.setMontantInitial(String.valueOf(Double.parseDouble(priceLabel.getText().replace("Montant initial: ", "").replace(" dt", "")))); // Obtenez le montant initial de l'enchère à partir de votre Label
        enchere.setDateDebut(Datedebut.getText().replace("Date debut: ", "")); // Obtenez la date de début de l'enchère à partir de votre Label
        enchere.setDateFin(Datefin.getText().replace("Date fin: ", "")); // O
        SocialMediaSharing socialMediaSharing = new SocialMediaSharing();
        socialMediaSharing.shareOnFacebook(enchere);
    }
}
