package tn.esprit.affariety.test;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;
//import tn.esprit.affariety.utils.MyDataBase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {


        //MyDataBase db = MyDataBase.getInstance();
        //MyDataBase db2 = MyDataBase.getInstance();

        //System.out.println(db);
        //System.out.println(db2);
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
            Categorie c= cs.getCategoryByName("Luminaires ");
       //System.out.println( );
        //Produit p= new Produit("Canapé en tissu moderne ","Un canapé en tissu avec un design moderne ", 250.0F,c);
        //Categorie cu = new Categorie("Literie  ","Créez un sanctuaire de sommeil  ");


       // try {
            //cs.ajouter(cu);
           //cs.modifier(new Categorie(17,"ff","gvv"));
           //ps.ajouter(p);
            //ps.modifier(new Produit(30,"Lampe de design minimaliste ","La combinaison de fonctionnalité et d'esthétique",80.0F,c));
          //  System.out.println(p);
           // cs.supprimer(18);
        //} catch (SQLException e){
            //System.err.println(e.getMessage());
        //}


    }

}
