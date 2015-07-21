package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import object.MQQuequeReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class MQChartDrilldownJSONServlet
 */
public class MQChartDrilldownJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MONTH_PARAMETER = "month";
	private static final String SYSTEM_PARAMETER = "system";
	private static final String DAY_PARAMETER = "day";
	private static final String YEAR_PARAMETER = "year";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MQChartDrilldownJSONServlet() {
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
		int day=Integer.parseInt(request.getParameter(DAY_PARAMETER));
		int month=Integer.parseInt(request.getParameter(MONTH_PARAMETER));
		int year=Integer.parseInt(request.getParameter(YEAR_PARAMETER));
		String system=request.getParameter(SYSTEM_PARAMETER);
	    DatabaseManager db=new DatabaseManager();
		PrintWriter out;
		String json="{\"series\":[";
		String categories="\"categories\":[";
		String putSeries="{\"name\":\"PUT\",\"data\":[";
		String getSeries="{\"name\":\"GET\",\"data\":[";
		try{
			response.setContentType("application/json");
			out = response.getWriter();
			Collection<MQQuequeReport> collection=db.getMqByDaySystem(year, month, day, system);
			int i=0;
			for(MQQuequeReport el:collection){
				if(i==0){
					putSeries=putSeries.concat(String.valueOf(el.getPutBytes()));
					getSeries=getSeries.concat(String.valueOf(el.getGetBytes()));
					categories=categories.concat("\""+el.getHour()+"\"");
					i++;
				}
				else{
					putSeries=putSeries.concat(", "+String.valueOf(el.getPutBytes()));
					getSeries=getSeries.concat(", "+String.valueOf(el.getGetBytes()));
					categories=categories.concat(", \""+el.getHour()+"\"");
				}
			}
			putSeries=putSeries.concat("]}");
		    getSeries=getSeries.concat("]}");
		    categories=categories.concat("]");
		   json=json.concat(putSeries+","+getSeries+"],"+categories+"}");
		   out.print(json);
		}catch(Exception e){
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
