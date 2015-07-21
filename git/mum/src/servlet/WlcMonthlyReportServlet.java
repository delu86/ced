package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.CECReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class WlcMonthlyReportServlet
 */
public class WlcMonthlyReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WlcMonthlyReportServlet() {
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
		int month=Integer.parseInt(request.getParameter("month"));
		int year=Integer.parseInt(request.getParameter("year"));
		DatabaseManager db=new DatabaseManager();
		try{
			response.setContentType("application/json");
			out = response.getWriter();
			Collection<CECReport> coll=db.getWLCReport(month, year);
			SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd");
			f1.setTimeZone(TimeZone.getTimeZone("UTC"));
            String json="{series:[";
            String serie="{\"name\":";
			String actualCec=null;
            int i=0;
            for(CECReport el:coll){
            	Date d=f1.parse(el.getDate());
				String date=String.valueOf(d.getTime());
            }
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
