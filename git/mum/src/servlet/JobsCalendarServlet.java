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
 * Servlet implementation class JobsCalendarServlet
 */
public class JobsCalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MONTH_PARAMETER = "month";
	private static final String JOBNAME_PARAMETER = "jobname";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobsCalendarServlet() {
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
		int month=Integer.parseInt(request.getParameter(MONTH_PARAMETER));
		String jobname=request.getParameter(JOBNAME_PARAMETER);
		DatabaseManager db=new DatabaseManager();
		PrintWriter out;
		try{
			response.setContentType("application/json");
			out = response.getWriter();
			Collection<BatchReport> collection=db.getBatchByNameMonth(month,jobname);
			String json="{\"data\":[";
			int i=0;
			for(BatchReport el:collection){
				if(i==0){
					i++;
					json=json.concat("{ \"title\":\""+jobname+"\", \"start\":\""+el.getInitialTitme()+"\", \"end\":\""+el.getEndTime()+"\" }");
				}
				else{
					json=json.concat(",{ \"title\":\""+jobname+"\", \"start\":\""+el.getInitialTitme()+"\", \"end\":\""+el.getEndTime()+"\" }");
				}
			}
			json=json.concat("]}");
			out.print(json);
		}
		catch(Exception e){
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
