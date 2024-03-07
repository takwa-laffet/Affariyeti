package tn.esprit.affariety.models;

public class Article {
    private int id;
    private String nomArticle;
    private String categorie;
    private float prix;

    public Article() {
    }

    public Article(int id, String nomArticle, String categorie, float prix) {
        this.id = id;
        this.nomArticle = nomArticle;
        this.categorie = categorie;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nomArticle='" + nomArticle + '\'' +
                ", categorie='" + categorie + '\'' +
                ", prix=" + prix +
                '}';
    }
}
