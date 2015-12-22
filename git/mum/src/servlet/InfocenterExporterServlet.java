package servlet;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exporter.ExcelExporter;

/**
 * Servlet implementation class InfocenterExporterServlet
 */
public class InfocenterExporterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATE_START_PARAMETER = "date-start";
	private static final String DATE_END_PARAMETER = "date-end";
	private static final String SUFFIX_FILE_NAME = "infocenter";
	private static final String  EXCEL_EXTENSION= ".xls";
	private static final String SELECT = "SELECT 'B' as type , BEGINTIME, ENDTIME, round( CPUTIME, 2) AS CPUTIME, round(ZIPTM, 2) as ZIPTM, ROUND(ELAPSED , 2 ) AS ELAPSED, DISKIO, ROUND( DISKIOTM , 2 ) AS DISKIOTM, "
			+"SMF30JBN, SMF30JNM, SMF30PGM, SMF30STM, SMF30STN , SMF30RUD,"
			+"SMF30USR, SYSTEM, TYPETASK, SMF30WID , CONDCODE "
			+"FROM CR00515.EPV30_4_STEP "
			+"where ( SMF30RUD= 'CRPRDIC' or SMF30RUD= 'CRPR2IC' or SMF30RUD= 'CRPR0IC' ) and TYPETASK = 'JOB' "
			+"AND SMF30PGM LIKE 'IKJE%' AND SYSTEM NOT IN ('ESYA')"
			+" and date(ENDTIME)>=? and date(ENDTIME)<=?"
			+"UNION "
			+"SELECT 'O' as type, BEGINTIME, ENDTIME, CPUTIME, ZIPTM, ELAPSED, DISKIO, DISKIOTM , "
			+"SMF30JBN, SMF30JNM, SMF30PGM, SMF30STM, SMF30STN , SMF30RUD," 
			+"SMF30USR, SYSTEM, TYPETASK, SMF30WID , CONDCODE " 
			+"FROM cr00515.epv30_4_step "
			+"where "
			+"SMF30PSN LIKE 'TSOFOC%' AND SYSTEM NOT IN ('ESYA') and date(ENDTIME)>=? and date(ENDTIME)<=? "
			+"order by 2 , 3 , SYSTEM, SMF30RUD ;";
	private static final String IDAA_RESOURCE_PATH = "datalayer.idaa";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
        public InfocenterExporterServlet() {
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
		ResourceBundle rb =   ResourceBundle.getBundle(IDAA_RESOURCE_PATH);
		String dateStart=request.getParameter(DATE_START_PARAMETER);
		String dateEnd=request.getParameter(DATE_END_PARAMETER);
        response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+EXCEL_EXTENSION);
	    
		try {
			ExcelExporter.getExcelFromIDAA(response.getOutputStream(), rb.getString("driver"),rb.getString("url"), SELECT, dateStart,dateEnd,dateStart,dateEnd);
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
