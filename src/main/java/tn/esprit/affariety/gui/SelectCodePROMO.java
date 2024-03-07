package tn.esprit.affariety.gui;

import tn.esprit.affariety.models.CategorieCodePromo;
import tn.esprit.affariety.models.CodePromo;
import tn.esprit.affariety.models.Discount;
import tn.esprit.affariety.models.User;
import tn.esprit.affariety.services.GestionCategorieCodePromo;
import tn.esprit.affariety.services.GestionCodePromo;
import tn.esprit.affariety.services.GestionDiscount;
import tn.esprit.affariety.utils.Session;
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
        System.out.println(cp.findByCode(String.valueOf(code.getText())));
        if(c!=null){

           if (cp.checkCodePromoByName(c,this.ccp)==1) {
               GestionDiscount gs = new GestionDiscount();
               GestionCategorieCodePromo gc = new GestionCategorieCodePromo();
               this.ccp.setLimite(this.ccp.getLimite()-1);
               gs.Create(new Discount(this.user,c ));
               gc.update(this.ccp);

        }else {
               System.out.println("aaaaaa");
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
