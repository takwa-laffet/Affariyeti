package tn.esprit.gestion.models;

public class Ticket {
    private int ticketId;

    private int enchereId;
    private double prix;

    // Constructeur
    public Ticket(int ticketId, int enchereId, double prix) {

        this.ticketId = ticketId;
        this.enchereId = enchereId;
        this.prix = prix;
    }
    public Ticket(){}

// Getters et setters
public int getTicketId() {
    return ticketId;
}

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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

    // Méthode toString
    @Override
    public String toString() {
        return "Ticket{" +
                "enchereId=" + enchereId +
     //           ", ticketId=" + ticketId +
                ", prix=" + prix + '\''+
                '}';
    }

}
