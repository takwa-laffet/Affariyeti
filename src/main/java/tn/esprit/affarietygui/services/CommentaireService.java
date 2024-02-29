package tn.esprit.affarietygui.services;

import tn.esprit.affarietygui.models.Commentaire;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.utils.Mydb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService implements IService<Commentaire>{
    private Connection connection;
    public  CommentaireService(){
        connection= Mydb.getInstance().getConnection();
    }
    @Override
    public int ajouter(Commentaire commentaire) throws SQLException {
      // String req = "INSERT INTO commentaire (id_pub, id_article, id_client, contenu, date_com) " +
           //     "VALUES ((SELECT id_pub FROM publication WHERE id_pub = ?), ?, ?, ?, ?)";
        String req = "INSERT INTO commentaire (id_pub, id_client, contenu, date_com) " +
                   "VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, commentaire.getPublication().getId_pub());
        preparedStatement.setInt(2, commentaire.getId_client());
        preparedStatement.setString(3, commentaire.getContenu());
        preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Utiliser la date courante
        preparedStatement.executeUpdate();
        return 0;
    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire c " +
                "JOIN publication p ON c.id_pub = p.id_pub " +
                "SET c.id_client = ?, c.contenu = ?, c.date_com = ? " +
                "WHERE c.id_com = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, commentaire.getId_client());
        preparedStatement.setString(2, commentaire.getContenu());
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(4, commentaire.getId_com());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id_com) throws SQLException {
        String req= "DELETE FROM commentaire WHERE id_com= ?";
        PreparedStatement ps=connection.prepareStatement(req);
        ps.setInt(1,id_com);
        ps.executeUpdate();
    }

    @Override
    public List<Commentaire> recuperer() throws SQLException {
        /*
        List<Commentaire> commentaires=new ArrayList<>();
        String req ="SELECT c.*,p.* FROM commentaire c JOIN publication p ON c.id_pub=p.id_pub";
        Statement st=connection.createStatement();
        ResultSet rs= st.executeQuery(req);
        while(rs.next()){
            Commentaire commentaire=new Commentaire();
            commentaire.setId_com(rs.getInt("id_com"));
            commentaire.setId_pub(rs.getInt("id_pub"));
            commentaire.setId_article(rs.getInt("id_article"));
            commentaire.setId_client(rs.getInt("id_client"));
            commentaire.setContenu(rs.getString("contenu"));
            commentaire.setDate_com(rs.getTimestamp("date_com"));
            commentaires.add(commentaire);

            Publication publication = new Publication();
            publication.setId_pub(rs.getInt("p.id_pub"));
            publication.setId_client(rs.getInt("p.id_client"));
            publication.setContenu(rs.getString("p.contenu"));
            publication.setNb_likes(rs.getInt("p.nb_likes"));
            publication.setNb_dislike(rs.getInt("p.nb_dislike"));
            publication.setDate_pub(rs.getTimestamp("p.date_pub"));
            publication.setPhoto(rs.getString("p.photo"));

            commentaire.setPublication(publication); // Assuming you have a setter for Publication in Commentaire class
           // commentaires.add(commentaire);
        }

        return commentaires;
        */return null;
    }


    public List<Commentaire> recuperer(int id_pub) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT c.*, p.* FROM commentaire c JOIN publication p ON c.id_pub = p.id_pub WHERE c.id_pub = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id_pub);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Commentaire commentaire = new Commentaire();
           commentaire.setId_com(rs.getInt("id_com"));
            commentaire.setId_pub(rs.getInt("id_pub"));
            commentaire.setId_client(rs.getInt("id_client"));
            commentaire.setContenu(rs.getString("contenu"));
            commentaire.setDate_com(rs.getTimestamp("date_com"));
//

//
            commentaires.add(commentaire);
        }
        return commentaires;
    }
    public Commentaire getCommentsById(int id_com) throws SQLException {
        String req = "SELECT * FROM Commentaire WHERE id_com = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id_com );
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId_com(rs.getInt("id_com"));
            commentaire.setId_pub(rs.getInt("id_pub"));
            commentaire.setId_client(rs.getInt("id_client"));
            commentaire.setContenu(rs.getString("contenu"));
            commentaire.setDate_com(rs.getTimestamp("date_com"));
            return commentaire;
        } else {
            // Handle the case where no publication with the given ID was found
            return null;
        }
}}

