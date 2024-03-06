package tn.esprit.gestion.services;

import tn.esprit.gestion.models.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface Tpservice<T> {

    void ajouter(T t) throws SQLException;


    void modifier(T t) throws SQLException;
    void supprimer(int id);

    List<T> reuperer(String nomEnchere, int clientId);

    List<Ticket> reuperercl(String clientName);

}



