package com.example.affariyetii.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketPaiment {

        private int ticketId;
        private int ticketp_id;

        private int client_id;
        private int enchere_id;

        // Constructeur
        public TicketPaiment(int ticketId, int client_id, int enchere_id) {

            this.ticketId = ticketId;
            this.client_id = client_id;
            this.enchere_id = enchere_id;
        }
        public TicketPaiment(){}

      //getters et setters

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketp_id(int ticketp_id) {
        this.ticketp_id = ticketp_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getEnchere_id() {
        return enchere_id;
    }

    public void setEnchere_id(int enchere_id) {
        this.enchere_id = enchere_id;
    }

    public int getTicketp_id() {
        return ticketp_id;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
    //toString
    public String toString(){
        return "TicketPaiment{" +
                "ticketId=" + ticketId +
                ", client_id=" + client_id +
                ", enchere_id=" + enchere_id +'}';
    }

}
