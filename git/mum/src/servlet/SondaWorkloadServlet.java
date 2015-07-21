package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.SondaWorkloadInterval;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class SondaWorkloadServlet
 */
public class SondaWorkloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SYSTEM_PARAMETER = "system";
	private static final String DATE_PARAMETER="date";
	private static final String SONDA_PAGE = "sondaWorkload.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SondaWorkloadServlet() {
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
	
		String system=request.getParameter(SYSTEM_PARAMETER);
		String date=request.getParameter(DATE_PARAMETER);
		DatabaseManager db=new DatabaseManager();
		try{
			Collection<SondaWorkloadInterval> collection=db.getSondaWkl(system, date);
			request.setAttribute("result", collection);
			request.getRequestDispatcher(SONDA_PAGE).forward(request, response);
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
