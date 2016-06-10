package registration;

import java.sql.Connection;
import java.sql.DriverManager;
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

public class SendAlertProva {

	public SendAlertProva(String group, String sender, String message) {
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
		}
		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/test","root", "");
			String select="SELECT email from utenti_alert WHERE gruppo=?";
			ps=connection.prepareStatement(select);
			ps.setString(1, group);
			rs=ps.executeQuery();
			while(rs.next()){
				sendMail(rs.getString(1),sender,message);
			}
			
	 
		} catch (Exception e) {
			
		}
		
	}

	private void sendMail(String dest, String sender, String alert) throws MessagingException {
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
	
        message.setText("Servizio Alert \n Inviato dall'utente:"+sender+"\n "+alert);

        // Aggiunta degli indirizzi del mittente e del destinatario
        InternetAddress fromAddress = new InternetAddress("noreply@cedacri.it");
        InternetAddress toAddress = new InternetAddress(dest);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // Invio del messaggio
        Transport.send(message);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Calendar calToday = Calendar.getInstance();
		System.out.println(dateFormat.format(calToday.getTime()));
		

	}

}
