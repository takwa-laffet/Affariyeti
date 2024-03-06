package com.example.affariyetii.test;

import com.example.affariyetii.services.Chat;
import com.example.affariyetii.services.EnchereService;
import com.example.affariyetii.services.TicketPaimentService;
import com.example.affariyetii.services.TicketService;

public class Main {
    public static void main(String[] args){
        EnchereService enchere = new EnchereService();
        TicketService ticket =new TicketService();
        TicketPaimentService ticketPaiment = new TicketPaimentService();
        Chat chat = new Chat();
        System.out.println(chat.repondreQuestion("hello"));
     //   ticketPaiment.reuperer("en1",12);
        //ticket.ajouter(new Ticket(10,12,5,30,"2023-12-12 15:30:00"));
        //ticket.modifier(new Ticket(1,12,5,5,"2023-12-12 15:30:00"))
        //ticket.supprimer(2);
        //System.out.println(ticket.reuperer());
        //enchere.ajouter(new Enchere("420437812_397039396144701_5918151331745243813_n.png","2023-12-12 14:00:00","2023-12-12 15:00:00","2025","en4"));
     //enchere.supprimer(100);
        //enchere.modifier(new Enchere(4,13,"2023-12-12 14:00:00","2023-12-12 15:00:00","en1",1000));
        // System.out.println(enchere.reuperer());

    }
}
