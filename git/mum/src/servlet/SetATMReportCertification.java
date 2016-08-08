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

/**
 *
 * @author CRE0260
 */
public class SetATMReportCertification extends HttpServlet {

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
            throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        User user=(User)request.getSession().getAttribute("user");
        if(user.getProfile().equals(StringConstants.CEDACRI)){
             String annoMese=request.getParameter(StringConstants.ANNOMESE_PARAMETER);
            try {
                callStoredProcedure(annoMese);
            } catch (NamingException ex) {
                Logger.getLogger(SetATMReportCertification.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SetATMReportCertification.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    private void callStoredProcedure(String annoMese) throws NamingException, SQLException {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
            DataSource datasource=(DataSource) envContext.lookup(StringConstants.CED_DB);
            CallableStatement callGeneraReoports = datasource.getConnection().
                    prepareCall("call atm_stat.setCertificazione('"+annoMese+"')");
            callGeneraReoports.execute();

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

}
