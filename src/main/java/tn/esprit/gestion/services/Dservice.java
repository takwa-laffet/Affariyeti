package tn.esprit.gestion.services;

import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.models.Livraison;

import java.sql.SQLException;
import java.util.List;

public interface Dservice<T> {
    void ajouter(T t)  throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;

    List<Depot> rechercher(Depot depot) throws SQLException;

}
