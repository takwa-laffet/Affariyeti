package com.example.affariyetii;

import com.example.affariyetii.utils.MyDatabase;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.*;
import java.time.LocalDateTime;


public class NotificationFormController {
    private final Connection connection;

    public NotificationFormController() {
        this.connection = MyDatabase.getInstance().getConnection();
    }

    public void btnNotifcationOnAction(ActionEvent actionEvent) {
        sendNotificationsForUpcomingAuctions();

        // Load the image for notification
        Image image = new Image("file:/C:/Users/takwa/IdeaProjects/affariyetii/src/main/resources/error.png");

        // Create and configure the notification
        Notifications notifications = Notifications.create()
                .graphic(new ImageView(image))
                .text("Allez pour enchérir")
                .title("Bonjour")
                .hideAfter(Duration.seconds(4));

        // Show the notification
        notifications.show();
    }

    public void sendNotificationsForUpcomingAuctions() {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime notificationTime = currentTime.plusMinutes(20);

            String query = "SELECT * FROM enchere WHERE CONCAT(date_debut, ' ', heured) BETWEEN ? AND ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(currentTime));
            ps.setTimestamp(2, Timestamp.valueOf(notificationTime));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int enchereId = rs.getInt("enchere_id");
                String nomEnchere = rs.getString("nom_enchere");
                String dateDebut = rs.getString("date_debut");
                String heureDebut = rs.getString("heured");

                // Send notification to clients for the upcoming auction
                String notificationMessage = "L'enchère '" + nomEnchere + "' débutera le " + dateDebut + " à " + heureDebut;
                sendNotificationToClients(notificationMessage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Method to send notification to clients (implement according to your application's notification mechanism)
    private void sendNotificationToClients(String message) {
        // Implement notification sending logic here
        // This could be sending an email, SMS, or in-app notification
        System.out.println("Notification sent: " + message);
    }
}
