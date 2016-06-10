/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CRE0260
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NEXT_PAGE = "welcome.jsp";
	private static final String PERSONAL_PIN_PAGE = "personalPin.jsp";
	private static final String INDEX_PAGE = "index.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException {
		String email=request.getParameter("email");
		String pin=request.getParameter("pin");
		ResourceBundle rb =   ResourceBundle.getBundle("registration.dbprp"); 
		String drivers=rb.getString("jdbc.drivers");
		String url=rb.getString("jdbc.completeUrl");
		try {
			Class.forName(drivers);
		} catch (ClassNotFoundException e) {
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		Connection connection = null;
		PreparedStatement p=null;
		ResultSet r=null;
		try {
			connection = DriverManager
			.getConnection(url);
	 
		} catch (SQLException e) {
		    request.setAttribute("error", e);

			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	 
		if (connection != null) {
			String select="SELECT verificato,attivato from utente where email=? and pin=?";
			try {
				p=connection.prepareStatement(select);
				MessageDigest md= MessageDigest.getInstance("SHA-256");
				md.update(pin.getBytes());
				byte[] bytes=md.digest();
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<bytes.length;i++){
					sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
				}
				String generetedPassword=sb.toString();
				p.setString(1, email);
				p.setString(2, generetedPassword);
				r=p.executeQuery();
				if(r.next()){
				if(r.getInt(2)==1){
					if(r.getInt(1)==1){
					
					request.getSession().setAttribute("user", email);
					request.getRequestDispatcher(NEXT_PAGE).forward(request, response);}
					else{
						
						request.getSession().setAttribute("user", email);
						request.getRequestDispatcher(PERSONAL_PIN_PAGE).forward(request, response);}						
					
				}				else{
					
					request.setAttribute("errorReg", "Account non attivato");
					request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
				}
				}
				else{
					
					request.setAttribute("errorReg", "Email o password errata");
					request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
				}
			} catch (SQLException e) {
				request.setAttribute("error", e);
				request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			}finally{
				if(r!=null)try{r.close();}catch(Exception e){}
				if(p!=null)try{p.close();}catch(Exception e){}
				if(connection!=null)try{connection.close();}catch(Exception e){}
			}
		
		} else {
			
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}
