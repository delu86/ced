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

import object.TapeReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class TapeReportJSONServlet
 */
public class TapeReportJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TapeReportJSONServlet() {
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
		DatabaseManager db=new DatabaseManager();
		PrintWriter out;
		try {
			Collection<TapeReport> collection=db.getTapeMountTimes();
			String json="[";
			String data="[";
			String categories="[";
			String actualCategory=null;
			response.setContentType("application/json");
		    out = response.getWriter();
			SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd");
			f1.setTimeZone(TimeZone.getTimeZone("UTC"));
			for(TapeReport el: collection){
				if(actualCategory==null){
					actualCategory=el.getType();
					categories=categories.concat("\""+actualCategory+"\"");
					Date d=f1.parse(el.getEpvdate());
					data=data.concat("[["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",el.getAvg_mount_time())+"]");
					}
				else{
					if(actualCategory.equals(el.getType())){
						Date d=f1.parse(el.getEpvdate());
						data=data.concat(",["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",el.getAvg_mount_time())+"]");
					}
					else{
						actualCategory=el.getType();
						categories=categories.concat(",\""+actualCategory+"\"");
						Date d=f1.parse(el.getEpvdate());
						data=data.concat("],\n[["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",el.getAvg_mount_time())+"]");
					}
				}
			}
			categories=categories.concat("]");
			data=data.concat("]]");
			json=json.concat(categories+","+data+"]");
			out.print(json);
			
		} catch (Exception e) {
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
