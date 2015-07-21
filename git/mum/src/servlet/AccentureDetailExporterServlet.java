package servlet;
/*
 * SELECT CASE when SYSTEM='ESYA' THEN 'TEST'
WHEN SYSTEM='GSY7' THEN '^^^' else 'PROD' end as AMBIENTE, SYSTEM, TYPETASK as Tipo_task,
date(ENDTIME) as data,concat(substr(BEGINTIME,12,4),"0") as ORA,
SMF30JBN as JOBNAME,SMF30JNM as JOBNUMBER,SMF30STM as STEPNAME,SMF30STN as STEPNUMBER,SMF30PSN
as PROC_STEP,SMF30PGM as PROGRAM_NAME,SMF30RUD as USER,CPUTIME
,CPUTIME*1007/600 as MIPS,SMF30SRV as SERVICE_UNITS,SMF30TEX as NUM_EXCP from smfacc.epv030_4_step_accenture
where date(ENDTIME)='2015-06-10' order by 4 , 1, 2 
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exporter.ExcelExporter;

/**
 * Servlet implementation class AccentureDetailExporterServlet
 */
public class AccentureDetailExporterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATE_PARAMETER = "date";
    private static final String SELECT="SELECT CASE when SYSTEM='ESYA' THEN 'TEST'"
    		+ " WHEN SYSTEM='GSY7' THEN '^^^' else 'PROD' end as AMBIENTE, SYSTEM, TYPETASK as Tipo_task,"
    		+ " date(ENDTIME) as data,concat(substr(ENDTIME,12,4),\"0\") as ORA,"
    		+ " SMF30JBN as JOBNAME,SMF30JNM as JOBNUMBER,SMF30STM as STEPNAME,SMF30STN as STEPNUMBER,SMF30PSN"
    		+ " as PROC_STEP,SMF30PGM as PROGRAM_NAME,SMF30RUD as USER,CPUTIME"
    		+ " ,CPUTIME*1007/600 as MIPS,SMF30SRV as SERVICE_UNITS,SMF30TEX as NUM_EXCP from smfacc.epv030_4_step_accenture"
    		+ " where date(ENDTIME)=? order by 4 , 1, 2 ";  
	private static final String SUFFIX_FILE_NAME = "accentureDetail";
	private static final String  EXCEL_EXTENSION= ".xls";
	private static final String RESOURCE_PATH = "datalayer.db";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccentureDetailExporterServlet() {
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
		String date=request.getParameter(DATE_PARAMETER);

       response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+date+EXCEL_EXTENSION);
	    
		try {
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_PATH, SELECT, date);
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
