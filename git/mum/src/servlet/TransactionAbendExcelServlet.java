package servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exporter.ExcelExporter;
import utility.MapUtility;

/**
 * Servlet implementation class TransactionAbendExcelServlet
 */
public class TransactionAbendExcelServlet extends HttpServlet {
        private final static String TABLE_PARAMETER_STRING="$table_name";
	private static final long serialVersionUID = 1L;
	private static final String SUFFIX_FILE_NAME = "transaction-abend";
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String RESOURCE_DB_PATH = 	"datalayer.db";
	private static final String SELECT ="SELECT SUBSTRING(START_010,12) AS hour,TRAN_001,ABCODEC_114,TOT,truncate(CPUTIME,3)as CPUTIME,DB2REQCT_180,truncate(ELAPSED,3)as response,USERID_089 FROM"
                + " "+TABLE_PARAMETER_STRING+" where ABCODEC_114<>\"\""+ 
                                              "and SYSTEM=? and date(START_010)=? order by CPUTIME desc";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionAbendExcelServlet() {
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
		response.setContentType("application/vnd.ms-excel");
	    String parameterSystem=request.getParameter("system");
	    String parameterDate=request.getParameter("date");
	    response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterDate+EXCEL_EXTENSION);
	    
		try {
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,SELECT.replace(TABLE_PARAMETER_STRING, MapUtility.mapTransactionTable().get(parameterSystem)),
					parameterSystem,parameterDate);
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
