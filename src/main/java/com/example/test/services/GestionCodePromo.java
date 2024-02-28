package com.example.test.services;

import com.example.test.models.CodePromo;
import com.example.test.models.User;  // Import User class
import com.example.test.models.CategorieCodePromo;  // Import CategorieCodePromo.java class
import com.example.test.utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionCodePromo implements Fonctions<CodePromo> {

    private Connection cnx;

    public GestionCodePromo() {
        this.cnx = DB.getInstance().getConnection();
    }

    @Override
    public void Create(CodePromo codePromo) {
        try {
            // Vérifier si 'idCode' est null avant l'insertion


            String sql = "INSERT INTO codepromo ( idCategorieCode, userId) VALUES ( ?, ?)";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromo.getCategorieCodePromo().getIdCcp());
                st.setInt(2, codePromo.getUser().getId());

                st.executeUpdate();

                System.out.println("Code promo created successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating code promo: " + e.getMessage());
        }
    }

    @Override
    public void delete(CodePromo codePromo) {
        try {
            String sql = "DELETE FROM codepromo WHERE idCode=?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromo.getIdCode());
                st.executeUpdate();

                System.out.println("Code promo deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting code promo: " + e.getMessage());
        }
    }

    @Override
    public void update(CodePromo codePromo) {
        try {
            String sql = "UPDATE codepromo SET idCategorieCode=?, userId=? WHERE idCode=?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromo.getCategorieCodePromo().getIdCcp());
                st.setInt(2, codePromo.getUser().getId());
                st.setInt(3, codePromo.getIdCode());

                st.executeUpdate();

                System.out.println("Code promo updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating code promo: " + e.getMessage());
        }
    }

    @Override
    public List<CodePromo> findAll() {
        List<CodePromo> codePromoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM codepromo";
            GestionUser gu = new GestionUser();
            GestionCategorieCodePromo  ccp = new GestionCategorieCodePromo();
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CodePromo codePromo = new CodePromo(
                                rs.getInt("idCode"),
                                gu.findById(rs.getInt("userId")),  // Assuming you have a constructor in User that takes an int
                               ccp.findById(rs.getInt("idCategorieCode") )// Assuming you have a constructor in CategorieCodePromo.java that takes a String

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
    public CodePromo findById(int codePromoId) {
        CodePromo codePromo = null;
        try {
            String sql = "SELECT * FROM codepromo WHERE idCode = ?";
            GestionUser gu = new GestionUser();
            GestionCategorieCodePromo  ccp = new GestionCategorieCodePromo();
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, codePromoId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        codePromo = new CodePromo(
                                rs.getInt("idCode"),
                               gu.findById(rs.getInt("userId")),  // Assuming you have a constructor in User that takes an int
                                ccp.findById(rs.getInt("idCategorieCode"))  // Assuming you have a constructor in CategorieCodePromo.java that takes a String

                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding code promo by id: " + e.getMessage());
        }
        return codePromo;
    }
    public int checkCodePromoByName(CodePromo codePromo,CategorieCodePromo ccp) {
        int id = 0;
        try {
            String sql = "SELECT COUNT(*) AS number FROM codepromo c , categoriecodepromo ccp WHERE ( c.idCategorieCode = ccp.idCcp ) AND\n" +
                    "ccp.code=? AND c.idCategorieCode=? AND ccp.limite>0 ";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setString(1, ccp.getCode());
                st.setInt(2, codePromo.getCategorieCodePromo().getIdCcp());
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("number");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding code promo by name: " + e.getMessage());
        }
        return id;
    }
    public CodePromo findByCode(String code) throws SQLException {
        CodePromo codePromo = null;
        try {
            String sql = "SELECT c.idCode , c.userId, c.idCategorieCode FROM codepromo c , categoriecodepromo cc WHERE cc.code=? AND c.idCategorieCode= cc.idCcp";
            GestionUser gu = new GestionUser();
            GestionCategorieCodePromo  ccp = new GestionCategorieCodePromo();
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setString(1, code);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        codePromo = new CodePromo(
                                rs.getInt("idCode"),
                                gu.findById(rs.getInt("userId")),  // Assuming you have a constructor in User that takes an int
                                ccp.findById(rs.getInt("idCategorieCode"))  // Assuming you have a constructor in CategorieCodePromo.java that takes a String

    // You may add additional methods based on your requirements
                        );
}}
                catch (SQLException e){
                    }
            }} catch (SQLException e) {
            System.out.println("Error finding code promo by name: " + e.getMessage());}
        return codePromo;
    }}