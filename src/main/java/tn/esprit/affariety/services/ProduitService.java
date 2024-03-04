package tn.esprit.affariety.services;

import javafx.scene.image.Image;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.utils.MyDataBase;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import javax.mail.Session;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IService<Produit> {
    private Connection connection;

    public ProduitService(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Produit produit) throws SQLException, AddressException {
        try {


            String req = "INSERT INTO produit (id_c,nom_p,description_p,prix_p,image_p) VALUES ('" + produit.getCategorie().getId_c() + "','" + produit.getNom_p() + "','" + produit.getDescription_p() + "','" + produit.getPrix_p() + "','" +produit.getImage_p() + "')";
            Statement st = connection.createStatement();


            st.executeUpdate(req);
            String request = " select * from user where role=?" ;
            PreparedStatement s = connection.prepareStatement(request);
            s.setString(1, "client");


            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("email"));
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ghadabm1641@gmail.com", "bhyy gtpa cjqh esjv");
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("ghadabm1641@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rs.getString("email")));
                message.setSubject("Nouveau produit");
                message.setText("Produit "+produit.getNom_p()+" est ajoute");
                session.setDebug(true);
                Transport.send(message);
                System.out.println("message sent successfully....");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*@Override
    public void modifier(Produit produit) throws SQLException {
        String req = "UPDATE produit SET nom_p = ?, description_p = ?, prix_p = ?, id_c = ? WHERE id_p = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, produit.getNom_p());
        ps.setString(2, produit.getDescription_p());
        ps.setFloat(3, produit.getPrix_p());
        ps.setInt(4, produit.getCategorie().getId_c());  // Assurez-vous que vous avez une méthode getId_c() dans votre classe Categorie
        ps.setInt(5, produit.getId_p());
        ps.executeUpdate();
    }*/
    @Override
    public void modifier(Produit produit) throws SQLException {
        String req = "UPDATE produit SET nom_p = ?, id_c = ?, description_p = ?, prix_p = ? WHERE id_p = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(req)) {
                ps.setString(1, produit.getNom_p());
                ps.setInt(2, produit.getCategorie().getId_c());
                ps.setString(3, produit.getDescription_p());
                ps.setFloat(4, produit.getPrix_p());

                ps.setInt(5, produit.getId_p());

                int rowsAffected = ps.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected.");
            }

            // Commit the changes
            connection.commit();
        } catch (SQLException e) {
            // If an error occurs, print details, rollback changes, and re-throw the exception
            e.printStackTrace();
            connection.rollback();
            throw e;
        } finally {
            // Reset auto-commit to true to avoid issues in subsequent database operations
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void supprimer(String nom_p) throws SQLException {
        String req = "DELETE FROM produit WHERE nom_p = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, nom_p);
        ps.executeUpdate();

    }

   /* @Override
    public List<Produit> recuperer() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req ="SELECT * FROM produit";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Produit produit = new Produit();
            produit.setId_p(rs.getInt("id_p"));
            produit.setNom_p(rs.getString("nom_p"));
            produit.setDescription_p(rs.getString("description_p"));


            produits.add(produit);

        }
        return produits

                ;
    }
*/
   @Override
   public List<Produit> recuperer() throws SQLException {
       List<Produit> produits = new ArrayList<>();
       String req = "SELECT p.*, c.nom_c as categorie_nom " +
               "FROM produit p " +
               "JOIN categorie c ON p.id_c = c.id_c";

       try (Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req)) {

           while (rs.next()) {
               Produit produit = new Produit();
               produit.setId_p(rs.getInt("id_p"));
               produit.setNom_p(rs.getString("nom_p"));

               produit.setDescription_p(rs.getString("description_p"));
               produit.setPrix_p(rs.getFloat("prix_p"));
               produit.setImage_p(rs.getString("image_p"));

               Categorie categorie = new Categorie();
               categorie.setId_c(rs.getInt("id_c"));

               categorie.setNom_c(rs.getString("categorie_nom")); // Retrieve the category name from the alias
               produit.setCategorie(categorie);

               produits.add(produit);
           }
       }

       return produits;
   }
   /*public Produit getProduitById(int produitId) throws SQLException {
        String req = "SELECT * FROM produit WHERE id_p = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, produitId);


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produit produit = new Produit();
                    produit.setId_p(rs.getInt("id_p"));
                    produit.setNom_p(rs.getString("nom_p"));
                    produit.setDescription_p(rs.getString("description_p"));
                    produit.setPrix_p(rs.getFloat("prix_p"));

                    return produit;
                }
            }
        }
        return null;
    }*/
   public Produit getProduitById(int produitId) throws SQLException {
       System.out.println(produitId);
       String req = "SELECT p.*, c.id_c " +
               "FROM produit p " +
               "JOIN categorie c ON p.id_c = c.id_c " +
               "WHERE p.id_p = ?";
       CategorieService cs = new CategorieService();

       try (PreparedStatement ps = connection.prepareStatement(req)) {
           ps.setInt(1, produitId);

           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                   Produit produit = new Produit();
                   produit.setId_p(rs.getInt("id_p"));
                   produit.setNom_p(rs.getString("nom_p"));
                   produit.setDescription_p(rs.getString("description_p"));
                   produit.setImage_p(rs.getString("image_p"));
                   produit.setPrix_p(rs.getFloat("prix_p"));

                   produit.setCategorie(cs.getCategoryById(rs.getInt("id_c")));

                   return produit;
               }
           }
       }
       return null;
   }

    public Produit getProduitByName(String name) throws SQLException {
        String req = "SELECT * FROM produit WHERE nom_p = ?";
        CategorieService cs = new CategorieService();

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produit produit = new Produit();
                    produit.setId_p(rs.getInt("id_p"));
                    produit.setCategorie(cs.getCategoryByName(rs.getString("id_c")));
                    produit.setNom_p(rs.getString("nom_p"));
                    produit.setDescription_p(rs.getString("description_p"));
                    produit.setPrix_p(rs.getFloat("prix_p"));

                    return produit;
                }
            }
        }

        // Return null or throw an exception if the category is not found
        return null; // You can choose a suitable default value
    }
    /*public Produit getProduitByName(String nomProduit) throws SQLException {
        String req = "SELECT p.*, c.nom_c as categorie_nom " +
                "FROM produit p " +
                "JOIN categorie c ON p.id_c = c.id_c " +
                "WHERE p.nom_p = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nomProduit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produit produit = new Produit();
                    produit.setId_p(rs.getInt("id_p"));
                    produit.setNom_p(rs.getString("nom_p"));
                    produit.setDescription_p(rs.getString("description_p"));
                    produit.setPrix_p(rs.getFloat("prix_p"));

                    Categorie categorie = new Categorie();
                    categorie.setId_c(rs.getInt("id_c"));
                    categorie.setNom_c(rs.getString("categorie_nom"));
                    produit.setCategorie(categorie);

                    return produit;
                }
            }
        }

        // Retourner null si le produit n'est pas trouvé
        return null;
    }*/
    public int countProducts() throws SQLException {
        String query = "SELECT COUNT(*) FROM produit";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        // En cas d'erreur ou si aucun produit n'est trouvé
        return 0;
    }

}

