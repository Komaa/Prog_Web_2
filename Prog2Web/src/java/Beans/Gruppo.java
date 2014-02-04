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
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author pietro
 */
public class Gruppo {

    private String titolo; //0 per pubblico, 1 per privato
    private int id_amministratore;
    private int tipo, aperto;
    private int sizecomponenti;
    private ArrayList<Comment> messaggi;
    public transient Connection con;
    private int id_gruppo;

    public Gruppo(Connection conne) {
        con = conne;
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

    public int insertGruppo() throws SQLException {
        int numero = 0;
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
        PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi (nome_gruppo,id_amministratore,data,tipo,aperto) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

        try {
            val = false;

            stm2.setString(1, titolo);
            stm2.setInt(2, id_amministratore);
            stm2.setString(3, creationDate);
            stm2.setInt(4, tipo);
            stm2.setInt(5, 0);
            //executeUpdate è per le query di inserimento!
            stm2.executeUpdate();
            ResultSet rs = stm2.getGeneratedKeys();
            if (rs.next()) {
                numero = rs.getInt(1);
            }
        } finally {
            stm2.close();
        }

        return numero;

    }

    public void updateGruppo() throws SQLException {
        PreparedStatement stm = con.prepareStatement("UPDATE gruppi SET nome_gruppo=?,tipo=? where id_gruppo=?");
        try {

            stm.setString(1, titolo);
            stm.setInt(2, tipo);
            stm.setString(3, String.valueOf(getId_gruppo()));

            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    static public Gruppo loadGruppo(int id, Connection con) throws SQLException {
        Gruppo gruppo = new Gruppo(con);
        PreparedStatement stm = con.prepareStatement("select * from gruppi where id_gruppo=?");
        stm.setString(1, String.valueOf(id));

        ResultSet rs = stm.executeQuery();
        try {
            while (rs.next()) {
                gruppo.setId_gruppo(rs.getInt("id_gruppo"));
                gruppo.titolo = rs.getString("nome_gruppo");
                gruppo.setId_amministratore(rs.getInt("id_amministratore"));
                gruppo.tipo = rs.getInt("tipo");
                gruppo.setAperto(rs.getInt("aperto"));

            }
        } finally {
            rs.close();
        }
        return gruppo;
    }

    static public HashMap<Integer, String> listaGruppiaperti(Connection con) throws SQLException {

        HashMap<Integer, String> listgruppi = new HashMap<Integer, String>();
        PreparedStatement stm = con.prepareStatement("select * from gruppi where tipo=?");
        stm.setInt(1, 0);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listgruppi.put(rs.getInt("id_gruppo"), Gruppo.loadGruppo(rs.getInt("id_gruppo"), con).getTitolo());

                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return listgruppi;
    }

    public ArrayList<Utente> invitabili() throws SQLException {
        String tmp;
        ArrayList<Utente> listautenti = new ArrayList<Utente>();
        PreparedStatement stm = con.prepareStatement("select utenti.id_utenti from utenti where id_utenti!=? && utenti.id_utenti NOT IN (select gruppi_utenti.id_utente FROM gruppi_utenti WHERE id_gruppo=?)");
        stm.setInt(1, getId_amministratore());
        stm.setInt(2, getId_gruppo());

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
        stm.setInt(1, getId_gruppo());
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Timestamp tm = rs.getTimestamp("data");
                    Date date = new Date(tm.getTime());
                    commento = new Comment(rs.getString("commenti"), Utente.loadUtente(rs.getInt("id_utente"), con), rs.getInt("id_gruppo"), date, rs.getString("allegato"));
                    listaCommenti.add(commento);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        messaggi=listaCommenti;
        return listaCommenti;

    }

    public boolean invita_utente(int id_utente) throws SQLException {

        boolean val = false;

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
        //al momento ho messo uno stato "1" se sono invitati
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (id_utente,id_gruppo,stato) VALUES (?,?,?)");
        try {
            stm.setInt(1, id_utente);
            stm.setInt(2, getId_gruppo());
            stm.setString(3, "1");
            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
            val = true;
        } finally {
            stm.close();

        }

        return val;
    }

    public boolean inserisci_admin(int id_utente) throws SQLException {

        boolean val = false;

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
        //al momento ho messo uno stato "1" se sono invitati
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (id_utente,id_gruppo,stato) VALUES (?,?,?)");
        try {
            stm.setInt(1, id_utente);
            stm.setInt(2, getId_gruppo());
            stm.setString(3, "2");
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
            stm.setInt(2, getId_gruppo());
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
        PreparedStatement stm = con.prepareStatement("select gruppi_utenti.id_utente FROM gruppi_utenti WHERE id_gruppo=? && stato = ?");
        stm.setInt(1, getId_gruppo());
        stm.setString(2, "2");

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listautenti.add(rs.getString("id_utente"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        setSizecomponenti(listautenti.size());
        return listautenti;

    }

    boolean checkusertogroup(int id_utente) throws SQLException {

        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti where gruppo=? and utente=? and stato=2");
        try {
            stm.setInt(1, getId_gruppo());
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
    
    public static void closegroup(int cod,Connection con) throws SQLException{
          PreparedStatement stm = con.prepareStatement("UPDATE gruppi SET aperto=? WHERE id_gruppo=?");
        try {

            stm.setInt(1, 1);
            stm.setInt(2, cod);
         

            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public int contaCommenti() throws SQLException {

        int commenti = 0;
        PreparedStatement stm = con.prepareStatement("select COUNT(*) from comments where id_gruppo=?");
        stm.setInt(1, getId_gruppo());
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

    /**
     * @return the id_amministratore
     */
    public int getId_amministratore() {
        return id_amministratore;
    }

    /**
     * @param id_amministratore the id_amministratore to set
     */
    public void setId_amministratore(int id_amministratore) {
        this.id_amministratore = id_amministratore;
    }

    /**
     * @return the aperto
     */
    public int getAperto() {
        return aperto;
    }

    /**
     * @param aperto the aperto to set
     */
    public void setAperto(int aperto) {
        this.aperto = aperto;
    }



    /**
     * @return the messaggi
     */
    public ArrayList<Comment> getMessaggi() {
        return messaggi;
    }

    /**
     * @param messaggi the messaggi to set
     */
    public void setMessaggi(ArrayList<Comment> messaggi) {
        this.messaggi = messaggi;
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

    
     public static ArrayList<Gruppo> getallgroups(Connection con) throws SQLException {
        Gruppo tmp;
        ArrayList<Gruppo> listagruppi = new ArrayList<Gruppo>();
        PreparedStatement stm = con.prepareStatement("select * FROM gruppi");

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    tmp=loadGruppo(rs.getInt("id_gruppo"), con);
                    tmp.listaCommenti();
                    tmp.listaUtentiGruppo();
                    listagruppi.add(tmp);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
       
        return listagruppi;
     }

    /**
     * @return the sizecomponenti
     */
    public int getSizecomponenti() {
        return sizecomponenti;
    }

    /**
     * @param sizecomponenti the sizecomponenti to set
     */
    public void setSizecomponenti(int sizecomponenti) {
        this.sizecomponenti = sizecomponenti;
    }
     
     
     
}
