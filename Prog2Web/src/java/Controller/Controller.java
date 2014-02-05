/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Comment;
import Beans.Gruppo;
import Beans.Utente;
import static Controller.Controller.realPath;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pietro
 */
public class Controller extends HttpServlet {

    static public String realPath;
    Database dbmanager = new Database();
    HttpSession session;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        realPath = getServletContext().getRealPath("/");
        int cmd = Integer.parseInt(request.getParameter("cmd"));
        HashMap<Integer, String> listainviti, listaagggruppi;
        Utente u = null;
        Gruppo gruppo = null;
        String stringapp, dirName = realPath + "tmp";
        String originalFilename = null;
        int cod_gruppo, cod_utente, intapp;
        session = request.getSession(true);
        // realPath = getServletContext().getRealPath("/");

        switch (cmd) {
            case 1:             //LOGIN
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                int id;
                u = new Utente(dbmanager.con);
                u.setUsername(username);
                u.setPassword(password);
                id = u.check_user();

                if (id > -1) {
                    
                    u.setCod(id);
                    u = Utente.loadUtente(id, dbmanager.con);
                    if(u.getConfermato()==1){
                    listaagggruppi = u.getaggiornamentigruppi();
                    u.aggiornadatalogin();
                    session.setAttribute("user_id", u.getCod());
                    listainviti = u.loadInviti();
                    request.setAttribute("listainviti", listainviti);
                    request.setAttribute("listaaggruppi", listaagggruppi);
                    request.setAttribute("user", u);
                    forward(request, response, "/home.jsp");
                    }else{
                     request.setAttribute("filtro", 1);
                    forward(request, response, "/index.jsp");   
                    }
                } else {
                    request.setAttribute("filtro", 0);
                    forward(request, response, "/index.jsp");
                }

                break;
            case 2:                     //LOGOUT 

                session.invalidate();
                request.setAttribute("filtro", 2);
                forward(request, response, "/index.jsp");
                break;
            case 3:                     //TASTO_REGISTRAZIONE
                session.invalidate();
                request.setAttribute("filtro", 10);
                forward(request, response, "/index.jsp");
                break;
            case 4:                     //REGISTRAZIONE

                u = new Utente(dbmanager.con);
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                MultipartRequest multi = new MultipartRequest(request, dirName, 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());

                username = multi.getParameter("username");
                password = multi.getParameter("password");
                String password1 = multi.getParameter("password1");
                String email = multi.getParameter("email");
                if ((username.equals("")) || (password.equals("")) || (password1.equals("")) || (email.equals(""))) {
                
                    forward(request, response, "/index.jsp");
                } else if(!Utente.checkname(username,dbmanager.con)){
                    request.setAttribute("filtro", 9);
                    forward(request, response, "/index.jsp");
                }else if (!password.equals(password1)) {
                   request.setAttribute("filtro", 4);
                    forward(request, response, "/index.jsp");
                } else if (!email.matches(emailPattern)) {
                    request.setAttribute("filtro", 5);
                    forward(request, response, "/index.jsp");
                } else {
                 
                    Enumeration files = multi.getFileNames();
                    while (files.hasMoreElements()) {
                        String namepi = (String) files.nextElement();
                        String filename = multi.getFilesystemName(namepi);
                        originalFilename = multi.getOriginalFileName(namepi);
                        String type = multi.getContentType(namepi);
                        File f = multi.getFile(namepi);              
                    }
                     if (originalFilename == null) {
                        originalFilename = "noimage.jpg";
                        u.setUsername(username);
                        u.setPassword(password);
                        u.setEmail(email);
                        u.setAvatar(originalFilename);
                        u.insertUtente();
                        u.getidbyusername();
                        String mg = "Caro " + u.getUsername() + "\n\n";
                        mg += "Si prega di confermare la tua mail prima accedere al forum cliccando sul link qui sotto\n";
                        mg += "<a href=\"http://localhost:8084/Prog2Web/Controller?cmd=21&cod=" + u.getCod() + "\">http://localhost:8084/Prog2Web/Controller?cmd=21&cod=" + u.getCod() + "</a>";
                        try {
                            MailUtility.sendMail(u.getEmail(), "Conferma mail", mg);
                        } catch (MessagingException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.setAttribute("filtro", 3);
                        forward(request, response, "/index.jsp");

                    } else if ((!originalFilename.substring(originalFilename.lastIndexOf(".")).equals(".jpg")) && (!originalFilename.substring(originalFilename.lastIndexOf(".")).equals(".png"))) {
                        //  System.out.println("||||||||||||||4|||||||||||");
                        String source = realPath + "tmp/" + originalFilename;
                        File afile = new File(source);
                        afile.delete();
                        request.setAttribute("filtro", 6);
                        forward(request, response, "/index.jsp");
                    } else {
                        // System.out.println("||||||||||||||5|||||||||||");
                        String source = realPath + "tmp/" + originalFilename;
//                System.out.println("sourEEEEEEEEEEEEEEEEEE:"+ source);
                        String destination = realPath + "img/" + originalFilename;
//                System.out.println("destinationNNNNNNNNNNNNNNNNNNN:"+ destination);
                        File afile = new File(source);
                        File bfile = new File(destination);
                        if (!(bfile.exists())) {
                            InputStream inStream = null;
                            OutputStream outStream = null;

                            try {

                                inStream = new FileInputStream(afile);
                                outStream = new FileOutputStream(bfile);

                                byte[] buffer = new byte[1024];
                                int length;
                                //copy the file content in bytes 
                                while ((length = inStream.read(buffer)) > 0) {
                                    outStream.write(buffer, 0, length);
                                }
                                inStream.close();
                                outStream.close();

                                //delete the original file
                                afile.delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        u.setUsername(username);
                        u.setPassword(password);
                        u.setEmail(email);
                        u.setAvatar(originalFilename);
                        u.insertUtente();
                        u.getidbyusername();
                        String mg = "Caro " + u.getUsername() + "<br><br>";
                        mg += "Si prega di confermare la tua mail prima accedere al forum cliccando sul link qui sotto<br>";
                        mg += "<a href=\"http://localhost:8084/Prog2Web/Controller?cmd=21&cod=" + u.getCod() + "\">http://localhost:8084/Prog2Web/Controller?cmd=21&cod=" + u.getCod() + "</a>";
                        try {
                            MailUtility.sendMail(u.getEmail(), "Conferma mail", mg);
                        } catch (MessagingException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.setAttribute("filtro", 3);
                        forward(request, response, "/index.jsp");
                    }

                }

                break;
            case 5:                     //GESTISCI_ACCOUNT     

                u = Utente.loadUtente((Integer) session.getAttribute("user_id"), dbmanager.con);
                request.setAttribute("user", u);
                forward(request, response, "/gestione.jsp");
                break;
            case 6:                     //CAMBIA_PASSWORD

                String pass = request.getParameter("pass");
                String pass1 = request.getParameter("pass1");
                String pass2 = request.getParameter("pass2");
                u = Utente.loadUtente((Integer) session.getAttribute("user_id"), dbmanager.con);
                if (pass.equals(u.getPassword())) {
                    if (pass1.equals(pass2)) {
                        u.setPassword(pass1);
                        u.updateUtente();
                        request.setAttribute("filtro", 2);
                        request.setAttribute("user", u);
                        forward(request, response, "/gestione.jsp");
                    } else {
                        request.setAttribute("filtro", 1);
                        request.setAttribute("user", u);
                        forward(request, response, "/gestione.jsp");
                    }
                } else {
                    request.setAttribute("filtro", 0);
                    request.setAttribute("user", u);
                    forward(request, response, "/gestione.jsp");
                }
                break;
            case 7:                     //CAMBIA_AVATAR
                id = (Integer) session.getAttribute("user_id");
                u = Utente.loadUtente(id, dbmanager.con);

                originalFilename = null;
                multi = new MultipartRequest(request, dirName, 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());
                Enumeration files = multi.getFileNames();
                while (files.hasMoreElements()) {
                    String namepi = (String) files.nextElement();
                    String filename = multi.getFilesystemName(namepi);
                    originalFilename = multi.getOriginalFileName(namepi);
                    String type = multi.getContentType(namepi);
                    File f = multi.getFile(namepi);
                }
              
                if (originalFilename == null) {
                    request.setAttribute("filtro", 3);
                    forward(request, response, "/gestione.jsp");

                } else if ((!originalFilename.substring(originalFilename.lastIndexOf(".")).equals(".jpg")) && (!originalFilename.substring(originalFilename.lastIndexOf(".")).equals(".png"))) {

                    String source = realPath + "tmp/" + originalFilename;
                    File afile = new File(source);
                    afile.delete();
                    request.setAttribute("filtro", 4);
                    forward(request, response, "/gestione.jsp");
                } else {

                    String source = realPath + "tmp/" + originalFilename;
//                System.out.println("sourEEEEEEEEEEEEEEEEEE:"+ source);
                    String destination = realPath + "img/" + originalFilename;
//                System.out.println("destinationNNNNNNNNNNNNNNNNNNN:"+ destination);
                    File afile = new File(source);
                    File bfile = new File(destination);
                    if (!(bfile.exists())) {
                        InputStream inStream = null;
                        OutputStream outStream = null;

                        try {

                            inStream = new FileInputStream(afile);
                            outStream = new FileOutputStream(bfile);

                            byte[] buffer = new byte[1024];
                            int length;
                            //copy the file content in bytes 
                            while ((length = inStream.read(buffer)) > 0) {
                                outStream.write(buffer, 0, length);
                            }
                            inStream.close();
                            outStream.close();

                            //delete the original file
                            afile.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    File immcancfile = new File(realPath + "img/" + u.getAvatar());
                    immcancfile.delete();
                    u.setAvatar(originalFilename);
                    u.updateUtente();
                    request.setAttribute("filtro", 5);
                    forward(request, response, "/gestione.jsp");
                }
                break;
            case 8:                     //TASTO_CREA_GRUPPO      

                id = (Integer) session.getAttribute("user_id");
                u = Utente.loadUtente(id, dbmanager.con);
                request.setAttribute("user", u);
                forward(request, response, "/creazione_gruppo.jsp");
                break;
            case 9:                     //CREA_GRUPPO    
                String titolo = request.getParameter("titolo");
                if (titolo.equals("")) {
                    request.setAttribute("filtro", 0);
                    forward(request, response, "/creazione_gruppo.jsp");
                } else if(!Gruppo.checknamegroup(titolo,dbmanager.con)){
                    request.setAttribute("filtro", 1);
                     forward(request, response, "/creazione_gruppo.jsp");
                }else {

                    int tipo = Integer.parseInt(request.getParameter("tipo"));
                    gruppo = new Gruppo(dbmanager.con);
                    gruppo.setTitolo(titolo);
                    gruppo.setId_amministratore((Integer) session.getAttribute("user_id"));
                    gruppo.setTipo(tipo);
                    gruppo.setId_gruppo(gruppo.insertGruppo());
                    gruppo.inserisci_admin(gruppo.getId_amministratore());
                    request.setAttribute("gruppo", gruppo);
                    request.setAttribute("invitabili", gruppo.invitabili());
                    request.setAttribute("filtro", 0);
                    forward(request, response, "/gestisci_gruppo.jsp");
                }
                break;
            case 10:                     //CAMBIA_TITOLO
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                stringapp = request.getParameter("titolo");
                 gruppo = Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                 if(stringapp.equals("")){
                      request.setAttribute("gruppo", gruppo);
                    request.setAttribute("invitabili", gruppo.invitabili());
                    request.setAttribute("filtro", 2);
                    forward(request, response, "/gestisci_gruppo.jsp");
                 }else if(!Gruppo.checknamegroup(stringapp,dbmanager.con)){
                       request.setAttribute("gruppo", gruppo);
                    request.setAttribute("invitabili", gruppo.invitabili());
                    request.setAttribute("filtro", 5);
                     forward(request, response, "/gestisci_gruppo.jsp");
                }else{
                   
                    String dirOldName = realPath + "groupsfolder/" + gruppo.getTitolo();
                    String dirNName = realPath + "groupsfolder/" + stringapp;

                    File theDir = new File(dirOldName);
                    File theNDir = new File(dirNName);
                    if (theDir.exists()) {
                        boolean result = theDir.renameTo(theNDir);
                    }
                    gruppo.setTitolo(stringapp);
                    gruppo.updateGruppo();
                    request.setAttribute("gruppo", gruppo);
                    request.setAttribute("invitabili", gruppo.invitabili());
                    request.setAttribute("filtro", 1);
                    forward(request, response, "/gestisci_gruppo.jsp");
                }
                break;
            case 11:                     //CAMBIA_FLAG
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                intapp = Integer.parseInt(request.getParameter("tipo"));
                gruppo = Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                gruppo.setTipo(intapp);
                gruppo.updateGruppo();
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                request.setAttribute("filtro", 3);
                forward(request, response, "/gestisci_gruppo.jsp");
                break;
            case 12:                     //INVITA UTENTE
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                intapp = Integer.parseInt(request.getParameter("id_utente"));
                gruppo = Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                gruppo.invita_utente(intapp);
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                request.setAttribute("filtro", 4);
                forward(request, response, "/gestisci_gruppo.jsp");
                break;
            case 13:                     //ELENCO GRUPPI

                u = Utente.loadUtente((Integer) session.getAttribute("user_id"), dbmanager.con);
                request.setAttribute("user", u);
                request.setAttribute("listagruppi", u.listaGruppi());
                request.setAttribute("listagruppipubblici", Gruppo.listaGruppiaperti(dbmanager.con));
                forward(request, response, "/gruppi.jsp");
                break;
            case 14:                     //ENTRA_GRUPPO
                u = Utente.loadUtente((Integer) session.getAttribute("user_id"), dbmanager.con);
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                gruppo = Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                request.setAttribute("user", u);
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("commenti", gruppo.listaCommenti());
                forward(request, response, "/gruppo.jsp");
                break;
            case 15:                     //INSERISCI_COMMENTO
                u = Utente.loadUtente((Integer) session.getAttribute("user_id"), dbmanager.con);

                //---------------------Upload eventuale file
                dirName = realPath + "tmp";

                multi = new MultipartRequest(request, dirName, 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());

                String messaggio = multi.getParameter("messaggio");
                messaggio = messaggio.replaceAll("<", "");
                messaggio = messaggio.replaceAll(">", "");
                cod_gruppo = Integer.parseInt(multi.getParameter("cod_gruppo"));
                gruppo = Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                if (messaggio.equals("")) {
                    request.setAttribute("gruppo", gruppo);
                    request.setAttribute("commenti", gruppo.listaCommenti());
                    request.setAttribute("filtro", 0);
                    forward(request, response, "/gruppo.jsp");
                } else {
                    files = multi.getFileNames();
                    while (files.hasMoreElements()) {
                        String namepi = (String) files.nextElement();
                        String filename = multi.getFilesystemName(namepi);
                        originalFilename = multi.getOriginalFileName(namepi);
                        String type = multi.getContentType(namepi);
                        File f = multi.getFile(namepi);
                    }
                    if (originalFilename != null) {
                        String source = realPath + "tmp/" + originalFilename;
                        String destination = realPath + "groupsfolder/" + gruppo.getTitolo() + "/" + originalFilename;
                        File afile = new File(source);
                        File bfile = new File(destination);
                        if (!(bfile.exists())) {
                            InputStream inStream = null;
                            OutputStream outStream = null;

                            try {

                                inStream = new FileInputStream(afile);
                                outStream = new FileOutputStream(bfile);

                                byte[] buffer = new byte[1024];
                                int length;
                                //copy the file content in bytes 
                                while ((length = inStream.read(buffer)) > 0) {
                                    outStream.write(buffer, 0, length);
                                }
                                inStream.close();
                                outStream.close();

                                //delete the original file
                                afile.delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            afile.delete();
                            Date date = new java.util.Date();
                            originalFilename = "noallegato";

                            String dirNamee = realPath + "groupsfolder/" + gruppo.getTitolo();
                            String relativName = "groupsfolder/" + gruppo.getTitolo();
                            String[] split = messaggio.split("\\$\\$");
                           
                            for (int i = 0; i < split.length; i++) {

                                if ((i % 2) == 1) {

                                    String dirNameN = dirNamee + "/" + split[i];
                                    String relativNameN = relativName + "/" + split[i];

                                    File theDir = new File(dirNameN);
                                    if (theDir.exists()) {
                                        split[i] = "<a href=\"" + relativNameN + "\"  target=\"_blank\">" + split[i] + "</a>";

                                    } else {
                                        split[i] = "<a href=\"http://" + split[i] + "\"  target=\"_blank\"> " + split[i] + "</a>";
                                    }
                                }
                            }
                            
                            messaggio = "";
                       
                            for (int i = 0; i < split.length; i++) {
                                messaggio += split[i];
                            }
                            
                            Comment commento = new Comment(messaggio, u, cod_gruppo, date, originalFilename);
                            commento.insertComment(dbmanager.con);
                            request.setAttribute("filtro", 1);
                            request.setAttribute("gruppo", gruppo);
                            request.setAttribute("commenti", gruppo.listaCommenti());
                            forward(request, response, "/gruppo.jsp");
                            
                        }
                    } else {
                        originalFilename = "noallegato";
                    }
                    Date date = new java.util.Date();
   String dirNamee = realPath + "groupsfolder/" + gruppo.getTitolo();
                            String relativName = "groupsfolder/" + gruppo.getTitolo();
                            String[] split = messaggio.split("\\$\\$");
                         
                            for (int i = 0; i < split.length; i++) {

                                if ((i % 2) == 1) {

                                    String dirNameN = dirNamee + "/" + split[i];
                                    String relativNameN = relativName + "/" + split[i];

                                    File theDir = new File(dirNameN);
                                    if (theDir.exists()) {
                                        split[i] = "<a href=\"" + relativNameN + "\"  target=\"_blank\">" + split[i] + "</a>";

                                    } else {
                                        split[i] = "<a href=\"http://" + split[i] + "\"  target=\"_blank\"> " + split[i] + "</a>";
                                    }
                                }
                            }
                            messaggio="";
                            for (int i = 0; i < split.length; i++) {
                                messaggio += split[i];
                            }
                            
                    Comment commento = new Comment(messaggio, u, cod_gruppo, date, originalFilename);
                    commento.insertComment(dbmanager.con);
                    request.setAttribute("filtro", 2);
                    request.setAttribute("user", u);
                    request.setAttribute("gruppo", gruppo);
                    request.setAttribute("commenti", gruppo.listaCommenti());
                    forward(request, response, "/gruppo.jsp");

                }
                break;
            case 16:                     //TASTO_HOME

                id = (Integer) session.getAttribute("user_id");
                u = Utente.loadUtente(id, dbmanager.con);
                listainviti = u.loadInviti();
                request.setAttribute("listainviti", listainviti);
                request.setAttribute("user", u);
                forward(request, response, "/home.jsp");

                break;
            case 17:                    //RIFIUTA_INVITO
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                id = (Integer) session.getAttribute("user_id");
                u = Utente.loadUtente(id, dbmanager.con);
                u.valuta_invito(cod_gruppo, 3);
                listainviti = u.loadInviti();
                request.setAttribute("filtro", 0);
                request.setAttribute("listainviti", listainviti);
                request.setAttribute("user", u);
                forward(request, response, "/home.jsp");
                break;
            case 18:                    //ACCETTA_INVITO
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                id = (Integer) session.getAttribute("user_id");
                u = Utente.loadUtente(id, dbmanager.con);
                u.valuta_invito(cod_gruppo, 2);
                listainviti = u.loadInviti();
                request.setAttribute("filtro", 1);
                request.setAttribute("listainviti", listainviti);
                request.setAttribute("user", u);
                forward(request, response, "/home.jsp");
                break;
            case 19:                    //GESTISCI_GRUPPO
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                gruppo = Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                forward(request, response, "/gestisci_gruppo.jsp");
                break;
            case 20:                    //PAGINA MODERAZIONE
                u = Utente.loadUtente((Integer) session.getAttribute("user_id"), dbmanager.con);
                request.setAttribute("user", u);
                ArrayList<Gruppo> listagruppimoderatore = Gruppo.getallgroups(dbmanager.con);
                request.setAttribute("listagruppimoderatore", listagruppimoderatore);
                forward(request, response, "/moderazione.jsp");
                break;
            case 21:                    //CONFERMA MAIL
                cod_utente = Integer.parseInt(request.getParameter("cod"));
                u = Utente.loadUtente(cod_utente, dbmanager.con);
                u.confermamail();
                request.setAttribute("filtro", 7);
                forward(request, response, "/index.jsp");
                break;
            case 22:                     //TASTO_RECUPERO_PASSWORD
                forward(request, response, "/recupero_password.jsp");
                break;
            case 23:                     //RECUPERO_PASSWORD
                stringapp = request.getParameter("cambio");
                u = new Utente(dbmanager.con);
                u.setUsername(stringapp);
                u.getidbyusername();
                if(u.getCod()==-1){
                 request.setAttribute("filtro", 0);
                 forward(request, response, "/recupero_password.jsp");
                }else{
                u = Utente.loadUtente(u.getCod(), dbmanager.con);
                String mg = "Caro " + u.getUsername() + "<br><br>";
                mg += "La tua password e': <br>";
                mg += u.getPassword();
                try {
                    MailUtility.sendMail(u.getEmail(), "Recupero Password", mg);
                } catch (MessagingException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("filtro", 8);
                forward(request, response, "/index.jsp");
                }
                
                break;
            case 24:                //CHIUDI_GRUPPO
                cod_gruppo = Integer.parseInt(request.getParameter("cod_gruppo"));
                Gruppo.closegroup(cod_gruppo, dbmanager.con);
                listagruppimoderatore = Gruppo.getallgroups(dbmanager.con);
                request.setAttribute("listagruppimoderatore", listagruppimoderatore);
                request.setAttribute("filtro", 0);
                forward(request, response, "/moderazione.jsp");
                break;       
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(page);
        rd.forward(request, response);
    }
}
