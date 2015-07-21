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
import utility.UtilityDate;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class WorkloadByDayJSON
 */
public class WorkloadByDayJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FORMAT_DATE = "yyyy-MM-dd";
	private static final String TIMEZONE = "UTC";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkloadByDayJSON() {
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
		long dayInMillis=Long.parseLong(request.getParameter("day"));
		
		DatabaseManager db=new DatabaseManager();
		try {
			response.setContentType("application/json");
			out = response.getWriter();
			String day=UtilityDate.fromMilliSecondToDateTimeString(dayInMillis,TIMEZONE,FORMAT_DATE);
			SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			f1.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
			Collection<WorkloadInterval> coll=db.getWorkloadByDay(system, day);
			String json="[";
			String categories="[";
			String data="[";
			String actualWorkload=null;
			for(WorkloadInterval work :coll){
				if(actualWorkload==null){
					actualWorkload=work.getWorkloadName();
					categories=categories.concat("\""+actualWorkload+"\"");
					Date d=f1.parse(work.getDate_interval_start());
					data=data.concat("[["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",work.getMipsCpu())+"]");
					}
				else{
					if(actualWorkload.equals(work.getWorkloadName())){
						Date d=f1.parse(work.getDate_interval_start());
						data=data.concat(",["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",work.getMipsCpu())+"]");
					}
					else{
						actualWorkload=work.getWorkloadName();
						categories=categories.concat(",\""+actualWorkload+"\"");
						Date d=f1.parse(work.getDate_interval_start());
						data=data.concat("],[["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",work.getMipsCpu())+"]");
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
