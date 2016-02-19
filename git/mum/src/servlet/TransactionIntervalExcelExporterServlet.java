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
	//private static final String SELECT_TRANSACTION ="SELECT START_010 as data ,SYSTEM as Sistema,TRAN_001 as transazione,USERID_089 as user,TOT as count,truncate(CPUTIME,3) as CPUtime,truncate(ELAPSED,3)as ELAPSED,truncate(J8CPUT_260TM,3) as J8,truncate(KY8CPUT_263TM,3) as K8,truncate(L8CPUT_259TM,3) as L8,truncate(MSCPUT_258TM,3) as MS,SCUSRHWM_106 as MEM,truncate(QRDISPT_255TM,3) as QR,truncate(S8CPUT_261TM,3) as S8,DB2REQCT_180 as DB2call,ABCODEO_113 as ABEND_START,ABCODEC_114 as ABEND_END FROM smfacc.epv110_1_trxacct_t10_rm where system=? and START_010=? order by TOT DESC";
	//private static final String SELECT_BATCH="SELECT DATET10,SMF30JBN,JESNUM,SMF30STM,SMF30STN,"+
        //    "SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX,CONDCODE,ABEND FROM smfacc.epv030_5_jobterm_t10_rm where  SYSTEM=? and DATET10=?";
	//private static final String SELECT_STC="SELECT DATET10,SMF30JBN,JESNUM,SMF30STM,SMF30STN,"+
        //    "SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX,CONDCODE,ABEND FROM smfacc.epv030_23_intrvl_t10_rm_STC where  SYSTEM=? and DATET10=?";
	
        private static final String SELECT_TRANSACTION ="SELECT TRAN_001,USERID_089,TOT,truncate(CPUTIME,3) as CPUTIME,truncate(ELAPSED,3)as ELAPSED,truncate(J8CPUT_260TM,3) as J8CPUT,"
                + "truncate(KY8CPUT_263TM,3) as KY8CPUT,truncate(L8CPUT_259TM,3) as L8CPUT,truncate(MSCPUT_258TM,3) as MSCPUT,SCUSRHWM_106,truncate(QRDISPT_255TM,3) as QRDISPT,truncate(S8CPUT_261TM,3) as S8CPUT,DB2REQCT_180,ABCODEO_113,ABCODEC_114 FROM "+TABLE_PARAMETER_STRING+" where system=? and START_010=? order by TOT DESC";
        private final static String SELECT_BATCH="SELECT DATET10,SMF30JBN,JESNUM,SMF30STM,SMF30STN,"+
	                                                       " SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX, CONDCODE,ABEND "
                                                               + "FROM "+TABLE_PARAMETER_STRING+" where SYSTEM=? and DATET10=?";
	private final static String SELECT_STC="SELECT DATET10,SMF30JBN,JESNUM,SMF30STM,SMF30STN,"+
            "SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX,CONDCODE,ABEND FROM "+TABLE_PARAMETER_STRING+" where SYSTEM=? and DATET10=?";
        
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
