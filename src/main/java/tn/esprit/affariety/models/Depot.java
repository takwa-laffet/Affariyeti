package tn.esprit.affariety.models;

import java.util.ArrayList;
import java.util.List;

public class Depot {
    private int iddepot;
    private String nomdepot;
    private String adresse;




    public Depot(int iddepot, String nomdepot, String adresse) {
        this.iddepot = iddepot;
        this.nomdepot = nomdepot;
        this.adresse = this.adresse;

    }

    public Depot(String nomdepot, String adresse) {
        this.nomdepot = nomdepot;
        this.adresse = adresse;
    }

    public Depot() {
    }

    public Depot(int iddepot) {
    }

    public int getIddepot() {
        return iddepot;
    }
    public void setIddepot(int iddepot) {
        this.iddepot = iddepot;
    }
    public String getNomdepot() {
        return nomdepot;
    }
    public void setNomdepot(String nomdepot) {
        this.nomdepot = nomdepot;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return "Depot{" +
                "iddepot=" + iddepot +
                "nomdepot='" + nomdepot + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
