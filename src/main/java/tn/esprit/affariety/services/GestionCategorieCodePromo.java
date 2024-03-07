package tn.esprit.affariety.services;

import tn.esprit.affariety.models.CategorieCodePromo;
import tn.esprit.affariety.utils.MyDataBase;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionCategorieCodePromo implements Fonctions<CategorieCodePromo> {

    private Connection cnx;

    public GestionCategorieCodePromo() {
        this.cnx = MyDataBase.getInstance().getConnection();
    }

    public void Create(CategorieCodePromo categorieCodePromo) {
        try {
            String sql = "INSERT INTO categorieCodepromo (valeur, code, limite) VALUES (?, ?, ?)";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, categorieCodePromo.getValeur());
                st.setString(2, categorieCodePromo.getCode());
                st.setInt(3, categorieCodePromo.getLimite());


                st.executeUpdate();

                System.out.println("Code promo created successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating code promo: " + e.getMessage());
        }
    }

    public void delete(CategorieCodePromo categorieCodePromo) {
        try {
            String sql = "DELETE FROM categorieCodePromo WHERE idCcp=?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, categorieCodePromo.getIdCcp());
                st.executeUpdate();

                System.out.println("Code promo deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting code promo: " + e.getMessage());
        }
    }

    public void update(CategorieCodePromo categorieCodePromo) {
        try {
            String sql = "UPDATE categorieCodePromo SET valeur=?, code=?, limite=? WHERE idCcp=?";
            System.out.println(categorieCodePromo.getIdCcp());
            PreparedStatement st = this.cnx.prepareStatement(sql);

            st.setInt(1, categorieCodePromo.getValeur());
            st.setString(2, categorieCodePromo.getCode());
            st.setInt(3, categorieCodePromo.getLimite());

            st.setInt(4, categorieCodePromo.getIdCcp()); //

            st.executeUpdate();

            System.out.println("CategorieCodePromo updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating CategorieCodePromo: " + e.getMessage());
        }
    }


    public List<CategorieCodePromo> findAll() {
        List<CategorieCodePromo> categorieCodePromoList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM categoriecodepromo";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CategorieCodePromo categorieCodePromo = new CategorieCodePromo(
                                rs.getString("code"),
                                rs.getInt("valeur"),
                                rs.getInt("limite")
                        );
                        categorieCodePromo.setIdCcp(rs.getInt("idCcp"));
                        categorieCodePromoList.add(categorieCodePromo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading CategorieCodePromo: " + e.getMessage());
        }
        return categorieCodePromoList;
    }


    public CategorieCodePromo findById(int idCcp) {
        CategorieCodePromo categorieCodePromo = null;
        try {
            String sql = "SELECT * FROM categorieCodePromo WHERE idCcp = ?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, idCcp); // Use the method parameter 'idCcp' instead of 'categorieCodePromo'
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        // Construire un objet CategorieCodePromo à partir des données de la base de données
                        categorieCodePromo = new CategorieCodePromo(
                                rs.getString("code"),
                                rs.getInt("valeur"),
                                rs.getInt("limite")
                                // Ajoutez d'autres attributs si nécessaire
                        );
                        categorieCodePromo.setIdCcp(rs.getInt("idCcp")); // Assurez-vous d'avoir une méthode setIdCcp dans la classe CategorieCodePromo
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error finding CategorieCodePromo by id: " + e.getMessage());
        }
        return categorieCodePromo;
    }
}
