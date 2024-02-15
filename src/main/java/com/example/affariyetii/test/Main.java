package com.example.affariyetii.test;

import com.example.affariyetii.models.Enchere;
import com.example.affariyetii.models.Ticket;
import com.example.affariyetii.services.EnchereService;
import com.example.affariyetii.services.TicketService;
import com.example.affariyetii.utils.MyDatabase;

public class Main {
    public static void main(String[] args){
        EnchereService enchere = new EnchereService();
        TicketService ticket =new TicketService();

      // ticket.ajouter(new Ticket(9,12,5,30,"2023-12-12 15:30:00"));
        //ticket.modifier(new Ticket(1,12,5,5,"2023-12-12 15:30:00"))
        //ticket.supprimer(2);
        //System.out.println(ticket.reuperer());
       // enchere.ajouter(new Enchere(9,13,"2023-12-12 14:30:000","2023-12-
        enchere.supprimer(5);
        //enchere.modifier(new Enchere(4,13,"2023-12-12 14:00:00","2023-12-12 15:00:00","en1",1000));
        //System.out.println(enchere.reuperer());
         }
}
