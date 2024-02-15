package com.example.affariyetii.services;

import com.example.affariyetii.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.affariyetii.models.Enchere; // Assuming the class is in package "models"

import javax.xml.transform.Result;


public class EnchereService implements Iservice<Enchere> {
    private Connection connection ;

    public EnchereService(){
        connection = MyDatabase.getInstance().getConnection();

    }
    @Override
    public void ajouter(Enchere enchere) {
        try {
     String req="INSERT INTO `enchere`(`enchere_id`,`article_id`, `date_debut`, `date_fin`, `montant_initial`, `nom_enchere`) VALUES ('"+enchere.getEnchereId()+"','"+enchere.getArticleId()+"','"+enchere.getDateDebut()+"','"+enchere.getDateFin()+"','"+enchere.getMontantInitial()+"','"+enchere.getNom_enchere()+"')";
        Statement st = connection.createStatement() ;
        st.executeUpdate(req);
        System.out.println("enchere Added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(Enchere enchere){
        try {
        String req="UPDATE enchere SET date_debut= ?,date_fin= ?,montant_initial= ? ,nom_enchere = ? where enchere_id= ?";
        PreparedStatement ps = null;

            ps = connection.prepareStatement(req);

        ps.setString(1,enchere.getDateDebut());
        ps.setString(2,enchere.getDateFin());
        ps.setDouble(3,enchere.getMontantInitial());
        ps.setString(4,enchere.getNom_enchere());
        ps.setInt(5,enchere.getEnchereId());
        ps.executeUpdate();
        System.out.println("enchere Update successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM enchere WHERE enchere_id = ? ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,id);
            String query1 = "DELETE FROM ticket_enchere WHERE ticket_id = ? ";
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1,id);
        ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
                    enchere.setEnchereId(rs.getInt("enchere_id"));
                    enchere.setArticleId(rs.getInt("article_id"));
                    enchere.setMontantInitial(rs.getDouble("montant_initial"));
                    enchere.setNom_enchere(rs.getString("nom_enchere"));
                    enchere.setDateDebut(rs.getString("date_debut"));
                    enchere.setDateFin(rs.getString("date_fin"));
            encheres.add(enchere);
        }
        return encheres;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
