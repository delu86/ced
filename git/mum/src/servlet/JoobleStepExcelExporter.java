package servlet;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exporter.ExcelExporter;

/**
 * Servlet implementation class JoobleStepExcelExporter
 */
public class JoobleStepExcelExporter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IDAA_RESOURCE_PATH = "datalayer.idaa";  
	private static final String SUFFIX_FILE_NAME = "export-step";
	private static final String EXCEL_EXTENSION= ".xls";
	private static final String SELECT="SELECT SMF30JBN , JESNUM , SMF30STM , SMF30STN , SMF30RUD , READTIME , ENDTIME ,"+
										" ROUND(CPUTIME, 2) AS CPUTIME, ZIPTM , ELAPSED , DISKIO , DISKIOTM , CONDCODE ,SMF30CL8 as class, SMF30PGM "+
									    " FROM CR00515.EPV30_4_STEP"+
										" WHERE SYSTEM = ? AND SMF30WID = 'JES2' AND SMF30JBN= ? AND JESNUM= ?"+
										" ORDER BY READTIME ";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoobleStepExcelExporter() {
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
		ResourceBundle rb =   ResourceBundle.getBundle(IDAA_RESOURCE_PATH);
		String system=request.getParameter("system");
		String jobname=request.getParameter("jobname");
		String jesnum=request.getParameter("jesnum");
        response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+EXCEL_EXTENSION);
	    
		try {
			ExcelExporter.getExcelFromIDAA(response.getOutputStream(), rb.getString("driver"),rb.getString("url"), SELECT, system,jobname,jesnum);
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
