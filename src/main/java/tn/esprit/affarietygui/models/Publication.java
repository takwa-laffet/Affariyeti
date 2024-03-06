/*package tn.esprit.affarietygui.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
public class Publication {
    private int id_pub,id_client,id_commentaire,nb_likes,nb_dislike;
    private String contenu,photo;
   private Timestamp date_pub;

    public Publication() {
    }


    public Publication(int id_pub, int id_client, int nb_dislike, int nb_likes, Timestamp date_pub, String contenu , String photo){
        this.id_pub=id_pub;
        this.id_client=id_client;
        this.nb_likes=nb_likes;
        this.nb_dislike=nb_dislike;
        this.date_pub=date_pub;
        this.contenu=contenu;

        this.photo=photo;
    }
    public Publication(int id_pub,int id_client,int nb_dislike,int nb_likes, String contenu ,String photo){
        this.id_pub=id_pub;
        this.id_client=id_client;
        this.nb_likes=nb_likes;
        this.nb_dislike=nb_dislike;
        this.date_pub = Timestamp.valueOf(LocalDateTime.now());
        this.contenu=contenu;

        this.photo=photo;
    }
    public Publication(int id_client,int nb_dislike,int nb_likes , String contenu ,String photo){
        this.id_client=id_client;
        this.nb_likes=nb_likes;
        this.nb_dislike=nb_dislike;
        this.contenu=contenu;
        this.date_pub = Timestamp.valueOf(LocalDateTime.now());
        this.photo=photo;
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

    public int getNb_likes() {
        return nb_likes;
    }

    public void setNb_likes(int nb_likes) {
        this.nb_likes = nb_likes;
    }

    public int getNb_dislike() {
        return nb_dislike;
    }

    public void setNb_dislike(int nb_dislike) {
        this.nb_dislike = nb_dislike;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Timestamp date_pub) {
        this.date_pub = date_pub;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id_pub=" + id_pub +
                ", id_client=" + id_client +
                ", nb_likes=" + nb_likes +
                ", nb_dislike=" + nb_dislike +
                ", contenu='" + contenu + '\'' +
                ", photo='" + photo + '\'' +
                ", date_pub=" + date_pub +
                '}';
    }
}
*/
package tn.esprit.affarietygui.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Publication {
    private int id_pub, id_client, nb_likes, nb_dislike;
    private String contenu, photo;
    private Timestamp date_pub;
    private User user;

    public Publication() {
    }

    public Publication(int id_pub, User user, int nb_dislike, int nb_likes, Timestamp date_pub, String contenu, String photo) {
        this.id_pub = id_pub;
        this.user = user;
        this.nb_likes = nb_likes;
        this.nb_dislike = nb_dislike;
        this.date_pub = date_pub;
        this.contenu = contenu;
        this.photo = photo;
    }

    public Publication(User user, int nb_dislike, int nb_likes, String contenu, String photo) {
        this.user = user;
        this.nb_likes = nb_likes;
        this.nb_dislike = nb_dislike;
        this.contenu = contenu;
        this.date_pub = Timestamp.valueOf(LocalDateTime.now());
        this.photo = photo;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getNb_likes() {
        return nb_likes;
    }

    public void setNb_likes(int nb_likes) {
        this.nb_likes = nb_likes;
    }

    public int getNb_dislike() {
        return nb_dislike;
    }

    public void setNb_dislike(int nb_dislike) {
        this.nb_dislike = nb_dislike;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Timestamp date_pub) {
        this.date_pub = date_pub;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id_pub=" + id_pub +
               " Nom utilisateur: " + (user != null ? user.getNom() : "N/A") +
        ", prénom utilisateur: " + (user != null ? user.getPrenom() : "N/A")+
        ", nb_likes=" + nb_likes +
                ", nb_dislike=" + nb_dislike +
                ", contenu='" + contenu + '\'' +
                ", photo='" + photo + '\'' +
                ", date_pub=" + date_pub +
                '}';
    }
}
