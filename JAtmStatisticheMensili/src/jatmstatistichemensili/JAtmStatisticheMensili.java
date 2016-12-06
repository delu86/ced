/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jatmstatistichemensili;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author CRE0260
 */
public class JAtmStatisticheMensili {
    
    
    private final static String DB_RESOURCES="./database.properties";
    public static final Logger logger=Logger.getLogger("Creazione Report Mensili");
    public static Connection connection;
    public static PropertyResourceBundle resourceBundle;
    public static String yearMonth;
    private static final String PROPERTIES_FILE="./prop.properties";
    /**
     * @param args the command line arguments
     * args[0]=YYYY-MM --> mese da creare le statistiche
     */
    public static void main(String[] args) {
        setUpLogfile();
        yearMonth=args[0];
        try (FileInputStream fisDB = new FileInputStream(DB_RESOURCES)) {
            resourceBundle=new PropertyResourceBundle(fisDB);
            createStatistics();
        }
        catch (FileNotFoundException ex) {
                Logger.getLogger(JAtmStatisticheMensili.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException | SQLException | ClassNotFoundException ex) {
                Logger.getLogger(JAtmStatisticheMensili.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createStatistics() throws SQLException, ClassNotFoundException {
        createDatabaseConnection();
        deleteOldData();
        setCedacriUnavailability();
        createCalendar();
        calculatesUnavailability();
        calculatesIPL();
        createsFinalReport();
        }

    private static void createDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName(resourceBundle.getString("driver")); 
        connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
    }
    private static void deleteOldData() {
        for(int i=1;i<=31;i++){
            executeStoredProcedure("call atm_stat.svecchiaDettaglio("+i+",'"+yearMonth+"-01')",
                                   "Svecchia dettaglio partizione n°"+i+" : {0}", 
                                   "Errore nello svecchiamento partizione n°"+i+": {0}");
           }
    }
    private static void executeStoredProcedure(String storedProcedureCall,String okMessage,String errorMessage){
          try ( 
               CallableStatement storedProcedure=connection.prepareCall(storedProcedureCall)){
               logger.log(Level.INFO,okMessage,storedProcedure.executeQuery());
               storedProcedure.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, errorMessage,ex.getMessage());
            }
    }

    private static void calculatesUnavailability() {
         for(int i=1;i<=getDaysOfMonth();i++){
            executeStoredProcedure("call atm_stat.calcolaIndisponibili("+i+",'"+yearMonth+"')",
                                   "Calcola indisponibilit? partizione n°"+i+": {0}", 
                                  " Errore nel calcolo indisponibilit? partizione n°"+i+": {0}");
             
         }
    }
    private static int getDaysOfMonth(){
        String[] splitDate=yearMonth.split("-");
        int year=Integer.parseInt(splitDate[0]);
        int month=Integer.parseInt(splitDate[1]);
        switch(month){
            case 1:
                return 31;
            case 2:
                if(year%4!=0)
                    return 28;
                else
                    return 29;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default: return 0;
        }
    }
    private static void setCedacriUnavailability() {
        executeStoredProcedure("call atm_stat.indispCedacri()",
                                  "callIndispCedacri : {0}", 
                                  "Errore nella elaborazione indispCedacri: {0}");
    }

    private static void createCalendar() {
        executeStoredProcedure("call atm_stat.creaCalendarioMese('"+yearMonth+"')",
                                   "callCreaCalendario : {0}", 
                                  " Errore nella creazione calendario: {0}");
    }

    private static void calculatesIPL() {
        executeStoredProcedure("call atm_stat.calcolaIpl('"+yearMonth+"')",
                                   "Calcola IPL : {0}", 
                                  " Errore nel calcolo IPL: {0}");
    }

    private static void createsFinalReport() {
        executeStoredProcedure("call atm_stat.creaRiepilogo('"+yearMonth+"')",
                                   "Crea riepilogo : {0}", 
                                  " Errore nella  creazione riepilogo: {0}");
    }

    private static void setUpLogfile() {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            resourceBundle=new PropertyResourceBundle(fis); 
        //    int GIORNO_DI_CONSOLIDAMENTO=Integer.valueOf(resourceBundle.getString("closing_day"));
            String filepath=resourceBundle.getString("filepath");
            FileHandler fh;
            fh=new FileHandler(filepath+"creazioneReport.log");
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);//RIMUOVE LA SCRITTURA A CONSOLE
            SimpleFormatter formatter=new SimpleFormatter();
            fh.setFormatter(formatter);
    }   catch (FileNotFoundException ex) {
            Logger.getLogger(JAtmStatisticheMensili.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JAtmStatisticheMensili.class.getName()).log(Level.SEVERE, null, ex);
        }}
}
