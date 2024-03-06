package com.example.test.models;

import java.util.Objects;

public class User {
    private int id;
    private String email;
    private String mdp;
    private Boolean status;
    private String nom;
    private String prenom;
    private String role;
    private String image;

    public User(int id, String email, String mdp, boolean status, String nom, String prenom,String role,String image) {

        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.status = status;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.image = image;
    }
    public User() {

    }

    public User(String s, int i) {
    }

    public String getVerification_code() {
        return verificationCode;
    }

    public void setVerification_code(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    private String verificationCode;
    public User(String email, String mdp, boolean status, String nom, String prenom,String role,String image) {

        this.email = email;
        this.mdp = mdp;
        this.status = status;
        this.nom = nom;
        this.prenom = prenom;
        this.role=role;
        this.image=image;
    }



    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", status=" + status +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(email, user.email) && Objects.equals(mdp, user.mdp) && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, mdp, nom, prenom);
    }
}

