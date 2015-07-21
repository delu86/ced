package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.User;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class MQChartDispatcherServlet
 */
public class MQChartDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MQ_CHART_PAGE = "mqChart.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MQChartDispatcherServlet() {
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
		try {
		User user=(User)request.getSession().getAttribute("user");

		if(user==null)
	    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
		else{
		DatabaseManager db=new DatabaseManager();
		
			Collection<String> systems=db.getMQSystems();
			request.setAttribute("systems", systems);
			request.getRequestDispatcher(MQ_CHART_PAGE).forward(request, response);
			}}
		catch(Exception e){
		e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}

}
