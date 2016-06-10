package registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import exception.ActivationException;
import exception.WrongEmailException;


public class GestoreRegistrazione {
	private Connection conn;
	private String email;
	private String localhost;
	private static final Pattern emailPattern = Pattern.compile(
	        "^[A-Za-z0-9-]+\\.[A-Za-z0-9-]+@([A-Za-z0-9-]+\\.)*cedacri.it?$"
	,Pattern.CASE_INSENSITIVE);
	private static final Pattern emailPatternSparkasse = Pattern.compile(
	        "^[A-Za-z0-9-]+\\.[A-Za-z0-9-]+@([A-Za-z0-9-]+\\.)*sparkasse.it?$"
	,Pattern.CASE_INSENSITIVE);
	private String userTable;
	private String smtpHost;
	private String smtpPort;
	private String activationCode;
	private String httpUrl;
	
	
	public GestoreRegistrazione(String mail) throws ClassNotFoundException, SQLException {
		   ResourceBundle rb =   ResourceBundle.getBundle("registration.dbprp");     

		String drivers=rb.getString("jdbc.drivers");
		
			Class.forName(drivers);
		
		
			localhost=rb.getString("localhost.ip");
			String url=rb.getString("jdbc.completeUrl");
			 smtpHost=rb.getString("smtp.host");
			 smtpPort=rb.getString("smtp.port");
			userTable=rb.getString("userTable");
			httpUrl=rb.getString("http.url");
			conn = DriverManager
			.getConnection(url); 
			email=mail;
	 
		
	}
    public void initializeRegisterProcedure(String pin) throws exception.WrongEmailException, exception.EmailAlreadyPresentException, SQLException, MessagingException{
		if(verifyPattern()){
			if(!verifyAlreadyRegistered()){
				Random rand=new Random();
				int randomCode1=rand.nextInt(10);
				int randomCode2=rand.nextInt(10);
				int randomCode3=rand.nextInt(10);
				int randomCode4=rand.nextInt(10);
				int randomCode5=rand.nextInt(10);
				int randomCode6=rand.nextInt(10);
				int randomCode7=rand.nextInt(10);
				int randomCode8=rand.nextInt(10);
				int randomCode9=rand.nextInt(10);
				String randomString=String.valueOf(randomCode1)+
						String.valueOf(randomCode2)+
						String.valueOf(randomCode3)+
						String.valueOf(randomCode4)+
						String.valueOf(randomCode5)+
						String.valueOf(randomCode6)+
						String.valueOf(randomCode7)+
						String.valueOf(randomCode8)+
						String.valueOf(randomCode9);

				saveRecord(pin,randomString);
				sendMail();
				
			}else throw new exception.EmailAlreadyPresentException("Email già presente");
			
		}
		else throw new exception.WrongEmailException("Email non del dominio ");

	}
    public void finalizeRegisterProcedure(String activationCode) throws SQLException, ActivationException{
    	String select="Select COUNT(*) from "+userTable+" where email=? and codice_attivazione=?";
    	PreparedStatement p=conn.prepareStatement(select);
    	p.setString(1, email);
    	p.setString(2, activationCode);
    	ResultSet r=p.executeQuery();
    	r.next();
    	if(r.getInt(1)==1){
    		String update="UPDATE "+userTable+" SET attivato=1 where email=?";
    		PreparedStatement p2=conn.prepareStatement(update);
        	p2.setString(1, email);
        	p2.executeUpdate();

    	}else {

    		throw new ActivationException("Errore nell'attivazione");
    	}
    	
    }
    
 
	/*
	 * la funzione verifica che la email inserita dall'utente matchi il pattern.
	 * 
	 */
	private boolean verifyPattern(){
		if(!emailPattern.matcher(email).matches()&&!emailPatternSparkasse.matcher(email).matches()){//se la mail non è del dominio cedacri.it
			return false;
		}
		return true;
       }
    private boolean verifyAlreadyRegistered(){
    	
	String select="SELECT COUNT(*) from "+userTable+" WHERE email=?";
    try {
		PreparedStatement p=conn.prepareStatement(select);
		p.setString(1, email);
		ResultSet r=p.executeQuery();
		p.close();
		r.next();
		if(r.getInt(1)==1){ 
			r.close();
			return false;
			
		}
		else return true;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}}
	private void saveRecord(String pin,String randomString) throws SQLException {

		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(randomString.getBytes());
			byte[] bytes=md.digest();
			//questi bytes sono in formato decimale, li convertiamo in formato esadecimale
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<bytes.length;i++){
				sb.append(Integer.toString((bytes[i] & 0xff)+0x100, 16).substring(1));
			}
			activationCode=sb.toString();
			String insert="Insert INTO "+userTable+" VALUES(?,?,0,1,?)";
            PreparedStatement p=conn.prepareStatement(insert);
			p.setString(1, email);
			p.setString(2, pin);
			p.setString(3, activationCode);
			p.executeUpdate();
			p.close();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
}
    private void sendMail() throws MessagingException {
        // Creazione di una mail session
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
		message.setSubject("Codice conferma registrazione");
		message.setText("Clicca sul link per attivare l'account: "+httpUrl+"?email="+email+"&actCode="+activationCode);
        // Aggiunta degli indirizzi del mittente e del destinatario
        InternetAddress fromAddress = new InternetAddress("noreply@cedacri.it");
        InternetAddress toAddress = new InternetAddress(email);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // Invio del messaggio
        Transport.send(message);
    
    }
	public void resendPin() throws MessagingException, WrongEmailException, NoSuchAlgorithmException, SQLException {
    	if(!verifyAlreadyRegistered()){
    	Random rand=new Random();
		int randomPin1=rand.nextInt(10);
		int randomPin2=rand.nextInt(10);
		int randomPin3=rand.nextInt(10);
		int randomPin4=rand.nextInt(10);
		int randomPin5=rand.nextInt(10);
		int randomPin6=rand.nextInt(10);
		String randomString=String.valueOf(randomPin1)+
				String.valueOf(randomPin2)+
				String.valueOf(randomPin3)+
				String.valueOf(randomPin4)+
				String.valueOf(randomPin5)+
				String.valueOf(randomPin6);
		updateRecord(randomString);
		sendPinMail(randomString);}
    	else throw new exception.WrongEmailException();		
	}
	private void sendPinMail(String pin) throws MessagingException {
        // Creazione di una mail session
        Properties props = new Properties();
        props.put("mail.smtp.host","10.99.19.14");
        props.put("mail.smtp.port","25");
        props.put("mail.smtp.localhost", localhost);
        props.put("mail.smtp.ehlo", false);
        props.put("mail.debug",true);
       
        Session session = Session.getInstance(props);
           
        session.setDebug(true);

        // Creazione del messaggio da inviare
        MimeMessage message = new MimeMessage(session);

			message.setSubject("Codice conferma registrazione");
	
        message.setText("Il codice per la conferma della registrazione a Cedacri Mobile è: "+pin);

        // Aggiunta degli indirizzi del mittente e del destinatario
        InternetAddress fromAddress = new InternetAddress("noreply@cedacri.it");
        InternetAddress toAddress = new InternetAddress(email);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // Invio del messaggio
        Transport.send(message);		
	}
	private void updateRecord(String pin) throws NoSuchAlgorithmException, SQLException {
		String insert="UPDATE "+userTable+" SET pin=?,verificato=0 where email=?";
	
			PreparedStatement p=conn.prepareStatement(insert);
			MessageDigest md= MessageDigest.getInstance("SHA-256");
			md.update(pin.getBytes());
			byte[] bytes=md.digest();
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<bytes.length;i++){
				sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
			}
			String generetedPassword=sb.toString();
			p.setString(1, generetedPassword);
			p.setString(2, email);
			p.executeUpdate();
			p.close();
				
	}
	public void updatePin(String pin) throws SQLException, NoSuchAlgorithmException {
		String insert="UPDATE "+userTable+" SET pin=?,verificato=1 where email=?";
		PreparedStatement p=conn.prepareStatement(insert);
		MessageDigest md= MessageDigest.getInstance("SHA-256");
		md.update(pin.getBytes());
		byte[] bytes=md.digest();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<bytes.length;i++){
			sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
		}
		String generetedPassword=sb.toString();
		p.setString(1, generetedPassword);		p.setString(2, email);
		p.executeUpdate();
		p.close();
			
	}
	public void close(){
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		GestoreRegistrazione g= new GestoreRegistrazione("franco.parisi@sparkasses.it");
		System.out.println(g.verifyPattern());
	}
}
