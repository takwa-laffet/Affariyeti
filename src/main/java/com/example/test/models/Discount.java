package com.example.test.models;

import java.util.Objects;

public class Discount {
    private User user ;
    private CodePromo codePromo ;
    private int idD ;

    public Discount(User user, CodePromo codePromo, int idD) {
        this.user = user;
        this.codePromo = codePromo;
        this.idD = idD;
    }

    public Discount(User user, CodePromo codePromo) {
        this.user = user;
        this.codePromo = codePromo;
    }

    public Discount() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CodePromo getCodePromo() {
        return codePromo;
    }

    public void setCodePromo(CodePromo codePromo) {
        this.codePromo = codePromo;
    }

    public int getIdD() {
        return idD;
    }

    public void setIdD(int idD) {
        this.idD = idD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(user, discount.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "user=" + user +
                ", codePromo=" + codePromo +
                ", idD=" + idD +
                '}';
    }
}
