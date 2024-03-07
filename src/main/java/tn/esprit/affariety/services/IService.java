package tn.esprit.affariety.services;
import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    void ajouter(T t) throws SQLException;
    int ajouterr(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(String nom) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;

}
