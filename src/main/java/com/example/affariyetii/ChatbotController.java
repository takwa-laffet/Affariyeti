package com.example.affariyetii;


import com.example.affariyetii.services.Chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

public class ChatbotController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInput;

    @FXML
    void sendMessage(ActionEvent event) {

        String message = userInput.getText().trim();
        if (!message.isEmpty()) {
            // Append user message to chat area
            appendMessage("You: " + message);

            if (message.equals("%" + "enchères" + "%")){
                Chat chat = new Chat();
                String response = chat.collecterCommentaires();
                appendMessage("Chatbot: " + response);
            }
            if (message.equals("%" + "FAQ" + "%")){
                Chat chat = new Chat();
                String response = chat.obtenirFAQ();
                appendMessage("Chatbot: " + response);
            }
            // Process user message and generate response
            String response = processUserMessage(message);

            // Append chatbot response to chat area
            appendMessage("Chatbot: " + response);

            // Clear user input field
            userInput.clear();
        }
        else {
            // Show error message if user input is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a message.");
            alert.showAndWait();
        }
        if (message.equals("exit")) {
            System.exit(0);
        }
        if (message.equals("quit")) {
            System.exit(0);
        }
        if(message.equals("bye")){
            System.exit(0);
        }
    }

    private String processUserMessage(String message) {
        Chat chat = new Chat();
        String response = chat.repondreQuestion(message);

        return response;
    }

    private void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }
    @FXML
    void sendMessage(DragEvent event) {
        chatArea.appendText(event + "\n");

    }
}
