package com.example.affariyetii.services;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.models.Ticket;
import com.example.affariyetii.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements Tservice<Ticket> {
    private Connection connection ;
    public TicketService(){
        connection = MyDatabase.getInstance().getConnection();


    }

    @Override
    public void ajouter(Ticket ticket) {
        try {
            String req="INSERT INTO ticket_enchere (enchere_id, prix) VALUES ('"+ticket.getEnchereId()+"','"+ticket.getPrix()+"')";
            Statement st = connection.createStatement() ;
            st.executeUpdate(req);
            System.out.println("ticket Added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void modifier(Ticket ticket){
        try {
            String req= "UPDATE ticket_enchere SET  enchere_id = ?, prix = ? WHERE ticket_id = ?";;
            PreparedStatement ps = null;

            ps = connection.prepareStatement(req);

            ps.setInt(2,ticket.getEnchereId());
            ps.setDouble(3,ticket.getPrix());
            ps.setInt(5,ticket.getTicketId());
            ps.executeUpdate();
            System.out.println("ticket Update successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM ticket_enchere WHERE ticket_id = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("ticket DELETED successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//rechercher
    public Ticket rechercher(int id){
        Ticket ticket = new Ticket();
        String query = "SELECT * FROM ticket_enchere WHERE ticket_id = ? ";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ticket.setEnchereId(rs.getInt("enchere_id"));
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setPrix(rs.getDouble("prix"));
            }
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //rechercher tous les tickets acheter par le client with join SELECT `ticketp_id`, `ticket_id`, `client_id`, `enchere_id` FROM `ticketp` WHERE 1

    @Override
    public List<Ticket> reuperer() {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM ticket_enchere ";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setEnchereId(rs.getInt("enchere_id"));
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setPrix(rs.getDouble("prix"));

                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
