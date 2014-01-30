/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author pietro
 */
public class Comment {

    private String text, id_gruppo, data, allegato;
    private Utente utente;

    public Comment() {
    }

    public Comment(String text, Utente utente, String id_gruppo, String data, String allegato) {
        this.text = text;
        this.utente = utente;
        this.id_gruppo = id_gruppo;
        this.data = data;
        this.allegato = allegato;
    }

    public static ArrayList<Comment> commento(String text, String id_utente, String id_gruppo, String data) {
        ArrayList<Comment> commento = null;

        return commento;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void setId_gruppo(String id_gruppo) {
        this.id_gruppo = id_gruppo;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setAllegato(String allegato) {
        this.allegato = allegato;
    }

    public String getText() {
        return text;
    }


    public String getId_gruppo() {
        return id_gruppo;
    }

    public String getData() {
        return data;
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

}
