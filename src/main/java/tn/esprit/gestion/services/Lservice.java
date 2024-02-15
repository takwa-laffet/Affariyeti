package tn.esprit.gestion.services;

import tn.esprit.gestion.models.Livraison;

import java.sql.SQLException;
import java.util.List;

public interface Lservice<T> {
    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    List<Livraison> recuperer() throws SQLException;

    // Sorting method for Livraison
    List<Livraison> tri() throws SQLException;

    // Searching method for Livraison by date



    List<Livraison> recherche(int id) throws SQLException;
}
