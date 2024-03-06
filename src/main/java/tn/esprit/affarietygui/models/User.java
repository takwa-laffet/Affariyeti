package tn.esprit.affarietygui.models;

public class User {
    private int id;
    private String email;
    private String mdp;
    private Boolean status;
    private String nom;
    private String prenom;
    private String verificationCode;
    private String role;

    public User() {
    }

    public User(int id, String email, String mdp, Boolean status, String nom, String prenom, String verificationCode, String role) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.status = status;
        this.nom = nom;
        this.prenom = prenom;
        this.verificationCode = verificationCode;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                ", verificationCode='" + verificationCode + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
