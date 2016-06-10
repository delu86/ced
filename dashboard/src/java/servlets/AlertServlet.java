/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CRE0260
 */
public class AlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String OK_PAGE = "pageAlert.jsp";
	private static final String ERROR_PAGE = "error.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlertServlet() {
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
		String sender=(String) request.getSession().getAttribute("user");
		String group=(String) request.getParameter("group");
		String message="Servizio Alert \n Inviato dall'utente:"+sender+"\n "+(String) request.getParameter("message");
		String sms=(String) request.getParameter("message");
		String select="SELECT email,telefono from utenti_alert WHERE gruppo=?";
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
		connection =UtilityServlet.getConnection();
			ps=connection.prepareStatement(select);
			ps.setString(1, group);
			rs=ps.executeQuery();
		DateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd_HHmmss");
		Calendar calToday = Calendar.getInstance();
			PrintWriter writer = new PrintWriter("//10.99.5.23/DiscoD/SMS/Spedire"+dateFormat.format(calToday.getTime())+".txt", "UTF-8");
			String conferma="Il messaggio:\""+message+"\" ? stato inviato a:\n";
			while(rs.next()){
				sendMail(rs.getString(1),message);
				conferma=conferma+rs.getString(1)+"\n";
				writer.println("+39"+rs.getString(2)+";"+sms);
				
			}
			sendMail(sender,conferma);
			writer.close();
			
			request.getRequestDispatcher(OK_PAGE).forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}finally{
			if(rs!=null) try{rs.close();}catch(Exception e){};
			if(ps!=null) try{ps.close();}catch(Exception e){};
			if(connection!=null) try{connection.close();}catch(Exception e){};
		}	
	}

	private void sendMail(String dest,String alert) throws MessagingException {
		ResourceBundle rb =   ResourceBundle.getBundle("registration.dbprp");     
		String localhost=rb.getString("localhost.ip");
		String smtpHost=rb.getString("smtp.host");
		String  smtpPort=rb.getString("smtp.port");
        Properties props = new Properties();
        props.put("mail.smtp.host",smtpHost);
        props.put("mail.smtp.port",smtpPort);
        props.put("mail.smtp.localhost", localhost);
        props.put("mail.smtp.ehlo", false);
        props.put("mail.debug",true);
       
        Session session = Session.getInstance(props);
           
        session.setDebug(true);

        // Creazione del messaggio da inviare
        MimeMessage message = new MimeMessage(session);

			message.setSubject("Cedacri Alert");
	
        message.setText(alert);

        // Aggiunta degli indirizzi del mittente e del destinatario
        InternetAddress fromAddress = new InternetAddress("noreply@cedacri.it");
        InternetAddress toAddress = new InternetAddress(dest);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // Invio del messaggio
        Transport.send(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
