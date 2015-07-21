package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.SystemHourReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class HourlyWorkloadJSONServlet
 */
public class HourlyWorkloadJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HourlyWorkloadJSONServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
		String system=request.getParameter("system");
		String cpuClass=request.getParameter("cpuClass");
		String d=request.getParameter("date");
		String date=normalizeDate(d);
		DatabaseManager db=new DatabaseManager();
		try{
			Collection<SystemHourReport> coll=db.getSystemReportByDate(date, system, cpuClass);
			response.setContentType("application/json");
			PrintWriter out=response.getWriter();
			String arrayHour="[";
			String arrayCPI="[";
			String arrayMips="[";
			String arrayCPUnumber="[";
			out.print("[");
			int i=0;
			for(SystemHourReport s:coll){
				if(i==0){
					arrayCPI=arrayCPI.concat(String.valueOf(s.getCpi()));
				    arrayHour=arrayHour.concat("\""+s.getFrom_hour()+"\"");
				    arrayMips=arrayMips.concat(String.valueOf(s.getMips()));
				    arrayCPUnumber=arrayCPUnumber.concat(String.valueOf(s.getNumberCPU()));

				    i++;}
				else{
					arrayCPI=arrayCPI.concat(","+String.valueOf(s.getCpi()));
			 		arrayHour=arrayHour.concat(","+"\""+s.getFrom_hour()+"\"");
			 		arrayMips=arrayMips.concat(","+String.valueOf(s.getMips()));	
			 		arrayCPUnumber=arrayCPUnumber.concat(","+String.valueOf(s.getNumberCPU()));

				}
			}

			arrayCPI=arrayCPI.concat("]");
			arrayHour=arrayHour.concat("]");
			arrayMips=arrayMips.concat("]");
			arrayCPUnumber=arrayCPUnumber.concat("]");

			out.print(arrayCPI);
			out.print(",");
			out.print(arrayHour);
			out.print(",");
			out.print(arrayMips);
			out.print(",");
			out.print(arrayCPUnumber);
			out.print("]");

			
		}
		catch (Exception e) {
			request.setAttribute("error", e);
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	private static String normalizeDate(String date) {
		// TODO Auto-generated method stub
		return date.substring(6,10)+date.substring(3,5)+date.substring(0,2);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
