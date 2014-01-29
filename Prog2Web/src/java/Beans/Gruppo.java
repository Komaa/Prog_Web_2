/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author pietro
 */
public class Gruppo {
    private String titolo; //0 per pubblico, 1 per privato
    private int id_amministratore;
    private int tipo;
    HashSet<Integer> componenti;
    ArrayList<Comment> messaggi;
    public transient Connection con;
    private int id_gruppo;
    
    Gruppo(Connection conne){
        con=conne;
    }
    /**
     * @return the titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @param titolo the titolo to set
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }



    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    
    public void insertGruppo() throws SQLException{
        
       boolean val = false;

                    String dirName = Controller.Controller.realPath + "groupsfolder/" + titolo;
                    File theDir = new File(dirName);
                    if (!theDir.exists()) {
                        System.out.println("creating directory: " + dirName);
                        boolean result = theDir.mkdirs();
                        if (result) {
                            System.out.println("DIR created");
                        }
                    }
                    Date data_creazione = Calendar.getInstance().getTime();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
                    String creationDate = ft.format(data_creazione);

                    //non esiste e quindi si può creare!
                    PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi (nome_gruppo,amministratore,data) VALUES (?,?,?)");
                    try {
                        val = false;

                        stm2.setString(1, titolo);
                        stm2.setInt(2, id_amministratore);
                        stm2.setString(3, creationDate);
                        //executeUpdate è per le query di inserimento!
                        stm2.executeUpdate();
                    } finally {
                        stm2.close();
                    }
               
                }
    
    public void updateGruppo() throws SQLException{
        PreparedStatement stm = con.prepareStatement("UPDATE gruppi SET nome_gruppo=? where id_gruppo=?");
        try {

            stm.setString(1, titolo);
            stm.setString(2, String.valueOf(id_gruppo));

            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
    
   static public Gruppo loadGruppo(int id,Connection con) throws SQLException {
       Gruppo gruppo=new Gruppo(con);
        PreparedStatement stm = con.prepareStatement("select * from gruppi where id_gruppo=?");
        stm.setString(1, String.valueOf(id));
       
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    gruppo.id_gruppo=rs.getInt("id_utenti");
                    gruppo.titolo=rs.getString("nome_gruppo");
                    gruppo.id_amministratore=rs.getInt("amministratore");                                                                    
                }
            } finally {
                rs.close();
            }
            return gruppo;
    }
    
   public ArrayList<Utente> invitabili() throws SQLException{
        String tmp;
        ArrayList<Utente> listautenti = new ArrayList<Utente>();
        PreparedStatement stm = con.prepareStatement("select utenti.id_utenti from utenti where id_utenti!=? && utenti.id_utenti NOT IN (select gruppi_utenti.id_utente FROM gruppi_utenti WHERE id_gruppo=?)");
        stm.setInt(1, id_amministratore);
        stm.setInt(2, id_gruppo);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listautenti.add(Utente.loadUtente(rs.getInt("id_utenti"), con));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }              
        return listautenti;
       
   }
   
    public ArrayList<Comment> listaCommenti() throws SQLException {

        Comment commento;
        ArrayList<Comment> listaCommenti = new ArrayList<Comment>();
        PreparedStatement stm = con.prepareStatement("select * from comments where id_gruppo=? ORDER BY data DESC");
        stm.setInt(1, id_gruppo);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    commento = new Comment(rs.getString("commenti"), rs.getString("id_utente"), rs.getString("id_gruppo"), rs.getString("data"), rs.getString("allegato"));
                    listaCommenti.add(commento);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return listaCommenti;

    }
    
    
    public boolean inserisci_amministratore() throws SQLException {

        boolean val = false;

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
        //al momento ho messo uno stato "1" se sono invitati
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (id_utente,id_gruppo,stato) VALUES (?,?,?)");
        try {
            val = false;

            stm.setInt(1, id_amministratore);
            stm.setInt(2, id_gruppo);
            stm.setString(3, "2");
            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();

        }
        val = false;
        return false;
    }

    public boolean inserisci_utente(int id_utente) throws SQLException {

        boolean val = false;

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
        //al momento ho messo uno stato "1" se sono invitati
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (utente,gruppo,stato) VALUES (?,?,?)");
        try {
            stm.setInt(1, id_utente);
            stm.setInt(2, id_gruppo);
            stm.setString(3, "1");
            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
            val = true;
        } finally {
            stm.close();

        }

        return val;
    }
    
    
    void addcomment(String messaggio, String cod_utente, String originalFilename) throws SQLException {
        Date data_creazione = Calendar.getInstance().getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationDate = ft.format(data_creazione);
        PreparedStatement stm = con.prepareStatement("INSERT INTO comments (id_utente, id_gruppo, data, commenti, allegato)VALUES (?, ?, ?, ?, ?)");
        try {
            stm.setString(1, cod_utente);
            stm.setInt(2, id_gruppo);
            stm.setString(3, creationDate);
            stm.setString(4, messaggio);
            stm.setString(5, originalFilename);
            int rs = stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    
     public ArrayList<String> listaUtentiGruppo() throws SQLException {
        String tmp;
        ArrayList<String> listautenti = new ArrayList<String>();
        PreparedStatement stm = con.prepareStatement("select gruppi_utenti.utente FROM gruppi_utenti WHERE gruppo=? && stato = ?");
        stm.setInt(1, id_gruppo);
        stm.setString(2, "2");

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listautenti.add(rs.getString("utente"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return listautenti;

    }
     
     
       boolean checkusertogroup(int id_utente) throws SQLException {

        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti where gruppo=? and utente=? and stato=2");
        try {
            stm.setInt(1, id_gruppo);
            stm.setInt(2, id_utente);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

    }
       
       public int contaCommenti() throws SQLException {

        int commenti = 0;
        PreparedStatement stm = con.prepareStatement("select COUNT(*) from comments where id_gruppo=?");
        stm.setInt(1, id_gruppo);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    commenti = rs.getInt(1);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return commenti;

    }
    
}
