package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.User;
import exporter.ExcelExporter;

/**
 * Servlet implementation class MF113ExporterServlet
 */
public class MF113ExporterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String SUFFIX_FILE_NAME="mf113-";   
    private static final String EXCEL_EXTENSION=".xls";
	private static final String RESOURCE_DB_PATH = "datalayer.db";
	private static final String SELECT="SELECT SYSTEM,aaaammgg,CPU,CLASS,`sum(INTSEC)` as DURATION,MIPS,CPI,L1MP,RNI,L2P,L3P,L4LP,L4RP,MEMP,PRB_STATE,AVG_UTIL FROM smfacc.r113_resume_ctrl WHERE aaaammgg=? ORDER BY SYSTEM,CPU";
    private static final String SELECT_REALE="SELECT SYSTEM,aaaammgg,CPU,CLASS,`sum(INTSEC)` as DURATION,MIPS,CPI,L1MP,RNI,L2P,L3P,L4LP,L4RP,MEMP,PRB_STATE,AVG_UTIL FROM smfacc.r113_resume_ctrl WHERE aaaammgg=?"
    		+ " and (SYSTEM='SIES'or SYSTEM='SIGE') ORDER BY SYSTEM,CPU";
	private static final Object REALE_PROFILE = "REALE";
	/**
     * @see HttpServlet#HttpServlet()
     */
    public MF113ExporterServlet() {
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
		User user=(User)request.getSession().getAttribute("user");
		String profile=user.getProfile();
	    response.setContentType("application/vnd.ms-excel");
	    String parameterDate=request.getParameter("date");
	    
	    response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+parameterDate+EXCEL_EXTENSION);
	    
		try {
			if(!profile.equals(REALE_PROFILE)){
				ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,SELECT
						,parameterDate);	
		    }
			else{
				ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,SELECT_REALE
						,parameterDate);
			}
			
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
