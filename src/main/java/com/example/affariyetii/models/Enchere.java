package com.example.affariyetii.models;

public class Enchere {
    private int enchereId,idclcreree,idclenchere;
    private String dateDebut,dateFin,nom_enchere,image,heured,heuref;
    private String montantInitial,montant_final;
    public Enchere(String image,String dateDebut,String dateFin, String montantInitial,String montant_final ,String nom_enchere,String heured,String heuref,int idclcreree){
        this.dateDebut=dateDebut;
        this.image=image;
        this.dateFin=dateFin;
        this.nom_enchere=nom_enchere;
        this.montantInitial=montantInitial;
        this.montant_final=montantInitial;
        this.heured=heured;
        this.heuref=heuref;
        this.idclcreree=idclcreree;

    }
    public Enchere(String dateDebut,String dateFin ,String montantInitial,String montant_final,String nom_enchere,String image,String heured,String heuref,int idclcreree,int idclenchere){
        this.nom_enchere=nom_enchere;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.image=image;
        this.montantInitial=montantInitial;
        this.montant_final=montant_final;
        this.heured=heured;
        this.heuref=heuref;
        this.idclcreree=idclcreree;
        this.idclenchere=idclenchere;
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
    public int getIdclenchere(){
        return idclenchere;}
    public void setIdclenchere(int idclenchere){
        this.idclenchere=idclenchere;
    }
    public int getIdclcreree(){
        return idclcreree;
    }
    public void setIdclcreree(int idclcreree){
        this.idclcreree=idclcreree;
    }
    public String getHeured(){
        return heured;
    }
    public String getHeuref(){
        return heuref;
    }
    public void setHeured(String heured){
        this.heured=heured;
    }
    public void setHeuref(String heuref){
        this.heuref=heuref;
    }
    // Méthode toString
    @Override
    public String toString() {
        return "Enchere{" +
//                "enchereId=" + enchereId +
                ", image='" + image + '\'' +
                ", cleint qui creer l'enchere'" + idclcreree + '\'' +
                ", cleint qui gagner l'enchere'" + idclenchere + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", heur debut='" + heured + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", heure Fin='" + heuref + '\'' +
                ", nom_enchere=" + nom_enchere +
                ", montantInitial=" + montantInitial +
                ", montantInitial=" + montant_final +
                '}';
    }

}
