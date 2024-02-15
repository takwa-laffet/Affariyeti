package tn.esprit.gestion.models;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public class Livraison {

    private int id;
    private String adresselivraison;
    private Timestamp datecommande;
    private Date datelivraison;
    private String statuslivraison;
    private int iddepot;
    private Depot depot; // Add a Depot property

    public Livraison(int id, String adresselivraison, Timestamp datecommande, Date datelivraison, String statuslivraison, int iddepot) {
        this.id = id;
        this.adresselivraison = adresselivraison;
        this.datecommande = datecommande;
        this.datelivraison = datelivraison;
        this.statuslivraison = statuslivraison;
        this.iddepot = iddepot;
    }

    public Livraison() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresselivraison() {
        return adresselivraison;
    }

    public void setAdresselivraison(String adresselivraison) {
        this.adresselivraison = adresselivraison;
    }

    public Timestamp getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(Timestamp datecommande) {
        this.datecommande = datecommande;
    }

    public Date getDatelivraison() {
        return datelivraison;
    }

    public void setDatelivraison(Date datelivraison) {
        this.datelivraison = datelivraison;
    }

    public String getStatuslivraison() {
        return statuslivraison;
    }

    public void setStatuslivraison(String statuslivraison) {
        this.statuslivraison = statuslivraison;
    }

    public int getIddepot() {
        return iddepot;
    }

    public void setIddepot(int iddepot) {
        this.iddepot = iddepot;
    }



    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public String getAdresse() {
        return this.getAdresse();
    }
}









