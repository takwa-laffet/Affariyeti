package tn.esprit.affarietygui.models;

public class GrosMots {
    private int id_GM;
    private String GrosMots;

    public GrosMots() {
    }

    public GrosMots(int id_GM, String grosMots) {
        this.id_GM = id_GM;
        GrosMots = grosMots;
    }

    public int getId_GM() {
        return id_GM;
    }

    public void setId_GM(int id_GM) {
        this.id_GM = id_GM;
    }

    public String getGrosMots() {
        return GrosMots;
    }

    public void setGrosMots(String grosMots) {
        GrosMots = grosMots;
    }

    @Override
    public String toString() {
        return "GrosMots{" +
                "id_GM=" + id_GM +
                ", GrosMots='" + GrosMots + '\'' +
                '}';
    }
}
