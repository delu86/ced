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
public class ActivationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NEXT_PAGE = "activationComplete.jsp";
	private GestoreRegistrazione g;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivationServlet() {
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
     String email=request.getParameter("email");
     String actCode=request.getParameter("actCode");
     try{
     g=new GestoreRegistrazione(email);
     g.finalizeRegisterProcedure(actCode);
     request.setAttribute("activation_message", "Attivazione effettuata con successo");	
     request.getRequestDispatcher(NEXT_PAGE).forward(request, response);
     
     }
    catch(Exception e){
        request.setAttribute("activation_message", e.getMessage());	
        request.getRequestDispatcher(NEXT_PAGE).forward(request, response);
    	
    }
     finally{
    	 g.close();
     }
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}

