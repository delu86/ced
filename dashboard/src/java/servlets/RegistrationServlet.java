/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.GestoreRegistrazione;

/**
 *
 * @author CRE0260
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String REGISTRATION_PAGE = "registration.jsp";
	private static final String OK_PAGE = "registrationComplete.jsp";
	private GestoreRegistrazione gest;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
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
		String pin=request.getParameter("pin");
	    try {
	    	 gest=new GestoreRegistrazione(email);
			MessageDigest md= MessageDigest.getInstance("SHA-256");
			md.update(pin.getBytes());
			byte[] bytes=md.digest();
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<bytes.length;i++){
				sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
			}
			String generetedPassword=sb.toString();
			try {
				gest.initializeRegisterProcedure(generetedPassword);
				request.getRequestDispatcher(OK_PAGE).forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.setAttribute("errorReg", e.getMessage());
				request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorReg", e.getMessage());
			request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
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
