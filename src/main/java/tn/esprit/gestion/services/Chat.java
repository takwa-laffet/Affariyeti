package tn.esprit.gestion.services;

import tn.esprit.gestion.utils.MyDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

public class Chat {

    private final Connection connection;

    public Chat() {
        this.connection = MyDatabase.getInstance().getConnection();
    }

    // Method to send email
    public String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";


        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
    public String repondreQuestion(String question) {
        return chatGPT(question);
    }

    public String assistanceEncherir(String itemId, double montantEnchere) {
        String question = "Comment puis-je enchérir sur l'article " + itemId + " avec un montant de " + montantEnchere + " ?";
        String completion = chatGPT(question);
        return completion;
    }
    public String alerteEncheres(String itemId) {
        String question = "Pouvez-vous m'alerter lorsque l'enchère pour l'article " + itemId + " est sur le point de se terminer ?";
        String completion = chatGPT(question);
        return completion;
    }
    public String suivreEncheres(String itemId) {
        String question = "Pouvez-vous me donner des mises à jour sur l'enchère pour l'article " + itemId + " ?";
        String completion = chatGPT(question);
        return completion;
    }
    public  String obtenirFAQ() {
        String question = "Pouvez-vous me donner des informations sur la procédure d'enchères ?";
        String completion = chatGPT(question);
        return completion;
    }
    public  String collecterCommentaires() {
        String question = "Pouvez-vous me demander ce que je pense de mon expérience aux enchères ?";
        String completion = chatGPT(question);
        return completion;
    }
    public String interactionPersonnalisee(String donneesUtilisateur) {
        String question = "Pouvez-vous me fournir des suggestions personnalisées en fonction de " + donneesUtilisateur + " ?";
        String completion = chatGPT(question);
        return completion;
    }
// function badword

    public String badword(String message) {
        String question = "detecter si le discours de contenu de la publication est haineux ou non si il ya haineux votre reponse doit etre 100% correcte afficher 1 si non afficher 0 just dit 0 ou 1 idont need anything else  " + message ;
        try {
            String completion = chatGPT(question);
            return completion;
        } catch (RuntimeException e) {
            // Gérer les erreurs liées à l'API ChatGPT
            System.err.println("Erreur lors de la détection de mots grossiers : " + e.getMessage());
            return "-1";
        }
    }

}