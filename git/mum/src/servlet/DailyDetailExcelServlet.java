package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exporter.ExcelExporter;

/**
 * Servlet implementation class DailyDetailExcelServlet
 */
public class DailyDetailExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUFFIX_FILE_NAME = "detail";
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String RESOURCE_DB_PATH = 	"datalayer.db";
	private static final String SELECT_TRANSACTION ="SELECT START_010 as data ,SYSTEM as Sistema,TRAN_001 as transazione,USERID_089 as user,TOT as count,truncate(CPUTIME,3) as CPUtime,truncate(ELAPSED,3)as ELAPSED,truncate(J8CPUT_260TM,3) as J8,truncate(KY8CPUT_263TM,3) as K8,truncate(L8CPUT_259TM,3) as L8,truncate(MSCPUT_258TM,3) as MS,SCUSRHWM_106 as MEM,truncate(QRDISPT_255TM,3) as QR,truncate(S8CPUT_261TM,3) as S8,DB2REQCT_180 as DB2call,ABCODEO_113 as ABEND_START,ABCODEC_114 as ABEND_END FROM smfacc.epv110_1_trxacct_t10_rm where system=? and date(START_010)=? order by START_010 ASC, TOT DESC";
	private static final String SELECT_BATCH="SELECT DATET10, SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,TOT,EXECTM,ELAPSED"+
            ",DISKIO,DISKIOTM,ZIPTM,CPUTIME,TAPEIO,TAPEIOTM FROM smfacc.epv030_5_jobterm_t10_rm where  SYSTEM=? and date(DATET10)=? order by DATET10 asc";
	private static final String SELECT_STC="SELECT DATET10,SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,TOT,EXECTM,ELAPSED"+
            ",DISKIO,DISKIOTM,ZIPTM,CPUTIME,TAPEIO,TAPEIOTM FROM smfacc.epv030_23_intrvl_t10_rm_STC where  SYSTEM=? and date(DATET10)=? order by DATET10 asc";
	private static final String TRANSACTION="Transazioni";
	private static final String JOBS="Jobs";
	private static final String STC="Started Task";
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DailyDetailExcelServlet() {
        super();
        
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
		String parameterDate=request.getParameter("date");
		response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterDate+EXCEL_EXTENSION);
		try {
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,new String[]{TRANSACTION,JOBS,STC}
			,new String[]{SELECT_TRANSACTION,SELECT_BATCH,SELECT_STC}
					,new String[]{parameterSystem,parameterDate},new String[]{parameterSystem,parameterDate},new String[]{parameterSystem,parameterDate});
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
