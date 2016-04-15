/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtility {
	private final String localhost;
	private final String smtpHost;
	private final String smtpPort;
	

	public MailUtility() {
		ResourceBundle rb =   ResourceBundle.getBundle("utility.mail");
		localhost=rb.getString("localhost.ip");
		smtpHost=rb.getString("smtp.host");
		smtpPort=rb.getString("smtp.port");
		 }
   public void sendMessage(String emailReceiver,String sender,String subject,String messageText) throws MessagingException{
	   Properties props = new Properties();
       props.put("mail.smtp.host",smtpHost);
       props.put("mail.smtp.port",smtpPort);
       props.put("mail.smtp.localhost", localhost);
       props.put("mail.smtp.ehlo", false);
       props.put("mail.debug",true);
       Session session = Session.getInstance(props);
       session.setDebug(true);
       MimeMessage message = new MimeMessage(session);
       message.setSubject(subject);
   	   message.setText(messageText);
       // Aggiunta degli indirizzi del mittente e del destinatario
       InternetAddress fromAddress = new InternetAddress(sender);
       InternetAddress toAddress = new InternetAddress(emailReceiver);
       message.setFrom(fromAddress);
       message.setRecipient(Message.RecipientType.TO, toAddress);
       // Invio del messaggio
       Transport.send(message);

   };


}