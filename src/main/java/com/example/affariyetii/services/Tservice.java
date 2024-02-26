package com.example.affariyetii.services;

import com.example.affariyetii.models.Enchere;

import java.sql.SQLException;
import java.util.List;

public interface Tservice<T> {

    void ajouter(T t) throws SQLException;


    void modifier(T t) throws SQLException;
    void supprimer(int id);

    List<T> reuperer();
}


