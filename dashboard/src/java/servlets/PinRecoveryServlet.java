/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import registration.GestoreRegistrazione;

/**
 *
 * @author CRE0260
 */
public class PinRecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NEXT_PAGE = "newPinSend.jsp";
	private static final String RESEND_PAGE ="pinRecovery.jsp";
	private GestoreRegistrazione gest;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PinRecoveryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException {
		String email=request.getParameter("email");
		try {
			 gest=new GestoreRegistrazione(email);
			try {
				gest.resendPin();
			response.sendRedirect(NEXT_PAGE);
			} catch (Exception e) {
				request.setAttribute("errorReg", "Email errata");
				request.getRequestDispatcher(RESEND_PAGE).forward(request, response);
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			gest.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
