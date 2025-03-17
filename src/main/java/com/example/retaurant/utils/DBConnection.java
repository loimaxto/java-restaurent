package com.example.retaurant.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private DBConnection() {
    }

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            InputStream input = new FileInputStream("src/main/java/config.properties");
            props.load(input);

            String URL = props.getProperty("db.url");
            String USER = props.getProperty("db.user");
            String PASSWORD = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found! or Unable to load config file");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println(e);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) { 
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    // run main for checking retieve infor connection only purpose !!
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Properties props = new Properties();
        InputStream input = new FileInputStream("src/main/java/config.properties");
        props.load(input);

        // Retrieve the properties
        String dbUrl = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");
        
        Connection c = getConnection();
        // Use the properties to establish a database connection
        System.out.println("Database URL: " + dbUrl);
        System.out.println("Database User: " + dbUser);
        System.out.println("Database Password: " + dbPassword);

    }
}
