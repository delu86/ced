/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import exporter.ExcelExporter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CRE0260
 */
public class SystemConsumptionExporterServlet extends HttpServlet {

    	private static final String SUFFIX_FILE_NAME = "consumption";
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String RESOURCE_DB_PATH = 	"datalayer.db2";
        private final static String SELECT_SYSTEM_CONSUMPTION_LAST30_DAY=
                          "SELECT t1.EPVDATE,t1.EPVHOUR, TRUNCATE(t1.MIPLPAR,2) as MIPS,TRUNCATE(t1.SMF70LAC,2) MSU_RA4H"
			+ " FROM mtrnd.lparcpu as t1 "
			+ " where t1.RSYSTEM=? and datediff(current_date,t1.epvdate)<=30"
			+ " order by t1.EPVDATE ASC, t1.EPVHOUR ASC;";
        private final static String SELECT_LPAR_CONSUMPTION_LAST30_DAY="SELECT t1.EPVDATE,t1.EPVHOUR,sum( TRUNCATE(t1.MIPLPAR,2))"
                        + " as MIPS ,sum(TRUNCATE(t1.SMF70LAC,2)) as MSU_RA4H"
			+ " FROM mtrnd.lparcpu as t1 "
			+ " where (t1.RSYSTEM='SIES' or t1.RSYSTEM='SIGE') and datediff(current_date,t1.epvdate)<=30"
			+ " group by t1.EPVDATE,t1.EPVHOUR order by t1.EPVDATE ASC, t1.EPVHOUR ASC;";
        
	private final static String SELECT_SYSTEM_CONSUMPTION_ZIIP_LAST30_DAY="SELECT t1.EPVDATE,t1.EPVHOUR, TRUNCATE(t1.MIPLPAR,2) as MIPS_ZIIp"
			+ " FROM mtrnd.lpariip as t1 "
			+ " where t1.RSYSTEM=? and datediff(current_date,t1.epvdate)<=30"
			+ " order by t1.EPVDATE ASC, t1.EPVHOUR ASC;";
	private final static String SELECT_LPAR_CONSUMPTION_ZIIP_LAST30_DAY="SELECT t1.EPVDATE,t1.EPVHOUR, TRUNCATE(t1.MIPLPAR,2) as MIPS_ZIIp"
			+ " FROM mtrnd.lpariip as t1 "
			+ " where (t1.RSYSTEM='SIES' or t1.RSYSTEM='SIGE') and datediff(current_date,t1.epvdate)<=30"
			+ " group by t1.EPVDATE,t1.EPVHOUR order by t1.EPVDATE ASC, t1.EPVHOUR ASC;";

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
        String system=request.getParameter("system");
        response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+system+EXCEL_EXTENSION);
        if(!system.equals("ALL")){
            try {
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,new String[]{"Standard","Ziip"}
			,new String[]{SELECT_SYSTEM_CONSUMPTION_LAST30_DAY,SELECT_SYSTEM_CONSUMPTION_ZIIP_LAST30_DAY}
					,new String[]{system},new String[]{system});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }else{
            try { 
		      ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,new String[]{"Standard","Ziip"}
			,new String[]{SELECT_LPAR_CONSUMPTION_LAST30_DAY,SELECT_LPAR_CONSUMPTION_ZIIP_LAST30_DAY},new String[]{},new String[]{});
					
                              } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
