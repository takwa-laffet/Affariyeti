package tn.esprit.gestion.services;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import tn.esprit.gestion.models.Enchere;

public class SocialMediaSharing {

    // Replace these with your own Facebook App ID and Access Token
    private final String APP_ID = "703981421938944";
    private final String ACCESS_TOKEN = "879b6d96ce14d41c69762a48dc817c37";

    public void shareOnFacebook(Enchere enchere) {
        // Initialize FacebookClient with access token
        FacebookClient facebookClient = new DefaultFacebookClient(ACCESS_TOKEN, Version.VERSION_10_0);

        // Message to be shared on Facebook
        String message = "Check out this auction: " + enchere.getNom_enchere() +
                "\nStarting price: " + enchere.getMontantInitial() + " dt" +
                "\nStart date: " + enchere.getDateDebut()+ " " + enchere.getHeured()+
                "\nEnd date: " + enchere.getDateFin()+" " + enchere.getHeuref();

        // Share the message on Facebook
        FacebookType response = facebookClient.publish("me/feed", FacebookType.class,
                Parameter.with("message", message));

        // Print the response
        System.out.println("Post ID: " + response.getId());
    }
}
