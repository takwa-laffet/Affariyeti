package com.example.affariyetii.services;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.models.TicketPaiment;
import com.example.affariyetii.utils.MyDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    //chercher le min prix de ticket
    public double minPrix() {
        double minPrix = 0;
        String query = "SELECT MIN(prix) AS min_prix FROM ticket_enchere";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                minPrix = rs.getDouble("min_prix");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return minPrix;
    }
    //chercher les tickets avec le nom de l'enchere i just have the name from the min to max et avec le id du client et afficher le date de l'enchere le montantintial et le prix tickect tous le table encherre de se id


}
