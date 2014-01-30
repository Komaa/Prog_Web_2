/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Beans.Gruppo;
import Beans.Utente;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HashMap<Integer, String> listainviti;
        Utente u = null;
        Gruppo gruppo=null;
        String stringapp;
        int cod_gruppo,cod_utente,intapp;
        session= request.getSession(true);
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
                 //u.aggiornadatalogin();
                if(id>-1){
                
                u = Utente.loadUtente(id, dbmanager.con);
                session.setAttribute("user_id", u.getCod());
                listainviti=u.loadInviti();
                request.setAttribute("listainviti", listainviti);
                request.setAttribute("user", u);
                forward(request, response, "/home.jsp");
                }else{
                 forward(request, response, "/index.jsp");
                }
                
                break;
            case 2:                     //LOGOUT 
               
                session.invalidate();
                forward(request, response, "/index.jsp");
                break;
            case 3:                     //TASTO_REGISTRAZIONE
                forward(request, response, "/registrazione.jsp");
                break;
            case 4:                     //REGISTRAZIONE
                u = new Utente(dbmanager.con);
               
                String dirName = realPath + "tmp";

//                MultipartRequest multi = new MultipartRequest(request, dirName, 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());
//
//                action = multi.getParameter("action");
//                messaggio = multi.getParameter("messaggio");
//                messaggio = messaggio.replaceAll("<", "");
//                messaggio = messaggio.replaceAll(">", "");    
//                utente = multi.getParameter("utente");
//                titolo_gruppo = multi.getParameter("Accedi");
//                if (messaggio.equals("")) {
//                    out.println(Stampa.header("Forum del gruppo: " + titolo_gruppo));
//                    out.println(Stampa.section_content("Questo è il forum del vostro gruppo, condividete!"));
//                    out.println(Stampa.div(2));
//                    out.println(Stampa.alert("danger", "E' obbligatorio inserire un commento!"));
//
//                } else {
////             System.out.println("FILES:");
//                    Enumeration files = multi.getFileNames();
//                    while (files.hasMoreElements()) {
//                        namepi = (String) files.nextElement();
//                        filename = multi.getFilesystemName(namepi);
//                        originalFilename = multi.getOriginalFileName(namepi);
//                        String type = multi.getContentType(namepi);
//                        File f = multi.getFile(namepi);
////                System.out.println("name: " + namepi);
////                System.out.println("filename: " + filename);
////                System.out.println("originalFilename: " + originalFilename);
////                System.out.println("type: " + type);
//                        if (f != null) {
////                System.out.println("f.toString(): " + f.toString());
////                System.out.println("f.getName(): " + f.getName());
////                System.out.println("f.exists(): " + f.exists());
////                System.out.println("f.length(): " + f.length());
//                        }
//                    }
//                    if (originalFilename != null) {
//                        String source = realPath + "tmp/" + originalFilename;
////                System.out.println("sourEEEEEEEEEEEEEEEEEE:"+ source);
//                        String destination = realPath + "groupsfolder/" + titolo_gruppo + "/" + originalFilename;
////                System.out.println("destinationNNNNNNNNNNNNNNNNNNN:"+ destination);
//                        File afile = new File(source);
//                        File bfile = new File(destination);
//                        if (!(bfile.exists())) {
//                            InputStream inStream = null;
//                            OutputStream outStream = null;
//
//                            try {
//
//                                inStream = new FileInputStream(afile);
//                                outStream = new FileOutputStream(bfile);
//
//                                byte[] buffer = new byte[1024];
//                                int length;
//                                //copy the file content in bytes 
//                                while ((length = inStream.read(buffer)) > 0) {
//                                    outStream.write(buffer, 0, length);
//                                }
//                                inStream.close();
//                                outStream.close();
//
//                                //delete the original file
//                                afile.delete();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            out.println(Stampa.header("Forum del gruppo: " + titolo_gruppo));
//                            out.println(Stampa.section_content("Questo è il forum del vostro gruppo, condividete!"));
//                            out.println(Stampa.div(2));
//                            out.println(Stampa.alert("danger", "Il file che hai caricato è già presente"));
//                            stampa = false;
//                            afile.delete();
//                        }
//                    } else {
//                        originalFilename = "noallegato";
//                    }
//                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                break;
            case 5:                     //GESTISCI_ACCOUNT     
                
                u = Utente.loadUtente((Integer)session.getAttribute("user_id"), dbmanager.con);
                request.setAttribute("user", u);
                forward(request, response, "/gestione.jsp");
                break;
            case 6:                     //CAMBIA_PASSWORD
               
                String pass = request.getParameter("pass");
                String pass1 = request.getParameter("pass1");
                String pass2 = request.getParameter("pass2");
                 u = Utente.loadUtente((Integer)session.getAttribute("user_id"), dbmanager.con);
                 if(pass.equals(u.getPassword())){
                if(pass1.equals(pass2)){
                    u.setPassword(pass1);
                    u.updateUtente();
                    request.setAttribute("user", u);
                    forward(request, response, "/gestione.jsp");      
                }else{
                request.setAttribute("user", u);
                forward(request, response, "/gestione.jsp");    
                }
                }else{
                     request.setAttribute("user", u);
                forward(request, response, "/gestione.jsp");   
                 }
                break;
            case 7:                     //CAMBIA_AVATAR
                break;
            case 8:                     //TASTO_CREA_GRUPPO      
                
                 id=(Integer)session.getAttribute("user_id");
                 u = Utente.loadUtente(id, dbmanager.con);
                 request.setAttribute("user", u);
                forward(request, response, "/creazione_gruppo.jsp");
                break;
            case 9:                     //CREA_GRUPPO    
                String titolo = request.getParameter("titolo");
                if(titolo.equals("")){
                    forward(request, response, "/creazione_gruppo.jsp");    
                }else{
                    
                    int tipo= Integer.parseInt(request.getParameter("tipo")); 
                    gruppo=new Gruppo(dbmanager.con);
                    gruppo.setTitolo(titolo);
                    gruppo.setId_amministratore((Integer) session.getAttribute("user_id"));
                    gruppo.setTipo(tipo);
                    gruppo.insertGruppo();
                    gruppo.inserisci_utente(gruppo.getId_amministratore());
                    request.setAttribute("gruppo", gruppo);
                    request.setAttribute("invitabili", gruppo.invitabili());
                    forward(request, response, "/gestisci_gruppo.jsp");
                }
                break;
            case 10:                     //CAMBIA_TITOLO
                cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                stringapp= request.getParameter("titolo"); 
                if(!stringapp.equals("")){
                gruppo= Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                gruppo.setTitolo(stringapp);
                gruppo.updateGruppo();
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                forward(request, response, "/gestisci_gruppo.jsp");
                }
                break;
            case 11:                     //CAMBIA_FLAG
                cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                intapp= Integer.parseInt(request.getParameter("tipo")); 
                gruppo= Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                gruppo.setTipo(intapp);
                gruppo.updateGruppo();
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                forward(request, response, "/gestisci_gruppo.jsp");
                break;    
            case 12:                     //INVITA UTENTE
                cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                intapp= Integer.parseInt(request.getParameter("id_utente")); 
                gruppo= Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                gruppo.inserisci_utente(intapp);
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                forward(request, response, "/gestisci_gruppo.jsp");
                break;
            case 13:                     //ELENCO GRUPPI
               
                u = Utente.loadUtente((Integer)session.getAttribute("user_id"), dbmanager.con);
                request.setAttribute("user", u);
                request.setAttribute("listagruppi", u.listaGruppi());
                request.setAttribute("listagruppipubblici", Gruppo.listaGruppiaperti(dbmanager.con));
                forward(request, response, "/gruppi.jsp");
                break;
            case 14:                     //ENTRA_GRUPPO
                cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                gruppo= Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("commenti", gruppo.listaCommenti());
                forward(request, response, "/gruppo.jsp");
                break;
            case 15:                     //INSERISCI_COMMENTO

                break;
            case 16:                     //TASTO_HOME
                 
                 id=(Integer)session.getAttribute("user_id");
                 u = Utente.loadUtente(id, dbmanager.con);
                 listainviti=u.loadInviti();
                 request.setAttribute("listainviti", listainviti);
                 request.setAttribute("user", u);
                 forward(request, response, "/home.jsp");
                
                 break;
            case 17:                    //RIFIUTA_INVITO
                 cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                 id=(Integer)session.getAttribute("user_id");  
                 u = Utente.loadUtente(id, dbmanager.con);
                 u.valuta_invito(cod_gruppo, 3);
                 listainviti=u.loadInviti();
                 request.setAttribute("listainviti", listainviti);
                 request.setAttribute("user", u);
                 forward(request, response, "/home.jsp");
                 break;
            case 18:                    //ACCETTA_INVITO
                 cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                 id=(Integer)session.getAttribute("user_id");  
                 u = Utente.loadUtente(id, dbmanager.con);
                 u.valuta_invito(cod_gruppo, 2);
                 listainviti=u.loadInviti();
                 request.setAttribute("listainviti", listainviti);
                 request.setAttribute("user", u);
                 forward(request, response, "/home.jsp");
                 break;
            case 19:                    //GESTISCI_GRUPPO
                cod_gruppo= Integer.parseInt(request.getParameter("cod_gruppo")); 
                gruppo= Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
                request.setAttribute("gruppo", gruppo);
                request.setAttribute("invitabili", gruppo.invitabili());
                forward(request, response, "/gestisci_gruppo.jsp");
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