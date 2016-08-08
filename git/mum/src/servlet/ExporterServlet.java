/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import exporter.ExcelExporter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import object.JSONUtility;
import object.StringConstants;

/**
 *
 * @author CRE0260
 */
public class ExporterServlet extends HttpServlet {

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
        JsonObject json= JSONUtility.getJsonObject(getServletContext().getRealPath(
                                    StringConstants.JSON_FOLDER
                                   +request.getParameter(StringConstants.ID_PARAMETER)
                                   +StringConstants.JSON_EXTENSION));
        JsonObject dbObject = json.getJsonObject(StringConstants.DATABASE_JSON_KEY_NAME);
        JsonArray parameters = dbObject.getJsonArray(StringConstants.PARAMETER_JSON_KEY_NAME);
        JsonArray admittedUsers = json.getJsonArray(StringConstants.ADMITTED_USERS);
        String [] paramArray=new String[parameters.size()];
        Iterator iterator=parameters.iterator();
        int index=0;
        while(iterator.hasNext()){
                    paramArray[index++]=request.getParameter(iterator.next().toString().replace("\"", ""));
                }
        String title=request.getParameter(StringConstants.TITLE_PARAMETER);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+title+".xlsx");
        try {
            ExcelExporter.writeXlsx(response.getOutputStream(), dbObject.get(StringConstants.URL_DB_RESOURCE).toString().replace("\"", "")
                    , dbObject.get(StringConstants.QUERY_JSON_KEY_NAME).toString().replace("\"", ""), paramArray);
        } catch (NamingException ex) {
            Logger.getLogger(ExporterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }}

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
