package tn.esprit.affariety.models;

public class DetailsCommande {

    private int id,num_article,quantite;
    private String nom_article;
    private float prix_unitaire,sous_totale;

    public DetailsCommande() {
    }

    public DetailsCommande(int id, int num_article, int quantite, String nom_article, float prix_unitaire, float sous_totale) {
        this.id = id;
        this.num_article = num_article;
        this.quantite = quantite;
        this.nom_article = nom_article;
        this.prix_unitaire = prix_unitaire;
        this.sous_totale = sous_totale;
    }

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

    @Override
    public String toString() {
        return "DetailsCommande{" +
                "id=" + id +
                ", num_article=" + num_article +
                ", quantite=" + quantite +
                ", nom_article='" + nom_article + '\'' +
                ", prix_unitaire=" + prix_unitaire +
                ", sous_totale=" + sous_totale +
                '}';
    }
}
