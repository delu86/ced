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
public class JobAnalysisServlet extends HttpServlet {
    
        private static final String COLUMN_TOKEN="$column";
        private static final String TIME_TOKEN="$time";
        private static final String SMF30PGM="SMF30PGM";
        private static final String SMF30JBN="SMF30JBN";
        
    private static final String RESOURCE_PATH = "datalayer.idaa";
    public static final String SELECT=
            "SELECT -(DAYS(begintime_date) - DAYS(CURRENT_DATE) ) as date , substr(begintime, 12 , 2) as hour,substr(begintime, 15 , 1) as minute , \n" +
            "round(sum($time), 2) as $time from CR00515.EPV30_23_INTRVL \n" +
            "where $column=? and SYSTEM=? \n" +
            "	  and begintime_date BETWEEN DATE(DAYS(current_date)-60) and DATE(DAYS(current_date)-1)\n" +
            "group by -(DAYS(begintime_date) - DAYS(CURRENT_DATE) ) , substr(begintime, 12 , 2) , substr(begintime, 15 , 1)";
    private static final String ELAPSED_OPTIONS="-E";
    private static final String DISKIO_OPTIONS="-D"; 
    private static final String ELAPSED="EXECTM";
    private static final String CPUTIME="cputime";
    private static final String DISKIO="DISKIO";
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
                float matrix [][]=new float[24*6][60];
                PrintWriter out = response.getWriter();
		String queryString=request.getParameter("query");
		String system=request.getParameter("system");
		response.setContentType("application/json");
		String select=SELECT;
                String queryType=request.getParameter("queryType");
                queryString=queryString.trim();
                if(queryString.contains(ELAPSED_OPTIONS)){
                    queryString=queryString.replace(ELAPSED_OPTIONS, "");
                    select=select.replace(TIME_TOKEN, ELAPSED);
                }
                else{
                    if(queryString.contains(DISKIO_OPTIONS)){
                    queryString=queryString.replace(DISKIO_OPTIONS, "");
                    select=select.replace(TIME_TOKEN, DISKIO);
                }
                else
                    select=select.replace(TIME_TOKEN, CPUTIME);
                }
                if(queryType.equals("job")){
                    select=select.replace(COLUMN_TOKEN,SMF30JBN);
                }else{
                    select=select.replace(COLUMN_TOKEN,SMF30PGM);
                }
                System.out.println(select);
		ResourceBundle rb =   ResourceBundle.getBundle(RESOURCE_PATH);
		Connection conn=null;
		PreparedStatement pStatement=null;
		try {
                    Class.forName(rb.getString("driver"));
                    conn=DriverManager.getConnection(rb.getString("url"));
                    pStatement=conn.prepareStatement(select);
                    pStatement.setString(2,system);
                    pStatement.setString(1, queryString);
			response.setContentType("application/json");
			out = response.getWriter();
			String json="{\"data\":[";
                        ResultSet rSet = pStatement.executeQuery();
		int i=0;
		while(rSet.next()){
                    matrix[rSet.getInt(2)*6+rSet.getInt(3)][rSet.getInt(1)-1]= rSet.getFloat(4);
		}
                for(int k=59;k>=0;k--){
                    for(int j=0;j<144;j++){
                        if(!(k==59&&j==0))
                            json=json.concat(", "+matrix[j][k]);
                        else
                            json=json.concat(String.valueOf(matrix[j][k]));
                    }
                }
		rSet.close();
		pStatement.close();
		conn.close();
                out.print(json+"]}");
		 }catch(ClassNotFoundException e){
			out.print("errore");
		} catch (SQLException e) {
                    out.print("errore");
            } catch (IOException e) {
                out.print("errore");
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
