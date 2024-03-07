package tn.esprit.affariety.models;

public class DetailsCommande {

    private int id, id_com, num_article, quantite;
    private String nom_article;
    private float prix_unitaire, sous_totale;
    private Commande commande;

    public DetailsCommande() {
    }
    public DetailsCommande(Commande commande,int num_article, int quantite, String nom_article, float prix_unitaire, float sous_totale) {
        this.commande=commande;
        this.num_article = num_article;
        this.quantite = quantite;
        this.nom_article = nom_article;
        this.prix_unitaire = prix_unitaire;
        this.sous_totale = sous_totale;

    }

   /* public DetailsCommande(int id_com,int num_article, int quantite, String nom_article, float prix_unitaire, float sous_totale) {
        this.id_com = id_com;
        this.num_article = num_article;
        this.quantite = quantite;
        this.nom_article = nom_article;
        this.prix_unitaire = prix_unitaire;
        this.sous_totale = sous_totale;
    }*/

    /*public DetailsCommande(int id, int id_com, int num_article, int quantite, String nom_article, float prix_unitaire, float sous_totale) {public DetailsCommande(int id, int num_article, int quantite, String nom_article, float prix_unitaire, float sous_totale) {
        this.id = id;
        this.num_article = num_article;
        this.quantite = quantite;
        this.nom_article = nom_article;
        this.prix_unitaire = prix_unitaire;
        this.sous_totale = sous_totale;
    }*/

    public DetailsCommande(int num_article, int quantite, String nom_article, float prix_unitaire, float sous_totale) {
        this.num_article = num_article;
        this.quantite = quantite;
        this.nom_article = nom_article;
        this.prix_unitaire = prix_unitaire;
        this.sous_totale = sous_totale;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_article() {
        return num_article;
    }

    public void setNum_article(int num_article) {
        this.num_article = num_article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public float getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(float prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public float getSous_totale() {
        return sous_totale;
    }

    public void setSous_totale(float sous_totale) {
        this.sous_totale = sous_totale;
    }

    public int getId_com() {
        return id_com;


    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    @Override
    public String toString() {
        return "DetailsCommande{" +
                "id=" + id +
                ", id_com=" + id_com +
                ", num_article=" + num_article +
                ", quantite=" + quantite +
                ", nom_article='" + nom_article + '\'' +
                ", prix_unitaire=" + prix_unitaire +
                ", sous_totale=" + sous_totale +
                ", commande=" + commande +
                '}';
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}

