/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author pietro
 */
public class Comment {

    private String text, allegato;
    private int id_gruppo;
    private Utente utente;
    private Date data;

    public Comment() {
    }

    public Comment(String text, Utente utente, int id_gruppo, Date data, String allegato) {
        this.text = text;
        this.utente = utente;
        this.id_gruppo = id_gruppo;
        this.data = data;
        this.allegato = allegato;
    }

    public void insertComment(Connection con) throws SQLException{
       
         Timestamp currentTimestamp= new Timestamp(data.getTime());
        
        PreparedStatement stm2 = con.prepareStatement("INSERT INTO comments (id_utente, id_gruppo, data, commenti, allegato) VALUES (?,?,?,?,?)");
        try{
            stm2.setInt(1, utente.getCod());
        stm2.setInt(2, id_gruppo);
        stm2.setTimestamp(3, currentTimestamp);
         stm2.setString(4, text);
         stm2.setString(5, allegato);
//      
           stm2.executeUpdate();
           } finally {
                        stm2.close();
                    }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    public String getText() {
        return text;
    }

    public String getAllegato() {
        return allegato;
    }

    /**
     * @return the utente
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
     * @return the id_gruppo
     */
    public int getId_gruppo() {
        return id_gruppo;
    }

    /**
     * @param id_gruppo the id_gruppo to set
     */
    public void setId_gruppo(int id_gruppo) {
        this.id_gruppo = id_gruppo;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

}
