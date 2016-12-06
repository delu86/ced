package jatm;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.PropertyResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import parser.ATMParserFactory;
import parser.AtmParser;
import static utility.DatabaseConnector.DB_RESOURCES;
/**
 *  Main class per il parsing dei log atm; 
 * Vengono istanziati due parser (uno per i log NCH e uno per i log Faro); se nella cartella sono presenti log 
 * parserizabili, questi vengono dati in pasto ai due parser. Al termine del processo di parsing vengono lanciate le stored
 * procedure per la normalizazione dei dati, per la creazione del file da backuppare sull'IDAA e per il dispatching dei dati
 * dalle tabelle temporanee a quelle definitive.
 * @author CRE0260
 */
public class JAtm {

    /**
     */
    
    public static final String PROPERTIES_FILE="./prop.properties";
    //formato del nome per i log Faro
    public static String FARO_LOG_NAME_FORMAT="(?i:LOGSIA.FARONCH.BTD.V.*\\.txt)";
    //formato del nome per i log NCH
    public static String NCH_LOG_NAME_FORMAT="(?i:LOGATM\\..*\\.txt)";
    public static final Logger logger=Logger.getLogger("MY log");
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Date date=Calendar.getInstance().getTime();
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");          
        FileHandler fh;
        PropertyResourceBundle resourceBundle;
        Connection connection=null;
         try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            resourceBundle=new PropertyResourceBundle(fis); 
        //    int GIORNO_DI_CONSOLIDAMENTO=Integer.valueOf(resourceBundle.getString("closing_day"));
            String filepath=resourceBundle.getString("filepath");
            String log_parser_name=resourceBundle.getString("log_parser");
            fh=new FileHandler(filepath+log_parser_name);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);//RIMUOVE LA SCRITTURA A CONSOLE
            SimpleFormatter formatter=new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Start");
            ATMParserFactory factory=new ATMParserFactory();
        AtmParser parser=factory.getAtmParser(ATMParserFactory.FARO_NCH_PARSER_TYPE);
        AtmParser parser2=factory.getAtmParser(ATMParserFactory.NCH_PARSER_TYPE);
        File directory_log=new File(filepath);
        File[] contents=directory_log.listFiles();
        int i=0;
        //scansiona la cartella dove vengono salvati i log
        for(File f :contents){
            String filename=f.getName();
            if (filename.matches(NCH_LOG_NAME_FORMAT)) {
                logger.log(Level.INFO, "Start parsing NCH log {0}", filename);
                parser2.parse(filepath+filename);
                i++;
            } else {
                if (filename.matches(FARO_LOG_NAME_FORMAT)) {
                    logger.log(Level.INFO, "Start parsing FARO log {0}", filename);
                    parser.parse(filepath+filename);
                    i++;
                }
            }
        }
        if(i!=0){
        logger.info("End parsing operations");
        try (FileInputStream fisDB = new FileInputStream(DB_RESOURCES)) {
            resourceBundle=new PropertyResourceBundle(fisDB);
        }
        Class.forName(resourceBundle.getString("driver"));        
        try {
            connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
            //    try (PreparedStatement s = connection.prepareStatement("REPLACE into atm_stat.last_update (id,date) VALUES (0,?)")) {
            //      s.setString(1, format.format(date));
            //      s.executeUpdate();
            //      s.close();
            try ( //Stored procedure per la normalizazione dei messaggi 294
                    CallableStatement call294Record = connection.prepareCall("call atm_stat.normalize294message")) {
                logger.log(Level.INFO, "Normalizzazione 294: {0}", call294Record.executeQuery());
            }
            try ( //Stored procedure per l'accoppiamento dei messaggi B24/B25
                    CallableStatement callB25Record = connection.prepareCall("call atm_stat.coupleB24B25")) {
                logger.log(Level.INFO,"Normalizzazione b25: {0}", callB25Record.executeQuery());
                callB25Record.close();
            }                
            try ( //Stored procedure per la creazione del file di backup
               CallableStatement callCreateBackup=connection.prepareCall("call atm_stat.createBackup")){
               logger.log(Level.INFO,"Crea backup: {0}",callCreateBackup.executeQuery());
              callCreateBackup.close();
            }
            //Stored procedure per l'accoppiamento dei messaggi A93-A94
               try ( CallableStatement callNormalizzaA93_A94 = connection.prepareCall("call atm_stat.coupleA93_A94")) {
                logger.log(Level.INFO,"Normalizza A93-A94: {0}",callNormalizzaA93_A94.executeQuery());
                callNormalizzaA93_A94.close();
            }
            try ( //Stored procedure per il dispatching dei record nelle tabelle giornaliere
                    CallableStatement calllogAtmRecordDispatcher = connection.prepareCall("call atm_stat.logAtmRecordDispatcher")) {
                logger.log(Level.INFO,"Dispatcher: {0}",calllogAtmRecordDispatcher.executeQuery());
                calllogAtmRecordDispatcher.close();
            }
                logger.info("End execution stored procedure");
                //se siamo al 10 del mese effettua la chiusura
                /*if(date.getDate()==GIORNO_DI_CONSOLIDAMENTO){
                logger.info("Inizio operazioni consolidamento mese");
                    closeMonth(connection);
                }*/
                
          //  }
           
        }
        catch (SQLException ex) {
                        
               logger.log(Level.WARNING, "Errore (tutti i dati temporanei verranno eliminati, i log zippati nella cartella /work/atm/archive): {0}",ex.getMessage());
               try ( //Stored procedure per la creazione del file di backup
               CallableStatement callresetTempDatabases=connection.prepareCall("call atm_stat.resetTempDatabases")){
               logger.log(Level.INFO,"Reset db temporanei: {0}",callresetTempDatabases.executeQuery());
               callresetTempDatabases.close();
            }
            }}
        else{
            logger.info("Nessun log trovato");
            /*if(date.getDate()==GIORNO_DI_CONSOLIDAMENTO){
                try (FileInputStream fisDB = new FileInputStream(DB_RESOURCES)) {
            resourceBundle=new PropertyResourceBundle(fisDB);
              }
            Class.forName(resourceBundle.getString("driver")); 
            connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));

                logger.info("Inizio operazioni consolidamento mese");
                    closeMonth(connection);
                }*/
        }
        } catch (IOException | SecurityException 
                e) {
               logger.log(Level.WARNING, "Errore (tutti i dati temporanei verranno eliminati): {0}",e.getMessage());
               try ( //Stored procedure per la creazione del file di backup
               CallableStatement callresetTempDatabases=connection.prepareCall("resetTempDatabases")){
               logger.log(Level.INFO,"Reset db temporanei: {0}",callresetTempDatabases.executeQuery());
               callresetTempDatabases.close();
            }
            }
         finally{
                try
                {connection.close();
                logger.info("Connection close bye ^_^");}
                catch(Exception e){
                    logger.info("Connection close bye ^_^");
                }
            }
    }

    //effettua le operazioni necessarie per chiudere il mese
    /*
    private static void closeMonth(Connection connection) {
        Calendar cal=Calendar.getInstance();
        cal.roll(Calendar.MONTH, false);        
        Date d=cal.getTime();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        String annoMese= formatter.format(d).substring(0, 7);
        
        //effettua gli svecchiamenti nelle tabelle di dettaglio   
        for(int i=1;i<=31;i++){
              try ( 
               CallableStatement callSvecchiaDettaglio=connection.prepareCall("call atm_stat.svecchiaDettaglio("+i+",'"+annoMese+"-01')")){
               logger.log(Level.INFO,"Svecchia dettaglio partizione n°"+i+" : {0}",callSvecchiaDettaglio.executeQuery());
               callSvecchiaDettaglio.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, "Errore nello svecchiamento partizione n°"+i+": {0}",ex.getMessage());
            }
           }
        try ( //Stored procedure per la creazione del calendario del mese da chiudere
               CallableStatement callIndispCedacri=connection.prepareCall("call atm_stat.indispCedacri()")){
               logger.log(Level.INFO,"callIndispCedacri : {0}",callIndispCedacri.executeQuery());
               callIndispCedacri.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, "Errore nella elaborazione indispCedacri: {0}",ex.getMessage());
            }
         try ( //Stored procedure per la creazione del calendario del mese da chiudere
               CallableStatement callCreaCalendario=connection.prepareCall("call atm_stat.creaCalendarioMese('"+annoMese+"')")){
               logger.log(Level.INFO,"callCreaCalendario : {0}",callCreaCalendario.executeQuery());
               callCreaCalendario.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, "Errore nella creazione del calendario: {0}",ex.getMessage());
            }
         for(int i=1;i<=cal.getMaximum(Calendar.DAY_OF_MONTH);i++){
             //stored procedure per il calcolo delle indisponibilità
             try (CallableStatement callcalcolaIndisponibilità=connection.prepareCall("call atm_stat.calcolaIndisponibilità("+i+",'"+annoMese+"')")){
               logger.log(Level.INFO,"calcolaIndisponibilità partizione n°"+i+": {0}",callcalcolaIndisponibilità.executeQuery());
               callcalcolaIndisponibilità.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, "calcolaIndisponibilità partizione n°"+i+": {0}",ex.getMessage());
            }
         }
         try ( //Stored procedure per il calcolo degli IPL
               CallableStatement callcalcolaIpl=connection.prepareCall("call atm_stat.calcolaIpl('"+annoMese+"')")){
               logger.log(Level.INFO,"Calcola IPL : {0}",callcalcolaIpl.executeQuery());
               callcalcolaIpl.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, "Errore nel calcolo degli IPL: {0}",ex.getMessage());
            }
         try ( //Stored procedure per la creazione del report finale
               CallableStatement callcreaRiepilogo=connection.prepareCall("call atm_stat.creaRiepilogo('"+annoMese+"')")){
               logger.log(Level.INFO,"Crea riepilogo : {0}",callcreaRiepilogo.executeQuery());
               callcreaRiepilogo.close();
            } catch (SQLException ex) {
                 logger.log(Level.WARNING, "Errore nella creazione del riepilogo: {0}",ex.getMessage());
            }
        
}*/
}