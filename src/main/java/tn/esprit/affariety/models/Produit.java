package tn.esprit.affariety.models;

import javafx.scene.image.Image;

public class Produit {
    private int id_p ;
    private int id_client;

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    private String nom_p , description_p ;
    private float prix_p;
    private Categorie categorie;
    private  User user;
    private String image_p;
    private int id_c;
    public Produit(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produit (int id_p, User id_client, String nom_p , String description_p , Float prix_p, String image_p, Categorie categorie){
        this.id_p = id_p;
        this.nom_p = nom_p;
        this.description_p = description_p;
        this.prix_p = prix_p;
        this.image_p = image_p;
        this.categorie =  categorie;
        this.user=id_client;


    }
    public Produit (String nom_p , String description_p ,Float prix_p, String image_p , Categorie categorie ){
        this.nom_p = nom_p;
        this.description_p = description_p;
        this.prix_p = prix_p;
        this.image_p = image_p;
        this.categorie =  categorie;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p){
        this.id_p = id_p;
    }

    public String getNom_p() {
        return nom_p;
    }

    public void setNom_p(String nom_p){
        this.nom_p = nom_p;
    }

    public String getDescription_p() {
        return description_p;
    }

    public void setDescription_p(String description_p){this.description_p = description_p;
    }
    public String getImage_p() {
        return image_p;
    }

    public void setImage_p(String image_p){this.image_p = image_p;
    }

    public float getPrix_p() {
        return prix_p;
    }

    public void setPrix_p(float prix_p) {
        this.prix_p = prix_p;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String toString() {
        return "Produit {nom_produit=" + nom_p +
                ", description_produit=" + description_p +
                ", prix_produit=" + prix_p +
                ", categorie=" + (categorie != null ? categorie : "N/A") +
                "}";
    }


}

