package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datalayer.DatabaseManager;

/**
 * Servlet implementation class DailyWorkloadServlet
 */
public class DailyWorkloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DAILY_CPI_PAGE = "dailyCPIReport.jsp";
	private static final String DAILY_MIPS_PAGE = "dailyMIPSReport.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DailyWorkloadServlet() {
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
		DatabaseManager db=new DatabaseManager();
		Collection<String> systems;
		try {
			systems = db.getSystems();
		
		request.setAttribute("systems", systems);
		request.setAttribute("system",request.getParameter("system"));
		request.setAttribute("date",request.getParameter("date"));
		if(request.getParameter("page").equals("CPI"))
			request.getRequestDispatcher(DAILY_CPI_PAGE).forward(request, response);
		else
			request.getRequestDispatcher(DAILY_MIPS_PAGE).forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
