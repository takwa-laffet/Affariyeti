package com.example.affariyetii.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
   private final String url ="jdbc:mysql://localhost:3306/affariety";
   private final String USER ="root";
   private final String PWD ="";
   private Connection connection ;

   public MyDatabase() {
      try {
         connection = DriverManager.getConnection(url,USER,PWD);
         System.out.println("connection successful");
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
   }
   public static MyDatabase getInstance(){
       if (instance == null)
           instance=new MyDatabase();
       return instance;
       }
   public Connection getConnection(){
       return connection;
   }
}
