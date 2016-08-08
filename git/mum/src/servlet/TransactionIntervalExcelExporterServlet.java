package servlet;

import datalayer.DatabaseManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.UtilityDate;
import exporter.ExcelExporter;
import utility.MapUtility;

/**
 * Servlet implementation class TransactionIntervalExcelExporterServlet
 */
public class TransactionIntervalExcelExporterServlet extends HttpServlet {
        private final static String TABLE_PARAMETER_STRING="$table_name";
        private static final long serialVersionUID = 1L;
	private static final String SUFFIX_FILE_NAME = "detail";
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String RESOURCE_DB_PATH = 	"datalayer.db2";
	private static final String SELECT_TRANSACTION ="SELECT TRAN_001,USERID_089,TOT,replace(truncate(CPUTIME,3),'.',',') as CPUTIME,"
                + "replace(truncate(ELAPSED,3),'.',',') as ELAPSED,truncate(J8CPUT_260TM,3) as J8CPUT,"
                + "truncate(KY8CPUT_263TM,3) as KY8CPUT,truncate(L8CPUT_259TM,3) as L8CPUT,truncate(MSCPUT_258TM,3) as MSCPUT,SCUSRHWM_106,truncate(QRDISPT_255TM,3) as QRDISPT,"
                + "truncate(S8CPUT_261TM,3) as S8CPUT,DB2REQCT_180,ABCODEO_113 as ABEND FROM "+TABLE_PARAMETER_STRING+" where system=? and START_010=? order by TOT DESC";
        
        private static final String TRANSACTION="Transazioni";
	private static final String JOBS="Jobs";
	private static final String STC="Started Task";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	private static final String TIMEZONE = "UTC";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionIntervalExcelExporterServlet() {
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
	    String parameterDate=UtilityDate.fromMilliSecondToDateTimeString(Long.parseLong(request.getParameter("date")),TIMEZONE,DATE_TIME_FORMAT);
	    String parameterSystem=request.getParameter("system");
	    response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterDate+EXCEL_EXTENSION);
	         try {
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,new String[]{TRANSACTION,JOBS,STC}
			,new String[]{SELECT_TRANSACTION.replace(TABLE_PARAMETER_STRING, MapUtility.mapTransactionTable().get(parameterSystem))
                                ,DatabaseManager.SELECT_BATCH_INTERVAL_REALE.replace(TABLE_PARAMETER_STRING, MapUtility.mapBatchTable().get(parameterSystem)),
                                DatabaseManager.SELECT_STC_INTERVAL_REALE.replace(TABLE_PARAMETER_STRING, MapUtility.mapSTCTable().get(parameterSystem))}
					,new String[]{parameterSystem,parameterDate},new String[]{parameterDate,parameterSystem},new String[]{parameterDate,parameterSystem});
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