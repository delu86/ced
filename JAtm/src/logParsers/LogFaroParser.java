/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logParsers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Parser per i log degli atm di tipo F.A.R.O (Funzionamento ATM Rilevato Online)
 * @author Simone De Luca CRE0260
 */
public class LogFaroParser {
    
    private Connection connection=null;
    private PreparedStatement statement=null;
    private ResultSet  resultSet=null;
    private static final String INSERT_RECORD_OK_TABLE="INSERT into atm.atm_faro_log VALUES"
            + "(?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_RECORD_SCARTI="INSERT into atm.atm_faro_log_scarti VALUES (?,?,?)";
    private static final String DB_RESOURCE_PATH="logParsers.database";
 
    
    
    private static final String LOG_FOLDER_PATH="C:\\Users\\cre0260\\Desktop\\ATM\\"; //percorso assoluto della cartella contenente i file di log
    private static final String FILE__LOG="LOGSIA.FARONCH.BTD.V6000.txt";//****DA ELIMINARE****
    
    /*
    Lunghezza dei campi che si trovano all'interno del log
    */
    private static final int LENGTH_CODABI=5;
    private static final int LENGTH_ABI_CA=5;
    private static final int LENGTH_MSG=3;
    private static final int LENGTH_LOGDATE=8; //Formato data:yyyyMMdd
    private static final int LENGTH_HOUR=2;
    private static final int LENGTH_MINUTE=2;
    private static final int LENGTH_SECOND=2;
    private static final int LENGTH_PR=1;
    private static final int LENGHT_BLANK_SPACES=22;
    private static final int LENGTH_ST=2;
    private static final int LENGTH_COD_ATM=4;
    private static final int LENGTH_CAB_ATM=5;
    private static final int LENGTH_A94=30;
    
    private static final String DATETIME_FORMAT="yyyy-MM-dd hh:mm:ss";
    
    
    private String codAbi;
    private String abiCA;
    private String msg;
    private String date;//data  log
    private String hour;
    private String minute;
    private String second;
    private String codAtm;
    private String cabATM;
    private String A94;
    private String pr;
    private String st;
    private int numMSG;
    private String dataOraMSG;
    private static final String COD_ABI_PATTERN="\\d\\d\\d\\d\\d"; //tutti i caratteri del codice ABI devono essere numerici
    private static final String WRONG_CODABI="00000";
    private static final String MIN_DATE="2004-01-02 00:00:00";
    private static final String A94_STRING="A94";
 
    
    public LogFaroParser() {
        File fileLog=new File(LOG_FOLDER_PATH+FILE__LOG);
        try {
            connectDB();
        } catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Scanner input = new Scanner(fileLog)) {
            //inizia la scansione del log
            while(input.hasNext()){
                scanRecord(input.nextLine());
                   }//termine scansione del file
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            disconnectDB();
            System.out.println("Connection close! BYE ^^");
        }
        
    }
    
    private void scanRecord(String record) throws ParseException{
                int index=0;
                int error;
                codAbi=record.substring(index, index+=LENGTH_CODABI);
                if(codAbi.matches(COD_ABI_PATTERN)){//codice istituto corretto
                    abiCA=record.substring(index, index+=LENGTH_ABI_CA);
                    msg=record.substring(index,index+=LENGTH_MSG);
                    date=record.substring(index,index+=4)
                            +"-"+record.substring(index,index+=2)+"-"+record.substring(index,index+=2);                 
                    hour=record.substring(index,index+=LENGTH_HOUR);
                   
                    minute=record.substring(index,index+=LENGTH_MINUTE);
                    second=record.substring(index,index+=LENGTH_SECOND);
                    date=date+" "+hour+":"+minute+":"+second;
                    pr=record.substring(index,index+=LENGTH_PR);
                    index+=LENGHT_BLANK_SPACES;
                    st=record.substring(index,index+=LENGTH_ST);
                    codAtm=record.substring(index,index+=LENGTH_COD_ATM);
                    cabATM=record.substring(index,index+=LENGTH_CAB_ATM);
                    A94=record.substring(index,index+=LENGTH_A94);
                    if(!codAbi.equals(WRONG_CODABI)){
                        DateFormat dateFormat=new SimpleDateFormat(DATETIME_FORMAT);
                        Date dayLog=dateFormat.parse(date);
                        if(dayLog.after(dateFormat.parse(MIN_DATE))){
                            A94=A94.replaceAll("\\x00", "");
                            if(A94.substring(0, 3).equals(A94_STRING)){
                                numMSG=0;
                                if(A94.substring(7,12).matches(COD_ABI_PATTERN))
                                    numMSG=Integer.parseInt(A94.substring(7,12));
                                dataOraMSG="20"+A94.substring(16,18)+"-"+A94.substring(14,16)+"-"+A94.substring(12,14)+" "+A94.substring(18,20)+
                                      ":"+  A94.substring(20,22)+":"+A94.substring(22,24);
                            }
                            try {
                                int stateUpdate=saveRecord();
                                
                            } 
                            catch(MySQLIntegrityConstraintViolationException ex){
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        else{//data inferiore al 02 Gennaio 2004
                            error=3;
                            try {
                                saveScarti(FILE__LOG, record, error);
                            } catch (SQLException ex) {
                                Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    else{//codice istituto = '00000'
                        error=2;
                            try {
                                saveScarti(FILE__LOG, record, error);
                            } catch (SQLException ex) {
                                Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                }
                else{//codice istituto errato
                             error=1;
                            try {
                                saveScarti(FILE__LOG, record, error);
                            } catch (SQLException ex) {
                                Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                }
    }
    
    //salva il record nel database 
    private int saveRecord() throws SQLException,MySQLIntegrityConstraintViolationException {
        statement=connection.prepareStatement(INSERT_RECORD_OK_TABLE);
        statement.setString(1, date);
        statement.setString(2, dataOraMSG);
        statement.setString(3, codAbi);
        statement.setString(4, codAtm);
        statement.setString(5, msg);
        statement.setString(6, pr);
        statement.setString(7, st);
        statement.setInt(8, numMSG);
        statement.setString(9, A94);
        return statement.executeUpdate();
        
    }
    private int saveScarti(String filename,String line, int error_code) throws SQLException{
        statement=connection.prepareStatement(INSERT_RECORD_SCARTI);
        statement.setString(1, filename);
        statement.setString(2, line);
        statement.setInt(3, error_code);
          return statement.executeUpdate();
    }
   
    //crea connessione con il database
    private void connectDB() throws ClassNotFoundException, SQLException{
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCE_PATH); 
        Class.forName(resourceBundle.getString("driver"));
        connection=DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
    }
    private void disconnectDB(){
        if(statement!=null) try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        LogFaroParser logFaroParser = new LogFaroParser();
    }
}
