package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.User;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class ChartDispatcherServlet
 */
public class ChartDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CPI_CHART_PAGE = "/pages/cpiCharts.jsp";
	private static final String ERROR_PAGE = "/pages/error.jsp";
	private static final String MIPS_CHART_PAGE = "/pages/mipsCharts.jsp";
	private static final Object MIPS ="MIPS";
	private static final Object CPI = "CPI";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartDispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page=request.getParameter("page");
		User user=(User)request.getSession().getAttribute("user");
	    if(user==null)
	    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL()+"?page="+page);
	    else{
	    DatabaseManager db=new DatabaseManager();
		try {
			Collection<String> systems=db.getSystems();
			request.setAttribute("systems", systems);
			
			if(page.equals(CPI))
				request.getRequestDispatcher(CPI_CHART_PAGE).forward(request, response);
			else
				if(page.equals(MIPS))
					request.getRequestDispatcher(MIPS_CHART_PAGE).forward(request, response);
		   } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", e);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
}
