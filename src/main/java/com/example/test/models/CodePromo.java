package com.example.test.models;

import java.util.Objects;

public class CodePromo {
    private int idCode;
    private User user;
    private CategorieCodePromo categorieCodePromo;

    public CodePromo(int idCode, User user, CategorieCodePromo categorieCodePromo) {
        this.idCode = idCode;
        this.user = user;
        this.categorieCodePromo = categorieCodePromo;
    }
    public CodePromo(User user, CategorieCodePromo categorieCodePromo) {
        this.user = user;
        this.categorieCodePromo = categorieCodePromo;
    }
    public CodePromo() {
    }

    public int getIdCode() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode = idCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategorieCodePromo getCategorieCodePromo() {
        return categorieCodePromo;
    }

    public void setCategorieCodePromo(CategorieCodePromo categorieCodePromo) {
        this.categorieCodePromo = categorieCodePromo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodePromo codePromo = (CodePromo) o;
        return idCode == codePromo.idCode && Objects.equals(user, codePromo.user)
                && Objects.equals(categorieCodePromo, codePromo.categorieCodePromo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCode, user, categorieCodePromo);
    }

    @Override
    public String toString() {
        return "CodePromo{" +
                "idCode=" + idCode +
                ", user=" + user +
                ", categorieCodePromo=" + categorieCodePromo +
                '}';
    }
}
