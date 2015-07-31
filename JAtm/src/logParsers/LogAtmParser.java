/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logParsers;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CRE0260
 */
public class LogAtmParser {
    
    //elementi per connessione al database
    private Connection connection=null;
    private PreparedStatement statement=null;
    private final String DB_RESOURCE_PATH="logParsers.database";
    private static final String INSERT_RECORD="INSERT into atm.logAtm VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    //cartella dove viene prelevato il log
    private final static String LOG_FOLDER_PATH="C:\\Users\\cre0260\\Desktop\\ATM\\";
    
    //codice di uscita del programma di parsing nel caso che il record abbia una lunghezza errata;
    private final static int WRONG_ERROR_LENGTH_EXIT_CODE=1234;
    
    //mappe utili
    private   HashMap<String,Integer> mapOpCodeAnomalie; //la map restituisce 1 se il codice operazione indica una anomalia;
    private   HashMap<String,Integer> mapOpCodeSbloccanti;//la map restituisce 1 se il codice operazione può sbloccare uno stato di indisponibiltià dell'ATM
    private   HashMap<String,Integer> mapAnomaliaIndisponibilità;//la map restituisce 1 se il codice anomalia indica una anomalia che rende indisponibile l'ATM
    /**
     *formato dei datetime
     */
    private final static String DATETIME_FORMAT="yyyy-MM-dd hh:mm:ss";
    private final static String LOGDATEFORMAT="yyyy-MM-dd";
    private final static String OPEDATE_FORMAT="ddMMyy";
    
    
    //codice banca non valido
    private static final String WRONG_CODABI="00000";
    //data minima per cui il record è valido
    private final String MIN_DATE="2004-01-02 00:00:00";
    
    private int indexOk=0;//numero di record corretti parserizzati
    private int indexKo=0;//numero di record errati trovati
    //formato di validità 
    private static final String CHECK_CHR_FORMAT="\\d{4}";
    
    public LogAtmParser(String fileLogName){
        initializeMapAnomaliaIndisponibilità();
        initializeMapOpCodeAnomalie();
        initializeMapOpCodeSbloccanti();
        File fileLog=new File(LOG_FOLDER_PATH+fileLogName);
        try{
            connectDB();
        }catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        }
          try (Scanner input = new Scanner(fileLog)) {
            //inizia la scansione del log
            while(input.hasNext()){
                scanRecord(input.nextLine());
                   }//termine scansione del file
            executesStoredProcedure();
        } catch (FileNotFoundException ex) {//file non trovato
            Logger.getLogger(LogFaroParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {//eccezione nel parser
            Logger.getLogger(LogParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{//disconnessione dal database
            disconnectDB();
              System.out.println("Record ok: "+indexOk);
              System.out.println("Record ko:"+indexKo);
            System.out.println("Connection close! BYE ^^");//da eliminare
        }
    }

    //scansiona un record del log
    private void scanRecord(String record) throws ParseException{
        int index=0;//cursore all'interno del record;indica a che carattere siamo arrivati nella scansione del record
        if(record.substring(0,4).matches(CHECK_CHR_FORMAT)){
            //yyyyMMdd
            String logdate=record.substring(index,index+=4)
                 +"-"+record.substring(index,index+=2)+"-"+record.substring(index,index+=2);//data in cui è stato scritto il record nel log
            String oraLog=record.substring(index,index+=2);//data in cui è stato scritto il record nel log
            String minutiLog=record.substring(index,index+=2);//data in cui è stato scritto il record nel log
            String secondiLog=record.substring(index,index+=2);//data in cui è stato scritto il record nel log
            String logProgr=record.substring(index,index+=3);
            String codAbi=record.substring(index,index+=5); //index=22
            index+=74;//index=96 salta codice inutile (codAtm1) e filler
            DateFormat dateFormat=new SimpleDateFormat(LOGDATEFORMAT);
            Date dayLog=dateFormat.parse(logdate);
            Date minDay=dateFormat.parse(MIN_DATE);
            if(!codAbi.equals(WRONG_CODABI)&&dayLog.after(minDay)){
                //System.out.println(codAbi+" -->"+logdate+" "+ora+":"+minuti+":"+secondi);
                String opeCode=record.substring(index,index+=3);//index=99; codice operazione
                String codAtm=record.substring(index,index+=4);//index=103; codice identificativo atm
                String opeNum=record.substring(index,index+=5);//index=108; numero dell'operazione
                String opedate=record.substring(index,index+=6);//index=114 formato ddMMyy-->data dell'operazione
                dateFormat=new SimpleDateFormat(OPEDATE_FORMAT);
                Date dayOpe=dateFormat.parse(opedate);
                if(dayOpe.after(minDay)){
                    //System.out.println(logdate+" "+oraLOG+":"+minutilOG+":"+secondiLog+"---->"+opedate);
                    int resto=record.length()-index-2;//numero dei caratteri rimasti da parserizzare nel record attuale
                    //System.out.println(resto);
                   String scarto;//eventuale byte inutile
                   String opehh="00";//ora dell'operazione
                   String opemin="00";//minuti dell'operazione
                   String recordRest="";//parte rimanente del record
                    if(resto>=0){
                           switch(resto){
                               case 1: 
                                   scarto=record.substring(index,index+=1);//index=115
                                   
                                   break;
                               case 2:
                                   opehh=record.substring(index, index+=2);//index=116
                                   
                                   break;
                               case 3: 
                                   opehh=record.substring(index, index+=2);//index=116
                                   scarto=record.substring(index,index+=1);//index=117
                                   
                                   break;
                               case 4:
                                   opehh=record.substring(index, index+=2);//index=116
                                   opemin=record.substring(index, index+=2);//index=118
                                  
                                   break;
                               default:
                                   if(resto<=154){
                                   opehh=record.substring(index, index+=2);//index=116
                                   opemin=record.substring(index, index+=2);//index=118
                                   recordRest=record.substring(index);
                                   
                                   }else{
                                    opehh=record.substring(index, index+=2);//index=116
                                    opemin=record.substring(index, index+=2);//index=118
                                    recordRest=record.substring(index, index+=154);//index=272
                                    
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
                            codiceAnomalia=recordRest.substring(11,13);
                            codiceRepl=recordRest.substring(13,20);
                        }else{
                            if (opeCode.equals("F20")) {
                                 codiceAnomalia=recordRest.substring(5,7);
                                 codiceRepl=recordRest.substring(7,14);
                            }
                            else{
                                codiceAnomalia=recordRest.substring(2,4);
                                codiceRepl=recordRest.substring(4,11);
                            }
                        }
                    }//fine verifica anomalia
                    String disponibilità="=";
                    String stato_a94="";
                    //verifica la disponiblità dell'atm(in servizio o fuori servizio
                    //'='=indifferente
          //        '1'=disponibile (in servizio)
         //*      altro=non disponibile (fuori servizio)
         //*        di cui '2'=problemi sistema
         //*               '3'=problemi gestore
         //*               '4'=problemi hardware
         //*               '0'=cause diverse non codificate;
                    if(codiceAnomalia.equals("")){
                        if(mapOpCodeSbloccanti.containsKey(opeCode))
                            disponibilità="1";
                        if(opeCode.equals("A94")){
                            stato_a94=recordRest.substring(7,8);
                            if(!stato_a94.equals("0")){
                                disponibilità="0";
                                 if (stato_a94.equals("1"))  disponibilità = "2";
                                 if (stato_a94.equals("2"))  disponibilità = "3";
                                 if (stato_a94.equals("3"))  disponibilità = "3";
                                 if (stato_a94.equals("4"))  disponibilità = "4";
                          } } }else{
                        if(mapAnomaliaIndisponibilità.containsKey(codiceAnomalia)){
                            disponibilità=String.valueOf(mapAnomaliaIndisponibilità.get(codiceAnomalia));
                            if(disponibilità.equals("3")) disponibilità="4";
                            if(disponibilità.equals("2")) disponibilità="3";
                            if(disponibilità.equals("1")) disponibilità="2";
                            
                        }}
                    if(!disponibilità.equals("=")&&
                            !disponibilità.equals("1")&&
                            !disponibilità.equals("2")&&
                            !disponibilità.equals("3")&&
                            disponibilità.equals("4"))
                            disponibilità="0";
                    //System.out.println(disponibilità);
                    String causale="";
                    String stato_sa="";
                    String esito="";
                    String stato_periferiche="";
                    if(opeCode.equals("A93")){
                        causale     = recordRest.substring(1,4);
                        
                    }
                    if(opeCode.equals("A94")){
                        causale     = recordRest.substring(4,7);
                        stato_sa     = recordRest.substring(7,8);
                        esito     = recordRest.substring(8,9);
                        stato_periferiche     = recordRest.substring(9,27);
                    }
                    try {
                        salvaRecord(logDateTime,opeDateTime,codAbi,codAtm,
                                opeCode, opeNum,codiceAnomalia, logProgr,
                                causale, stato_sa, esito ,stato_periferiche,codiceRepl,disponibilità);
                    } catch (SQLException ex) {
                        Logger.getLogger(LogAtmParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    indexOk++;
                    
                }else{//data operazione minore di mindate
                    indexKo++;
                    System.out.println("data operazione minore di mindate-->"+record);
                }
                
            }
            else{//data inferiore a mindate o codice abi errato
                indexKo++;
                System.out.println("data inferiore a mindate o codice abi errato-->"+record);
            }
        }else{//check record errato
            indexKo++;
            System.out.println("check record errato-->"+record);
        }
        
    }
    
    //crea connessione con il database
    private void connectDB() throws ClassNotFoundException, SQLException{
        ResourceBundle resourceBundle=ResourceBundle.getBundle(DB_RESOURCE_PATH); 
        Class.forName(resourceBundle.getString("driver"));
        connection=DriverManager.getConnection(resourceBundle.getString("url"),resourceBundle.getString("user"),resourceBundle.getString("password"));
    }

    private void disconnectDB() {
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

    private void initializeMapOpCodeAnomalie() {
        mapOpCodeAnomalie=new HashMap<>();
        mapOpCodeAnomalie.put("A20", 1);
        mapOpCodeAnomalie.put("D20", 1);
        mapOpCodeAnomalie.put("E20", 1);
        mapOpCodeAnomalie.put("F20", 1);
        mapOpCodeAnomalie.put("G20", 1);
        mapOpCodeAnomalie.put("I20", 1);
        mapOpCodeAnomalie.put("U20", 1);
    }

    private void initializeMapOpCodeSbloccanti() {
        mapOpCodeSbloccanti=new HashMap<>();
        mapOpCodeSbloccanti.put("A34", 1);
        mapOpCodeSbloccanti.put("A50", 1);
        mapOpCodeSbloccanti.put("A52", 1);
        mapOpCodeSbloccanti.put("A60", 1);
        mapOpCodeSbloccanti.put("A72", 1);
        mapOpCodeSbloccanti.put("A74", 1);
        mapOpCodeSbloccanti.put("A76", 1);
        mapOpCodeSbloccanti.put("A84", 1);
        mapOpCodeSbloccanti.put("A94", 1);
        mapOpCodeSbloccanti.put("A98", 1);
        mapOpCodeSbloccanti.put("E72", 1);
        mapOpCodeSbloccanti.put("F72", 1);
        mapOpCodeSbloccanti.put("G72", 1);
        mapOpCodeSbloccanti.put("I72", 1);
        mapOpCodeSbloccanti.put("I74", 1);
        mapOpCodeSbloccanti.put("I76", 1);
        mapOpCodeSbloccanti.put("S74", 1);
        mapOpCodeSbloccanti.put("P78", 1);
        mapOpCodeSbloccanti.put("W72", 1);
    }

    private void initializeMapAnomaliaIndisponibilità() {
        mapAnomaliaIndisponibilità=new HashMap<>();
        mapAnomaliaIndisponibilità.put("19", 3);
        mapAnomaliaIndisponibilità.put("35", 3);
        mapAnomaliaIndisponibilità.put("40", 3);
        mapAnomaliaIndisponibilità.put("44", 3);
        mapAnomaliaIndisponibilità.put("45", 3);
        mapAnomaliaIndisponibilità.put("46", 3);
        mapAnomaliaIndisponibilità.put("47", 3);
        mapAnomaliaIndisponibilità.put("48", 2);
        mapAnomaliaIndisponibilità.put("49", 2);
        mapAnomaliaIndisponibilità.put("51", 3);
        mapAnomaliaIndisponibilità.put("54", 3);
        mapAnomaliaIndisponibilità.put("55", 3);
        mapAnomaliaIndisponibilità.put("53", 3);
        mapAnomaliaIndisponibilità.put("56", 3);
    }


    private void salvaRecord(String logDateTime, String opeDateTime, String codAbi, String codAtm, String opeCode, String opeNum, String codiceAnomalia, String logProgr, String causale, String stato_sa, String esito, String stato_periferiche, String codiceRepl, String disponibilità) throws SQLException {
       
        statement=connection.prepareStatement(INSERT_RECORD);
        if(!(opeCode.equals("A94")||opeCode.equals("A93"))){
        statement.setString(1, opeDateTime);
        statement.setString(2, opeDateTime);
        statement.setString(3, logDateTime);
        statement.setString(4, codAbi);
        statement.setString(5, opeCode);
        statement.setString(6, opeNum);
        statement.setString(7, codAbi+"-"+codAtm);
        statement.setString(8, disponibilità);
        statement.setString(9, codiceAnomalia);
        statement.setString(10, codiceRepl);
        statement.setString(11, logProgr);
        statement.setString(12, causale);
        statement.setString(13, stato_sa);
        statement.setString(15, stato_periferiche);
        statement.setString(14, esito);
        statement.setString(16, null);
        statement.setString(17, null);
        statement.executeUpdate();}
        else{
        statement.setString(1, logDateTime);
        statement.setString(2, opeDateTime);
        statement.setString(3, logDateTime);
        statement.setString(4, codAbi);
        statement.setString(5, opeCode);
        statement.setString(6, opeNum);
        statement.setString(7, codAbi+"-"+codAtm);
        statement.setString(8, disponibilità);
        statement.setString(9, codiceAnomalia);
        statement.setString(10, codiceRepl);
        statement.setString(11, logProgr);
        statement.setString(12, causale);
        statement.setString(13, stato_sa);
        statement.setString(15, stato_periferiche);
        statement.setString(14, esito);
        statement.setString(16, null);
        statement.setString(17, null);
        statement.executeUpdate();
        
       }
        statement.close();
       
       
    }
      

    private void executesStoredProcedure() {
        try {
            CallableStatement callNormalizzaA93_A94=connection.prepareCall("call atm.normalizzaA93_A94");
            CallableStatement call294Record=connection.prepareCall("call atm.get294Log");
            CallableStatement callB25Record=connection.prepareCall("call atm.accoppiaB24B25");
            System.out.println("Normalizzazione a93 a94");
            callNormalizzaA93_A94.executeQuery();
            System.out.println("Normalizzazione 294");
            call294Record.executeQuery();
            System.out.println("Normalizzazione b25");
            callB25Record.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(LogAtmParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public static void main(String[] args) throws ParseException {
        try{
        
        LogFaroParser parser2=new LogFaroParser("ATM/LOGSIA.FARONCH.BTD.V6032.txt");
        LogFaroParser parser1=new LogFaroParser("ATM/LOGSIA.FARONCH.BTD.V6033.txt");
        LogAtmParser  parser3 =new LogAtmParser("ATM/LOGATM.20150715.NCH.txt");
        }
            catch(Exception e){
                e.printStackTrace();
        }
        
    }

    
    
}
