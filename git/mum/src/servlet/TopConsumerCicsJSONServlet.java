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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CRE0260
 */
public class TopConsumerCicsJSONServlet extends HttpServlet {

    private static final String SELECT="SELECT \n" +
    "    `cicshour`.`APPLVTNAME`,\n" +
    "    `cicshour`.`TRANSACTNAME`,\n" +
    "    `cicshour`.`EPVDATE` as DATE,\n" +
    "    `cicshour`.`EPVHOUR`,\n" +
    "    `cicshour`.`CTRANS`,\n" +
    "    round(`cicshour`.`TOTCPUTM`,2),\n" +
    "    round(`cicshour`.`TOTELAP`,2),\n" +
    "    round(`cicshour`.`TOTIRESP`,2),\n" +
    "    round(`cicshour`.`TOTL8CPU`,2),\n" +
    "    `cicshour`.`TOTDB2RQ` \n" +
    "    \n" +
    "FROM `mthru`.`cicshour` \n" +
    "WHERE SYSTEM = ?   AND EPVDATE  = ?\n" + 
    "AND (`TRANSACTNAME` NOT LIKE 'C%' OR `TRANSACTNAME` IN ('CSMI','CPMI','CVMI'))" +
    "ORDER BY TRANSACTNAME DESC ;";
    
        private static final String SELECT_BY_HOUR="SELECT \n" +
    "    `cicshour`.`APPLVTNAME`,\n" +
    "    `cicshour`.`TRANSACTNAME`,\n" +
    "    `cicshour`.`EPVDATE` as DATE,\n" +
    "    `cicshour`.`EPVHOUR`,\n" +
    "    `cicshour`.`CTRANS`,\n" +
    "    round(`cicshour`.`TOTCPUTM`,2),\n" +
    "    round(`cicshour`.`TOTELAP`,2),\n" +
    "    round(`cicshour`.`TOTIRESP`,2),\n" +
    "    round(`cicshour`.`TOTL8CPU`,2),\n" +
    "    `cicshour`.`TOTDB2RQ` \n" +
    "FROM `mthru`.`cicshour` \n" +
    "WHERE SYSTEM = ?   AND EPVDATE  = ? AND EPVHOUR= ?\n" +
    "ORDER BY TRANSACTNAME DESC ;";
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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String system=request.getParameter("system");
	String date=request.getParameter("date");
        String hour=request.getParameter("hour");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            ResourceBundle rb =   ResourceBundle.getBundle("datalayer.db2");
            String jsonString="{\"data\":[";
	    String dataJSON="";
	    out = response.getWriter();
            Class.forName(rb.getString("driver"));
            conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
            if(hour==null)
            ps=conn.prepareStatement(SELECT);
            else{
                ps=conn.prepareStatement(SELECT_BY_HOUR);
                ps.setString(3, hour);
            }
            ps.setString(1, system);
            ps.setString(2, date);
            rs=ps.executeQuery();
            int i=0;     
                 while(rs.next()){
                    if(i==0){
					dataJSON=dataJSON.concat("{"+
						"\"APPLVTNAME\":"+	"\""+rs.getString(1)+"\","+
                                                "\"TRANSACTNAME\":"+	"\""+rs.getString(2)+"\","+
                                                "\"EPVHOUR\":"+	"\""+rs.getString(4)+"\","+
                                                "\"EPVDATE\":"+	"\""+rs.getString(3)+"\","+
                                                "\"CTRANS\":"+	"\""+rs.getString(5)+"\","+
                                                "\"TOTCPUTM\":"+	"\""+rs.getString(6)+"\","+
                                                "\"TOTELAP\":"+	"\""+rs.getString(7)+"\","+
                                                "\"TOTIRESP\":"+	"\""+rs.getString(8)+"\","+
                                                "\"TOTL8CPU\":"+	"\""+rs.getString(9)+"\","+
                                                "\"TOTDB2RQ\":"+	"\""+rs.getString(10)+"\""+
						"}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
							"\"APPLVTNAME\":"+	"\""+rs.getString(1)+"\","+
                                                "\"TRANSACTNAME\":"+	"\""+rs.getString(2)+"\","+
                                                "\"EPVHOUR\":"+	"\""+rs.getString(4)+"\","+
                                                "\"EPVDATE\":"+	"\""+rs.getString(3)+"\","+
                                                "\"CTRANS\":"+	"\""+rs.getString(5)+"\","+
                                                "\"TOTCPUTM\":"+	"\""+rs.getString(6)+"\","+
                                                "\"TOTELAP\":"+	"\""+rs.getString(7)+"\","+
                                                "\"TOTIRESP\":"+	"\""+rs.getString(8)+"\","+
                                                "\"TOTL8CPU\":"+	"\""+rs.getString(9)+"\","+
                                                "\"TOTDB2RQ\":"+	"\""+rs.getString(10)+"\""+
						"}");
									} 
                 }
                 jsonString=jsonString.concat(dataJSON+"]}");
			out.print(jsonString);
        } catch (ClassNotFoundException ex) {
			ex.printStackTrace();
        } catch (SQLException ex) {
			ex.printStackTrace();
        } finally {
            try {
                
                ps.close();
                conn.close();
            } catch (SQLException ex) {
			ex.printStackTrace();
            }
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
