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
public class GetColumnsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String SELECT="SELECT table_schema,table_name,column_name,data_type,column_comment from information_schema.COLUMNS where column_name LIKE ? or column_comment like ?\n" +
                                 " order by column_comment;";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();   
        try {
            String jsonString="{\"data\":[";
	    String dataJSON="";
            out = response.getWriter();
            String queryString=request.getParameter("query");
	    String RESOURCE_PATH="datalayer.db2";
            ResourceBundle rb =   ResourceBundle.getBundle(RESOURCE_PATH);
		Connection conn=null;
		PreparedStatement pStatement=null;
		Class.forName(rb.getString("driver"));
                conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		pStatement=conn.prepareStatement(SELECT);
		pStatement.setString(1,"%"+queryString+"%");
                pStatement.setString(2,"%"+queryString+"%");
		ResultSet rSet = pStatement.executeQuery();
                int i=0;
                		while(rSet.next()){
                                 				if(i==0){
					
					dataJSON=dataJSON.concat("{"+
						"\"schema\":"+	"\""+rSet.getString(1)+"\","+
						"\"table\":"+	"\""+rSet.getString(2)+"\","+
						"\"column\":"+	"\""+rSet.getString(3)+"\","+
						"\"datatype\":"+		"\""+rSet.getString(4)+"\","+
						"\"description\":"+		"\""+rSet.getString(5)+"\""+
								
				         	"}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
						"\"schema\":"+	"\""+rSet.getString(1)+"\","+
						"\"table\":"+	"\""+rSet.getString(2)+"\","+
						"\"column\":"+	"\""+rSet.getString(3)+"\","+
						"\"datatype\":"+		"\""+rSet.getString(4)+"\","+
						"\"description\":"+		"\""+rSet.getString(5)+"\""+		
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
