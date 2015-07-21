package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.StepReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class StepByJesnumServlet
 */
public class StepByJesnumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StepByJesnumServlet() {
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
		DatabaseManager db=new DatabaseManager();
		String system=request.getParameter("system");
		String query=request.getParameter("jobname");
		String jesnum=request.getParameter("jesnum");
		PrintWriter out;
		response.setContentType("application/json");
		String jsonString="{\"data\":[";
		String dataJSON="";
		try {
			out = response.getWriter();
			Collection<StepReport> reports=db.getStepByJesnum(system,query,jesnum);
			int i=0;
			for(StepReport report:reports){
				if(i==0){
					
					dataJSON=dataJSON.concat("{"+
						"\"JOBname\":"+	"\""+report.getJobName()+"\","+
						"\"JESNUM\":"+	"\""+report.getJesNumber()+"\","+
						"\"SMF30STM\":"+	"\""+report.getStepNameString()+"\","+
						"\"SMF30STN\":"+	"\""+report.getStepNumber()+"\","+
						"\"USER\":"+	"\""+report.getRacfUserIdString()+"\","+
						"\"READTIME\":"+		"\""+report.getReadTime()+"\","+
						"\"ENDTIME\":"+		"\""+report.getEndTime()+"\","+
						"\"CPUTIME\":"+		"\""+report.getCpuTime()+"\","+
						"\"ZIPTIME\":"+		"\""+report.getZipTime()+"\","+
						"\"ELAPSED\":"+		"\""+report.getElapsed()+"\","+
						"\"DISKIO\":"+		"\""+report.getDiskio()+"\","+
						"\"DISKIOTM\":"+		"\""+report.getDiskioTime()+"\","+
						"\"CLASS\":"+		"\""+report.getClass8()+"\","+
						"\"SMF30PGM\":"+		"\""+report.getProgramName()+"\""+
						
				         	"}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
							"\"JOBname\":"+	"\""+report.getJobName()+"\","+
							"\"JESNUM\":"+	"\""+report.getJesNumber()+"\","+
							"\"SMF30STM\":"+	"\""+report.getStepNameString()+"\","+
							"\"SMF30STN\":"+	"\""+report.getStepNumber()+"\","+
							"\"USER\":"+	"\""+report.getRacfUserIdString()+"\","+
							"\"READTIME\":"+		"\""+report.getReadTime()+"\","+
							"\"ENDTIME\":"+		"\""+report.getEndTime()+"\","+
							"\"CPUTIME\":"+		"\""+report.getCpuTime()+"\","+
							"\"ZIPTIME\":"+		"\""+report.getZipTime()+"\","+
							"\"ELAPSED\":"+		"\""+report.getElapsed()+"\","+
							"\"DISKIO\":"+		"\""+report.getDiskio()+"\","+
							"\"DISKIOTM\":"+		"\""+report.getDiskioTime()+"\","+
							
							"\"CLASS\":"+		"\""+report.getClass8()+"\","+
							"\"SMF30PGM\":"+		"\""+report.getProgramName()+"\""+
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
