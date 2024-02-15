package tn.esprit.affarietygui.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Commentaire {
    private int id_com,id_pub,id_article,id_client;
    private String contenu;
    private Timestamp date_com;
    public Commentaire() {
    }
    public Commentaire(int id_com, int id_pub , int id_article, int id_client, String contenu , Timestamp date_com) {
        this.id_com=id_com;
        this.id_pub=id_pub;
        this.id_article=id_article;
        this.id_client=id_client;
        this.contenu=contenu;
        this.date_com=date_com;
    }
    public Commentaire(int id_com, int id_article, int id_client, String contenu ) {
        this.id_com=id_com;
        this.id_article=id_article;
        this.id_client=id_client;
        this.contenu=contenu;
        this.date_com=Timestamp.valueOf(LocalDateTime.now());
    }
    public Commentaire(int id_com, int id_pub , int id_article, int id_client, String contenu ) {
        this.id_com=id_com;
        this.id_pub=id_pub;
        this.id_article=id_article;
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

    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
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

    @Override
    public String toString() {
        return "commentaire{" +
                "id_com=" + id_com +
                ", id_pub=" + id_pub +
                ", id_article=" + id_article +
                ", id_client=" + id_client +
                ", contenu='" + contenu + '\'' +
                ", date_com=" + date_com +
                '}';
    }
}
