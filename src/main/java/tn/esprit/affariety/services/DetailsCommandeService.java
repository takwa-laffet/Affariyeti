package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.models.DetailsCommande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.affariety.utils.MyDataBase;

public class DetailsCommandeService implements IService<DetailsCommande> {
    private Connection connection;

    public DetailsCommandeService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(DetailsCommande detailsCommande) throws SQLException {
        // Vérifier que tous les champs sont remplis et que les valeurs sont valides
        if (detailsCommande.getNum_article() <= 0 || detailsCommande.getQuantite() <= 0 ||
                detailsCommande.getPrix_unitaire() <= 0 ) {
            throw new IllegalArgumentException("Veuillez remplir tous les champs avec des valeurs valides.");
        }

        // Calculer le sous-total
        float sousTotal = detailsCommande.getQuantite() * detailsCommande.getPrix_unitaire();
        detailsCommande.setSous_totale(sousTotal);

        // Préparer la requête SQL pour l'insertion
        String req = "INSERT INTO detailsCommande (id_com, num_article, nom_article, quantite, prix_unitaire, sous_total)" +
                "SELECT ?, ?, ?, ?, ?, ? FROM commande c JOIN detailsCommande dc ON c.id = dc.id_com WHERE c.id = ?";

        // Exécuter la requête
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, detailsCommande.getCommande().getId());
            ps.setInt(2, detailsCommande.getNum_article());
            ps.setString(3, detailsCommande.getNom_article());
            ps.setInt(4, detailsCommande.getQuantite());
            ps.setFloat(5, detailsCommande.getPrix_unitaire());
            ps.setFloat(6, detailsCommande.getSous_totale());
            ps.setInt(7, detailsCommande.getCommande().getId()); // Pour correspondre à la clause WHERE

            ps.executeUpdate();
        }
    }

    @Override
    public int ajouterr(DetailsCommande detailsCommande) throws SQLException {
        return 0;
    }


    @Override
    public void modifier(DetailsCommande detailsCommande) throws SQLException {

    }

    @Override
    public void supprimer(String nom) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<DetailsCommande> recuperer() throws SQLException {
        return null;
    }


    public List<DetailsCommande> recupererC (Commande commande) throws SQLException {
        List<DetailsCommande> detailsCommandes =  new ArrayList<>();
        String req = "SELECT * FROM detailsCommande WHERE id_com = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, commande.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetailsCommande detailsCommande = new DetailsCommande();
                detailsCommande.setId(rs.getInt("id"));
                detailsCommande.setNum_article(rs.getInt("num_article"));
                detailsCommande.setNom_article(rs.getString("nom_article"));
                detailsCommande.setQuantite(rs.getInt("quantite"));
                detailsCommande.setPrix_unitaire(rs.getFloat("prix_unitaire"));
                detailsCommande.setSous_totale(rs.getFloat("sous_total"));
                detailsCommandes.add(detailsCommande);
            }
        }
        return detailsCommandes;
    }


     /*@Override
   public void ajouter(DetailsCommande detailsCommande) throws SQLException {
        String req = "INSERT INTO DetailsCommande(num_article,nom_article,quantite,prix_unitaire) values('"+detailsCommande.getNum_article()+"','"+ detailsCommande.getNom_article()+"','"+detailsCommande.getQuantite()+"','"+detailsCommande.getPrix_unitaire()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }*/
   /* public void ajouter1(DetailsCommande detailsCommande) throws SQLException {
        String req = "INSERT INTO detailsCommande (id_com, num_article, nom_article, quantite, prix_unitaire, sous_total)"+"VALUES (?, ?, ?, ?, ?,?)";

                ;
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, detailsCommande.getCommande().getId());
        ps.setInt(2, detailsCommande.getNum_article());
        ps.setString(3, detailsCommande.getNom_article());
        ps.setInt(4, detailsCommande.getQuantite());
        ps.setFloat(5, detailsCommande.getPrix_unitaire());
        ps.setFloat(6, detailsCommande.getSous_totale());

         // Assuming you have a method to get id_com from DetailsCommande object
        ps.executeUpdate();
        ps.close();
    }*/
 /*  public void ajouter1(DetailsCommande detailsCommande) throws SQLException {
       // Vérifier que tous les champs sont remplis et que les valeurs sont valides
       if (detailsCommande.getNum_article() <= 0 || detailsCommande.getQuantite() <= 0 ||
               detailsCommande.getPrix_unitaire() <= 0 ) {
           throw new IllegalArgumentException("Veuillez remplir tous les champs avec des valeurs valides.");
       }

       // Calculer le sous-total
       float sousTotal = detailsCommande.getQuantite() * detailsCommande.getPrix_unitaire();
       detailsCommande.setSous_totale(sousTotal);

       // Préparer la requête SQL pour l'insertion
       String req = "INSERT INTO detailsCommande (id_com, num_article, nom_article, quantite, prix_unitaire, )" +
               "VALUES (?, ?, ?, ?, ?)";

       // Exécuter la requête
       try (PreparedStatement ps = connection.prepareStatement(req)) {
           ps.setInt(1, detailsCommande.getCommande().getId());
           ps.setInt(2, detailsCommande.getNum_article());
           ps.setString(3, detailsCommande.getNom_article());
           ps.setInt(4, detailsCommande.getQuantite());
           ps.setFloat(5, detailsCommande.getPrix_unitaire());


           ps.executeUpdate();
       }
   }




    @Override
    public void modifier(DetailsCommande detailsCommande) throws SQLException {
        String req = "UPDATE detailsCommande SET num_article = ? ,nom_article =? ,quantite =? ,prix_unitaire =? ,sous_total =? WHERE  id =? ";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1,detailsCommande.getNum_article());
        ps.setString(2, detailsCommande.getNom_article());
        ps.setInt(3,detailsCommande.getQuantite());
        ps.setFloat(4,detailsCommande.getPrix_unitaire());
        ps.setFloat(5,detailsCommande.getSous_totale());
        ps.setInt(6,detailsCommande.getId());
        ps.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "Delete FROM detailsCommande WHERE id=?";
        PreparedStatement ps =connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();
    }*/

   /* @Override
    public List<DetailsCommande> recuperer() throws SQLException {
        List<DetailsCommande> detailsCommandes =  new ArrayList<>();
        String req = "SELECT dc.*,c.* FROM detailscommande dc JOIN commande c ON dc.id_com=c.id";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()){
            DetailsCommande detailsCommande = new DetailsCommande();
            detailsCommande.setId(rs.getInt("id"));
            detailsCommande.setNum_article(rs.getInt("num_article"));
            detailsCommande.setNom_article(rs.getString("nom_article"));
            detailsCommande.setQuantite(rs.getInt("quantite"));
            detailsCommande.setPrix_unitaire(rs.getFloat("prix_unitaire"));
            detailsCommande.setSous_totale(rs.getFloat("sous_total"));

            detailsCommandes.add(detailsCommande);


            Commande commande = new Commande();
            commande.setId(rs.getInt("c.id"));
            commande.setEtat(rs.getString("c.etat"));
            commande.setCmd_client(rs.getInt("c.cmd_client"));
            commande.setCmd_date(rs.getTimestamp("c.cmd_date"));

            detailsCommande.setCommande(commande);




        }
        return detailsCommandes ;
    }*/

    public List<Integer> recupererIdsCommande() throws SQLException {
        List<Integer> idsCommande = new ArrayList<>();
        String req = "SELECT id FROM commande";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                idsCommande.add(rs.getInt("id"));
            }
        }
        return idsCommande;
    }
    }

