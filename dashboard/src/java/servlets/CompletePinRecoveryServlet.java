/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.GestoreRegistrazione;

/**
 *
 * @author CRE0260
 */
public class CompletePinRecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String WELCOME_PAGE = "welcome.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private GestoreRegistrazione gest;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompletePinRecoveryServlet() {
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
			HttpServletResponse response) throws ServletException, IOException {
		String email=(String)request.getSession().getAttribute("user");
		String pin=request.getParameter("pin");
   	try {
			gest=new GestoreRegistrazione(email);
		    gest.updatePin(pin);
			request.getRequestDispatcher(WELCOME_PAGE).forward(request, response);
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
   	finally{
   		gest.close();
   	}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
