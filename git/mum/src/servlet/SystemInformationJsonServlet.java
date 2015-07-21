package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.SystemReport;
import utility.UtilityDate;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class SystemInformationJsonServlet
 */
public class SystemInformationJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemInformationJsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String system=request.getParameter("system");
		String cpuClass=request.getParameter("cpuClass");
		int offset=Integer.valueOf(request.getParameter("offset"));
		int limit=Integer.valueOf(request.getParameter("limit"));
		DatabaseManager db=new DatabaseManager();
		try {
			Collection<SystemReport> coll=db.getLastdaysSystemReport(limit, system, cpuClass,offset);
			response.setContentType("application/json");
			PrintWriter out=response.getWriter();
			String arrayDate="[";
			String arrayCPI="[";
			String arrayMips="[";
			out.print("[");
			int i=0;
			for(SystemReport s:coll){
				Calendar c=Calendar.getInstance();
				c.setTime(UtilityDate.getDate(s.getDate(), UtilityDate.formatDateDatabase));
				String dayOfweek=null;
				switch (c.get(Calendar.DAY_OF_WEEK)){
				case 1: dayOfweek="Dom";
				        break;
				case 2: dayOfweek="Lun";
		                break;
				case 3:
					dayOfweek="Mar";
			        break;
				case 4:
					dayOfweek="Mer";
			        break;
				case 5:
					dayOfweek="Gio";
			        break;
				case 6:
					dayOfweek="Ven";
			        break;
				case 7:
					dayOfweek="Sab";
			        break;
				}
				if(i==0){
					arrayCPI=arrayCPI.concat(String.valueOf(s.getCpi()));
				    arrayDate=arrayDate.concat("\""+dayOfweek+", "+s.getDate().substring(6, 8)+"-"+s.getDate().substring(4, 6)+"-"+s.getDate().substring(0, 4)+"\"");
				    arrayMips=arrayMips.concat(String.valueOf(s.getMips()));
				    i++;}
				else{
					arrayCPI=arrayCPI.concat(","+String.valueOf(s.getCpi()));
			 		arrayDate=arrayDate.concat(","+"\""+dayOfweek+", "+s.getDate().substring(6, 8)+"-"+s.getDate().substring(4, 6)+"-"+s.getDate().substring(0, 4)+"\"");
			 		arrayMips=arrayMips.concat(","+String.valueOf(s.getMips()));	
				}
			}

			arrayCPI=arrayCPI.concat("]");
			arrayDate=arrayDate.concat("]");
			arrayMips=arrayMips.concat("]");
			out.print(arrayCPI);
			out.print(",");
			out.print(arrayDate);
			out.print(",");
			out.print(arrayMips);
			out.print("]");
		} catch (Exception e) {
			request.setAttribute("error", e);
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
}
