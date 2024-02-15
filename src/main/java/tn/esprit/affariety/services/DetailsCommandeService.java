package tn.esprit.affariety.services;

import tn.esprit.affariety.models.DetailsCommande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.affariety.utils.MyDatabase;

public class DetailsCommandeService implements IService<DetailsCommande> {
    private Connection connection ;
    public DetailsCommandeService(){connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(DetailsCommande detailsCommande) throws SQLException {
        String req = "INSERT INTO DetailsCommande(num_article,nom_article,quantite,prix_unitaire,sous_total) values('"+detailsCommande.getNum_article()+"','"+ detailsCommande.getNom_article()+"','"+detailsCommande.getQuantite()+"','"+detailsCommande.getPrix_unitaire()+"','"+detailsCommande.getSous_totale()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
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
    }

    @Override
    public List<DetailsCommande> recuperer() throws SQLException {
        List<DetailsCommande> detailsCommandes =  new ArrayList<>();
        String req = "SELECT * FROM detailsCommande";
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
        }
        return detailsCommandes ;
    }
    }

