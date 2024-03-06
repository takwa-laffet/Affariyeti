package com.example.affariyetii.services;

import com.example.affariyetii.models.Enchere;

import java.sql.SQLException;
import java.util.List;

public interface Eservice<T> {
    void ajouter(T t) throws SQLException;

    void ajouterafine(Enchere enchere);

    void modifier(T t) throws SQLException;
    void supprimer(int id);

    List<T> reuperer();

    void modifierEnchere(int id, Enchere newEnchere);

    // Add this method to your EnchereService class
    int rechercherIdParNom(String nomEnchere);

    List<Enchere> getTopSaleAuctions();
}
