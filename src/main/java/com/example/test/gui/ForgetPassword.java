package com.example.test.gui;

import com.example.test.services.GestionUser;
import com.example.test.utils.MailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;



public class ForgetPassword {
    @FXML
    private TextField codeField;
    @FXML
    private Label codeLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField emailTextfield;
    @FXML
    private Label invalidText;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private Button verifierButton;
    @FXML
    private Label passwordL;
    @FXML
    private Label confirmL;
    @FXML
    private Button changeButton;
    @FXML
    private ImageView imageView;


    public void getVerificationCode(ActionEvent event){
        emailTextfield.setDisable(true);
        GestionUser us = new GestionUser();
        if(us.UserExistsByEmail(emailTextfield.getText()) == false){

            invalidText.setText("User does not exist");
            invalidText.setVisible(true);
            emailTextfield.setDisable(false);

            return;
        }
        if( MailUtil.sendPasswordResetMail(emailTextfield.getText(), us.getVerificationCodeByEmail(emailTextfield.getText()))) {
            codeField.setVisible(true);
            codeLabel.setVisible(true);
            loginButton.setVisible(false);
            verifierButton.setVisible(true);


        }
    }
    public void changePassword(ActionEvent event){
        GestionUser us = new GestionUser();
        String code = us.getVerificationCodeByEmail(emailTextfield.getText());
        if(codeField.getText().equals(code)){
            System.out.println("ok");
            codeField.setDisable(true);
            loginButton.setVisible(false);
            verifierButton.setVisible(false);
            password.setVisible(true);
            confirmpassword.setVisible(true);
            passwordL.setVisible(true);
            confirmL.setVisible(true);
            changeButton.setVisible(true);
        }else{
            invalidText.setText("Invalid code");
            invalidText.setVisible(true);
        }
    }
    public void updatePassword(ActionEvent event){
        GestionUser us = new GestionUser();
        String code = us.getVerificationCodeByEmail(emailTextfield.getText());
        if(codeField.getText().equals(code)){

            if(!password.getText().equals(confirmpassword.getText())){
                invalidText.setText("Passwords do not match");
                invalidText.setVisible(true);
                return;
            }

            String hashedPassword = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
            System.out.printf(hashedPassword);
            us.changePasswordByEmail(emailTextfield.getText(), hashedPassword);
        }
    }
}
