package tn.esprit.gestion.services;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.utils.MyDatabase;

import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LivraisonService implements Lservice<Livraison> {


    private Connection connection;


    public LivraisonService() {
        connection = MyDatabase.getInstance().getConnection();
    }



    @Override
    public List<Livraison> recuperer() throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();
        String req = "SELECT livraison.id, livraison.adresselivraison, livraison.datecommande, livraison.datelivraison, livraison.statuslivraison, depot.iddepot, depot.nomdepot, depot.adresse " +
                "FROM livraison " +
                "LEFT JOIN depot ON livraison.iddepot = depot.iddepot";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Livraison livraison = new Livraison();
                livraison.setId(rs.getInt("id"));
                livraison.setAdresselivraison(rs.getString("adresselivraison"));
                livraison.setDatecommande(rs.getTimestamp("datecommande"));
                livraison.setDatelivraison(rs.getDate("datelivraison"));
                livraison.setStatuslivraison(rs.getString("statuslivraison"));

                // Check if the iddepot is null
                int iddepot = rs.getInt("iddepot");
                if (!rs.wasNull()) {
                    Depot depot = new Depot();
                    depot.setIddepot(iddepot);
                    depot.setNomdepot(rs.getString("nomdepot"));
                    depot.setAdresse(rs.getString("adresse"));
                    livraison.setIddepot(iddepot);
                } else {
                    // If iddepot is null, set it to -1 or any other suitable default value
                    livraison.setIddepot(-1); // Example: -1 indicates null iddepot
                }

                livraisons.add(livraison);
            }

            System.out.println("Connexion réussie");
            System.out.println("Liste livraison:");
            livraisons.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livraisons;
    }


    private Livraison createLivraisonFromResultSet(ResultSet rs) throws SQLException {
       Livraison livraison = new Livraison(
               rs.getInt("id"),
               rs.getString("adresselivraison"),
               rs.getTimestamp("datecommande"),
               rs.getDate("datelivraison"),
               rs.getString("statuslivraison"),
               rs.getInt("iddepot")
       );
       return livraison;
    }

    @Override
    public void modifier(Livraison livraison) throws SQLException {
        String req = "UPDATE livraison SET adresselivraison = ?, datecommande = ?, datelivraison = ?, statuslivraison = ?, iddepot = ? WHERE id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, livraison.getAdresselivraison());
            pst.setTimestamp(2, new Timestamp(livraison.getDatecommande().getTime()));
            pst.setDate(3, new Date(livraison.getDatelivraison().getTime()));
            pst.setString(4, livraison.getStatuslivraison());
            pst.setInt(5, livraison.getIddepot());
            pst.setInt(6, livraison.getId());
            pst.executeUpdate();

            System.out.println("Livraison modifiée avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM livraison WHERE id = ?";
        System.out.println("Deleting Livraison with ID: " + id);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();

        System.out.println("Livraison supprimée avec succès");
    }


    @Override
    public void ajouter(Livraison livraison) throws SQLException {
        String req = "INSERT INTO livraison (adresselivraison, datecommande, datelivraison, statuslivraison, iddepot) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement st = connection.prepareStatement(req);
        st.setString(1, livraison.getAdresselivraison());
        st.setTimestamp(2, new Timestamp(livraison.getDatecommande().getTime()));
        st.setTimestamp(3, new Timestamp(livraison.getDatelivraison().getTime()));
        st.setString(4, livraison.getStatuslivraison());
        st.setInt(5, livraison.getIddepot());

        st.executeUpdate();

        System.out.println("Livraison ajoutée avec succès");
        System.out.println(livraison);
    }


    public void close() {
    }

    public boolean idExists(int id) {
        try {
            String req = "SELECT * FROM livraison WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Livraison> tri() throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();
        String req = "SELECT * FROM livraison";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Livraison livraison = createLivraisonFromResultSet(rs);
                livraisons.add(livraison);
            }
        }

        // Sort the deliveries by 'datelivraison'
        Collections.sort(livraisons, Comparator.comparing(Livraison::getDatelivraison));

        return livraisons;
    }

    @Override
    public List<Livraison> recherche(int id) throws SQLException {
        List<Livraison> elements = new ArrayList<>();
        String req = "SELECT l.id, l.adresselivraison, l.datecommande, l.datelivraison, l.statuslivraison, "
                + "d.iddepot, d.nomdepot, d.adresse "
                + "FROM livraison l LEFT JOIN depot d ON l.iddepot = d.iddepot WHERE l.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Livraison livraison = createLivraisonFromResultSet(rs);

                Depot depot = new Depot();

                depot.setIddepot(rs.getInt("iddepot"));
                depot.setNomdepot(rs.getString("nomdepot"));
                depot.setAdresse(rs.getString("adresse"));

                elements.add(livraison);
            }
        }
        return elements;
    }

}



