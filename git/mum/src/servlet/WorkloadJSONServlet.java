package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.WorkloadInterval;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class WorkloadServlet
 */
public class WorkloadJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkloadJSONServlet() {
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
		PrintWriter out;
		String system=request.getParameter("system");
		int limit=Integer.parseInt(request.getParameter("limit"));
		int offset=Integer.parseInt(request.getParameter("offset"));
		DatabaseManager db=new DatabaseManager();
		try {
			response.setContentType("application/json");
			out = response.getWriter();
			Collection<WorkloadInterval> coll=db.getLast30dayWorkload(system,limit,offset);
			SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd HH");
			f1.setTimeZone(TimeZone.getTimeZone("UTC"));
			String json="[";
			String categories="[";
			String data="[[";
			String actualWorkload=null;
			for(WorkloadInterval work :coll){
				Date d=f1.parse(work.getDate_interval_start());
				String date=String.valueOf(d.getTime());
				if(actualWorkload==null){
					actualWorkload=work.getWorkloadName();
					categories=categories.concat("\""+actualWorkload+"\"");
					
					data=data.concat("["+date+","+String.format(Locale.US,"%.2f",work.getMipsCpu())+"]");
					}
				else{
					if(actualWorkload.equals(work.getWorkloadName())){
						
						data=data.concat(",["+date+","+String.format(Locale.US,"%.2f",work.getMipsCpu())+"]");
					}
					else{
						actualWorkload=work.getWorkloadName();
						categories=categories.concat(",\""+actualWorkload+"\"");
						data=data.concat("],[["+date+","+String.format(Locale.US,"%.2f",work.getMipsCpu())+"]");
					}
				}
			}
			categories=categories.concat("]");
			data=data.concat("]]");
			json=json.concat(categories+","+data+"]");
			out.print(json);
			
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
