/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accenturemonthlysteps;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import jxl.write.WriteException;

/**
 *
 * @author CRE0260
 */
public class AccentureMonthlySteps {

        private static final String SELECT="SELECT CASE when SYSTEM='ESYA' THEN 'TEST'\n" +
    "     	 WHEN S YSTEM='GSY7' THEN '^^^' else 'PROD' end as AMBIENTE, SYSTEM, TYPETASK as Tipo_task,\n" +
    "    		 date(ENDTIME) as data,concat(substr(ENDTIME,12,4),\"0\") as ORA,\n" +
    "    		 SMF30JBN as JOBNAME,SMF30JNM as JOBNUMBER,SMF30STM as STEPNAME,SMF30STN as STEPNUMBER,SMF30PSN\n" +
    "    		 as PROC_STEP,SMF30PGM as PROGRAM_NAME,SMF30RUD as USER,CPUTIME\n" +
    "    		 ,CASE WHEN system='ESYA' or system='ZSY5' THEN CPUTIME*1384.7/600  \n" +
    "             ELSE CPUTIME*1007.6/600 end as MIPS,SMF30SRV as SERVICE_UNITS,SMF30TEX as TOT_IO from smfacc.epv030_4_step_accenture\n" +
    "    		 where date(ENDTIME)=? order by 4 , 1, 2  ";  
	private static final String SUFFIX_FILE_NAME = "accentureDetail";
	private static final String  EXCEL_EXTENSION= ".xls";
	private static final String RESOURCE_PATH = "accenturemonthlysteps.db";
        private static final String FILENAME="accenture";
        private FileOutputStream stream;
        private static final String LOG_NAME="log_accenture";
        public static final Logger logger=Logger.getLogger("MY log");
        
        public AccentureMonthlySteps(String path, String period){
            try {
                FileHandler fh=new FileHandler(path+LOG_NAME+period);
                stream=new FileOutputStream(path+FILENAME+EXCEL_EXTENSION);
                String month=period.substring(5);
                String year=period.substring(0,4);
                logger.addHandler(fh);
                logger.setUseParentHandlers(false);//RIMUOVE LA SCRITTURA A CONSOLE
                SimpleFormatter formatter=new SimpleFormatter();
                fh.setFormatter(formatter);
                logger.log(Level.INFO, "Start creating workbook for Accenture: {0}", period);
                String arraySelect[];
                String arrayParameter[][];
                switch(Integer.valueOf(month)){
                        case 1:
                            arraySelect=new String[31];
                            break;
                        case 2: 
                            if(Integer.valueOf(year)%4==0)
                            arraySelect=new String[29];
                            else
                                arraySelect=new String[28];
                            break;
                        case 3: 
                            arraySelect=new String[31];
                            break;
                        case 4: 
                            arraySelect=new String[30];
                            break;
                        case 5: 
                            arraySelect=new String[31];
                            break;
                        case 6: 
                            arraySelect=new String[30];
                            break;
                        case 7: 
                            arraySelect=new String[31];
                            break;
                        case 8: 
                            arraySelect=new String[31];
                            break;
                        case 9: 
                            arraySelect=new String[30];
                            break;
                        case 10: 
                            arraySelect=new String[31];
                            break;
                        case 11: 
                            arraySelect=new String[30];
                            break;
                        case 12: 
                            arraySelect=new String[31];
                            break;
                        default: throw new WrongDataException();
                                }
                arrayParameter=new String[arraySelect.length][1];
                String arrayTitle[]=new String[arraySelect.length];
                int i=0;
                while (i<arraySelect.length) {
                    arraySelect[i]=SELECT;
                    arrayTitle[i]=period+"-"+String.valueOf(i+1);
                    arrayParameter[i][0]=period+"-"+String.valueOf(i+1);
                    i++;
                     }
                ExcelExporter.getExcelFromDbQuery(stream,RESOURCE_PATH, arrayTitle, arraySelect,arrayParameter);
                logger.log(Level.INFO, "FINISH without error");
                
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                logger.log(Level.SEVERE, "Errore: {0}", ex.getMessage());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Errore: {0}", ex.getMessage());
            } catch (WriteException | SQLException | ClassNotFoundException ex) {
                logger.log(Level.SEVERE, "Errore: {0}", ex.getMessage());
            }
            catch (WrongDataException ex){
                logger.log(Level.SEVERE, "Errore data non valida");
            }
            
        }
    /**
     * @param args the command line arguments
     * args[0] yyyy-MM
     * args[1] path
     */
    public static void main(String[] args) {
            AccentureMonthlySteps accentureMonthlySteps = new AccentureMonthlySteps(args[1], args[0]);
        
    }
    
}
