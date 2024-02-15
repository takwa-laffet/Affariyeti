package com.example.affariyetii.models;

public class Tickect {
    private int ticketId;
    private int clientId;
    private int enchereId;
    private double montantEnchere;
    private String dateEnchere;

    // Constructeur
    public Ticket(int clientId, int enchereId, double montantEnchere, String dateEnchere) {
        this.clientId = clientId;
        this.enchereId = enchereId;
        this.montantEnchere = montantEnchere;
        this.dateEnchere = dateEnchere;
    }
    public Tickect(){}

}
