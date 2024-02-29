package com.example.affariyetii.services;

import com.example.affariyetii.utils.MyDatabase;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

public class EmailService {

    private final Connection connection;

    public EmailService() {
        this.connection = MyDatabase.getInstance().getConnection();
    }

    // Method to send email
    public void sendEmail(String toEmailAddress, String subject, String bodyText) {
        final String fromEmailAddress = "affarietyaffariety@gmail.com"; // Your Gmail address
        final String CLIENT_SECRET_PATH = "C://Users/takwa/Downloads/client_secret.json";
        final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

        try {
            JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            // Load client secrets
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(
                    EmailService.class.getResourceAsStream(CLIENT_SECRET_PATH)));


            // Set up authorization code flow
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets,
                    Arrays.asList("https://www.googleapis.com/auth/gmail.send"))
                    .build();

            // Generate authorization URL
            AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl()
                    .setRedirectUri(REDIRECT_URI);

            // Redirect user to authorization URL and get authorization code
            // (You need to handle the redirection and obtain the authorization code from the user)

            // Example code to exchange authorization code for access token
            // You should replace this with the actual authorization code obtained from the user
            String authorizationCode = "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";
            GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(authorizationCode)
                    .setRedirectUri(REDIRECT_URI);
            TokenResponse tokenResponse = tokenRequest.execute();

            // Extract access token
            String accessToken = tokenResponse.getAccessToken();

            // Set up email properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Create session with OAuth2 authentication
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmailAddress, accessToken);
                }
            });

            // Create and send email message
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmailAddress));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress));
                message.setSubject(subject);
                message.setText(bodyText);

                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Error sending email", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing email service", e);
        }
    }

    // Method to retrieve user email by client ID
    public String getUserEmailByClientId(String nom, String prenom) {
        String query = "SELECT email FROM user WHERE nom = ? AND prenom = ? AND role = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, "client");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                } else {
                    throw new IllegalArgumentException("Client with nom and prenom not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving client by name", e);
        }
    }
}
