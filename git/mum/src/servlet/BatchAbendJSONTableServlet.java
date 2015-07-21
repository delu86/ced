package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import object.BatchReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class BatchAbendJSONTableServlet
 */
public class BatchAbendJSONTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SYSTEM_PARAMETER="system";
	private static final String DATE_PARAMETER="date";
	private static final String LIMIT_PARAMETER = "limit";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchAbendJSONTableServlet() {
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
		String system=request.getParameter(SYSTEM_PARAMETER);
		String date=request.getParameter(DATE_PARAMETER);
		int limit=Integer.parseInt(request.getParameter(LIMIT_PARAMETER));
		DatabaseManager db=new DatabaseManager();
		response.setContentType("application/json");
		try {
			String jsonString="{\"data\":[";
			String dataJSON="";
			out = response.getWriter();
			Collection<BatchReport> collection=db.getBatchInAbend(system, date,limit);
			int i=0;
			for(BatchReport report:collection){
				if(i==0){
					dataJSON=dataJSON.concat("{"+
						"\"hour\":"+	"\""+report.getDateInterval()+"\","+
						"\"job\":"+	"\""+report.getJobName()+"\","+
						"\"condcode\":"+	"\""+report.getConditionCode()+"\","+
						"\"cpu_sec\":"+		"\""+report.getCpuTime()+"\","+
						"\"response\":"+		"\""+report.getElapsed()+"\","+
						"\"count\":"+		"\""+report.getCount()+"\","+
						"\"ziptime\":"+		"\""+report.getZipTime()+"\","+
						"\"user\":"+		"\""+report.getRacfUserIdString()+"\""+
				         	"}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
							"\"hour\":"+	"\""+report.getDateInterval()+"\","+
							"\"job\":"+	"\""+report.getJobName()+"\","+
							"\"condcode\":"+	"\""+report.getConditionCode()+"\","+
							"\"cpu_sec\":"+		"\""+report.getCpuTime()+"\","+
							"\"response\":"+		"\""+report.getElapsed()+"\","+
							"\"count\":"+		"\""+report.getCount()+"\","+
							"\"ziptime\":"+		"\""+report.getZipTime()+"\","+
							"\"user\":"+		"\""+report.getRacfUserIdString()+"\""+							"}");
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
