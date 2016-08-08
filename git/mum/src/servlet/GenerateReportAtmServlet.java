/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import object.StringConstants;
import object.User;
import utility.MailUtility;

/**
 *
 * @author CRE0260
 */
public class GenerateReportAtmServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        User user=(User)request.getSession().getAttribute("user");
        if(user.getProfile().equals(StringConstants.CEDACRI)){
        MailUtility mailUtility=new MailUtility();
            try{
                String annoMese=request.getParameter(StringConstants.ANNOMESE_PARAMETER);
                generateReports(annoMese);
                mailUtility.sendMessage(user.getUser(), "noReply@ced.it", "Generazione reports atm "+annoMese 
                                    , "Reports atm generati.\n");
            }
            catch(NamingException e){
                Logger.getLogger(GenerateReportAtmServlet.class.getName()).log(Level.SEVERE, null, e);
            }catch (MessagingException e) {
                Logger.getLogger(GenerateReportAtmServlet.class.getName()).log(Level.SEVERE, null, e);
            } 
            catch (SQLException e) {
                Logger.getLogger(GenerateReportAtmServlet.class.getName()).log(Level.SEVERE, null, e);
            } }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private void generateReports(String annoMese) throws NamingException, SQLException {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
            DataSource datasource=(DataSource) envContext.lookup(StringConstants.CED_DB);
            CallableStatement callGeneraReoports = datasource.getConnection().
                    prepareCall("call atm_stat.creaRiepilogo('"+annoMese+"')");
            callGeneraReoports.execute();

    }

}
