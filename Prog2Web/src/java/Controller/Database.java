/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author pietro
 */
public class Database implements Serializable{
    
     public transient Connection con;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //carica i driver per connettersi al database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3036/test", "root", "root"); //creo connessione al database   
            this.con = con;
        } catch (Exception e) {
            System.out.println("Errore nella connessione al server");
            e.printStackTrace();
        }

    }
    
     public static void shutdown() {
        try {
            DriverManager.getConnection("com:mysql:;shutdown=true");
        } catch (Exception e) {
        }
    }
}
