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
    public void ajouter(Commentaire commentaire) throws SQLException {
        String req = "INSERT INTO commentaire (id_pub, id_article, id_client, contenu, date_com) " +
                "VALUES ((SELECT id_pub FROM publication WHERE id_pub = ?), ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, commentaire.getId_pub());
        preparedStatement.setInt(2, commentaire.getId_article());
        preparedStatement.setInt(3, commentaire.getId_client());
        preparedStatement.setString(4, commentaire.getContenu());
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Utiliser la date courante
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire c " +
                "JOIN publication p ON c.id_pub = p.id_pub " +
                "SET c.id_article = ?, c.id_client = ?, c.contenu = ?, c.date_com = ? " +
                "WHERE c.id_com = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, commentaire.getId_article());
        preparedStatement.setInt(2, commentaire.getId_client());
        preparedStatement.setString(3, commentaire.getContenu());
        preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(5, commentaire.getId_com());
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
        List<Commentaire> commentaires=new ArrayList<>();
        String req ="SELECT * FROM commentaire";
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

        }

        return commentaires;
    }    }
