package tn.esprit.affariety.services;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.utils.MyDataBase;
import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommandeService implements IService<Commande>{
    private Connection connection ;
    public CommandeService(){
        connection= tn.esprit.affariety.utils.MyDataBase.getInstance().getConnection();
    }

    @Override

    public void ajouter(Commande commande) throws SQLException {
        String req = "INSERT INTO commande (cmd_client,etat,cmd_date) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, commande.getCmd_client());
        preparedStatement.setString(2, commande.getEtat());
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Utiliser la date courante
        preparedStatement.executeUpdate();


    }

    @Override
    public int ajouterr(Commande commande) throws SQLException {
        return 0;
    }

    @Override
    public void modifier(Commande commande) throws SQLException {
        String req = "UPDATE commande SET cmd_client = ? ,etat =? ,cmd_date =? WHERE  id =? ";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(2,commande.getEtat());
        ps.setInt(1,commande.getCmd_client());
        ps.setTimestamp(3,commande.getCmd_date());
        ps.setInt(4,commande.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(String nom) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "Delete FROM commande WHERE id=?";
        PreparedStatement ps =connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();


    }
  public boolean idExists(int id) throws SQLException {
      try{
          String req = "SELECT * FROM commande WHERE id=?";
          PreparedStatement ps = connection.prepareStatement(req);
          ps.setInt(1, id);
          ResultSet rs = ps.executeQuery();
          return rs.next();
      } catch (SQLException e) {
          e.printStackTrace();
          return false;
      }
  }

    @Override
    public List<Commande> recuperer() throws SQLException {
        List<Commande> commandes =  new ArrayList<>();
        String req = "SELECT * FROM commande";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()){
            Commande commande = new Commande();
            commande.setId(rs.getInt("id"));
            commande.setCmd_client(rs.getInt("cmd_client"));
            commande.setEtat(rs.getString("etat"));
            commande.setCmd_date(rs.getTimestamp("cmd_date"));
            commandes.add(commande);
        }
        return commandes ;

    }


    public Commande recupererParId(int commandId) throws SQLException {
        String req = "SELECT * FROM commande WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, commandId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Commande commande = new Commande();
            commande.setId(rs.getInt("id"));
            commande.setCmd_client(rs.getInt("cmd_client"));
            commande.setEtat(rs.getString("etat"));
            commande.setCmd_date(rs.getTimestamp("cmd_date"));
            return commande;
        } else {
            return null; // Retourner null si la commande n'est pas trouvée
        }
    }
    public List<Commande> trierParClient() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM commande ORDER BY cmd_client";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next()){
            Commande commande = new Commande();
            commande.setId(rs.getInt("id"));
            commande.setCmd_client(rs.getInt("cmd_client"));
            commande.setEtat(rs.getString("etat"));
            commande.setCmd_date(rs.getTimestamp("cmd_date"));
            commandes.add(commande);
        }
        return commandes;
    }
    private Commande createCommandeFromResultSet(ResultSet rs) throws SQLException {
        return new Commande(
                rs.getInt("id"),
                rs.getInt("etat"),
                rs.getString("cmd_client"),
                rs.getTimestamp("cmd_date")

        );
    }
    public List<Commande> tri() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM commande ORDER BY cmd_date DESC";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Commande commande = createCommandeFromResultSet(rs);
                commandes.add(commande);
            }
        }

        // Sort the deliveries by 'datelivraison'
        Collections.sort(commandes, Comparator.comparing(Commande::getCmd_date));

        return commandes;
    }
}





