package tn.esprit.affariety.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Livraison {
    private int id;
    private String adresselivraison;
    private Timestamp datecommande;
    private Date datelivraison;
    private String statuslivraison;
    private int iddepot;
    private float longitude;
    private float latitude;


    public Livraison() {
    }

    public Livraison(int id, String adresselivraison, Timestamp datecommande, Date datelivraison, String statuslivraison, int iddepot, float longitude, float latitude) {
        this.id = id;
        this.adresselivraison = adresselivraison;
        this.datecommande = datecommande;
        this.datelivraison = datelivraison;
        this.statuslivraison = statuslivraison;
        this.iddepot = iddepot;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public Livraison(String text, Timestamp datecommande, Date date, String string, int iddepot, float latitude, float longitude) {

        this.adresselivraison = text;
        this.datecommande = datecommande;
        this.datelivraison = date;
        this.statuslivraison = string;
        this.iddepot = iddepot;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

}