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
import java.util.HashMap;
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
    
    protected HashMap<String,Integer> mapOpcodeAnomsn;
    protected HashMap<String,Integer> mapOpcodeAtmSblo;
    protected HashMap<String,Integer> mapAnomcodeAtmIndi;
    
    /**
     *formato dei datetime
     */
    protected   final static String DATETIME_FORMAT="yyyy-MM-dd hh:mm:ss";
    protected   final static String OPEDATE_FORMAT="ddMMyy";
    
    private void initializeMapOpcodeAnomsn() {
        mapOpcodeAnomsn=new HashMap<>();
        mapOpcodeAnomsn.put("A20", 1);
        mapOpcodeAnomsn.put("D20", 1);
        mapOpcodeAnomsn.put("E20", 1);
        mapOpcodeAnomsn.put("F20", 1);
        mapOpcodeAnomsn.put("G20", 1);
        mapOpcodeAnomsn.put("I20", 1);
        mapOpcodeAnomsn.put("U20", 1);
    }
    private void initializeMapOpcodeAtmSblo(){
        mapOpcodeAtmSblo=new HashMap<>();
        mapOpcodeAtmSblo.put("A34", 1);
        mapOpcodeAtmSblo.put("A50", 1);
        mapOpcodeAtmSblo.put("A52", 1);
        mapOpcodeAtmSblo.put("A60", 1);
        mapOpcodeAtmSblo.put("A72", 1);
        mapOpcodeAtmSblo.put("A74", 1);
        mapOpcodeAtmSblo.put("A76", 1);
        mapOpcodeAtmSblo.put("A84", 1);
        mapOpcodeAtmSblo.put("A94", 1);
        mapOpcodeAtmSblo.put("A98", 1);
        mapOpcodeAtmSblo.put("E72", 1);
        mapOpcodeAtmSblo.put("F72", 1);
        mapOpcodeAtmSblo.put("G72", 1);
        mapOpcodeAtmSblo.put("I72", 1);
        mapOpcodeAtmSblo.put("I74", 1);
        mapOpcodeAtmSblo.put("I76", 1);
        mapOpcodeAtmSblo.put("S74", 1);
        mapOpcodeAtmSblo.put("P78", 1);
        mapOpcodeAtmSblo.put("W72", 1);
    }
    private void initializemapAnomcodeAtmIndi(){
        mapAnomcodeAtmIndi=new HashMap<>();
        mapAnomcodeAtmIndi.put("19", 3);
        mapAnomcodeAtmIndi.put("35", 3);
        mapAnomcodeAtmIndi.put("40", 3);
        mapAnomcodeAtmIndi.put("44", 3);
        mapAnomcodeAtmIndi.put("45", 3);
        mapAnomcodeAtmIndi.put("46", 3);
        mapAnomcodeAtmIndi.put("47", 3);
        mapAnomcodeAtmIndi.put("48", 2);
        mapAnomcodeAtmIndi.put("49", 2);
        mapAnomcodeAtmIndi.put("51", 3);
        mapAnomcodeAtmIndi.put("54", 3);
        mapAnomcodeAtmIndi.put("55", 3);
        mapAnomcodeAtmIndi.put("53", 3);
        mapAnomcodeAtmIndi.put("56", 3);
         }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LogParser(String fileLogName){
        initializeMapOpcodeAnomsn();
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
