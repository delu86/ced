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
public class TopCicsExportServlet extends HttpServlet {

    private static final String  EXCEL_EXTENSION= ".xls";
    private static final String  RESOURCE_DB_PATH = "datalayer.db2";
    private final String SELECT="SELECT \n" +
    "    `cicshour`.`APPLVTNAME`,\n" +
    "    `cicshour`.`TRANSACTNAME`,\n" +
    "    `cicshour`.`EPVDATE`,\n" +
    "    `cicshour`.`EPVHOUR`,\n" +
    "    `cicshour`.`CTRANS`,\n" +
    "    round(`cicshour`.`TOTCPUTM`,2) as TOTCPUTM,\n" +
    "    round(`cicshour`.`TOTELAP`,2) as TOTELAP,\n" +
    "    round(`cicshour`.`TOTIRESP`,2) as TOTIRESP,\n" +
    "    round(`cicshour`.`TOTL8CPU`,2) as TOTL8CPU,\n" +
    "    `cicshour`.`TOTDB2RQ` \n" +
    "    \n" +
    "FROM `mthru`.`cicshour` \n" +
    "WHERE SYSTEM = ?   AND EPVDATE  = ?\n" +
    "AND (`TRANSACTNAME` NOT LIKE 'C%' OR `TRANSACTNAME` IN ('CSMI','CPMI','CVMI'))"
            + "ORDER BY TRANSACTNAME DESC ;";
    
    private static final String SELECT_BY_HOUR="SELECT \n" +
    "    `cicshour`.`APPLVTNAME`,\n" +
    "    `cicshour`.`TRANSACTNAME`,\n" +
    "    `cicshour`.`EPVDATE` as DATE,\n" +
    "    `cicshour`.`EPVHOUR`,\n" +
    "    `cicshour`.`CTRANS`,\n" +
    "    round(`cicshour`.`TOTCPUTM`,2) as TOTCPUTM,\n" +
    "    round(`cicshour`.`TOTELAP`,2) as TOTELAP,\n" +
    "    round(`cicshour`.`TOTIRESP`,2) as TOTIRESP,\n" +
    "    round(`cicshour`.`TOTL8CPU`,2) as TOTL8CPU,\n" +
    "    `cicshour`.`TOTDB2RQ` \n" +
    "    \n" +
    "FROM `mthru`.`cicshour` \n" +
    "WHERE SYSTEM = ?   AND EPVDATE  = ? AND EPVHOUR= ?\n" +
    "ORDER BY TRANSACTNAME DESC ;";

    /**;
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
        		response.setContentType("application/vnd.ms-excel");
	    String parameterDate=request.getParameter("date");
	    String parameterSystem=request.getParameter("system");
            String hour=request.getParameter("hour");
	    response.setHeader("Content-Disposition", "attachment; filename=TOP_CONSUMER_CICS"+"_"+parameterSystem+"_"+parameterDate+EXCEL_EXTENSION);
	         try {
                     if(hour==null)
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,SELECT,new String[]{parameterSystem,parameterDate});
                     else
                        ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,SELECT_BY_HOUR,new String[]{parameterSystem,parameterDate,hour});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
