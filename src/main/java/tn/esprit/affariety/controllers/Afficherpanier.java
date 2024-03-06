package tn.esprit.affariety.controllers;

import javafx.collections.ObservableList;
import tn.esprit.affariety.models.Panier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.services.PanierService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.BreakIterator;
import java.util.List;


public class Afficherpanier {
    ObservableList<Panier> list = FXCollections.observableArrayList();


    @FXML
    private TextField quantiteTextField;

    @FXML
    private TextField prixTextField;

    @FXML
    private ListView<Panier> panierListView;

    public int idp;
    public float prix;
    public String nom;

    private final PanierService ps = new PanierService();


    public Article article ;






    @FXML
    void initialize() {
        try {
            List<Panier> co = ps.recuperer();
            ObservableList<Panier> ob = FXCollections.observableList(co);
            panierListView.setItems(ob);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
