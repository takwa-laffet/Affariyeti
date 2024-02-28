package com.example.test.gui;

import com.example.test.models.CategorieCodePromo;
import com.example.test.models.CodePromo;
import com.example.test.models.Discount;
import com.example.test.models.User;
import com.example.test.services.GestionCodePromo;
import com.example.test.services.GestionDiscount;
import com.example.test.utils.Session;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SelectCodePROMO {
    @javafx.fxml.FXML
    private TextField code;
    private CategorieCodePromo ccp ;
    private User user;

    @javafx.fxml.FXML
    public void verife(ActionEvent actionEvent) throws SQLException {
        GestionCodePromo cp = new GestionCodePromo();
        CodePromo c = new CodePromo();
        c=cp.findByCode(String.valueOf(code.getText()));
        if(c!=null){

           if (cp.checkCodePromoByName(c,this.ccp)==1) {
               GestionDiscount gs = new GestionDiscount();

               gs.Create(new Discount(this.user,c ));
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Code Promo expired ");
            alert.setHeaderText("Code Promo  expired");
            alert.setContentText("can you try again?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Call delete method if "OK" is clicked




            }
        }
    }}
    public void initData(CategorieCodePromo ccp) {
        this.ccp= ccp ;
        this.user = Session.getSession().getUser();
    }
}
