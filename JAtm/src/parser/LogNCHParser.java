 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import static jatm.JAtm.logger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author CRE0260
 */
public class LogNCHParser extends AbstractATMParser{
    //codice di uscita del programma di parsing nel caso che il line abbia una lunghezza errata;
    private final static int WRONG_ERROR_LENGTH_EXIT_CODE=1234;
    private static final String INSERT_RECORD="REPLACE into atm_stat.logAtm_temp VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_A93_A94_RECORD="REPLACE into atm_stat.logAtm_temp_a93_a94 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final   HashMap<String,Integer> mapOpCodeAnomalie=initializeMapOpCodeAnomalie(); //la map restituisce 1 se il codice operazione indica una anomalia;
    private final   HashMap<String,Integer> mapOpCodeSbloccanti=initializeMapOpCodeSbloccanti();//la map restituisce 1 se il codice operazione può sbloccare uno stato di indisponibiltià dell'ATM
    private final   HashMap<String,Integer> mapAnomaliaIndisponibilità=initializeMapAnomaliaIndisponibilità();//la map restituisce 1 se il codice anomalia indica una anomalia che rende indisponibile l'ATM
    
    //codice banca non valido
    private static final String WRONG_CODABI="00000";
    //data minima per cui il line è valido
    private final String MIN_DATE="2004-01-02 00:00:00";
    

    //formato di validità 
    private static final String CHECK_CHR_FORMAT="\\d{4}";
    
    @Override
    protected void scanRecord(String line) throws ParseException, SQLException {
                   int index=0;//cursore all'interno del line;indica a che carattere siamo arrivati nella scansione del line
        if(line.substring(0,4).matches(CHECK_CHR_FORMAT)){
            //yyyyMMdd
            String logdate=line.substring(index,index+=4)
                 +"-"+line.substring(index,index+=2)+"-"+line.substring(index,index+=2);//data in cui è stato scritto il line nel log
            String oraLog=line.substring(index,index+=2);//data in cui è stato scritto il line nel log
            String minutiLog=line.substring(index,index+=2);//data in cui è stato scritto il line nel log
            String secondiLog=line.substring(index,index+=2);//data in cui è stato scritto il line nel log
            String logProgr=line.substring(index,index+=3);
            String codAbi=line.substring(index,index+=5); //index=22
            index+=74;//index=96 salta codice inutile (codAtm1) e filler
            DateFormat dateFormat=new SimpleDateFormat(LOGDATEFORMAT);
            Date dayLog=dateFormat.parse(logdate);
            Date minDay=dateFormat.parse(MIN_DATE);
         //   if(codAbi.equals("03032")||codAbi.equals("05385")){
            if(!codAbi.equals(WRONG_CODABI)&&dayLog.after(minDay)){
                //System.out.println(codAbi+" -->"+logdate+" "+ora+":"+minuti+":"+secondi);
                String opeCode=line.substring(index,index+=3);//index=99; codice operazione
                String codAtm=line.substring(index,index+=4);//index=103; codice identificativo atm
                String opeNum=line.substring(index,index+=5);//index=108; numero dell'operazione
                String opedate=line.substring(index,index+=6);//index=114 formato ddMMyy-->data dell'operazione
                dateFormat=new SimpleDateFormat(OPEDATE_FORMAT);
                Date dayOpe=dateFormat.parse(opedate);
                if(dayOpe.after(minDay)){
                    //System.out.println(logdate+" "+oraLOG+":"+minutilOG+":"+secondiLog+"---->"+opedate);
                    int resto=line.length()-index-2;//numero dei caratteri rimasti da parserizzare nel line attuale
                    //System.out.println(resto);
                   String scarto;//eventuale byte inutile
                   String opehh="00";//ora dell'operazione
                   String opemin="00";//minuti dell'operazione
                   String lineRest="";//parte rimanente del line
                    if(resto>=0){
                           switch(resto){
                               case 1: 
                                   scarto=line.substring(index,index+=1);//index=115
                                   
                                   break;
                               case 2:
                                   opehh=line.substring(index, index+=2);//index=116
                                   
                                   break;
                               case 3: 
                                   opehh=line.substring(index, index+=2);//index=116
                                   scarto=line.substring(index,index+=1);//index=117
                                   
                                   break;
                               case 4:
                                   opehh=line.substring(index, index+=2);//index=116
                                   opemin=line.substring(index, index+=2);//index=118
                                  
                                   break;
                               default:
                                   if(resto<=154){
                                   opehh=line.substring(index, index+=2);//index=116
                                   opemin=line.substring(index, index+=2);//index=118
                                   lineRest=line.substring(index);
                                   
                                   }else{
                                    opehh=line.substring(index, index+=2);//index=116
                                    opemin=line.substring(index, index+=2);//index=118
                                    lineRest=line.substring(index, index+=154);//index=272
                                    
                                  }
                                   break;
                           }//end switch
                       }//end if 
                    String logDateTime=logdate+" "+oraLog+":"+minutiLog+":"+secondiLog;
                    String opeDateTime="20"+opedate.substring(4)+"-"+opedate.substring(2, 4)+"-"+opedate.substring(0, 2)
                            +" "+opehh+":"+opemin+":00";
                    String codiceAnomalia="";
                    String codiceRepl="";
                    if(mapOpCodeAnomalie.containsKey(opeCode)){//il codice operazione corrisponde ad una anomalia?
                        if(opeCode.equals("E20")){
                            codiceAnomalia=lineRest.substring(11,13);
                            codiceRepl=lineRest.substring(13,20);
                        }else{
                            if (opeCode.equals("F20")) {
                                 codiceAnomalia=lineRest.substring(5,7);
                                 codiceRepl=lineRest.substring(7,14);
                            }
                            else{
                                codiceAnomalia=lineRest.substring(2,4);
                                codiceRepl=lineRest.substring(4,11);
                            }
                        }
                    }//fine verifica anomalia
                    String disponibilità="=";
                    
                    if(codiceAnomalia.equals("")){
                        if(mapOpCodeSbloccanti.containsKey(opeCode))
                            disponibilità="0";
                        if(opeCode.equals("A94")){
                            String stato_a94;
                            stato_a94=lineRest.substring(7,8);
                            if(!stato_a94.equals("0")){
                                disponibilità="0";
                                 if (stato_a94.equals("1"))  disponibilità = "2";
                                 if (stato_a94.equals("2"))  disponibilità = "2";
                                 if (stato_a94.equals("3"))  disponibilità = "2";
                                     if (stato_a94.equals("4"))  disponibilità = "1";
                          } 
                           int stato_tastiera=Integer.parseInt(lineRest.substring(9,10));
                           int stato_lettore_badge=Integer.parseInt(lineRest.substring(10,11));
                           int stato_dispensatore=Integer.parseInt(lineRest.substring(11,12));
                           int stato_modulo_cifratura=Integer.parseInt(lineRest.substring(16,17));
                           if(disponibilità.equals("0")&&(
                                   stato_tastiera>=2||stato_lettore_badge>=2||stato_dispensatore>=2||stato_modulo_cifratura>=2))
                               disponibilità="1";
                        } }else{
                        if(mapAnomaliaIndisponibilità.containsKey(codiceAnomalia)){
                            disponibilità=String.valueOf(mapAnomaliaIndisponibilità.get(codiceAnomalia));
                            
                        }}
                    if(!disponibilità.equals("=")&&
                            !disponibilità.equals("1")&&
                            !disponibilità.equals("2")&&
                            !disponibilità.equals("3")&&
                            !disponibilità.equals("4"))
                            disponibilità="0";
                    //System.out.println(disponibilità);
                    String causale="";
                    String stato_sa="";
                    String esito="";
                    String stato_periferiche="";
                    if(opeCode.equals("A93")){
                        causale     = lineRest.substring(1,4);
                        
                    }
                    if(opeCode.equals("A94")){
                        causale     = lineRest.substring(4,7);
                        stato_sa     = lineRest.substring(7,8);
                        esito     = lineRest.substring(8,9);
                        stato_periferiche     = lineRest.substring(9,27);
                    }
                    
                    if(!(opeCode.equals("A94")||opeCode.equals("A93"))){
                        if(!opeCode.equals("A98"))
                            saveRecord(INSERT_RECORD,null,opeDateTime,opeDateTime,logDateTime,codAbi,
                            opeCode, opeNum,codAbi+"-"+codAtm, disponibilità,
                            codiceAnomalia, codiceRepl, logProgr ,causale,stato_sa,esito,stato_periferiche,
                            null,null);
                        else
                            saveRecord(INSERT_RECORD,null,logDateTime,opeDateTime,logDateTime,codAbi,
                            opeCode, opeNum,codAbi+"-"+codAtm, disponibilità,
                            codiceAnomalia, codiceRepl, logProgr ,causale,stato_sa,esito,stato_periferiche,
                            null,null);
                    }
                    
                    else
                            saveRecord(INSERT_A93_A94_RECORD,null,logDateTime,opeDateTime,logDateTime,codAbi,
                            opeCode, opeNum,codAbi+"-"+codAtm, disponibilità,
                            codiceAnomalia, codiceRepl, logProgr ,causale,stato_sa,esito,stato_periferiche,
                            null,null);
                    indexOk++;
                    
                }else{//data operazione minore di mindate
                    indexKo++;
                   logger.log(Level.SEVERE, "data operazione minore di mindate--> {0}",line);
                }
                
            }
            else{//data inferiore a mindate o codice abi errato
                indexKo++;
                logger.log(Level.SEVERE, "data inferiore a mindate o codice abi errato--> {0}",line);
            }
    //    }
        }else{//check line errato
            indexKo++;
            logger.log(Level.SEVERE, "check line errato--> {0}",line);
        }
    }
    
     private HashMap<String,Integer> initializeMapOpCodeAnomalie() {
       HashMap<String,Integer> map=new HashMap<>();
        map.put("A20", 1);
        map.put("D20", 1);
        map.put("E20", 1);
        map.put("F20", 1);
        map.put("G20", 1);
        map.put("I20", 1);
        map.put("U20", 1);
        return map;
    }

    private HashMap<String,Integer> initializeMapOpCodeSbloccanti() {
        HashMap<String,Integer> map=new HashMap<>();
        map.put("A72", 1);
        map.put("A98", 1);
        map.put("A94", 1);
        map.put("E72", 1);
        map.put("F72", 1);
        map.put("G72", 1);
        map.put("I72", 1);
        map.put("W72", 1);
        return map;
    }

    private HashMap<String,Integer> initializeMapAnomaliaIndisponibilità() {
        HashMap<String,Integer> map=new HashMap<>();
        // 1 indisponibilità HW
        // 2 indisponibilità Gestore(banca)
        // 3 indisponibilità altra causa
        // 4 indisponibilità Cedacri
        map.put("19", 1);
        map.put("35", 2);
        map.put("40", 2);
        map.put("44", 1);
        map.put("45", 1);
        map.put("46", 1);
        map.put("47", 1);
        map.put("48", 1);
        map.put("49", 1);
        map.put("51", 1);
        map.put("54", 2);
        map.put("55", 1);
        map.put("53", 1);
        map.put("56", 1);
        map.put("A0", 2);
        return map;
    }
        /*
            CallableStatement callCreateBackup=connection.prepareCall("call atm_stat.createBackup");
            CallableStatement callNormalizzaA93_A94=connection.prepareCall("call atm_stat.coupleA93_A94");
            CallableStatement calllogAtmRecordDispatcher=connection.prepareCall("call atm_stat.logAtmRecordDispatcher");
            System.out.println("Normalizza A93-A94");
            callNormalizzaA93_A94.executeQuery();
            System.out.println("Dispatcher ");
            calllogAtmRecordDispatcher.executeQuery();
        */


}
        