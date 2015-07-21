package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.GestoreRegistrazione;

/**
 * Servlet implementation class CreateNewAccountServlet
 */
public class CreateNewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String MESSAGE_ATTRIBUTE_OK = "message_ok";
	private static final String MESSAGE_OK = "Registrazione effettuata con successo. Fai click sul link ricevuto sulla email per completare la registrazione";
	private static final String INDEX = "login.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewAccountServlet() {
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
		String password=request.getParameter("password");
		try {
			switch(GestoreRegistrazione.registration(email, password)){
			case GestoreRegistrazione.REGISTRATION_OK:
				request.setAttribute(MESSAGE_ATTRIBUTE_OK,MESSAGE_OK);
			    request.getRequestDispatcher(INDEX).forward(request,response);
			    return;
		    case 1:
			case 2:
			case 3:
			case GestoreRegistrazione.USER_ALREADY_REGISTERED:
				 request.setAttribute(MESSAGE_ATTRIBUTE,"Utente già registrato");
		         request.getRequestDispatcher(INDEX).forward(request,response);
		         return;
			
			}
		} catch (Exception e) {
			request.setAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
			request.getRequestDispatcher(INDEX).forward(request,response);
		}
		}
		
	

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
