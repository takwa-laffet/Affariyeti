package com.example.affariyetii.services;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.models.Ticket;
import com.example.affariyetii.models.TicketPaiment;
import com.example.affariyetii.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketPaimentService implements Tpservice<TicketPaiment> {
    private Connection connection;

    public TicketPaimentService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void ajouter(TicketPaiment ticketPaiment) {
        try {
            String req = "INSERT INTO ticketp (ticket_id, client_id, enchere_id) VALUES ('"+ticketPaiment.getTicketId()+"','"+ticketPaiment.getClient_id()+"','"+ticketPaiment.getEnchere_id()+"')";
            Statement st = connection.createStatement() ;
            st.executeUpdate(req);
            System.out.println("ticket Added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void modifier(TicketPaiment ticketPaiment) {
        try {
            String req = "UPDATE ticketp SET client_id = ?, enchere_id = ? WHERE ticket_id = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, ticketPaiment.getClient_id());
            ps.setInt(2, ticketPaiment.getEnchere_id());
            ps.setInt(3, ticketPaiment.getTicketId());
            ps.executeUpdate();
            System.out.println("Ticket payment updated successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimer(int ticketId) {
        try {
            String query = "DELETE FROM ticketp WHERE ticket_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, ticketId);
            ps.executeUpdate();
            System.out.println("Ticket payment deleted successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<TicketPaiment> reuperer(String nomEnchere, int clientId) {
        List<TicketPaiment> tickets = new ArrayList<>();
        String query = "SELECT * FROM ticketp " +
                "INNER JOIN ticket_enchere ON ticketp.enchere_id = ticket_enchere.enchere_id " +
                "INNER JOIN enchere e ON ticket_enchere.enchere_id = e.enchere_id " +
                "WHERE e.nom_enchere = ? AND ticketp.client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nomEnchere);
            ps.setInt(2, clientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TicketPaiment ticketPaiment = new TicketPaiment();
                ticketPaiment.setTicketId(rs.getInt("ticket_id"));
                ticketPaiment.setClient_id(rs.getInt("client_id"));
                ticketPaiment.setEnchere_id(rs.getInt("enchere_id"));
                tickets.add(ticketPaiment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> reuperercl(String clientName) {
        return null;
    }


    public TicketPaiment rechercher(int ticketId) {
        TicketPaiment ticketPaiment = new TicketPaiment();
        String query = "SELECT * FROM ticketp WHERE ticket_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ticketPaiment.setTicketId(rs.getInt("ticket_id"));
                ticketPaiment.setClient_id(rs.getInt("client_id"));
                ticketPaiment.setEnchere_id(rs.getInt("enchere_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketPaiment;
    }

    public List<TicketPaiment> recuperer() {
        List<TicketPaiment> ticketPaiments = new ArrayList<>();
        String query = "SELECT * FROM ticketp";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TicketPaiment ticketPaiment = new TicketPaiment();
                ticketPaiment.setTicketId(rs.getInt("ticket_id"));
                ticketPaiment.setClient_id(rs.getInt("client_id"));
                ticketPaiment.setEnchere_id(rs.getInt("enchere_id"));
                ticketPaiments.add(ticketPaiment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketPaiments;
    }
    //chercher les tickets avec le nom de l'enchere i just have the name from the min to max et avec le id du client et afficher le date de l'enchere le montantintial et le prix tickect tous le table encherre de se id
    public List<Enchere> getEncheresParticipatedByUser(String clientName, String clientPrenom) {
        List<Enchere> participatedEncheres = new ArrayList<>();
        String query = "SELECT e.* FROM enchere e " +
                "INNER JOIN ticketp t ON e.enchere_id = t.enchere_id " +
                "INNER JOIN user u ON t.client_id = u.id " +
                "WHERE u.role = 'client' AND u.nom = ? AND u.prenom = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, clientName);
            ps.setString(2, clientPrenom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere.setEnchereId(rs.getInt("enchere_id"));
                enchere.setImage(rs.getString("image"));
                enchere.setMontantInitial(rs.getString("montant_initial"));
                enchere.setNom_enchere(rs.getString("nom_enchere"));
                enchere.setDateDebut(rs.getString("date_debut"));
                enchere.setDateFin(rs.getString("date_fin"));
                participatedEncheres.add(enchere);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participatedEncheres;
    }
//ajouterTicketPaiment
public void ajouterTicketPaiment(int ticketId ,int clientId, int enchereId) {
    // SQL query to insert ticket payment details
    String query = "INSERT INTO ticketp (ticket_id,client_id, enchere_id) VALUES (?,?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, ticketId);
        statement.setInt(2, clientId);
        statement.setInt(3, enchereId);
        statement.executeUpdate(); // Execute the insert query
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception properly in your application
    }
} public List<Integer> getUnlinkedTicketIds() {
        List<Integer> unlinkedTicketIds = new ArrayList<>();
        String query = "SELECT te.ticket_id FROM ticket_enchere te " +
                "LEFT JOIN ticketp t ON te.ticket_id = t.ticket_id " +
                "WHERE t.ticket_id IS NULL";
        try (
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                unlinkedTicketIds.add(resultSet.getInt("ticket_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
        return unlinkedTicketIds;
    }
    public List<Ticket> searchTicketsByNomAndPrenom(String nom, String prenom) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String query = "SELECT * FROM ticketp WHERE client_id IN (SELECT id FROM user WHERE nom = ? AND prenom = ? )";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setEnchereId(rs.getInt("enchere_id"));
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setPrix(rs.getDouble("prix"));
                tickets.add(ticket);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public int reuperercl(String clientName,String clintprenom) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT ticketp_id FROM ticketp WHERE client_id IN ( SELECT id FROM user WHERE nom = ? AND prenom = ? )";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, clientName);
            ps.setString(2, clintprenom);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ticket_id"));
                tickets.add(ticket);  }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets.size();}

    // afficher le nombre de ticketp for enchere
    public int countTicketByEnchereId(String encherenom) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM ticketp WHERE enchere_id IN (SELECT enchere_id FROM enchere WHERE nom_enchere = ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, encherenom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public List<Enchere> getEnchereIdsByClientId(int clientId) {
        List<Enchere> encheres = new ArrayList<>();
        try {
            String query = "SELECT e.*\n" +
                    "FROM user u\n" +
                    "INNER JOIN ticketp t ON u.id = t.client_id\n" +
                    "INNER JOIN enchere e ON t.enchere_id = e.enchere_id\n" +
                    "WHERE u.id = ? ;\n";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
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
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return encheres;

}
}
