/*package tn.esprit.affarietygui.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Commentaire {
    private int id_com,id_pub,id_client;
    private String contenu;
    private Timestamp date_com;
    private Publication publication;
    public Commentaire() {
    }
    public Commentaire(int id_com,Publication publication, int id_client, String contenu , Timestamp date_com) {
        this.id_com=id_com;
        this.publication=publication;
        this.id_client=id_client;
        this.contenu=contenu;
        this.date_com=date_com;
    }

    public Commentaire(Publication publication, int id_client, String contenu ) {
        this.publication=publication;
        this.id_client=id_client;
        this.contenu=contenu;
        this.date_com=Timestamp.valueOf(LocalDateTime.now());
    }
    public Commentaire(int id_com, int id_pub, int id_client, String contenu ) {
        this.id_com=id_com;
        this.id_pub=id_pub;
        this.id_client=id_client;
        this.contenu=contenu;
        this.date_com=Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDate_com() {
        return date_com;
    }

    public void setDate_com(Timestamp date_com) {
        this.date_com = date_com;
    }
    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
    @Override
    public String toString() {
        return  " id_client=" + id_client +" commentaire:  " +
             //   "id_com=" + id_com +
               // ", id_pub=" + id_pub +

                contenu + '\'' +
                "date : " + date_com +
             //   ",publication=" + (publication != null ? publication : "N/A") +
                '}';
    }
}*/
package tn.esprit.affariety.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Commentaire {
    private int id_com, id_pub, id_client;
    private String contenu;
    private Timestamp date_com;
    private Publication publication;
    private User user;

    public Commentaire() {
    }

    public Commentaire(int id_com, Publication publication, User user, String contenu, Timestamp date_com) {
        this.id_com = id_com;
        this.publication = publication;
        this.user = user;
        this.contenu = contenu;
        this.date_com = date_com;
    }

    public Commentaire(Publication publication, User user, String contenu) {
        this.publication = publication;
        this.user = user;
        this.contenu = contenu;
        this.date_com = Timestamp.valueOf(LocalDateTime.now());
    }

    public Commentaire(int id_com, int id_pub, User user, String contenu) {
        this.id_com = id_com;
        this.id_pub = id_pub;
        this.user = user;
        this.contenu = contenu;
        this.date_com = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDate_com() {
        return date_com;
    }

    public void setDate_com(Timestamp date_com) {
        this.date_com = date_com;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Nom utilisateur: " + (user != null ? user.getNom() : "N/A") +
                ", prénom utilisateur: " + (user != null ? user.getPrenom() : "N/A") +
                ", commentaire:  " +
                contenu + '\'' +
                ", date : " + date_com;
    }
}
