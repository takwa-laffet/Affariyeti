package tn.esprit.affariety.services;
import tn.esprit.affariety.models.Panier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService {

    private Connection connection ;
    public PanierService(){
        connection= tn.esprit.affariety.utils.MyDatabase.getInstance().getConnection();
    }

    public void ajouterArticlePanier(Panier panier) throws SQLException {
        String query = "INSERT INTO panier (nom_article, quantite, prix, totale) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, panier.getNom_article());
            preparedStatement.setInt(2, panier.getQuantite());
            preparedStatement.setFloat(3, panier.getPrix());
            preparedStatement.setFloat(4, panier.getTotale());
            preparedStatement.executeUpdate();
            System.out.println("Article ajouté au panier avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'article au panier : " + e.getMessage());
            throw e;
        }
    }

    public List<Panier> recuperer() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String req = "SELECT * FROM panier";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Panier panier = new Panier();
            panier.setId(rs.getInt("id"));
            panier.setNom_article(rs.getString("nom_article"));
            panier.setPrix(rs.getFloat("prix"));

            paniers.add(panier);
        }
        rs.close();
        st.close();
        return paniers;
    }
    public void updateStock(String nomArticle, int quantiteArticle) throws SQLException {
        String sql = "UPDATE article SET stock = stock - ? WHERE nom = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(2, nomArticle);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du stock : " + e.getMessage());
            throw e;
        }
    }

}



