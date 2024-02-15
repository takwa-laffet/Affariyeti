package tn.esprit.affariety.services;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.utils.MyDatabase;
import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements IService<Commande>{
    private Connection connection ;
    public CommandeService(){
        connection= tn.esprit.affariety.utils.MyDatabase.getInstance().getConnection();
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
    public void supprimer(int id) throws SQLException {
        String req = "Delete FROM commande WHERE id=?";
        PreparedStatement ps =connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();


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


}





