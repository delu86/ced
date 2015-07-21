package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.User;
import utility.UtilityDate;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class TablesMFCountersServlet
 */
public class TablesMFCountersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TABLES_PAGE = "tablesMF.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TablesMFCountersServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DatabaseManager db= new DatabaseManager();

		try {
			User user=(User)request.getSession().getAttribute("user");

			if(user==null)
		    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
			else{
				String prof=request.getParameter("profile");
				String profile=user.getProfile();
				if(profile.equals("REALE"))
					prof=profile;
		    	if(!profile.equals("CED")&&!profile.equals("REALE")){
		    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
		    	}else{

           
			String date=request.getParameter("data");
			
			if(date==null){
			    String d=UtilityDate.conversionToDBformat(UtilityDate.getDate(-1));
				request.setAttribute("results",db.getCPUReportsByDay(d,prof));
				request.setAttribute("date",d);	
			}
			else{
				request.setAttribute("results",db.getCPUReportsByDay(date,prof));
				request.setAttribute("date",date);	
			}
			request.getRequestDispatcher(TABLES_PAGE+"?profile="+prof).forward(request, response);
			}}} catch (Exception e) {
			request.setAttribute("error",e);
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
