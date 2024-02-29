package tn.esprit.affarietygui.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mydb {
    private static Mydb instance;
    private final String URL="jdbc:mysql://localhost:3306/affariety";
    private final String USER="root";
    private final String PASSWORD="";
    private Connection connection;
    private Mydb(){
        try {
            connection= DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static Mydb getInstance(){
        if(instance == null)
            instance = new Mydb();
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }
}
