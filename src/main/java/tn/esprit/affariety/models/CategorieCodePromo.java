package tn.esprit.affariety.models;

import java.util.Objects;

public class CategorieCodePromo {
    private int idCcp;
    private String code;
    private int valeur;
    private int limite;

    public CategorieCodePromo() {
    }

    public CategorieCodePromo(String code, int valeur, int limite) {
        this.code = code;
        this.valeur = valeur;
        this.limite = limite;
    }

    public CategorieCodePromo(int idCcp, String code, int valeur, int limite) {
        this.idCcp = idCcp;
        this.code = code;
        this.valeur = valeur;
        this.limite = limite;
    }

    public int getIdCcp() {
        return idCcp;
    }

    public void setIdCcp(int idCcp) {
        this.idCcp = idCcp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorieCodePromo that = (CategorieCodePromo) o;
        return idCcp == that.idCcp && valeur == that.valeur && limite == that.limite && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCcp, code, valeur, limite);
    }

    @Override
    public String toString() {
        return "CategorieCodePromo.java{" +
                "idCcp=" + idCcp +
                ", code='" + code + '\'' +
                ", valeur=" + valeur +
                ", limite=" + limite +
                '}';
    }
}
