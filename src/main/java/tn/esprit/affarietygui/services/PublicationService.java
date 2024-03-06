package tn.esprit.affarietygui.services;

import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.models.User;
import tn.esprit.affarietygui.utils.Mydb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationService implements IService<Publication> {
    private Connection connection;
    public  PublicationService(){
        connection= Mydb.getInstance().getConnection();
    }
    @Override
    public int ajouter(Publication publication) throws SQLException {
       /* String req="INSERT INTO publication(id_client,contenu,nb_likes,nb_dislike,date_pub,photo)VALUES('"+publication.getId_client()+"','"+publication.getContenu()+"','"+publication.getNb_likes()+"','"+publication.getNb_dislike()+"','"+publication.getDate_pub()+"',"+publication.getPhoto()+")";
        Statement st = connection.createStatement();
        st.executeUpdate(req);*/
        String req = "INSERT INTO publication (id_client,contenu, nb_likes, nb_dislike, date_pub, photo) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, publication.getId_client());
        preparedStatement.setString(2, publication.getContenu());
        preparedStatement.setInt(3, publication.getNb_likes());
        preparedStatement.setInt(4, publication.getNb_dislike());
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Utiliser la date courante
        preparedStatement.setString(6, publication.getPhoto());
        preparedStatement.executeUpdate();
        return 0;
    }

    @Override
    public void modifier(Publication publication) throws SQLException {
        String req = "UPDATE publication SET id_client= ? ,  nb_likes = ?, nb_dislike = ?, contenu = ?, photo = ? ,date_pub=? WHERE id_pub = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, publication.getId_client());
        preparedStatement.setInt(2, publication.getNb_likes());
        preparedStatement.setInt(3, publication.getNb_dislike());
        preparedStatement.setString(4, publication.getContenu());
        preparedStatement.setString(5, publication.getPhoto());
        preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(7, publication.getId_pub());
        preparedStatement.executeUpdate();
    }

    public void modifierlikedislike(Publication publication) throws SQLException {
        String req = "UPDATE publication SET id_client= ? ,  nb_likes = ?, nb_dislike = ? WHERE id_pub = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, publication.getId_client());
        preparedStatement.setInt(2, publication.getNb_likes());
        preparedStatement.setInt(3, publication.getNb_dislike());
        preparedStatement.setInt(4, publication.getId_pub());
        preparedStatement.executeUpdate();
    }
    @Override
    public void supprimer(int id_pub) throws SQLException {
        String req= "DELETE FROM publication WHERE id_pub= ?";
        PreparedStatement ps=connection.prepareStatement(req);
        ps.setInt(1,id_pub);
        ps.executeUpdate();
    }


//version1
   /* @Override
    public List<Publication> recuperer() throws SQLException {
        List<Publication> publications=new ArrayList<>();
        String req ="SELECT * FROM publication";
        Statement st=connection.createStatement();
        ResultSet rs= st.executeQuery(req);
        while(rs.next()){
            Publication publication=new Publication();
            publication.setId_pub(rs.getInt("id_pub"));
            publication.setId_client(rs.getInt("id_client"));
            publication.setContenu(rs.getString("contenu"));
            publication.setNb_likes(rs.getInt("nb_likes"));
            publication.setNb_dislike(rs.getInt("nb_dislike"));
            publication.setPhoto(rs.getString("photo"));
            publication.setDate_pub(rs.getTimestamp("date_pub"));

            publications.add(publication);

        }

        return publications;
    }*/

    //version2
    @Override
    public List<Publication> recuperer() throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String req = "SELECT p.*, u.nom AS user_nom, u.prenom AS user_prenom  " +
                "FROM publication p " +
                "JOIN user u ON p.id_client = u.id";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()) {
            Publication publication = new Publication();
            publication.setId_pub(rs.getInt("id_pub"));
            publication.setId_client(rs.getInt("id_client"));
            publication.setContenu(rs.getString("contenu"));
            publication.setNb_likes(rs.getInt("nb_likes"));
            publication.setNb_dislike(rs.getInt("nb_dislike"));
            publication.setPhoto(rs.getString("photo"));
            publication.setDate_pub(rs.getTimestamp("date_pub"));

            // Créez un objet User pour stocker les informations de l'utilisateur
            tn.esprit.affarietygui.models.User user = new User();
            user.setNom(rs.getString("user_nom")); // Assurez-vous que le setter du nom de l'utilisateur est présent dans la classe User
            user.setPrenom(rs.getString("user_prenom"));
            // Associez l'utilisateur au commentaire
            publication.setUser(user);
            publications.add(publication);
        }
        return publications;
    }
    public Publication getPublicationById(int id_pub) throws SQLException {
        String req = "SELECT * FROM publication WHERE id_pub = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id_pub);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Publication publication = new Publication();
            publication.setId_pub(rs.getInt("id_pub"));
            publication.setId_client(rs.getInt("id_client"));
            publication.setContenu(rs.getString("contenu"));
            publication.setNb_likes(rs.getInt("nb_likes"));
            publication.setNb_dislike(rs.getInt("nb_dislike"));
            publication.setPhoto(rs.getString("photo"));
            publication.setDate_pub(rs.getTimestamp("date_pub"));
            return publication;
        } else {
            // Handle the case where no publication with the given ID was found
            return null;
        }
    }
    public List<Publication> filtrerParDate(Date date) throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String req = "SELECT p.*, u.nom AS user_nom, u.prenom AS user_prenom " +
                "FROM publication p " +
                "JOIN user u ON p.id_client = u.id " +
                "WHERE DATE(p.date_pub) = DATE(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setDate(1, date);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Publication publication = new Publication();
            publication.setId_pub(rs.getInt("id_pub"));
            publication.setId_client(rs.getInt("id_client"));
            publication.setContenu(rs.getString("contenu"));
            publication.setNb_likes(rs.getInt("nb_likes"));
            publication.setNb_dislike(rs.getInt("nb_dislike"));
            publication.setPhoto(rs.getString("photo"));
            publication.setDate_pub(rs.getTimestamp("date_pub"));

            // Ajoutez la publication à la liste des publications
            publications.add(publication);
        }
        return publications;
    }





}