package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements IService<Categorie> {
    private Connection connection;
    public CategorieService(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Categorie categorie) throws SQLException {
        String req = "INSERT INTO categorie (nom_c) VALUES ( '" + categorie.getNom_c() + "')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);


    }

    @Override
    public void modifier(Categorie categorie) throws SQLException {
        String req = "UPDATE categorie SET nom_c = ?  WHERE id_c = ? " ;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(req)) {
                ps.setString(1, categorie.getNom_c());
                ps.setInt(2, categorie.getId_c());
                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected.");
            }

            // Commit the changes
            connection.commit();
        } catch (SQLException e) {
            // If an error occurs, print details, rollback changes, and re-throw the exception
            e.printStackTrace();
            connection.rollback();
            throw e;
        } finally {
            // Reset auto-commit to true to avoid issues in subsequent database operations
            connection.setAutoCommit(true);
        }

    }


    @Override
    public void supprimer(String nom_c) throws SQLException {
        String req = "DELETE FROM categorie WHERE nom_c = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, nom_c);
        ps.executeUpdate();

    }

    @Override
    public List<Categorie> recuperer() throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        String req = "SELECT * FROM categorie";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId_c(rs.getInt("id_c"));
                categorie.setNom_c(rs.getString("nom_c"));
                categories.add(categorie);
            }
        }

        return categories;
    }


    public Categorie getCategoryByName(String categoryName) throws SQLException {
        String req = "SELECT * FROM categorie WHERE nom_c = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, categoryName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categorie category = new Categorie();
                    category.setId_c(rs.getInt("id_c"));
                    category.setNom_c(rs.getString("nom_c"));

                    return category;
                }
            }
        }

        // Return null or throw an exception if the category is not found
        return null; // You can choose a suitable default value
    }
    public Categorie getCategoryById(int categoryId) throws SQLException {
        String req = "SELECT * FROM categorie WHERE id_c = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categorie category = new Categorie();
                    category.setId_c(rs.getInt("id_c"));
                    category.setNom_c(rs.getString("nom_c"));

                    return category;
                }
            }
        }
        return null;
    }
    public int countCategories() throws SQLException {
        String query = "SELECT COUNT(*) FROM categorie";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);

            }
        }
        return 0;
    }


}
