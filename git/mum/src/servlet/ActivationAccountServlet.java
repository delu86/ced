package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class ActivationAccountServlet
 */
public class ActivationAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE_ATTRIBUTE = "message";
	private static final Object MESSAGE_ERROR_SERVER = "Errore: server momentaneamente irraggiungibile; riprovare più tardi";
	private static final String INDEX = "login.jsp";
	private static final String MESSAGE_OK = "message_ok";
	private static final Object MESSAGE_OK_TEXT =  "Attivazione andata a buon fine effettuare il login per accedere";
	private static final Object MESSAGE_ERROR_ACTIVATION = "Errore:attivazione non eseguita";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivationAccountServlet() {
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
		String email=request.getParameter("email");
		String activationCode=request.getParameter("activationCode");
		DatabaseManager db=new DatabaseManager();
		try {
			if(db.activateUser(email, activationCode)){
				request.setAttribute(MESSAGE_OK, MESSAGE_OK_TEXT);
				request.getRequestDispatcher(INDEX).forward(request, response);
			}else{
				request.setAttribute(MESSAGE_ATTRIBUTE,MESSAGE_ERROR_ACTIVATION);
				request.getRequestDispatcher(INDEX).forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute(MESSAGE_ATTRIBUTE, MESSAGE_ERROR_SERVER);
			request.getRequestDispatcher(INDEX).forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
