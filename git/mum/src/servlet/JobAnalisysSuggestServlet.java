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
public class JobAnalisysSuggestServlet extends HttpServlet {

        private static final String TABLE_TOKEN="$table";
        private static final String COLUMN_TOKEN="$column";
        private static final String TABLE_JOB="smfacc.r30jbnintrvl";
        private static final String TABLE_PROGRAM="smfacc.r30pgmname";
        private static final String SMF30PGM="SMF30PGM";
        private static final String SMF30JBN="SMF30JBN";
    	private static final String RESOURCE_PATH = "datalayer.db";
	private static final String SELECT = "SELECT $column FROM $table WHERE SYSTEM=? and $column like ? "+
                                             " order by $column LIMIT 20";

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
		PrintWriter out;
                String select;
                String queryType=request.getParameter("queryType");
                
                if(queryType.equals("job")){
                    select=SELECT.replace(COLUMN_TOKEN,SMF30JBN).replace(TABLE_TOKEN,TABLE_JOB);
                }else{
                    select=SELECT.replace(COLUMN_TOKEN,SMF30PGM).replace(TABLE_TOKEN,TABLE_PROGRAM);
                }
                
		String queryString=request.getParameter("query");
		String system=request.getParameter("system");
		String resString="[";
		response.setContentType("application/json");
		out = response.getWriter();
		ResourceBundle rb =   ResourceBundle.getBundle(RESOURCE_PATH);
		Connection conn=null;
		PreparedStatement pStatement=null;
                try {
                    Class.forName(rb.getString("driver"));
                    		conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		pStatement=conn.prepareStatement(select);
		pStatement.setString(1,system);
		pStatement.setString(2, queryString+"%");
		ResultSet rSet = pStatement.executeQuery();
		int i=0;
		while(rSet.next()){
			if(i==0){
				resString=resString.concat("\""+rSet.getString(1)+"\"");
				i++;
			}
			else{
				resString=resString.concat(",\""+rSet.getString(1)+"\"");
			}
		}
		rSet.close();
		pStatement.close();
		conn.close();
		out.print(resString+"]");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JobAnalisysSuggestServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(JobAnalisysSuggestServlet.class.getName()).log(Level.SEVERE, null, ex);
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
