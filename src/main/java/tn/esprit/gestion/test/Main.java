package tn.esprit.gestion.test;

import org.w3c.dom.ls.LSOutput;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.services.DepotService;
import tn.esprit.gestion.services.LivraisonService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {


        // Assuming the class that contains the modifier method is named LivraisonService
        LivraisonService livraisonService = new LivraisonService();

        // Create a Livraison object with the required information
        Livraison livraisonToUpdate = new Livraison();
        livraisonToUpdate.setId(184);  // Set the ID of the Livraison you want to update
        livraisonToUpdate.setAdresselivraison("New okba");

        // Set Timestamp value for datecommande
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse("2024-02-28 10:30:00");
            livraisonToUpdate.setDatecommande(new Timestamp(parsedDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
        }

        // Set Date value for datelivraison
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse("2024-03-05");
            livraisonToUpdate.setDatelivraison(new java.sql.Date(parsedDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
        }

        livraisonToUpdate.setStatuslivraison("New Status");
        livraisonToUpdate.setIddepot(2);  // Set the depot ID as needed

        // Call the modifier method to update the Livraison
        try {
            livraisonService.modifier(livraisonToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
        }



    }}



        // Example: Search by ID



    /*
        try {
            Date utilDate = dateFormat.parse("2022-01-01");
            Timestamp dateCommande = new Timestamp(utilDate.getTime());

            Depot depot = new Depot();
            depot.setIddepot(1);

            Livraison livraison = new Livraison("kasserine", dateCommande, new java.sql.Date(utilDate.getTime()), "en cours", depot.getIddepot());
            livraisonService.ajouter(livraison);
            System.out.println("Livraison added successfully.");
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error adding Livraison: " + e.getMessage());
        }

        // Deleting an existing Livraison
        int idToDelete = 141; // replace with the ID of the Livraison you want to delete

        try {
            livraisonService.supprimer(idToDelete);
            System.out.println("Livraison deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting Livraison: " + e.getMessage());
        }

        // Retrieving and printing the list of Livraisons
        try {
            List<Livraison> livraisons = livraisonService.recherche(Timestamp.valueOf("2024-02-23 14:46:36"));
            System.out.println(livraisons);
        } catch (SQLException e) {
            e.printStackTrace(); // This will print the full stack trace for debugging.
            System.out.println("Error retrieving Livraisons: " + e.getMessage());
        }
        */





        /**/


/*
        try {
            int id = 146; // Assurez-vous d'initialiser correctement votre identifiant


            // Récupérez la Livraison que vous souhaitez modifier (peut-être à partir de votre liste de livraisons ou de la base de données)
            Livraison livraisonAModifier = livraisonService.getById(id); // Utilisez la méthode getById pour récupérer la Livraison par son ID

            // Vérifiez si la Livraison a été trouvée
            if (livraisonAModifier != null) {
                // Modifiez les données de la Livraison si nécessaire
                livraisonAModifier.setAdresselivraison("ppppppppp"); // Exemple de modification

                // Appelez la méthode modifier de LivraisonService avec l'instance modifiée de Livraison
                livraisonService.modifier(livraisonAModifier);

                System.out.println("Opération réussie !!!");
            } else {
                System.out.println("Livraison non trouvée pour l'ID : " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }}}




/*
DepotService depotService = new DepotService();
       Depot depot = new Depot();
        depot.setIddepot(2);
        depot.setNomdepot("magasin principal");
        depot.setAdresse("tunis");
        depotService.ajouter(depot);


/*
        try {
            int id = 1; // Assurez-vous d'initialiser correctement votre identifiant
            DepotService depotService = new DepotService();

            // Créez une instance de Depot avec les données appropriées
            Depot depot = new Depot(7, "Magasin Principal", "TUNIS");

            // Appelez la méthode modifier de DepotService avec l'instance de Depot
            depotService.modifier(depot);

            System.out.println("Opération réussie !!!");
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

*/
        /*

        try {
            int id;
          DepotService depotService = new DepotService();
            depotService.supprimer(2);
            System.out.println("bye bye !!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

    }*/









