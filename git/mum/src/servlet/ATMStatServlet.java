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
    private static final String SELECT="SELECT substr(cod_atm,7,10) as codAtm,30 as giorni,30*24-ROUND(SUM(duration)/3600) as ore_ok,ROUND(SUM(\n" +
"	duration)/3600)as ore_KO,ROUND((SUM(duration)/3600)*100/(30*24), 2) as perc_ko,  \n" +
"ROUND(SUM(if(FARO>600,FARO,0))/3600) AS ore_faro_ko,ROUND(SUM(if(opecodeStart<>'A93' and anomcod not in('52','54') and faro>600  ,FARO,0))/3600) \n" +
"as sla7,ROUND(SUM(ifnull(tot*60,0))/3600) as sla8_ko  from\n" +
"(select derived.*,CASE \n" +
"		WHEN time(start)<'02:00:00' and time(endt)>'05:00:00' and dayname(start) not in ('Saturday','Sunday') THEN duration-3600*3\n" +
"		WHEN time(start)>='02:00:00' and time(endt)<='05:00:00' and dayname(start) not in ('Saturday','Sunday') THEN 0\n" +
"		WHEN time (start)<'02:00:00' and time(endt)<='05:00:00' \n" +
"                                 and time(endt)>'02:00:00'\n" +
"                                 and dayname(start) not in ('Saturday','Sunday') THEN \n" +
"		timestampdiff(SECOND,start,CAST(concat(date(start),' 02:00:00') as datetime)) \n" +
"		WHEN time (start)>'02:00:00' and time(start)<='05:00:00' and time(endt)>'05:00:00'\n" +
"															and dayname(start) not in ('Saturday','Sunday') THEN \n" +
"															timestampdiff(SECOND,CAST(concat(date(start),' 05:00:00') as datetime), endt)\n" +
"                                                            else duration end as faro\n" +
"from\n" +
"(SELECT a.cod_atm,dispStart,opecodeStart,anomCod,start,if(date(start)=date(a.end),a.end,CAST(concat(date(start),' 23:59:59') as datetime)) as endt                                         \n" +
" ,duration,tot from atm_stat.atm_indisponibili a left join(SELECT count(*) as tot,cod_atm from atm_stat.sla8_minutes  join atm_stat.atm_indisponibili on start<=concat(datetime,':00') and end>=concat(datetime,':59')\n" +
"and hour(concat(datetime,':00'))<'02' and hour(concat(datetime,':00'))>'05'\n" +
"group by cod_Atm ) as sla8 on a.cod_Atm=sla8.cod_Atm ) as derived\n" +
")as der\n" +
"where  substr(cod_atm,1,5)	=?   and substr(start,1,7)=? group by cod_atm;";
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
            
            ps=conn.prepareStatement(SELECT);
            
            ps.setString(1, codAbi);
            ps.setString(2, period);
            rs=ps.executeQuery();
            int i=0;
            while (rs.next()) {                
                if(i==0){
					dataJSON=dataJSON.concat("{"+
						"\"CODATM\":"+	"\""+rs.getString(1)+"\","+
                                                "\"GIORNI\":"+	"\""+rs.getString(2)+"\","+
                                                "\"ORE_OK\":"+	"\""+rs.getString(3)+"\","+
                                                "\"ORE_KO\":"+	"\""+rs.getString(4)+"\","+
                                                "\"PERC_KO\":"+	"\""+rs.getString(5)+"\","+
                                                "\"ORE_OK_FARO\":"+	"\"-\","+
                                                "\"ORE_KO_FARO\":"+	"\""+rs.getString(6)+"\","+
                                                "\"PERC_KO_FARO\":"+	"\"-\","+
                                                "\"ORE_KO_SLA7\":"+	"\""+rs.getString(7)+"\","+
                                                "\"PERC_KO_SLA7\":"+	"\"-\","+
                                                "\"ORE_KO_SLA8\":"+	"\""+rs.getString(8)+"\","+
                                                "\"PERC_KO_SLA8\":"+	"\"-\""+
                                                "}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
						"\"CODATM\":"+	"\""+rs.getString(1)+"\","+
                                                "\"GIORNI\":"+	"\""+rs.getString(2)+"\","+
                                                "\"ORE_OK\":"+	"\""+rs.getString(3)+"\","+
                                                "\"ORE_KO\":"+	"\""+rs.getString(4)+"\","+
                                                "\"PERC_KO\":"+	"\""+rs.getString(5)+"\","+
                                                "\"ORE_OK_FARO\":"+	"\"-\","+
                                               "\"ORE_KO_FARO\":"+	"\""+rs.getString(6)+"\","+
                                                "\"PERC_KO_FARO\":"+	"\"-\","+
                                                "\"ORE_KO_SLA7\":"+	"\""+rs.getString(7)+"\","+
                                                "\"PERC_KO_SLA7\":"+	"\"-\","+
                                                "\"ORE_KO_SLA8\":"+	"\""+rs.getString(8)+"\","+
                                                "\"PERC_KO_SLA8\":"+	"\"-\""+
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
