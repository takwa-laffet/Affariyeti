package com.example.test.services;

import com.example.test.models.CodePromo;
import com.example.test.models.Discount;
import com.example.test.utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionDiscount implements Fonctions<Discount> {

    private Connection cnx;

    public GestionDiscount() {
        this.cnx = DB.getInstance().getConnection();
    }

    @Override
    public void Create(Discount codePromo) {
        try {
            // Vérifier si 'idCode' est null avant l'insertion


            String sql = "INSERT INTO discount ( codePromoId, userId) VALUES ( ?, ?)";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromo.getCodePromo().getIdCode());
                st.setInt(2, codePromo.getUser().getId());

                st.executeUpdate();

                System.out.println("Discount created successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating Discount : " + e.getMessage());
        }
    }

    @Override
    public void delete(Discount codePromo) {
        try {
            String sql = "DELETE FROM discount WHERE idD=?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromo.getIdD());
                st.executeUpdate();

                System.out.println("Discount deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Discount : " + e.getMessage());
        }
    }

    @Override
    public void update(Discount codePromo) {
        try {
            String sql = "UPDATE discount SET codePromoId=?, userId=? WHERE idD=?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromo.getCodePromo().getIdCode());
                st.setInt(2, codePromo.getUser().getId());
                st.setInt(3, codePromo.getIdD());

                st.executeUpdate();

                System.out.println("discount updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating discount : " + e.getMessage());
        }
    }

    @Override
    public List<Discount> findAll() {
        List<Discount> codePromoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM discount";
            GestionUser gu = new GestionUser();
            GestionCodePromo ccp = new GestionCodePromo();
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Discount codePromo = new Discount(

                                  gu.findById(rs.getInt("userId")),//,,Assuming you have a constructor in User that takes an int
                                ccp.findById(rs.getInt("codePromoId") ),// Assuming you have a constructor in CategorieCodePromo.java that takes a String

                                rs.getInt("idD")
                        );
                        codePromoList.add(codePromo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading code promos: " + e.getMessage());
        }
        return codePromoList;
    }

    @Override
    public Discount findById(int codePromoId) {
        Discount codePromo = null;
        try {
            String sql = "SELECT * FROM discount WHERE idD = ?";
            GestionUser gu = new GestionUser();
            GestionCodePromo  ccp = new GestionCodePromo();
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromoId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        codePromo = new Discount(

                                gu.findById(rs.getInt("userId")),//,,Assuming you have a constructor in User that takes an int
                                ccp.findById(rs.getInt("codePromoId") ),// Assuming you have a constructor in CategorieCodePromo.java that takes a String

                                rs.getInt("idD")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding code promo by id: " + e.getMessage());
        }
        return codePromo;
    }

    // You may add additional methods based on your requirements

}

