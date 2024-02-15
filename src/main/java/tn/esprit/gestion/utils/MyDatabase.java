package tn.esprit.gestion.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MyDatabase {
    private static MyDatabase instance;
    private final String URL = "jdbc:mysql://localhost:3306/affariyeti";
    private final String USR = "root";
    private final String PWD = "";
    private final Connection connection;


   private MyDatabase() {
        Connection tempConnection = null;
        try {
            tempConnection = DriverManager.getConnection(URL, USR, PWD);
            System.out.println("Connexion réussie");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            connection = tempConnection;
        }
    }

    public static MyDatabase getInstance()
    {
        if(instance == null)
            instance = new MyDatabase();
        return instance;
    }
    public Connection getConnection()
    {
        return connection;
    }






}






