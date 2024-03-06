package com.example.affariyetii.services;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EnchereService implements Eservice<Enchere> {
    private Connection connection;

    public EnchereService() {
        connection = MyDatabase.getInstance().getConnection();

    }

    //ajouter
    @Override
    public void ajouter(Enchere enchere) {
        try {
            String req = "INSERT INTO `enchere`(`image`,`date_debut`,`heured`,`date_fin`,`heuref`,`montant_initial`,`nom_enchere`,`idclcreree`) VALUES ('" + enchere.getImage() + "','" + enchere.getDateDebut() + "','" + enchere.getHeured() + "','" + enchere.getDateFin() + "','" + enchere.getHeuref() + "','" + enchere.getMontantInitial() + "','" + enchere.getNom_enchere() + "','" + enchere.getIdclcreree() + "')";
            Statement st = connection.createStatement();
            st.executeUpdate(req);

            System.out.println("enchere Added successfully!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void ajouterafine(Enchere enchere) {
        try {
            String req = "INSERT INTO `enchere`(`image`,`date_debut`,`heured`,`date_fin`,`heuref`,`montant_initial`,`nom_enchere`,`idclcreree`, `montant_final`) VALUES ('" + enchere.getImage() + "','" + enchere.getDateDebut() + "','" + enchere.getHeured() + "','" + enchere.getDateFin() + "','" + enchere.getHeuref() + "','" + enchere.getMontantInitial() + "','" + enchere.getNom_enchere() + "','" + enchere.getIdclcreree() + "','" + enchere.getMontantInitial() + "')";
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("enchere Update successfully!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    //modifier
    @Override
    public void modifier(Enchere enchere) {
        try {
            String req = "UPDATE enchere SET date_debut= ?,heured= ?,date_fin= ?,heuref= ?,montant_initial= ? ,nom_enchere = ?,image = ? where enchere_id= ?";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(req);
            ps.setString(1, enchere.getDateDebut());
            ps.setString(1, enchere.getHeured());
            ps.setString(2, enchere.getDateFin());
            ps.setString(2, enchere.getHeuref());
            ps.setString(3, enchere.getMontantInitial());
            ps.setString(4, enchere.getNom_enchere());
            ps.setInt(5, enchere.getEnchereId());
            ps.setString(6, enchere.getImage());
            ps.executeUpdate();
            System.out.println("enchere Update successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // supprimer
    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM enchere WHERE enchere_id = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /*to do create 30 ticket autoumatique */


    // reuperer
    @Override
    public List<Enchere> reuperer() {
        List<Enchere> encheres = new ArrayList<>();
        String query = "SELECT * FROM enchere";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setImage(rs.getString("image"));
                enchere.setMontantInitial(rs.getString("montant_initial"));
                enchere.setNom_enchere(rs.getString("nom_enchere"));
                enchere.setDateDebut(rs.getString("date_debut"));
                enchere.setHeured(rs.getString("heured"));
                enchere.setDateFin(rs.getString("date_fin"));
                enchere.setHeuref(rs.getString("heuref"));
                enchere.setMontant_final(rs.getString("montant_final"));
                enchere.setIdclcreree(rs.getInt("idclcreree"));
                encheres.add(enchere);
            }
            return encheres;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //chercher par le nom
    public List<Enchere> chercher(String nom) {
        List<Enchere> encheres = new ArrayList<>();
        String query = "SELECT * FROM enchere WHERE nom_enchere LIKE ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setEnchereId(rs.getInt("enchere_id"));
                enchere.setImage(rs.getString("image"));
                enchere.setMontantInitial(rs.getString("montant_initial"));
                enchere.setNom_enchere(rs.getString("nom_enchere"));
                enchere.setDateDebut(rs.getString("date_debut"));
                enchere.setHeured(rs.getString("heured"));
                enchere.setDateFin(rs.getString("date_fin"));
                enchere.setHeuref(rs.getString("heuref"));
                enchere.setIdclcreree(rs.getInt("idclcreree"));
                encheres.add(enchere);
            }
            return encheres;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //chercher avec le montant minimale de l'enchere et le nom de l'enchere
    public List<Enchere> chercherMin(String nom, double min) {
        List<Enchere> encheres = new ArrayList<>();
        String query = "SELECT * FROM enchere WHERE nom_enchere LIKE ? AND montant_initial >= ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + nom + "%");
            ps.setDouble(2, min);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setEnchereId(rs.getInt("enchere_id"));
                enchere.setImage(rs.getString("image"));
                enchere.setMontantInitial(rs.getString("montant_initial"));
                enchere.setNom_enchere(rs.getString("nom_enchere"));
                enchere.setDateDebut(rs.getString("date_debut"));
                enchere.setHeured(rs.getString("heured"));
                enchere.setDateFin(rs.getString("date_fin"));
                enchere.setHeuref(rs.getString("heuref"));
                enchere.setIdclcreree(rs.getInt("idclcreree"));
                encheres.add(enchere);
            }
            return encheres;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifierEnchere(int id, Enchere newEnchere) {
        try {
            String req = "UPDATE enchere SET image = ?, date_debut = ?, heured = ?, date_fin = ?,heuref = ?, montant_initial = ? WHERE enchere_id = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, newEnchere.getImage());
            ps.setString(2, newEnchere.getDateDebut());
            ps.setString(2, newEnchere.getHeured());
            ps.setString(3, newEnchere.getDateFin());
            ps.setString(3, newEnchere.getHeuref());
            ps.setString(4, newEnchere.getMontantInitial());
            ps.setInt(5, id);
            ps.executeUpdate();
            System.out.println("Enchere updated successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Add this method to your EnchereService class
    @Override
    public int rechercherIdParNom(String nomEnchere) {
        String query = "SELECT enchere_id FROM enchere WHERE nom_enchere = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nomEnchere);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("enchere_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Enchere ID by name", e);
        }
        throw new IllegalArgumentException("Enchere with name " + nomEnchere + " not found");
    }


    @Override
    public List<Enchere> getTopSaleAuctions() {
        List<Enchere> topSaleAuctions = new ArrayList<>();

        String query = "SELECT * FROM enchere ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setEnchereId(rs.getInt("enchere_id"));
                enchere.setImage(rs.getString("image"));
                enchere.setDateDebut(rs.getString("date_debut"));
                enchere.setHeured(rs.getString("heured"));
                enchere.setDateFin(rs.getString("date_fin"));
                enchere.setHeuref(rs.getString("heuref"));
                enchere.setMontantInitial(rs.getString("montant_initial"));
                enchere.setNom_enchere(rs.getString("nom_enchere"));

                // Add more fields as needed
                topSaleAuctions.add(enchere);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topSaleAuctions;
    }

    public int getUserIdByNomAndPrenom(String nom, String prenom) {
        String query = "SELECT id FROM user WHERE nom = ? AND prenom = ? and role = 'client'";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Enchere ID by name", e);
        }
        throw new IllegalArgumentException("Enchere with name " + nom + " not found");

    }

    // chercher les encheres created by user id
    public List<Enchere> getEncheresByUserId(int userId) {
        List<Enchere> encheres = new ArrayList<>();
        String query = "SELECT * FROM enchere WHERE idclcreree = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setEnchereId(rs.getInt("enchere_id"));
                enchere.setImage(rs.getString("image"));
                enchere.setDateDebut(rs.getString("date_debut"));
                enchere.setHeured(rs.getString("heured"));
                enchere.setDateFin(rs.getString("date_fin"));
                enchere.setHeuref(rs.getString("heuref"));
                enchere.setMontantInitial(rs.getString("montant_initial"));
                enchere.setNom_enchere(rs.getString("nom_enchere"));
                encheres.add(enchere);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return encheres;
    }
//modifier le montant de l'enchere finale et idclenchere et enchere id
    public void modifierMontantEnchere(int id, double montant ,int idclenchere) {
        String query = "UPDATE enchere SET montant_final = ?, idclcreree = ? WHERE enchere_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, montant);
            ps.setInt(2, idclenchere);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Enchere updated successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

