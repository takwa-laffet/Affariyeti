package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Depot;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.affariety.models.Depot;

public class DepotService implements Dservice<Depot> {
    private Connection connection;

    public DepotService() {
        connection = MyDataBase.getInstance().getConnection();
    }

@Override
    public void ajouter(Depot depot) throws SQLException {
        String req = "INSERT INTO depot (nomdepot, adresse) VALUES (?, ?)";
        PreparedStatement st = connection.prepareStatement(req);
        st.setString(1, depot.getNomdepot());
        st.setString(2, depot.getAdresse());
        st.executeUpdate();
    }



    @Override
    public void modifier(Depot depot) throws SQLException {
        String req = "UPDATE depot SET nomdepot=?,adresse=? WHERE iddepot=?";

        PreparedStatement ps = connection.prepareStatement(req);

        ps.setString(1, depot.getNomdepot());
        ps.setString(2, depot.getAdresse());
        ps.setInt(3, depot.getIddepot());
        ps.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String updateLivraisonsQuery = "UPDATE livraison SET iddepot = NULL WHERE iddepot = ?";
        String deleteDepotQuery = "DELETE FROM depot WHERE iddepot = ?";

        try {
            // Begin a transaction
            connection.setAutoCommit(false);

            // Assign NULL to iddepot in the livraison table
            try (PreparedStatement updateLivraisonsStatement = connection.prepareStatement(updateLivraisonsQuery)) {
                updateLivraisonsStatement.setInt(1, id);
                updateLivraisonsStatement.executeUpdate();
            }

            // Delete the depot
            try (PreparedStatement deleteDepotStatement = connection.prepareStatement(deleteDepotQuery)) {
                deleteDepotStatement.setInt(1, id);
                deleteDepotStatement.executeUpdate();
            }

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback the transaction in case of an exception
            connection.rollback();
            throw e;
        } finally {
            // Restore auto-commit mode
            connection.setAutoCommit(true);
        }
    }


    @Override
    public List<Depot> recuperer() throws SQLException {

        List<Depot> depots = new ArrayList<>();
        String req = "SELECT * from depot;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);


        while (rs.next()) {
            int depotId = rs.getInt("depot.iddepot");
            // If the depot does not exist in the map, create a new one
            Depot depot = new Depot();
            depot.setIddepot(depotId);
            depot.setNomdepot(rs.getString("depot.nomdepot"));
            depot.setAdresse(rs.getString("depot.adresse"));

            depots.add(depot);

        }







        return depots;
    }

    public List<Depot> rechercher(Depot depot) throws SQLException {
        return null;
    }

    public Depot getById(int id) throws SQLException {
        String query = "SELECT * FROM depot WHERE iddepot = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Depot depot = new Depot();
                    depot.setIddepot(resultSet.getInt("iddepot"));
                    depot.setNomdepot(resultSet.getString("nomdepot"));
                    depot.setAdresse(resultSet.getString("adresse"));
                    return depot;
                }
            }
        }
        return null; // Return null if the depot with the specified ID is not found
    }

}