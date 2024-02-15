package tn.esprit.affarietygui.services;

import tn.esprit.affarietygui.models.Publication;
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
    public void ajouter(Publication publication) throws SQLException {
       /* String req="INSERT INTO publication(id_client,contenu,nb_likes,nb_dislike,date_pub,photo)VALUES('"+publication.getId_client()+"','"+publication.getContenu()+"','"+publication.getNb_likes()+"','"+publication.getNb_dislike()+"','"+publication.getDate_pub()+"',"+publication.getPhoto()+")";
        Statement st = connection.createStatement();
        st.executeUpdate(req);*/
            String req = "INSERT INTO publication (id_client,contenu, nb_likes, nb_dislike, date_pub, photo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, publication.getId_client());
            preparedStatement.setString(3, publication.getContenu());
            preparedStatement.setInt(4, publication.getNb_likes());
            preparedStatement.setInt(5, publication.getNb_dislike());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // Utiliser la date courante
            preparedStatement.setString(7, publication.getPhoto());
            preparedStatement.executeUpdate();
        }

    @Override
    public void modifier(Publication publication) throws SQLException {
        String req = "UPDATE publication SET id_client= ? ,  nb_likes = ?, nb_dislike = ?, contenu = ?, photo = ? ,date_pub=? WHERE id_pub = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, publication.getId_client());
        preparedStatement.setInt(3, publication.getNb_likes());
        preparedStatement.setInt(4, publication.getNb_dislike());
        preparedStatement.setString(5, publication.getContenu());
        preparedStatement.setString(6, publication.getPhoto());
        preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(8, publication.getId_pub());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id_pub) throws SQLException {
    String req= "DELETE FROM publication WHERE id_pub= ?";
    PreparedStatement ps=connection.prepareStatement(req);
    ps.setInt(1,id_pub);
    ps.executeUpdate();
    }

    @Override
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
    }
}
