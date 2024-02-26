package com.example.affariyetii.models;

public class Enchere {
    private int enchereId;
    private String dateDebut,dateFin,nom_enchere,image;
    private String montantInitial,montant_final;
    public Enchere(String image,String dateDebut,String dateFin, String montantInitial,String nom_enchere){
        this.dateDebut=dateDebut;
        this.image=image;
        this.dateFin=dateFin;
        this.nom_enchere=nom_enchere;
        this.montantInitial=montantInitial;

    }
    public Enchere(String dateDebut,String dateFin ,String montantInitial,String montant_final,String nom_enchere,String image){
        this.nom_enchere=nom_enchere;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.image=image;
        this.montantInitial=montantInitial;
        this.montant_final=montant_final;
    }

    public Enchere(){ }
    //getters and setters
    public int getEnchereId() {
        return enchereId;
    }

    public void setEnchereId(int enchereId) {
        this.enchereId = enchereId;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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

    public String getMontantInitial() {
        return montantInitial;
    }

    public void setMontantInitial(String montantInitial) {
        this.montantInitial = montantInitial;
    }
    public String getNom_enchere() {
        return nom_enchere;
    }

    public void setNom_enchere(String nom_enchere) {

        this.nom_enchere = nom_enchere;
    }
    public String getMontant_final() {
        return montant_final;
    }
    public void setMontant_final(String montant_final) {
        this.montant_final = montant_final;
    }
    // Méthode toString
    @Override
    public String toString() {
        return "Enchere{" +
//                "enchereId=" + enchereId +
                ", image='" + image + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", nom_enchere=" + nom_enchere +
                ", montantInitial=" + montantInitial +
                ", montantInitial=" + montant_final +
                '}';
    }

}
