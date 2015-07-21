package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.GestoreRegistrazione;

/**
 * Servlet implementation class NewPasswordServlet
 */
public class NewPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPasswordServlet() {
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
		
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		try {
			GestoreRegistrazione.setNewPassword(email,password);
			request.setAttribute("message_ok", "Nuova password salvata");
			request.getRequestDispatcher("login.jsp").forward(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
