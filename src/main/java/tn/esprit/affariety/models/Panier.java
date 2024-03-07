package tn.esprit.affariety.models;

public class Panier {
    private int id,quantite;
    private String nom_article;
    private float prix ,totale;

    public Panier() {
    }

    public Panier(int id, int quantite, String nom_article, float prix, float totale) {
        this.id = id;
        this.quantite = quantite;
        this.nom_article = nom_article;
        this.prix = prix;
        this.totale = totale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", nom_article='" + nom_article + '\'' +
                ", prix=" + prix +
                ", totale=" + totale +
                '}';
    }
}
