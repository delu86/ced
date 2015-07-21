package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.GestoreRegistrazione;

/**
 * Servlet implementation class PasswordRecoveryServlet
 */
public class PasswordRecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordRecoveryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
		String email=request.getParameter("email");
		String activationCode=request.getParameter("activationCode");
		try {
			if(GestoreRegistrazione.verifyPasswordRecovery(email,activationCode)){
				request.setAttribute("email",email);
				request.getRequestDispatcher("recoveryForm.jsp").forward(request, response);
			}
			else{
				request.setAttribute("message", "Errore nella richiesta");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} 
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String email=request.getParameter("email");
		try {
			if(GestoreRegistrazione.alreadyRegistered(email)){
                GestoreRegistrazione.passwordRecovery(email);
                request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else{
			request.setAttribute("message", "Utente non registrato, effettuare procedura di registrazione.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
                                                               