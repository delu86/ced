package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.User;
import utility.GestoreRegistrazione;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class LoginControlServlet
 */
public class LoginControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE_ATTRIBUTE = "message";
	private static final Object MESSAGE_ERROR = "Errore: username o password errati";
	private static final Object MESSAGE_DOMAIN_ERROR = "Errore: dominio mail non autorizzato";
	private static final Object MESSAGE_ERROR_SERVER = "Errore: server momentaneamente irraggiungibile; riprovare più tardi";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControlServlet() {
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
		
		String url=request.getParameter("url_req");
		String user=request.getParameter("username");
		String password=request.getParameter("password");
		String[] remember=request.getParameterValues("cookie");
		DatabaseManager db=new DatabaseManager();		
		try {
			User u=db.verifyUser(user, password);
			if(u!=null){
			if(!u.getProfile().equals(GestoreRegistrazione.NO_PROFILES)){
				request.getSession().setAttribute("user", u);
				if(remember!=null){
					Cookie cookie = new Cookie("user",user);
					cookie.setMaxAge(30*24 * 60 * 60);  // 1 month. 
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				db.updateLastAccess(user);
				if(url.equals("null"))
					response.sendRedirect("index.jsp");
				else
					response.sendRedirect(url);
			}
			else{
				request.setAttribute(MESSAGE_ATTRIBUTE, MESSAGE_DOMAIN_ERROR);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}}
			else{
				request.setAttribute(MESSAGE_ATTRIBUTE, MESSAGE_ERROR);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(MESSAGE_ATTRIBUTE, MESSAGE_ERROR_SERVER);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
