package com.example.affariyetii.models;

public class Enchere {
    private int enchereId,articleId;
    private String dateDebut,dateFin,nom_enchere;
    private double montantInitial;
    public Enchere(int enchereId, int articleId, String dateDebut,String dateFin ,String nom_enchere,double montantInitial){
        this.enchereId=enchereId;
        this.articleId=articleId;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.nom_enchere=nom_enchere;
        this.montantInitial=montantInitial;

    }
    public Enchere(){ }
    //getters and setters
    public int getEnchereId() {
        return enchereId;
    }

    public void setEnchereId(int enchereId) {
        this.enchereId = enchereId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public double getMontantInitial() {
        return montantInitial;
    }

    public void setMontantInitial(double montantInitial) {
        this.montantInitial = montantInitial;
    }
    public String getNom_enchere() {
        return nom_enchere;
    }

    public void setNom_enchere(String nom_enchere) {
        this.nom_enchere = nom_enchere;
    }
    // Méthode toString
    @Override
    public String toString() {
        return "Enchere{" +
                "enchereId=" + enchereId +
                ", articleId=" + articleId +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", nom_enchere=" + nom_enchere +
                ", montantInitial=" + montantInitial +
                '}';
    }

}
