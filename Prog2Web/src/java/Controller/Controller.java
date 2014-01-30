/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Beans.Gruppo;
import Beans.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
        int cmd = Integer.parseInt(request.getParameter("cmd"));
        HttpSession session = request.getSession(true);
        Utente u = null;
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
                u = Utente.loadUtente(id, dbmanager.con);
                session.setAttribute("user_id", u.getCod());
                HashMap<Integer, String> listainviti=u.loadInviti();
                request.setAttribute("listainviti", listainviti);
                request.setAttribute("user", u);
                forward(request, response, "/home.jsp");
                break;
            case 2:                     //LOGOUT              
                session.invalidate();
                forward(request, response, "/index.jsp");
                break;
            case 3:                     //TASTO_REGISTRAZIONE
                forward(request, response, "/registrazione.jsp");
                break;
            case 4:                     //REGISTRAZIONE
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
                forward(request, response, "/creazione_gruppo.jsp");
                break;
            case 9:                     //CREA_GRUPPO    
                String titolo = request.getParameter("titolo");
                if(titolo.equals("")){
                    forward(request, response, "/creazione_gruppo.jsp");    
                }else{
                    int tipo= Integer.parseInt(request.getParameter("tipo")); 
                    Gruppo gruppo=new Gruppo(dbmanager.con);
                    gruppo.setTitolo(titolo);
                    gruppo.setId_amministratore((Integer) session.getAttribute("user_id"));
                    gruppo.setTipo(tipo);
                    gruppo.insertGruppo();
                    request.setAttribute("gruppo", gruppo);
                    request.setAttribute("invitabili", gruppo.invitabili());
                    forward(request, response, "/gestisci_gruppo.jsp");
                }
                break;
            case 10:                     //CAMBIA_TITOLO
                
                break;
            case 11:                     //CAMBIA_FLAG
                
                break;    
            case 12:                     //INVITA UTENTE
                
                break;
            case 13:                     //ELENCO GRUPPI
                u = Utente.loadUtente((Integer)session.getAttribute("user_id"), dbmanager.con);
                request.setAttribute("listagruppi", u.listaGruppi());
                request.setAttribute("listagruppipubblici", Gruppo.listaGruppiaperti(dbmanager.con));
                forward(request, response, "/gruppi.jsp");
                break;
            case 14:                     //ENTRA_GRUPPO
                int cod_gruppo= Integer.parseInt(request.getParameter("tipo")); 
                Gruppo gruppo= Gruppo.loadGruppo(cod_gruppo, dbmanager.con);
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
            case 17:                    //RIFIUTA_INVITO
                
            case 18:                    //ACCETTA_INVITO
                
                
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