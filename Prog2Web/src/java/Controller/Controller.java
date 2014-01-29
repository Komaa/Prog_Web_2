/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
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
            case 1:
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
            case 2:
                //---------------------Upload eventuale file
//                String dirName = realPath + "tmp";
//
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
//                //----------FINE UPLOAD-----
//            }
            
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