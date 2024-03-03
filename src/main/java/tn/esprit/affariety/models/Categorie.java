package tn.esprit.affariety.models;

public class Categorie {
    private int id_c;
    private String nom_c;

    public Categorie() {

    }

    public Categorie(int id_c, String nom_c) {
        this.id_c = id_c;
        this.nom_c = nom_c;
    }

    public Categorie(String nom_c) {
        this.nom_c = nom_c;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public String getNom_c() {
        return nom_c;
    }

    public void setNom_c(String nom_c) {
        this.nom_c = nom_c;
    }

    //public String getDescription_c() {
       // return description_c;
    //}

    //public void setDescription_c(String description_c) {
        //this.description_c = description_c;
    //}

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return nom_c;

    }
}
