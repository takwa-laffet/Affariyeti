package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Depot;
import tn.esprit.affariety.models.Livraison;

import java.sql.SQLException;
import java.util.List;

public interface Dservice<T> {
    void ajouter(T t)  throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;

    List<Depot> rechercher(Depot depot) throws SQLException;

}
