package tn.esprit.affariety.services;
import javax.mail.internet.AddressException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T>{


        void ajouter(T t) throws SQLException, AddressException;
        void modifier(T t) throws SQLException;
        void supprimer(String idc) throws SQLException;
        List<T>recuperer() throws SQLException;
    }

