package tn.esprit.affariety.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import tn.esprit.affariety.models.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import tn.esprit.affariety.services.ProduitService;

/**
 *
 * @author SUWIMA
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private GridPane cardHolder;
    ObservableList<CustomerCard> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ProduitService getallproduits=new ProduitService();

        ObservableList<Produit> reservations = FXCollections.observableArrayList();



        try {
        for (int i=0;i<getallproduits.recuperer().size();i++){

            System.out.println(getallproduits.recuperer().get(i).getImage_p());




        list.add(new CustomerCard(getallproduits.recuperer().get(i).getId_p(),getallproduits.recuperer().get(i).getNom_p(), getallproduits.recuperer().get(i).getPrix_p(), getallproduits.recuperer().get(i).getImage_p()));
            System.out.println(list.get(i));
        cardHolder.setAlignment(Pos.CENTER);
        cardHolder.setVgap(20.00);
        cardHolder.setHgap(20.00);
        cardHolder.setStyle("-fx-padding:10px;-fx-border-color:transparent");
        }
            int count = 0;
            int maxCardsPerRow = 3;

            for (int i = 0; i < getallproduits.recuperer().size(); i += maxCardsPerRow) {

                for (int j = 0; j < maxCardsPerRow && (i + j) < getallproduits.recuperer().size(); j++) {
                    cardHolder.add(list.get(count), j, i / maxCardsPerRow);
                    count++;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void onSearch() {
        int count = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                cardHolder.add(list.get(count), j, i);
                count++;

            }
        }

    }
}