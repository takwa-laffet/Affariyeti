package com.example.affariyetii.services;

import com.example.affariyetii.models.Ticket;
import com.example.affariyetii.models.TicketPaiment;

import java.sql.SQLException;
import java.util.List;

public interface Tpservice<T> {

    void ajouter(T t) throws SQLException;


    void modifier(T t) throws SQLException;
    void supprimer(int id);

    List<T> reuperer(String nomEnchere, int clientId);

    List<Ticket> reuperercl(String clientName);

}



