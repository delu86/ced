/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logParsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *Parser per il log atm NCH
 * Esempio record del LOG:
 * 2009011408341200203032           A210602002251401090834000 
 * 2009011408590202003032           A940602000001401090858000000800000004400
 * 
 * @author CRE0260
 */
public class LogNCHParser extends LogParser{
    
    
    private static final String FILE__LOG="LOGATM.20150701.020052.NCH.txt";//****DA ELIMINARE****
    private static final String CHECK_CHR_FORMAT="\\d{4}";
    //variabili per la lunghezza dei campi di ogni record del log
    private final static int LENGTH_CHECK_CHR=4;//lunghezza in byte del check_chr (serve per battezzare la validità del log) 
    private final static int LENGTH_DATE=8;    //lunghezza campo data (YYYYMMDD)
    private final static int LENGTH_HOUR=2;    //lunghezza campo ora
    private final static int LENGTH_MINUTE=2;  //lunghezza campo minuti
    private final static int LENGTH_SECONDS=2; //lunghezza campo secondi
    private final static int LENGTH_LOGPROGR=3;//lunghezza campo logprog
    private final static int LENGTH_CODABI=5;  //lunghezza campo codice ABI (campo identificativo di ogni banca) 
    private final static int LENGTH_CODATM1=4; //lunghezza codice ATM
    private final static int LENGTH_FILLER=70; //lunghezza del campo filler(spazi vuoti dopo codice abi) 
    private final static int LENGTH_OPCOD=3;   //lunghezza del campo codice operazione
    private final static int LENGTH_CODATM=4;  //lunghezza del campo codice atm
    private final static int LENGTH_OPENUM=5;  // lunghezza campo num
    private final static int LENGTH_OPEDATE=6; //Lunghezza campo data(DDMMYY)
    private static final String WRONG_CODABI="00000";
    private String date;
    private String hour;
    private final String MIN_DATE="2004-01-02 00:00:00";
    private String minute;
    private String second;
    private String logprog;
    private String codabi;
    private String codatm1;
    private String opecod;
    private String codatm;
    private String openum;
    private String opeDate;
    private int i=0;
    public LogNCHParser(String fileLogName){
        super(fileLogName);
        System.out.println(String.format("Record ok: %d\nRecord Errati: %d",indexOK,indexKO));
        
    }

    @Override
    void scanRecord(String record) throws ParseException {
         i+=1;
        int index=0;
        String checkChr=record.substring(0,4);
        if (checkChr.matches(CHECK_CHR_FORMAT)) {
            date=record.substring(index,index+=4)
                            +"-"+record.substring(index,index+=2)+"-"+record.substring(index,index+=2);
            hour=record.substring(index,index+=LENGTH_HOUR);
                   
                    minute=record.substring(index,index+=LENGTH_MINUTE);
                    second=record.substring(index,index+=LENGTH_SECONDS);
                    date=date+" "+hour+":"+minute+":"+second;
            DateFormat dateFormat=new SimpleDateFormat(DATETIME_FORMAT);
            Date dayLog=dateFormat.parse(date);
            Date minDay=dateFormat.parse(MIN_DATE);
            if(dayLog.after(minDay)) {//se la data è ok
               logprog=record.substring(index, index+=LENGTH_LOGPROGR);
               codabi=record.substring(index, index+=LENGTH_CODABI);
               codatm1=record.substring(index, index+=LENGTH_CODATM1);
              
               if (!codabi.equals(WRONG_CODABI)) {//codice abi corretto
                   int recorLength=record.length();
                   int resto=recorLength-114;
                   index+=LENGTH_FILLER;
                   
                   opecod=record.substring(index, index+=LENGTH_OPCOD);
                   codatm=record.substring(index, index+=LENGTH_CODATM);
                   openum=record.substring(index, index+=LENGTH_OPENUM);
                   opeDate=record.substring(index, index+=LENGTH_OPEDATE);
                   DateFormat opedateFormat=new SimpleDateFormat(OPEDATE_FORMAT);
                   dayLog=opedateFormat.parse(opeDate);
                   String scar;
                   String opehh="00";
                   String opemin="00";
                   String operest="";
                   if(dayLog.after(minDay)){//se opedate corretta
                       if(resto>=0){
                           switch(resto){
                               case 1: 
                                   scar=record.substring(index,index+=1);
                                   resto=0;
                                   break;
                               case 2:
                                   opehh=record.substring(index, index+=LENGTH_HOUR);
                                   resto=0;
                                   break;
                               case 3: 
                                   opehh=record.substring(index, index+=LENGTH_HOUR);
                                   scar=record.substring(index,index+=1);
                                   resto=0;
                                   break;
                               case 4:
                                   opehh=record.substring(index, index+=LENGTH_HOUR);
                                   opemin=record.substring(index, index+=LENGTH_MINUTE);
                                   resto=0;
                                   break;
                               default:
                                   if(resto<=154){
                                   opehh=record.substring(index, index+=LENGTH_HOUR);
                                   opemin=record.substring(index, index+=LENGTH_MINUTE);
                                   operest=record.substring(index);
                                   resto=0;
                                   }else{
                                    opehh=record.substring(index, index+=LENGTH_HOUR);
                                    opemin=record.substring(index, index+=LENGTH_MINUTE);
                                       
                                    try{
                                        operest=record.substring(index, index+=154);}
                                    catch(Exception e){
                                        
                                        System.out.println(recorLength+"-"+i+"-"+resto+"-"+index);
                                    }
                                    resto+=-154;
                                   }
                                   break;
                           }//end switch
                       }//end if 
                       opeDate="20"+opeDate.substring(4)+"-"+opeDate.substring(2, 4)+"-"+opeDate.substring(0, 2)
                               +" "+opehh+":"+opemin;
                       String operest2="";
                       if(resto>0){
                           if (resto<=150) {
                               operest2=record.substring(index);
                               resto=0;
                               } else {
                               operest2=record.substring(index, index+=150);
                               resto+=-150;
                           }
                       }
                   
                   
                   
                   }else{//se opedate errata
                       indexKO++;
                   }
                   
               } else { //codice abi errato
                   indexKO++;
               }
           }
           else{//se la data è errata
               System.out.println(record);//da eliminare
               indexKO++;
           }
           
            
        } else {
            System.out.println(record);//da eliminare
            indexKO++;
        }
       
    }
    
    public static void main(String[] args) throws ParseException {
         LogNCHParser parser=new LogNCHParser(FILE__LOG);
        //DateFormat dateFormat=new SimpleDateFormat(OPEDATE_FORMAT);
        //Date dayLog=dateFormat.parse("300615");
        //System.out.println(dayLog);
    }

}


