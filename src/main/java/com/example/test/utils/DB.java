package com.example.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private String url="jdbc:mysql://localhost:3306/affariety";
    private String username="root";
    private String password="";
    private Connection connection;
    private  static   DB  instance ;
    private DB(){
        try{
            this.connection=DriverManager.getConnection(url,username,password);
            System.out.println("Connected ! ");
        }catch (SQLException exp){
            System.out.println(exp.getMessage());

        }

    }
    public static DB getInstance(){
        if(instance==null){
            instance=new DB();
        }
        return instance;
    }

    public  Connection getConnection(){
        return  this.connection;
//houni aamalna base de donnes instance sigleton connection taaaytilha b
// this.connection=drivermaneger.getconnection taatiha comme para url user name 'root' password
       // w fil le5r taaml public  Connection getConnection(){
        //        return  this.connection;
    }


}
