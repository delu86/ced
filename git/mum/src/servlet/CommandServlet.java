/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CRE0260
 */
public class CommandServlet extends HttpServlet {


    public static final HashMap<String,String> mapCommandAction=initializeMapCommandAction();
    public static final String COMMAND_PARAMETER="command";
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
        String command= request.getParameter(COMMAND_PARAMETER);
        if(mapCommandAction.containsKey(command))
            //request.getRequestDispatcher(mapCommandAction.get(command)).forward(request, response);
            response.sendRedirect(mapCommandAction.get(command));
        else
            //request.getRequestDispatcher(mapCommandAction.get("help")).forward(request, response);
            response.sendRedirect(mapCommandAction.get("help"));
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

    private static HashMap<String, String> initializeMapCommandAction() {
         HashMap<String, String> hashMap=new HashMap<String, String>();
         hashMap.put("jbtrend", "jMonthWKL.jsp");
         //easter egg
         hashMap.put("degrado", "https://www.youtube.com/watch?v=Kgh0kI0ubcU");
         hashMap.put("dash", "index.jsp");
         hashMap.put("cpi", "chartsDispatcher?page=CPI");
         hashMap.put("mips", "chartsDispatcher?page=MIPS");
         hashMap.put("cast", "cast.jsp");
         hashMap.put("exit", "login.jsp");
         hashMap.put("wkl", "cedacriCharts.jsp");
         hashMap.put("wklr", "realeCharts.jsp");
         hashMap.put("wkla", "carigeCharts.jsp");
         hashMap.put("admin", "admin");
         hashMap.put("workload", "cedacriCharts.jsp");
         hashMap.put("jbl", "jooble.jsp");
         hashMap.put("jba", "jobAnalysis.jsp");
         hashMap.put("jooble", "jooble.jsp");
         hashMap.put("epv", "http://10.99.252.22/datifs/html/cedacri/HTM/START.HTML");
         hashMap.put("epvr", "http://10.99.252.22/datifs/html/reale/HTM/START.HTML");
         hashMap.put("epva", "http://10.99.252.22/datifs/html/carigeass/HTM/START.HTML");
         hashMap.put("epvc", "http://10.99.252.22/datifs/html/credem/HTM/START.HTML");
         hashMap.put("atm", "atm.jsp");
         hashMap.put("sla", "slDocs.jsp");
         hashMap.put("nav", "Browser.jsp");
         hashMap.put("file", "Browser.jsp");
         hashMap.put("help", "helpCommand.jsp");
         return hashMap;
    }

}
