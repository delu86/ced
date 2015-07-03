/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logParsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CRE0260
 */
public abstract class LogParser {
    
    protected  Connection connection=null;
    protected PreparedStatement statement =null;
    protected final String DB_RESOURCE_PATH="logParsers.database";
   
    protected   final String LOG_FOLDER_PATH="C:\\Users\\cre0260\\Desktop\\ATM\\";
   
    protected long indexOK=0;
    protected long  indexKO=0;
    
    
    /**
     *formato dei datetime
     */
    protected   final static String DATETIME_FORMAT="yyyy-MM-dd hh:mm:ss";
    protected   final static String OPEDATE_FORMAT="ddMMyy";
    
    
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LogParser(String fileLogName){
        File fileLog=new File(LOG_FOLDER_PATH+fileLogName);
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LogParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            disconnectDB();
            System.out.println("Connection close! BYE ^^");
        }
    }
    abstract void scanRecord(String record)  throws ParseException;
    
    //crea connessione con il database
    private void connectDB() throws ClassNotFoundException, SQLException{
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCE_PATH); 
        Class.forName(resourceBundle.getString("driver"));
        connection=DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
    }
    //disconnessione dal database
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
    
}
