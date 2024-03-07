package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Commentaire;
import tn.esprit.affariety.models.Publication;
import tn.esprit.affariety.models.User;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentaireService implements IService<Commentaire>{
    private Connection connection;
    public  CommentaireService(){
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {

    }

    @Override
    public int ajouterr(Commentaire commentaire) throws SQLException {
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
    public void supprimer(String nom) throws SQLException {

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

//version 1
   /* public List<Commentaire> recuperer(int id_pub) throws SQLException {
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
    }*/

    //version2
    public List<Commentaire> recuperer(int id_pub) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT c.*, p.*, u.nom AS nom_utilisateur ,u.prenom AS prenom_utilisateur  FROM commentaire c " +
                "JOIN publication p ON c.id_pub = p.id_pub " +
                "JOIN user u ON c.id_client = u.id " +
                "WHERE c.id_pub = ?";
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

            // Créez un objet User pour stocker les informations de l'utilisateur
            User user = new User();
            user.setNom(rs.getString("nom_utilisateur")); // Assurez-vous que le setter du nom de l'utilisateur est présent dans la classe User
            user.setPrenom(rs.getString("prenom_utilisateur"));
            // Associez l'utilisateur au commentaire
            commentaire.setUser(user);

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
}
    public Map<String, Integer> getCommentCountsPerUser() throws SQLException {
        Map<String, Integer> commentCountsPerUser = new HashMap<>();

        String query = "SELECT u.nom, u.prenom, COUNT(c.id_com) AS comment_count " +
                "FROM commentaire c " +
                "JOIN user u ON c.id_client = u.id " +
                "GROUP BY u.nom, u.prenom";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String userPrenom = resultSet.getString("prenom");
                String userName = resultSet.getString("nom");
                int commentCount = resultSet.getInt("comment_count");
                commentCountsPerUser.put(userPrenom + " " + userName, commentCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return commentCountsPerUser;
    }
    // Method to retrieve the number of comments per date
    public Map<Timestamp, Integer> getCommentCountsPerDate() throws SQLException {
        Map<Timestamp, Integer> commentCountsPerDate = new HashMap<>();

        String query = "SELECT DATE(date_com) AS comment_date, COUNT(id_com) AS comment_count " +
                "FROM commentaire " +
                "GROUP BY DATE(date_com)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Timestamp commentDate = resultSet.getTimestamp("comment_date");
                int commentCount = resultSet.getInt("comment_count");
                commentCountsPerDate.put(commentDate, commentCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return commentCountsPerDate;
    }
    // Method to retrieve the number of comments per publication of each user
    public Map<String, Integer> getCommentCountsPerUserAndPublication() throws SQLException {
        Map<String, Integer> commentCountsPerUser = new HashMap<>();

        String query = "SELECT CONCAT(u.nom, ' ', u.prenom) AS user_name, COUNT(c.id_com) AS comment_count " +
                "FROM commentaire c " +
                "JOIN publication p ON c.id_pub = p.id_pub " +
                "JOIN user u ON p.id_client = u.id " +
                "GROUP BY user_name";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String userName = resultSet.getString("user_name");
                int commentCount = resultSet.getInt("comment_count");
                String key = userName + ":" + commentCount;
                commentCountsPerUser.put(key, commentCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return commentCountsPerUser;
    }


}


