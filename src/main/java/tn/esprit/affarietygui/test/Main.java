package tn.esprit.affarietygui.test;

import tn.esprit.affarietygui.models.Commentaire;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.CommentaireService;
import tn.esprit.affarietygui.services.PublicationService;
import tn.esprit.affarietygui.utils.Mydb;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        // Créer une instance de la classe PublicationService
        PublicationService publicationService = new PublicationService();
        CommentaireService commentaireService = new CommentaireService();
//recuperer_pub
            /* try {
            System.out.println(publicationService.recuperer());
            System.out.println("Publication recupéré avec succès !");

        } catch (SQLException e) {
            System.out.println("Erreur " + e.getMessage());
        }

 */
        /*Mydb db=Mydb.getInstance();
        Mydb db2=Mydb.getInstance();
        System.out.println(db);
        System.out.println(db2);*/
        // Création d'une instance de Publication avec des valeurs fictives
       // PublicationService ps=new PublicationService();
       // ps.ajouter(new Publication(1,"je veux une coiff",0,0,"Contenu de la publication"));
//ajout_pub
/*
        // Créer une instance de la classe Publication avec les informations de la nouvelle publication
        Publication nouvellePublication = new Publication(1, 0, 0, "Contenu de la publication", "Chemin/vers/photo");

            // Appeler la méthode ajouter pour ajouter la nouvelle publication dans la base de données
        try {
            publicationService.ajouter(nouvellePublication);
            System.out.println("Publication ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la publication : " + e.getMessage());
        }
 */
//modifier_pub
        /*
        try {
            publicationService.modifier(new Publication(2,1, 2, 4, "new Contenu de la publication", "Chemin1/vers/photo"));
            System.out.println("Publication modifiée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification de la publication : " + ex.getMessage());

        }
*/
//supprimer_pub
     /*
        try {
            publicationService.supprimer(2);
            System.out.println("Publication supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la publication : " + ex.getMessage());

        }
        */
 //ajout_com
/*
      // Créer une instance de la classe Publication avec les informations de la nouvelle publication
        Commentaire nvCom = new Commentaire( 1,3, 1, 1, "nv commentaire");

        // Appeler la méthode ajouter pour ajouter la nouvelle publication dans la base de données
        try {
            commentaireService.ajouter(nvCom);
            System.out.println("commentaire ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
 */
//modifier_commentaire
      /*try {
            commentaireService.modifier(new Commentaire(3, 2, 5, "new ccccomm"));
            System.out.println("commentaire modifié avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du commantaire : " + ex.getMessage());

        }*/
//supprimer_commentaire
      /*  try {
            commentaireService.supprimer(2);
            System.out.println("commentaire supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du commentaire: " + ex.getMessage());

        }*/
//recuperer_commentaire
        /*try {
            System.out.println(commentaireService.recuperer());
            System.out.println("commentaire recupéré avec succès !");

        } catch (SQLException e) {
            System.out.println("Erreur " + e.getMessage());
        }*/
    }

        
    }


