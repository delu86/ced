package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exporter.ExcelExporter;
import exporter.XlsxExporter;
import java.sql.SQLException;
import jxl.write.WriteException;
import utility.MapUtility;

/**
 * Servlet implementation class DailyDetailExcelServlet
 */
public class DailyDetailExcelServlet extends HttpServlet {
        private final static String TABLE_PARAMETER_STRING="$table_name";
	private static final long serialVersionUID = 1L;
	private static final String SUFFIX_FILE_NAME = "detail";
	private static final String EXCEL_EXTENSION = ".xls";
        private static final String EXCEL_XEXTENSION = ".xlsx";
	private static final String RESOURCE_DB_PATH = 	"datalayer.db";
        private static final String RESOURCE_DB_PATH_2 = 	"datalayer.db2";
	
        private static final String SELECT_TRANSACTION ="SELECT START_010 as data ,SYSTEM as Sistema,TRAN_001 as transazione,"
                + "USERID_089 as user,TOT as count,truncate(CPUTIME,3) as CPUtime,truncate(ELAPSED,3)as ELAPSED"
                + ",truncate(J8CPUT_260TM,3) as J8,truncate(KY8CPUT_263TM,3) as K8,truncate(L8CPUT_259TM,3) as L8,"
                + "truncate(MSCPUT_258TM,3) as MS,SCUSRHWM_106 as MEM,truncate(QRDISPT_255TM,3) as QR,"
                + "truncate(S8CPUT_261TM,3) as S8,DB2REQCT_180 as DB2call,ABCODEO_113 as ABEND_START,ABCODEC_114 as ABEND_END "
                + " FROM "+TABLE_PARAMETER_STRING+" where system=? and date(START_010)=? "
                + "order by START_010 ASC, TOT DESC";   
	
        //private static final String SELECT_BATCH="SELECT DATET10, SMF30JBN,SMF30STN,JESNUM,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,EXECTM,ELAPSED"+
        //    ",ZIPTM,CPUTIME FROM "+TABLE_PARAMETER_STRING+" where  SYSTEM=? and date(DATET10)=? order by DATET10 asc";
	//private static final String SELECT_STC="SELECT DATET10,SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,EXECTM,ELAPSED"+
        //    " ,ZIPTM,CPUTIME FROM "+TABLE_PARAMETER_STRING+" where  SYSTEM=? and date(DATET10)=? order by DATET10 asc";
	private static final String SELECT_JES="SELECT substr(BEGINTIME,1,19) as datetime,"
                + " SMF30JBN,SMF30STN,JESNUM,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,EXECTM,ELAPSED"
                + ",ZIPTM,CPUTIME ,  "
                + "CASE \n" +
                  "WHEN SMF30PGM='DFHSIP' THEN 'CICS'\n" +
                  "WHEN SMF30PGM='DSNYASCP'  THEN 'DB2'\n" +
                  "WHEN SMF30PGM='DXRRLM00'  THEN 'DB2'\n" +
                  "WHEN SMF30PGM='DSNX9STP'  THEN 'DB2'\n" +
                  "WHEN SMF30PGM='ASNLRP25'  THEN 'DB2'\n" +
                  "WHEN SMF30PGM='DSNX9WLM'  THEN 'DB2'\n" +
                  "WHEN SRVCLSNAME='SYSTEM'  THEN 'SYS'\n" +
                  "WHEN SMF30WID='TSO'       THEN 'TSO'\n" +
                  "WHEN SMF30WID='OMVS'       THEN 'OMVS'\n" +
                  "WHEN SMF30PGM='CSQXJST'   THEN 'MQS'\n" +
                  "WHEN SMF30PGM='CSQYASCP'  THEN 'MQS'\n" +
                  "WHEN SMF30WID LIKE 'JES%' THEN 'JOB'\n" +
                  "WHEN SMF30WID LIKE 'STC%' THEN 'STC'\n" +
                  "WHEN SMF30WID='ASCH'      THEN 'ASCH'\n" +
                  "ELSE 'OTHER'\n" +
                  "END as WKL_TYPE FROM "+TABLE_PARAMETER_STRING
                + " where  date(begintime)=?   order by 1 asc";
        
        private static final String JOBS="Jobs";
	private static final String STC="Started Task";
        private static final String SELECT_TRAN_CARIGE="SELECT `cicsdayh`.`SYSTEM`,\n" +
"    `cicsdayh`.`APPLVTNAME`,\n" +
"    `cicsdayh`.`EPVDATE` AS DATE,\n" +
"    `cicsdayh`.`EPVHOUR` AS HOUR,\n" +
"    `cicsdayh`.`EPV_SU`,\n" +
"    `cicsdayh`.`RDATE`,\n" +
"    `cicsdayh`.`GMTOFF`,\n" +
"    `cicsdayh`.`TOTCPUTM`,\n" +
"    `cicsdayh`.`TOTELAP`,\n" +
"    `cicsdayh`.`TOTIRESP`,\n" +
"    `cicsdayh`.`TOTL8CPU`,\n" +
"    `cicsdayh`.`CTRANS`,\n" +
"    `cicsdayh`.`CPUDSU`,\n" +
"    `cicsdayh`.`TOTIIPTM`,\n" +
"    `cicsdayh`.`TOTELGTM`\n" +
"FROM `mtrnd`.`cicsdayh` WHERE SYSTEM=? AND EPVDATE=?;";
        private static final String SELECT_JOB_CARIGE="SELECT `epv030_23_intrvl`.`READTIME`,\n" +
"    `epv030_23_intrvl`.`SMF30JBN`,\n" +
"    `epv030_23_intrvl`.`JESNUM`,\n" +
"    `epv030_23_intrvl`.`INITIALTIME`,\n" +
"    `epv030_23_intrvl`.`BEGINTIME`,\n" +
"    `epv030_23_intrvl`.`EPVDATE`,\n" +
"    `epv030_23_intrvl`.`EPVHOUR`,\n" +
"    `epv030_23_intrvl`.`SMF30CPI`,\n" +
"    `epv030_23_intrvl`.`IFATM`,\n" +
"    `epv030_23_intrvl`.`IFETM`,\n" +
"    `epv030_23_intrvl`.`CPUTIME`,\n" +
"    `epv030_23_intrvl`.`ZIETM`,\n" +
"    `epv030_23_intrvl`.`ZIPTM`,\n" +
"    `epv030_23_intrvl`.`SMF30CSC`,\n" +
"    `epv030_23_intrvl`.`IFASU`,\n" +
"    `epv030_23_intrvl`.`IFESU`,\n" +
"    `epv030_23_intrvl`.`ENDTIME`,\n" +
"    `epv030_23_intrvl`.`EPV_SU`,\n" +
"    `epv030_23_intrvl`.`SMF30LPI`,\n" +
"    `epv030_23_intrvl`.`SMF30PGI`,\n" +
"    `epv030_23_intrvl`.`SMF30PGO`,\n" +
"    `epv030_23_intrvl`.`SMF30BIA`,\n" +
"    `epv030_23_intrvl`.`SMF30BOA`,\n" +
"    `epv030_23_intrvl`.`SMF30PGM`,\n" +
"    `epv030_23_intrvl`.`SMF30CPM`,\n" +
"    `epv030_23_intrvl`.`SMF30ZNF`,\n" +
"    `epv030_23_intrvl`.`SMF30SNF`,\n" +
"    `epv030_23_intrvl`.`SMFTIME`,\n" +
"    `epv030_23_intrvl`.`SRVCLSNAME`,\n" +
"    `epv030_23_intrvl`.`SMF30WID`,\n" +
"    `epv030_23_intrvl`.`SYSPLEX`,\n" +
"    `epv030_23_intrvl`.`SYSTEM`,\n" +
"    `epv030_23_intrvl`.`ZIPSU`,\n" +
"    `epv030_23_intrvl`.`TYPETASK`,\n" +
"    `epv030_23_intrvl`.`DISKIO`,\n" +
"    `epv030_23_intrvl`.`TAPEIO`,\n" +
"    `epv030_23_intrvl`.`TOTLIO`,\n" +
"    `epv030_23_intrvl`.`FLAGXDD`,\n" +
"    `epv030_23_intrvl`.`SPLITDD`,\n" +
"    `epv030_23_intrvl`.`SMF30WLM`,\n" +
"    `epv030_23_intrvl`.`SMF30VPI`,\n" +
"    `epv030_23_intrvl`.`SMF30VPO`,\n" +
"    `epv030_23_intrvl`.`GROUP1`,\n" +
"    `epv030_23_intrvl`.`GROUP2`\n" +
"FROM `mdeta`.`epv030_23_intrvl` WHERE SYSTEM=? AND DATE(BEGINTIME)=?;";
        private static final String SELECT_RMF_CARIGE="SELECT `lparcpu`.`SERIAL`,\n" +
"    `lparcpu`.`GMTOFF`,\n" +
"    `lparcpu`.`EPVDATE` as Date,\n" +
"    `lparcpu`.`EPVHOUR` as Hour,\n" +
"    `lparcpu`.`DURTIME`,\n" +
"    `lparcpu`.`SMF70PDT`,\n" +
"    `lparcpu`.`SMF70LPM`,\n" +
"    `lparcpu`.`SMF70LPN`,\n" +
"    `lparcpu`.`MIPLPAR`,\n" +
"    `lparcpu`.`MIPS`,\n" +
"    `lparcpu`.`MSU`,\n" +
"    `lparcpu`.`EPVCIN`,\n" +
"    `lparcpu`.`REALCPU`,\n" +
"    `lparcpu`.`CPUBUSY`,\n" +
"    `lparcpu`.`PCTLPAR`,\n" +
"    `lparcpu`.`MVSBUSY`,\n" +
"    `lparcpu`.`RSYSTEM`,\n" +
"    `lparcpu`.`SMF70CIN`,\n" +
"    `lparcpu`.`SMF70CPA`,\n" +
"    `lparcpu`.`SMF70LAC`,\n" +
"    `lparcpu`.`SMF70MSU`,\n" +
"    `lparcpu`.`SMF70NSW`,\n" +
"    `lparcpu`.`SMF70WLA`,\n" +
"    `lparcpu`.`SWSU`,\n" +
"    `lparcpu`.`TOTDUR`,\n" +
"    `lparcpu`.`SMF70GMU`,\n" +
"    `lparcpu`.`SMF70GNM`,\n" +
"    `lparcpu`.`_INT_`,\n" +
"    `lparcpu`.`SMF70NCA`,\n" +
"    `lparcpu`.`SMF70MCR`,\n" +
"    `lparcpu`.`SMF70GAU`,\n" +
"    `lparcpu`.`MIPOVHD`,\n" +
"    `lparcpu`.`PCTOVHD`,\n" +
"    `lparcpu`.`MIPAAPCP`,\n" +
"    `lparcpu`.`MIPIIPCP`,\n" +
"    `lparcpu`.`PCTAAPCP`,\n" +
"    `lparcpu`.`PCTIIPCP`,\n" +
"    `lparcpu`.`RDATE`\n" +
"FROM `mtrnd`.`lparcpu` WHERE RSYSTEM=? AND EPVDATE=?;";
        
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DailyDetailExcelServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		String parameterSystem=request.getParameter("system");
		String parameterDate=request.getParameter("date");
		
		try {
                    if(parameterSystem.equals("ASDN")||parameterSystem.equals("ASSV"))
                    {
                        response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterDate+EXCEL_EXTENSION);
  			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH_2,new String[]{"Transactions",JOBS,"RECORD70"}
			,new String[]{SELECT_TRAN_CARIGE    ,
                            SELECT_JOB_CARIGE,SELECT_RMF_CARIGE
                            }
					,new String[]{parameterSystem,parameterDate},new String[]{parameterSystem,parameterDate},new String[]{parameterSystem,parameterDate});
                  
                }else{
                        String flag=request.getParameter("flag");
                        if(flag.equals("job")){
                            response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterDate+EXCEL_EXTENSION);
                        
                            ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH_2,new String[]{"JES"}    
			,new String[]{
                            SELECT_JES.replace(TABLE_PARAMETER_STRING, MapUtility.mapJesTable().get(parameterSystem)),
                            },new String[]{parameterDate});
                        
                        }
                        else{
                            response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+parameterSystem+"_"+parameterDate+EXCEL_XEXTENSION);
                         
                        /*
                        XlsxExporter.writeXLSX(response.getOutputStream(),RESOURCE_DB_PATH,new String[]{"Transactions",JOBS,STC}    
			,new String[]{
                            SELECT_TRANSACTION.replace(TABLE_PARAMETER_STRING, MapUtility.mapTransactionTable().get(parameterSystem)),
                            SELECT_BATCH.replace(TABLE_PARAMETER_STRING, MapUtility.mapBatchTable().get(parameterSystem)),
                            SELECT_STC.replace(TABLE_PARAMETER_STRING, MapUtility.mapSTCTable().get(parameterSystem))}
					,new String[]{parameterSystem,parameterDate},new String[]{parameterSystem,parameterDate},new String[]{parameterSystem,parameterDate});
                        */
                        XlsxExporter.writeXLSX(response.getOutputStream(),RESOURCE_DB_PATH_2,    
			
                            SELECT_TRANSACTION.replace(TABLE_PARAMETER_STRING, MapUtility.mapTransactionTable().get(parameterSystem)),
                         new String[]{parameterSystem,parameterDate});}
                    }} catch (IOException e) {
		} catch (WriteException e) {
                } catch (ClassNotFoundException e) {
                } catch (SQLException e) {
            }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
