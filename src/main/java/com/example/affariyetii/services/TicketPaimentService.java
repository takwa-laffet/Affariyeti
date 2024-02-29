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

    public List<Integer> getUnlinkedTicketIds() {
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
}
