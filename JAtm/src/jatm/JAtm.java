package jatm;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import parser.ATMParserFactory;
import parser.AtmParser;
import static utility.DatabaseConnector.DB_RESOURCES;
/**
 *
 * @author CRE0260
 */
public class JAtm {

    /**
     */
    public static String FARO_LOG_NAME_FORMAT="(?i:LOGSIA.FARONCH.BTD.V.*\\.txt)";
    public static String NCH_LOG_NAME_FORMAT="(?i:LOGATM\\..*\\.txt)";
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ATMParserFactory factory=new ATMParserFactory();
        AtmParser parser=factory.getAtmParser(ATMParserFactory.FARO_NCH_PARSER_TYPE);
        AtmParser parser2=factory.getAtmParser(ATMParserFactory.NCH_PARSER_TYPE);
        File directory_log=new File("C:\\Users\\cre0260\\Desktop\\ATM\\ATMNEW\\");
        File[] contents=directory_log.listFiles();
        for(File f :contents){
            String filename=f.getName();
            if (filename.matches(NCH_LOG_NAME_FORMAT)) {
                parser2.parse(filename);
            } else {
                if (filename.matches(FARO_LOG_NAME_FORMAT)) {
                 parser.parse(filename);
                }
            }
        }
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCES);
        Class.forName(resourceBundle.getString("driver"));
        try (Connection connection = DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"))) {
            CallableStatement call294Record=connection.prepareCall("call atm_stat.normalize294message");
            CallableStatement callB25Record=connection.prepareCall("call atm_stat.coupleB24B25");
            System.out.println("Normalizzazione 294");
            call294Record.executeQuery();
            System.out.println("Normalizzazione b25");
            callB25Record.executeQuery();
            CallableStatement callCreateBackup=connection.prepareCall("call atm_stat.createBackup");
            CallableStatement callNormalizzaA93_A94=connection.prepareCall("call atm_stat.coupleA93_A94");
            CallableStatement calllogAtmRecordDispatcher=connection.prepareCall("call atm_stat.logAtmRecordDispatcher");
            System.out.println("Crea backup");
            callCreateBackup.executeQuery();
            System.out.println("Normalizza A93-A94");
            callNormalizzaA93_A94.executeQuery();
            System.out.println("Dispatcher ");
            calllogAtmRecordDispatcher.executeQuery();
            connection.close();
        }
    }
}
