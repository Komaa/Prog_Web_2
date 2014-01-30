/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
/**
 *
 * @author pietro
 */
public class Utente {
    private String username; //0 normale, 1 moderatore
    private String password;
    private String email;
    private int tipo, cod;
    private String avatar;
    private HashMap<Integer,String> inviti=new HashMap<Integer,String>();
    public transient Connection con;
    private Date data;
    
    public Utente(Connection conne){
        con=conne;
 
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
    /**
     * @return the cod
     */
    public int getCod() {
        return cod;
    }
    /**
     * @param cod the cod to set
     */
    public void setCod(int cod) {
        this.cod = cod;
    }
    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }
    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    
    
    public void insertUtente() throws SQLException{
       // System.out.println(con+"\n"+username+password+email+" "+avatar);
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        PreparedStatement stm2 = con.prepareStatement("INSERT INTO utenti (username, password, email, tipo, avatar, data_accesso) VALUES (?,?,?,?,?,?)");
        try{
            stm2.setString(1, username);
        stm2.setString(2, password);
        stm2.setString(3, email);
         stm2.setInt(4, 0);
         stm2.setString(5, avatar);
         stm2.setString(6, currentTime);
//      
           stm2.executeUpdate();
           } finally {
                        stm2.close();
                    }
    }
    
    public void aggiornadatalogin() throws SQLException{
         java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
     PreparedStatement stm = con.prepareStatement("UPDATE utenti SET data_accesso=? WHERE id_utenti=?");
        stm.setString(1, String.valueOf(currentTime));
        stm.setString(2, String.valueOf(cod));
      
        int rs = stm.executeUpdate();
    }
    
    public void updateUtente() throws SQLException{
        PreparedStatement stm = con.prepareStatement("UPDATE utenti SET password='?', avatar='?' WHERE id_utenti='?'");
        stm.setString(1, password);
        stm.setString(2, avatar);
        stm.setString(3, String.valueOf(cod));
      
        int rs = stm.executeUpdate();
    }
    
   static public Utente loadUtente(int id,Connection con) throws SQLException {
       Utente utente=new Utente(con);
       String app;
        PreparedStatement stm = con.prepareStatement("select * from utenti where id_utenti=?");
        stm.setString(1, String.valueOf(id));
       
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    utente.cod=rs.getInt("id_utenti");
                    utente.username=rs.getString("username");
                    utente.password=rs.getString("password");
                    utente.email=rs.getString("email");
                    utente.tipo=rs.getInt("tipo");
                    utente.avatar=rs.getString("avatar");      
                    app=rs.getString("data_accesso");  
                    System.out.println(app);
              //      Date date = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").parse(app);
                //    utente.data=date;
               //     utente.data = new Date(rs.getInt("data_account")); 
                }
            } finally {
                rs.close();
            }
            return utente;
    }
    
    public HashMap<Integer, String> loadInviti() throws SQLException{
   
   
        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti where id_utente=? and stato=?");
        stm.setInt(1, cod);
        stm.setInt(2, 1);
        try {
            ResultSet rs = stm.executeQuery();
            try {
               
                while (rs.next()) {
                    inviti.put(rs.getInt("id_gruppo"), Gruppo.loadGruppo(rs.getInt("id_gruppo"), con).getTitolo());
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return inviti;
    }
    
 
       public int check_user() throws SQLException {
        int id=-1;
        boolean val = false;
        PreparedStatement stm = con.prepareStatement("select * from utenti where username=? and password=?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                  
                    id=Integer.valueOf(rs.getString("id_utenti"));
               
                } 
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return id;
    }
    /**
     * @return the inviti
     */
    public HashMap<Integer,String> getInviti() {
        return inviti;
    }
    /**
     * @param inviti the inviti to set
     */
    public void setInviti(HashMap<Integer,String> inviti) {
        this.inviti = inviti;
    }
    
     public ArrayList<Gruppo> listaGruppi() throws SQLException {
       
       ArrayList<Gruppo> listinviti = new ArrayList<Gruppo>();
        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti where id_utente=? and stato=?");
        stm.setInt(1, cod);
        stm.setString(2, "2");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listinviti.add(Gruppo.loadGruppo(rs.getInt("id_gruppo"), con));
    
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return listinviti;
    }
    
      public HashMap<Integer,String> listaaggGruppi() throws SQLException {
       
       HashMap<Integer,String> listinviti = new HashMap<Integer,String>();
        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti,utenti where id_utente=?, stato=? and utenti.data_account<");
        stm.setInt(1, cod);
        stm.setString(2, "2");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listinviti.put(rs.getInt("id_gruppo"), Gruppo.loadGruppo(rs.getInt("id_gruppo"), con).getTitolo());
    
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return listinviti;
    }
      
      public void valuta_invito(int id_gruppo,int stato) throws SQLException {
        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        PreparedStatement stm = con.prepareStatement("UPDATE gruppi_utenti SET stato=? where id_gruppo=? and id_utente=? and stato=?");
        try {

            stm.setInt(1, stato);
            stm.setInt(2, id_gruppo);
            stm.setInt(3, cod);
            stm.setInt(4, 1);

            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();

        }

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