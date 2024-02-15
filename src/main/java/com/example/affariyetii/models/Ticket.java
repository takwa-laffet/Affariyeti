package com.example.affariyetii.models;

public class Ticket {
    private int ticketId;
    private int clientId;
    private int enchereId;
    private double prix;
    private String dateEnchere;

    // Constructeur
    public Ticket(int ticketId,int clientId, int enchereId, double prix, String dateEnchere) {
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.enchereId = enchereId;
        this.prix = prix;
        this.dateEnchere = dateEnchere;
    }
    public Ticket(){}

// Getters et setters
public int getTicketId() {
    return ticketId;
}

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getEnchereId() {
        return enchereId;
    }

    public void setEnchereId(int enchereId) {
        this.enchereId = enchereId;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(String dateEnchere) {
        this.dateEnchere = dateEnchere;
    }
    // Méthode toString
    @Override
    public String toString() {
        return "Ticket{" +
                "enchereId=" + enchereId +
                ", clientId=" + clientId +
                ", ticketId=" + ticketId +
                ", prix=" + prix +
                ", dateEnchere='" + dateEnchere + '\'' +
                '}';
    }

}
