package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.BatchReport;
import utility.UtilityDate;
import datalayer.DatabaseManager;
import object.StepReport;

/**
 * Servlet implementation class BatchDetailJSONServlet
 */
public class BatchDetailJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TIMEZONE = "UTC";
	private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm";
	private static final Object JOB_TYPE = "job";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchDetailJSONServlet() {
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
		
		PrintWriter out;
		String system=request.getParameter("system");
		String type=request.getParameter("type"); //job oppure stc (start&task)
		long dateInMillis=Long.parseLong(request.getParameter("day"));
		String daytime=UtilityDate.fromMilliSecondToDateTimeString(dateInMillis, TIMEZONE,FORMAT_DATETIME);
                DatabaseManager db=new DatabaseManager();
		response.setContentType("application/json");
		try {
			String jsonString="{\"data\":[";
			String dataJSON="";
			out = response.getWriter();
			Collection<StepReport> collection;
			if(type.equals(JOB_TYPE))
				 collection=db.getBatchByDate(daytime, system);
			else
				collection=db.getSTCByDate(daytime, system);
			int i=0;
			for(StepReport  report:collection){
				
			if(i==0){			 
                          //DATET10,SMF30JBN,JESNUM,SMF30STM,SMF30STN,
	                 //SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX,CONDCODE,ABEND
					dataJSON=dataJSON.concat("{"+
					"\"SMF30JBN\":"+	"\""+report.getJobName()+"\","+
					"\"JESNUM\":"+	        "\""+report.getJesNumber()+"\","+
					"\"SMF30STM\":"+	"\""+report.getStepNameString()+"\","+
					"\"SMF30STN\":"+		"\""+report.getStepNumber()+"\","+
					"\"SMF30PSN\":"+		"\""+report.getProcStep()+"\","+
					"\"SMF30PGM\":"+		"\""+report.getProgramName()+"\","+
					"\"SMF30RUD\":"+		"\""+report.getRacfUserIdString()+"\","+
					"\"CPUTIME\":"+		"\""+report.getCpuTime()+"\","+
					"\"SMF30SRV_L\":"+		"\""+report.getServiceClass()+"\","+
					"\"SMF30TEX\":"+		"\""+report.getNumExcp()+"\","+
					"\"CONDCODE\":"+		"\""+report.getConditionCode()+"\","+
					"\"ABEND\":"+		"\""+report.getAbend()+"\""+
					"}");
				i++;
			}
			else{
				dataJSON=dataJSON.concat(",{"+					
			                "\"SMF30JBN\":"+	"\""+report.getJobName()+"\","+
					"\"JESNUM\":"+	        "\""+report.getJesNumber()+"\","+
					"\"SMF30STM\":"+	"\""+report.getStepNameString()+"\","+
					"\"SMF30STN\":"+		"\""+report.getStepNumber()+"\","+
					"\"SMF30PSN\":"+		"\""+report.getProcStep()+"\","+
					"\"SMF30PGM\":"+		"\""+report.getProgramName()+"\","+
					"\"SMF30RUD\":"+		"\""+report.getRacfUserIdString()+"\","+
					"\"CPUTIME\":"+		"\""+report.getCpuTime()+"\","+
					"\"SMF30SRV_L\":"+		"\""+report.getServiceClass()+"\","+
					"\"SMF30TEX\":"+		"\""+report.getNumExcp()+"\","+
					"\"CONDCODE\":"+		"\""+report.getConditionCode()+"\","+
					"\"ABEND\":"+		"\""+report.getAbend()+"\""+
                                        "}");
								}
		}
			jsonString=jsonString.concat(dataJSON+"]}");
			out.print(jsonString);
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
