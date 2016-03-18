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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
    public static final String SELECT="SELECT * from(\n" +
"SELECT  T1.DATETIM as datetm, sum(IFNULL(round($time, 2), 0)) AS $time from\n" +
"(SELECT concat(concat(concat(cast( DATA as CHAR(10)), ' ') , HH_M) , '0')\n" +
" as datetim from (SELECT distincT(date(begintime)) as DATA from CR00515.EPV30_23_INTRVL) der\n" +
"CROSS JOIN CR00515.TIME_10MIN  ) as t1 LEFT JOIN CR00515.EPV30_23_INTRVL T2 ON T1.DATETIM=concat(substr(BEGINTIME , 1 , 15 ), '0' )\n" +
"AND $column=? and SYSTEM=?\n" +
"group by T1.DATETIM , $column , SYSTEM \n" +
") as derived\n" +
"where \n" +
"timestampdiff(16,   char(timestamp(concat(cast(datetm as CHAR(16)) , ':00.000000'))-current timestamp) ) BETWEEN -35 and -1\n" +
"ORDER BY 1 ASC;"
                                       ;
    private static final String ELAPSED_OPTIONS="-e";
    private static final String ELAPSED="EXECTM";
    private static final String CPUTIME="cputime";
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
                    select=select.replace(TIME_TOKEN, CPUTIME);
                }
                
                if(queryType.equals("job")){
                    select=select.replace(COLUMN_TOKEN,SMF30JBN);
                }else{
                    select=select.replace(COLUMN_TOKEN,SMF30PGM);
                }
		ResourceBundle rb =   ResourceBundle.getBundle(RESOURCE_PATH);
		Connection conn=null;
		PreparedStatement pStatement=null;
		
		SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		f1.setTimeZone(TimeZone.getTimeZone("UTC"));
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
                    Date d=f1.parse(rSet.getString(1));
                    String dateInMillis=String.valueOf(d.getTime());
			if(i==0){
				json=json.concat("["+dateInMillis+" , "+rSet.getString(2)+"]");
				i++;
			}
			else{
				json=json.concat(",["+dateInMillis+" , "+rSet.getString(2)+"]");
			}
		}
		rSet.close();
		pStatement.close();
		conn.close();
                out.print(json+"]}");
		}catch(Exception e){
			out.print(e.getMessage());
		}
		
                finally{
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
