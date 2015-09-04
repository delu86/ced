package jatm;

import parser.ATMParserFactory;
import parser.AtmParser;
/**
 *
 * @author CRE0260
 */
public class JAtm {

    /**
     * @param args the command line arguments
     */
    public static String FARO_LOG_NAME_FORMAT="LOGSIA.FARONCH.BTD.V*.txt";
    public static String NCH_LOG_NAME_FORMAT="LOGATM.*.txt";
    public static void main(String[] args) {
        ATMParserFactory factory=new ATMParserFactory();
        AtmParser parser=factory.getAtmParser(ATMParserFactory.FARO_NCH_PARSER_TYPE);
        AtmParser parser2=factory.getAtmParser(ATMParserFactory.NCH_PARSER_TYPE);
        parser.parse("LOGSIA.FARONCH.BTD.V6110.txt"); 
        parser.parse("LOGSIA.FARONCH.BTD.V6111.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6112.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6113.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6114.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6115.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6116.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6117.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6118.txt");
        parser.parse("LOGSIA.FARONCH.BTD.V6119.txt");
        parser2.parse("LOGATM.20150901.020242.NCH.txt");
        parser2.parse("LOGATM.20150902.020138.NCH.txt");
        parser2.parse("LOGATM.20150903.020106.NCH.txt");
        parser2.parse("LOGATM.20150904.020108.NCH.txt");
        
    }
}
