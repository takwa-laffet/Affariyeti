package tn.esprit.affarietygui.services;

import tn.esprit.affarietygui.utils.Mydb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class Chat {

    private final Connection connection;

    public Chat() {
        this.connection = Mydb.getInstance().getConnection();
    }

    public String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            // Créer la requête HTTP POST
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Construire le corps de la requête
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Vérifier le code de réponse HTTP
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lire la réponse
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Extraire et retourner le contenu de la réponse
                return extractContentFromResponse(response.toString());
            } else {
                // Gérer les erreurs HTTP
                throw new RuntimeException("Échec de la récupération de la réponse de l'API ChatGPT. Code d'erreur HTTP : " + responseCode);
            }
        } catch (IOException e) {
            // Gérer les erreurs d'E/S
            throw new RuntimeException("Erreur lors de l'envoi de la requête à l'API ChatGPT : " + e.getMessage(), e);
        }
    }

    public String badword(String message) {
        String question = "detecter si le discours de contenu de la publication est haineux ou non si il ya haineux votre reponse doit etre 100% correcte afficher 1 si non afficher 0 just dit 0 ou 1 idont need anything else  " + message ;
        try {
            String completion = chatGPT(question);
            return completion;
        } catch (RuntimeException e) {
            // Gérer les erreurs liées à l'API ChatGPT
            System.err.println("Erreur lors de la détection de mots grossiers : " + e.getMessage());
            return "-1"; // Retourner une valeur d'erreur spécifique
        }
    }

    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marqueur pour le début du contenu
        int endMarker = response.indexOf("\"", startMarker); // Marqueur pour la fin du contenu
        return response.substring(startMarker, endMarker); // Retourner la sous-chaîne contenant uniquement la réponse
    }
}
