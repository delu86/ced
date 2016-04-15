/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import object.StringConstants;
import object.User;

/**
 * Procedura di registrazione:
 * 1) verifica che l'utente non sia già registrato( mail già presente nel db)
 * 2) verifica dominio mail sia tra quelli consentiti;
 * 3) verifica che la password rispetti i criteri minimi di sicurezza(lunghezza, caratteri speciali ecc)
 * 4) cripta la password tramite algoritmo di DIGEST
 * 5) genera codice di attivazione
 * 7)salva record nel db
 * 6) invia mail all'utente per consentire di concludere l'attivazione dell'account
 * @author CRE0260
 *
 */
public class RegistrationManager{
	
	public static final int REGISTRATION_OK=0;//codice per procedura di registrazione andata a buon fine
	public static final int DOMINIO_ERROR=1;//codice errore per dominio mail non consentito
	public static final int PASSWORD_SHORT_ERROR=2;//codice errore per password troppo corta
	public static final int PASSWORD_PATTERN_ERROR=3;//codice errore per errato pattern della password
	public static final int USER_ALREADY_REGISTERED=4;//codice di errore
    	
	public static final int PASSWORD_MIN_LENGHT=6;//lunghezza minima della password
	private static final String MESSAGE_ACTIVATION_PROCEDURE = "Per completare la procedura di registrazione e poter effettuare il login al portale"
			+ " cliccare sul seguente link http://10.99.252.22/mainframeMonitor/pages/activation?activationCode=";
	private static final String SENDER_ACTIVATION = "noreply@cedacri.it";
	private static final String SUBJECT_ACTIVATION = "Completamento procedura di registrazione Portale Statistiche Cedacri";
	private static final String UPDATE_RECOVERY_PASSWORD =  "UPDATE smfacc.users SET activationCode=? , abilitato=0  where email=?";
	private static final String MESSAGE_RECOVERY_PROCEDURE = "Per completare la procedura di recupero password e poter effettuare il login al portale"
			+ " cliccare sul seguente link http://10.99.252.22/mainframeMonitor/pages/recovery?activationCode=";
	private static final String SUBJECT_RECOVERY = "Ripristino password";
	public static final String NO_PROFILES = "NONE";
	 
	
	
	
	public static int registration(String user, String password) throws MessagingException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, NamingException{
		if(alreadyRegistered(user)) return USER_ALREADY_REGISTERED;
		if(password.length()<PASSWORD_MIN_LENGHT) return PASSWORD_SHORT_ERROR;
		if(!verifyPasswordPattern(password)) return PASSWORD_PATTERN_ERROR;
		String activationCode=generateActivationCode();
		saveRecord(user,password,activationCode);
		sendMail(user,MESSAGE_ACTIVATION_PROCEDURE,activationCode);
		return REGISTRATION_OK;
	}

	private static void sendMail(String user,
			String messageActivationProcedure, String activationCode) throws MessagingException {
		MailUtility mail=new MailUtility();
		mail.sendMessage(user, SENDER_ACTIVATION, SUBJECT_ACTIVATION, MESSAGE_ACTIVATION_PROCEDURE+activationCode+"&email="+user);
		
	}

	private static void saveRecord(String email, String password,
			String activationCode) {
		ResourceBundle rb =   ResourceBundle.getBundle("utility.db");
		String userTable=rb.getString("table");
		Connection conn=null;
		PreparedStatement p=null;
		String insert="Insert INTO "+userTable+" (email,password,abilitato,activationCode,profilo) VALUES(?,?,0,?,?)";
	    try{
	    conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));	
	    p=conn.prepareStatement(insert);
	    p.setString(1, email);
	    p.setString(2, PasswordUtility.generateStorngPasswordHash(password));
	    p.setString(3, activationCode);
	    p.setString(4,getProfiles(email));
	    p.executeUpdate();
	    }catch(SQLException | NoSuchAlgorithmException | InvalidKeySpecException e){
                
	    }finally{
			if(p!=null) try{p.close();} catch(Exception e){}
			if(conn!=null) try{conn.close();} catch(Exception e){}
	    }
	}
	private static String getProfiles(String email){
		String[]split=email.split("@");
		String domain=split[1];
		ResourceBundle rb =   ResourceBundle.getBundle("utility.db");
		String profileTable=rb.getString("tableProfiles");
		Connection conn=null;
		PreparedStatement p=null;
		ResultSet rSet=null;
		String select="SELECT profile from "+profileTable+" where domain=?";
		 try{
			    conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));	
			    p=conn.prepareStatement(select);
			    p.setString(1, domain);
			    rSet=p.executeQuery();
			    if(rSet.next())
			    	return rSet.getString(1);
			    return NO_PROFILES;
			    }catch(Exception e){
			    	return null;
			    }finally{
			    	if(rSet!=null) try{rSet.close();} catch(Exception e){}
					if(p!=null) try{p.close();} catch(Exception e){}
					if(conn!=null) try{conn.close();} catch(Exception e){}
			    }
		
	}

	//genera il codice di attivazione
	private static String generateActivationCode() throws NoSuchAlgorithmException {
		SecureRandom sRandom=SecureRandom.getInstance("SHA1PRNG");
		byte[] code=new byte[16];
		sRandom.nextBytes(code);
		BigInteger bigInteger=new BigInteger(1,code);
		String hexString=bigInteger.toString(16);
		return hexString;
	}


	
    //verifica che la password rispetti il pattern stabilito(caratteri speciali,maiuscole ecc)
	private static boolean verifyPasswordPattern(String password) {
		
		return true;
	}
	
    
    public static void passwordRecovery(String user) throws ClassNotFoundException, SQLException, MessagingException, NoSuchAlgorithmException, NamingException{
    	        Context initContext = new InitialContext();
                Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
                DataSource datasource=(DataSource) envContext.lookup(StringConstants.CED_DB);
                String newCode;
            try (PreparedStatement p = datasource.getConnection().prepareStatement(UPDATE_RECOVERY_PASSWORD)) {
                newCode = RegistrationManager.generateActivationCode();
                p.setString(1, newCode);
                p.setString(2, user);
                p.executeUpdate();
            }
		MailUtility mail=new MailUtility();
		mail.sendMessage(user, SENDER_ACTIVATION, SUBJECT_RECOVERY, MESSAGE_RECOVERY_PROCEDURE+newCode+"&email="+user);
		
	    
    }
	//verifica che l'utente non sia già registrato nel sistema; return true se l'utente è presente nel db, false altrimenti,
	public static boolean alreadyRegistered(String user) throws ClassNotFoundException, SQLException, NamingException {
	        Context initContext = new InitialContext();
                Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
                DataSource datasource=(DataSource) envContext.lookup(StringConstants.CED_DB);
                String select="SELECT COUNT(*) from smfacc.users WHERE email=?";
		PreparedStatement p= datasource.getConnection().prepareStatement(select);
			p.setString(1, user);
			ResultSet r=p.executeQuery();
			r.next();
                return r.getInt(1)==1;
		}


	public static boolean verifyPasswordRecovery(String email,
			String activationCode) throws ClassNotFoundException, SQLException, NamingException {
		String select="SELECT COUNT(*) from smfacc.users WHERE email=? and activationCode=?";
		Context initContext = new InitialContext();
                Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
                DataSource datasource=(DataSource) envContext.lookup(StringConstants.CED_DB);
                        PreparedStatement p=datasource.getConnection().prepareStatement(select);
			p.setString(1, email);
			p.setString(2, activationCode);
			ResultSet r=p.executeQuery();
			r.next();
			return r.getInt(1)==1;	}

	public static void setNewPassword(String email, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		ResourceBundle rb =   ResourceBundle.getBundle("utility.db");
		String select="UPDATE "+rb.getString("table")+" SET password=? , abilitato=1  where email=?";
		Connection conn=null;
	    PreparedStatement p=null;
		
			Class.forName(rb.getString("driver"));
			conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));			
			p=conn.prepareStatement(select);
			p.setString(2, email);
			p.setString(1, PasswordUtility.generateStorngPasswordHash(password));
			p.executeUpdate();
			p.close();
			conn.close();
	}

    public static User verifyUser(String user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static boolean activateUser(String email, String activationCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}