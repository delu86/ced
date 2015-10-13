package jatm;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
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
    public static final String PROPERTIES_FILE="parser.prop";
    //formato del nome per i log Faro
    public static String FARO_LOG_NAME_FORMAT="(?i:LOGSIA.FARONCH.BTD.V.*\\.txt)";
    //formato del nome per i log NCH
    public static String NCH_LOG_NAME_FORMAT="(?i:LOGATM\\..*\\.txt)";
    public static final Logger logger=Logger.getLogger("MY log");
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        FileHandler fh;
        ResourceBundle resourceBundle=ResourceBundle.getBundle(PROPERTIES_FILE);
        try {
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
        resourceBundle=ResourceBundle.getBundle(DB_RESOURCES);
        Class.forName(resourceBundle.getString("driver"));        
        try (Connection connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"))) {
            Date date=Calendar.getInstance().getTime();
            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm");          
            //    try (PreparedStatement s = connection.prepareStatement("REPLACE into atm_stat.last_update (id,date) VALUES (0,?)")) {
           //      s.setString(1, format.format(date));
          //      s.executeUpdate();
         //      s.close();
                
                //Stored procedure per la normalizazione dei messaggi 294
                CallableStatement call294Record=connection.prepareCall("call atm_stat.normalize294message");
                
                logger.log(Level.INFO, "Normalizzazione 294: {0}", call294Record.executeQuery());
                call294Record.close();
                //Stored procedure per l'accoppiamento dei messaggi B24/B25
                CallableStatement callB25Record=connection.prepareCall("call atm_stat.coupleB24B25");
                logger.log(Level.INFO,"Normalizzazione b25: {0}", callB25Record.executeQuery());
                callB25Record.close();                
                
                
                //Stored procedure per la creazione del file di backup
                CallableStatement callCreateBackup=connection.prepareCall("call atm_stat.createBackup");
                logger.log(Level.INFO,"Crea backup: {0}",callCreateBackup.executeQuery());
                callCreateBackup.close();
                //Stored procedure per l'accoppiamento dei messaggi A93-A94
                CallableStatement callNormalizzaA93_A94=connection.prepareCall("call atm_stat.coupleA93_A94");
                
                logger.log(Level.INFO,"Normalizza A93-A94: {0}",callNormalizzaA93_A94.executeQuery());
                callNormalizzaA93_A94.close();
                //Stored procedure per il dispatching dei record nelle tabelle giornaliere
                CallableStatement calllogAtmRecordDispatcher=connection.prepareCall("call atm_stat.logAtmRecordDispatcher");
                logger.log(Level.INFO,"Dispatcher: {0}",calllogAtmRecordDispatcher.executeQuery());
                calllogAtmRecordDispatcher.close();
                logger.info("End execution stored procedure");
                connection.close();
          //  }
            logger.info("Connection close bye ^_^");
        }}
        else{
            logger.info("Nessun log trovato");
        }
        } catch (IOException | SecurityException e) {
            logger.log(Level.WARNING, "Errore: {0}", e.getMessage());
        }
        
    }
}
     