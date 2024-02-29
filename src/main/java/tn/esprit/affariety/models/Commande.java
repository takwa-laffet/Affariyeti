package tn.esprit.affariety.models;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Commande {
    // Member variables should be private to encapsulate them
    private int id;
    private int cmd_client;
    private String etat;
    private Timestamp cmd_date;

    // Constructor with all parameters
    public Commande(int id, int cmd_client, String etat, Timestamp cmd_date) {
        this.id = id;
        this.cmd_client = cmd_client;
        this.etat = etat;
        this.cmd_date = cmd_date;
    }

    // Constructor without id (assuming id will be generated automatically)
    public Commande(int cmd_client, String etat) {
        this.cmd_client = cmd_client;
        this.etat = etat;
        this.cmd_date = Timestamp.valueOf(LocalDateTime.now());

    }

    public Commande(int id, int cmd_client, String etat) {
        this.id = id;
        this.cmd_client = cmd_client;
        this.etat = etat;
        this.cmd_date = Timestamp.valueOf(LocalDateTime.now());
    }

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCmd_client() {
        return cmd_client;
    }

    public void setCmd_client(int cmd_client) {
        this.cmd_client = cmd_client;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Timestamp getCmd_date() {
        return cmd_date;
    }

    public void setCmd_date(Timestamp cmd_date) {
        this.cmd_date = cmd_date;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", cmd_client=" + cmd_client +
                ", etat='" + etat + '\'' +
                ", cmd_date=" + cmd_date +
                '}';
    }
}
