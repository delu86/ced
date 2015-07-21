package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exporter.ExcelExporter;

/**
 * Servlet implementation class CpiDetailExportServlet
 */
public class CpiDetailExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUFFIX_FILE_NAME = "detail";
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String RESOURCE_DB_PATH = 	"datalayer.db";
    private static final String SELECT="SELECT `epv113_2_hdcap`.`SYSTEM`, "+
    " `epv113_2_hdcap`.`EPVDATE` as data, "+
    " `epv113_2_hdcap`.`EPVHOUR` as ora , "+
    " MINUTE( SM113STM ) AS min, "+
    " SM113CPU AS CPU , SM113CPT AS TIPO,"+    
    " round(  BASIC00  /  BASIC01    , 3)   AS CPI,"+
    " `epv113_2_hdcap`.`DURTIME`,"+
    " `epv113_2_hdcap`.`EFFGHZ`"+
    " FROM `smfacc`.`epv113_2_hdcap`  where MONTH(EPVDATE) = ? and YEAR(EPVDATE)=?  AND SYSTEM=? "+
    " ORDER BY 1,2,3,4 ,5 ";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CpiDetailExportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		String parameterSystem=request.getParameter("system");
		String parameterMonth=request.getParameter("date").substring(0, 2);
		String parameterYear=request.getParameter("date").substring(3, 7);
		response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterMonth+EXCEL_EXTENSION);
		try {
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,SELECT,parameterMonth,parameterYear,parameterSystem);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
