package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.SystemReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class LastDayCpiServlet
 */
public class LastDayCpiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LastDayCpiServlet() {
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
		try {
			DatabaseManager db=new DatabaseManager();
			Collection<SystemReport> coll=db.getYesterdayCPI();
			
			response.setContentType("application/json");
			out = response.getWriter();
			String result="[";
			String categories="[";
			String data="[";
			int i=0;
			for(SystemReport s: coll){
				if(i==0){
					categories=categories.concat(" \""+s.getSystem()+"\" ");
					data=data.concat(String.format(Locale.US, "%.2f", s.getCpi()));
					i++;
				}
				else{
					categories=categories.concat(", \""+s.getSystem()+"\" ");
					 
					data=data.concat(" , "+ String.format(Locale.US, "%.2f", s.getCpi()));
				}
			}
			categories=categories.concat("]");
			data=data.concat("]]");
			result=result.concat(categories+","+data);
			out.print(result);
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
