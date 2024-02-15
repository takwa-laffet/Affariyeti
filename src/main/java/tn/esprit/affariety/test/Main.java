package tn.esprit.affariety.test;
import java.sql.SQLException;
import tn.esprit.affariety.models.Commande;
import tn.esprit.affariety.services.CommandeService;
import tn.esprit.affariety.services.DetailsCommandeService;
import tn.esprit.affariety.models.DetailsCommande;

import java.text.ParseException;
public class Main {
    public static void main(String[] args)  throws ParseException, SQLException {



        // Créer une instance de la classe Publication avec les informations de la nouvelle publication
       //Commande nouvellecmd = new Commande(1,"en cours");
       CommandeService commandeService = new CommandeService();
        DetailsCommandeService detailsCommandeService = new DetailsCommandeService();
        // Créer une instance de la classe PublicationService
        System.out.println(commandeService.recuperer());
        try {
            // Appeler la méthode ajouter pour ajouter la nouvelle commande dans la base de données
           commandeService.ajouter(new Commande(12,"en cours"));
            System.out.println("commande ajoute avec succes");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la commande : " + e.getMessage());
        }
        try {
            // Appeler la méthode modifier pour modifier la  commande dans la base de données
            commandeService.modifier(new Commande(40,12,"en cours"));
            System.out.println("commande modifier avec succes");
        } catch (SQLException e) {
            System.out.println("Erreur lors de modification de la commande : " + e.getMessage());
        }
        try {
            // Appeler la méthode modifier pour modifier la  commande dans la base de données
            commandeService.supprimer(32);
            System.out.println("commande supprimee avec succes");
        } catch (SQLException e) {
            System.out.println("Erreur lors de suppression de la commande : " + e.getMessage());
        }
        //ajouter details commande
        try {
            detailsCommandeService.ajouter(new DetailsCommande(12,3,"vase",12,45));
            System.out.println("details commande ajoute avec succes");
        }catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la details commande : " + e.getMessage());
        }
        //modifier details commande
        try {
            detailsCommandeService.modifier(new DetailsCommande(12,3,"vase",12,45));
            System.out.println("details commande modifier avec succes");
        }catch (SQLException e) {
            System.out.println("Erreur lors de modification de la details commande : " + e.getMessage());
        }
        //supprimer details commande
        try {
            detailsCommandeService.supprimer(12);
            System.out.println("details commande supprimee avec succes");
        }catch (SQLException e) {
            System.out.println("Erreur lors de suppression de la details commande : " + e.getMessage());
        }
        //recuperer details commande
        try {
            System.out.println(detailsCommandeService.recuperer());
        }catch (SQLException e) {
            System.out.println("Erreur lors de recuperation de la details commande : " + e.getMessage());
        }

    }

    }






