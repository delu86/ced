/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CRE0260
 */
public class ATMStatServlet extends HttpServlet {

    
    private static final String COD_ABI_PARAMETER="codAbi";
    private static final String PERIOD_PARAETER="period";//yyyy-MM
    public static final String SELECT_CREDEM="select CASE when dayofweek(date)=1 then concat('Domenica, ',date)\n" +
            "when dayofweek(date)=2 then concat('Lunedi, ',date)\n" +
            "when dayofweek(date)=3 then concat('Martedi, ',date)\n" +
            "when dayofweek(date)=4 then concat('Mercoledi, ',date)\n" +
            "when dayofweek(date)=5 then concat('Giovedi, ',date)\n" +
            "when dayofweek(date)=6 then concat('Venerdi, ',date)\n" +
            "when dayofweek(date)=7 then concat('Sabato, ',date) end\n" +
            "as giorno  ,tot_atm,ore_totali_faro,ore_ko_faro,replace(round(perc_ko_faro,2),'.',',') as perc_ko,"
            + "replace(round(100-perc_ko_faro,2),'.',',') as perc_ok_faro"
            + " ,replace(ko_sla7,'.',','),replace(round(ko_sla7*100/ore_totali_faro,2),'.',',') as perc_sla7,"
            + "replace(ko_sla8,'.',','),"
            + "replace(round(ko_sla8*100/ore_totali_faro,2),'.',',') as perc_sla8 "
            + "from atm_stat.riepilogo_per_giorno_credem\n" +
            "where substr(date,1,7)=? and codAbi=? order by date;";
    public static final String SELECT="select CASE when dayofweek(date)=1 then concat('Domenica, ',date)\n" +
            "when dayofweek(date)=2 then concat('Lunedi, ',date)\n" +
            "when dayofweek(date)=3 then concat('Martedi, ',date)\n" +
            "when dayofweek(date)=4 then concat('Mercoledi, ',date)\n" +
            "when dayofweek(date)=5 then concat('Giovedi, ',date)\n" +
            "when dayofweek(date)=6 then concat('Venerdi, ',date)\n" +
            "when dayofweek(date)=7 then concat('Sabato, ',date) end\n" +
            "as giorno  ,tot_atm,ore_totali_faro,ore_ko_faro,"
            + "replace(round(perc_ko_faro,2),'.',',') as perc_ko,"
            + "replace(round(100-perc_ko_faro,2),'.',',') as perc_ok_faro"
            + " ,replace(ko_sla7,'.',','),"
            + "replace(round(ko_sla7*100/ore_totali_faro,2),'.',',') as perc_sla7, '' , '' "
            + "from atm_stat.riepilogo_per_giorno\n" +
              " where substr(date,1,7)=? and codAbi=? order by date;";
    public final String CREDEM_ABI="03032";
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
        response.setContentType("Content-Type    text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String codAbi=request.getParameter(COD_ABI_PARAMETER);
        String period=request.getParameter(PERIOD_PARAETER);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs,rs_faro;
        try {
            ResourceBundle rb =   ResourceBundle.getBundle("datalayer.db");
             String jsonString="{\"data\":[";
	    String dataJSON="";
	    out = response.getWriter();
            Class.forName(rb.getString("driver"));
            conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
            if(codAbi.equals(CREDEM_ABI))
                ps=conn.prepareStatement(SELECT_CREDEM);
                else
                ps=conn.prepareStatement(SELECT);
            ps.setString(2, codAbi);
            ps.setString(1, period);
            rs=ps.executeQuery();
            int i=0;
            while (rs.next()) {                
                if(i==0){
                   
					dataJSON=dataJSON.concat("{"+
						"\"DATA\":"+	"\"<a>"+rs.getString(1)+"</a>\","+
                                                "\"TotaleATM\":"+	"\""+rs.getString(2)+"\","+
                                                "\"ORE_OK_FARO\":"+	"\""+rs.getString(3)+"\","+
                                                "\"ORE_KO_FARO\":"+	"\""+rs.getString(4)+"\","+
                                                "\"PERC_KO\":"+	"\""+rs.getString(5)+"\","+
                                                "\"PERC_OK\":"+	"\""+rs.getString(6)+"\","+
                                                "\"KO_SLA7\":"+	"\""+rs.getString(7)+"\","+
                                                "\"PERC_SLA7\":"+	"\""+rs.getString(8)+"\","+
                                                "\"KO_SLA8\":"+	"\""+rs.getString(9)+"\","+
                                                "\"PERC_SLA8\":"+	"\""+rs.getString(10)+"\""+
                                                "}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
						"\"DATA\":"+	"\"<a>"+rs.getString(1)+"</a>\","+
                                                "\"TotaleATM\":"+	"\""+rs.getString(2)+"\","+
                                                "\"ORE_OK_FARO\":"+	"\""+rs.getString(3)+"\","+
                                                "\"ORE_KO_FARO\":"+	"\""+rs.getString(4)+"\","+
                                                "\"PERC_KO\":"+	"\""+rs.getString(5)+"\","+
                                                "\"PERC_OK\":"+	"\""+rs.getString(6)+"\","+
                                                "\"KO_SLA7\":"+	"\""+rs.getString(7)+"\","+
                                                "\"PERC_SLA7\":"+	"\""+rs.getString(8)+"\","+
                                                "\"KO_SLA8\":"+	"\""+rs.getString(9)+"\","+
                                                "\"PERC_SLA8\":"+	"\""+rs.getString(10)+"\""+
                                                "}");
            }}
            
            rs.close();
            ps.close();
            conn.close();
            jsonString=jsonString.concat(dataJSON+"]}");
	    out.print(jsonString);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ATMStatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ATMStatServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();

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
