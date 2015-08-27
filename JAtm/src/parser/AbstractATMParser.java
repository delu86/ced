/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

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
 * Classe astratta per parsing dei log Atm. Ogni classe concreta 
 * implementa la scansione del record di un tipo concreto di log(FARO_NCH e ATM_NCH)
 * @author CRE0260
 */
public abstract class AbstractATMParser implements AtmParser{

    
    private final static String DB_RESOURCES="parser.database";
    protected Connection connection;
    private final String LOG_FOLDER_PATH="C:\\Users\\cre0260\\Desktop\\ATM\\ATM\\";
    private PreparedStatement statement;
    
    protected final static String DATETIME_FORMAT="yyyy-MM-dd hh:mm:ss";
    protected final static String LOGDATEFORMAT="yyyy-MM-dd";
    protected final static String OPEDATE_FORMAT="ddMMyy";
    protected String filename;
    protected int indexOk=0; //numero di record coerenti con i requisiti 
    protected int indexKo=0; //numero di record non coerenti con i requisiti 
   
    
    @Override
    /*
    la funzione prende in ingresso il nome file da scannerizzare; per ogni record scandito
    e coerente con i requisiti e il tracciato del log, viene salvato all'interno di un database
    (MYSQL...vedi file properties per la connessione)
    */
    public void parse(String filename) {
        this.filename=filename;
        File fileLog=new File(LOG_FOLDER_PATH+filename);
          try (Scanner input = new Scanner(fileLog)) {
            connectToDB();
              
            //inizia la scansione del log
            while(input.hasNext()){
                String line;
                scanRecord(line=input.nextLine());
                //System.out.println(line);
                   }//termine scansione del file
            
              System.out.println("Record ok: "+indexOk);
              System.out.println("Record Ko: "+indexKo);
              //finalizzazione tramite esecuzione delle stored procedure
              executesStoredProcedure();
            
        } catch (FileNotFoundException ex) {//file non trovato
            Logger.getLogger(LogFaroNCHParser.class.getName()).log(Level.ALL, null, ex);
              System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AbstractATMParser.class.getName()).log(Level.ALL, null, ex);
            System.out.println(ex.getMessage());
        }
          catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(LogFaroNCHParser.class.getName()).log(Level.ALL, null, ex);
             System.out.println(ex.getMessage());
        }
        finally{//disconnessione dal database
            
            System.out.println("Connection close! BYE ^^");//da eliminare
        }
    }
    //stored procedure da eseguire alla fine della scansione del log; effettuano normalizzazione 
    //dei dati
    protected  abstract  void executesStoredProcedure() throws SQLException; 

    protected abstract void scanRecord(String line) throws ParseException,SQLException;
    
    //salva il record nel db
    protected void saveRecord(String insertSQL,String...  parameters) throws SQLException{
            statement=connection.prepareStatement(insertSQL);
            int indexParameter=1;
            for(String s:parameters){
                statement.setString(indexParameter, s);
                indexParameter++;
            }
            statement.executeUpdate();
            statement.close();
        
        
    }
    
    protected void connectToDB() throws ClassNotFoundException, SQLException{
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCES);
        Class.forName(resourceBundle.getString("driver"));
        connection=DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
    }
    protected void disconnectDB(){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogFaroNCHParser.class.getName()).log(Level.ALL, null, ex);
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
