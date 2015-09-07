/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author CRE0260
 */
public class LogFaroNCHParser extends AbstractATMParser{
    
    
    private static final String INSERT_RECORD_OK_TABLE="REPLACE into atm_stat.log_faro_nch_temp VALUES"
            + "(?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_RECORD_SCARTI="INSERT into atm_stat.atm_faro_log_scarti VALUES (?,?,?)";
    /*
    Lunghezza dei campi che si trovano all'interno del log
    */
    private static final int LENGTH_CODABI=5;
    private static final int LENGTH_ABI_CA=5;
    private static final int LENGTH_MSG=3;
    private static final int LENGTH_LOGDATE=8; //Formato data:yyyyMMdd
    private static final int LENGTH_HOUR=2;
    private static final int LENGTH_MINUTE=2;
    private static final int LENGTH_SECOND=2;
    private static final int LENGTH_PR=1;
    private static final int LENGHT_BLANK_SPACES=22;
    private static final int LENGTH_ST=2;
    private static final int LENGTH_COD_ATM=4;
    private static final int LENGTH_CAB_ATM=5;
    private static final int LENGTH_A94=30;
    
    private static final String COD_ABI_PATTERN="\\d{5}"; //tutti i caratteri del codice ABI devono essere numerici
    private static final String WRONG_CODABI="00000";
    private static final String MIN_DATE="2004-01-02 00:00:00";
    private static final String A94_STRING="A94";
    

    @Override
    protected void scanRecord(String line) throws ParseException, SQLException{
        //System.out.println("Ciao");
        String codAbi,abiCA,msg,date,hour,minute,second,codAtm,cabATM,A94,pr,st,dataOraMSG = "";
         int numMSG = 0;
         int index=0;
         
                int error;
                codAbi=line.substring(index, index+=LENGTH_CODABI);
                if(codAbi.matches(COD_ABI_PATTERN)){//codice istituto corretto
                    abiCA=line.substring(index, index+=LENGTH_ABI_CA);
                    msg=line.substring(index,index+=LENGTH_MSG);
                    date=line.substring(index,index+=4)
                            +"-"+line.substring(index,index+=2)+"-"+line.substring(index,index+=2);                 
                    hour=line.substring(index,index+=LENGTH_HOUR);
                   
                    minute=line.substring(index,index+=LENGTH_MINUTE);
                    second=line.substring(index,index+=LENGTH_SECOND);
                    date=date+" "+hour+":"+minute+":"+second;
                    pr=line.substring(index,index+=LENGTH_PR);
                    index+=LENGHT_BLANK_SPACES;
                    st=line.substring(index,index+=LENGTH_ST);
                    codAtm=line.substring(index,index+=LENGTH_COD_ATM);
                    cabATM=line.substring(index,index+=LENGTH_CAB_ATM);
                    A94=line.substring(index,index+=LENGTH_A94);
                    //System.out.println(A94);
                    if(!codAbi.equals(WRONG_CODABI)){
                        DateFormat dateFormat=new SimpleDateFormat(DATETIME_FORMAT);
                        Date dayLog=dateFormat.parse(date);
                        if(dayLog.after(dateFormat.parse(MIN_DATE))){
                            A94=A94.replaceAll("\\x00", "");
                            if(A94.substring(0, 3).equals(A94_STRING)){
                                
                                if(A94.substring(7,12).matches(COD_ABI_PATTERN))
                                    numMSG=Integer.parseInt(A94.substring(7,12));
                                dataOraMSG="20"+A94.substring(16,18)+"-"+A94.substring(14,16)+"-"+A94.substring(12,14)+" "+A94.substring(18,20)+
                                      ":"+  A94.substring(20,22)+":"+A94.substring(22,24);
                            }
                            
                            saveRecord(INSERT_RECORD_OK_TABLE, String.valueOf(indexOk),date,dataOraMSG,codAbi,codAtm,msg,pr,st,String.valueOf(numMSG),A94);
                            indexOk++;
                            
                        }
                        else{//data inferiore al 02 Gennaio 2004
                            error=3;
                            indexKo++;
                            saveRecord(INSERT_RECORD_SCARTI,filename, line, String.valueOf(error));
                        }
                    }
                    else{//codice istituto = '00000'
                        error=2;
                        indexKo++;
                        saveRecord(INSERT_RECORD_SCARTI,filename, line, String.valueOf(error));
                    }
                }
                else{//codice istituto errato
                             error=1;
                             indexKo++;
                             saveRecord(INSERT_RECORD_SCARTI,filename, line, String.valueOf(error));
                }
    }

    
}
