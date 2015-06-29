/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logParsers;
/**
 *Parser per il log atm NCH
 * Esempio record del LOG:
 * 2009011408341200203032           A210602002251401090834000 
 * 2009011408590202003032           A940602000001401090858000000800000004400
 * 
 * @author CRE0260
 */
public class LogNCHParser {
    //PATH dove vengono caricati i log
    
    private final static String PATH_LOG="";
    private final static int LENGTH_CHECK_CHR=4;//lunghezza in byte del check_chr (serve per battezzare la validità del log) 
    private final static int LENGTH_DATE=8;    //lunghezza campo data
    private final static int LENGTH_HOUR=2;    //lunghezza campo ora
    private final static int LENGTH_MINUTE=2;  //lunghezza campo minuti
    private final static int LENGTH_SECONDS=2; //lunghezza campo secondi
    private final static int LENGTH_LOGPROGR=3;//lunghezza campo logprog
    private final static int LENGTH_CODABI=5;  //lunghezzqa campo  
    private final static int LENGTH_CODATM1=4;
   }
