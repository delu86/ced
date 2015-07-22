package datalayer;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import object.BatchReport;
import object.CECReport;
import object.CPUReport;
import object.MQQuequeReport;
import object.SondaWorkloadInterval;
import object.StepReport;
import object.SystemConsumptionsReport;
import object.SystemHourReport;
import object.SystemReport;
import object.TapeReport;
import object.TransactionReport;
import object.User;
import object.VolumeSMF;
import object.VolumeTimeInformation;
import object.WorkloadInterval;
import utility.PasswordUtility;
import utility.UtilityDate;

public class DatabaseManager {
	
	public static String UPDATE_LAST_ACCESS="UPDATE smfacc.users  SET lastAccess=? where email=?  ";

	
	private final static String TABLE_PARAMETER_STRING="$table_name";
	
	private final static String SELECT_VOLUMI_SMF="SELECT concat(\"20\",SUBSTRING(Q3,3,9)) , CAST(sum(size) / 1000000000 as UNSIGNED INTEGER)FROM `support`.`nfs`"
			+ "GROUP BY SUBSTRING(Q3,3,9)  ORDER BY concat(\"20\",SUBSTRING(Q3,3,9));";

	private final static String SELECT_SONDA_WORKLOAD="SELECT time, CASE WHEN sum(t2.cputime)>10 THEN 'GREEN'"
			+ "	WHEN sum(t2.cputime)<10 and sum(t2.cputime)>1 THEN 'YELLOW' ELSE 'RED' END AS SEMAPHORE"
			+ " FROM smfacc.interval_workload as t1 left join "+TABLE_PARAMETER_STRING
			+ " as t2 on t1.time=substr(t2.DATA_INT10,12,5) and t2.SYSTEM=? and date(t2.DATA_INT10)=? group by time";
	
	
	private final static String SELECT_ERROR_COUNT_WKL="SELECT count(*) from(SELECT time, CASE WHEN sum(t2.cputime)>10 THEN 'GREEN'"
			+ "	WHEN sum(t2.cputime)<10 and sum(t2.cputime)>1 THEN 'YELLOW' ELSE 'RED' END AS SEMAPHORE"
			+ " FROM smfacc.interval_workload as t1 left join "+TABLE_PARAMETER_STRING
			+ " as t2 on t1.time=substr(t2.DATA_INT10,12,5) and t2.SYSTEM=? and date(t2.DATA_INT10)=? group by time) as derived"
			+ " where derived.semaphore='RED' or derived.semaphore='YELLOW'";
	private final static String SELECT_WLC_BY_MONTH=" SELeCT EPVDATE,SERIAL,MAX(CAST(MSU4hra AS unsigned INTEGER)) FROM("
			+ " SELECT EPVDATE,SERIAL,EPVHOUR,SUM(MSU4hra) as MSU4hra from("
			+ " SELECT EPVDATE,EPVHOUR,SERIAL,SMF70GNM,SMF70GMU, ((CASE"
			+ " WHEN SMF70GAU<=0 then SMF70GMU else SUM(SMF70LAC) end )) as MSU4hra from mtrnd.lparcpu"
			+ " where EPVDATE>? and EPVDATE<=?"
			+ " group by EPVDATE,EPVHOUR,SERIAL,SMF70GNM) as derived"
			+ " group by EPVDATE,SERIAL,EPVHOUR) AS DERIVED GROUP BY EPVDATE,SERIAL order by SERIAL, EPVDATE ASC";
	private final static String SELECT_SYSTEM_CONSUMPTION_LAST30_DAY="SELECT t1.EPVDATE,t1.EPVHOUR, TRUNCATE(t1.MIPLPAR,2),TRUNCATE(t1.SMF70LAC,2)"
			+ " FROM mtrnd.lparcpu as t1 "
			+ " where t1.RSYSTEM=? and datediff(current_date,t1.epvdate)<=30"
			+ " order by t1.EPVDATE ASC, t1.EPVHOUR ASC;";
	private final static String SELECT_SYSTEM_CONSUMPTION_ZIIP_LAST30_DAY="SELECT t1.EPVDATE,t1.EPVHOUR, TRUNCATE(t1.MIPLPAR,2)"
			+ " FROM mtrnd.lpariip as t1 "
			+ " where t1.RSYSTEM=? and datediff(current_date,t1.epvdate)<=30"
			+ " order by t1.EPVDATE ASC, t1.EPVHOUR ASC;";
	private final static String SELECT_VOLUME_TIMES_DAILY="SELECT APPLVTNAME,TRUNCATE(SUM(TOTCPUTM),2),SUM(CTRANS) from mtrnd.cicsdayh"
			+ " WHERE SYSTEM=? and EPVDATE=?"
			+ " GROUP BY EPVDATE,APPLVTNAME ORDER BY EPVDATE ASC, APPLVTNAME ASC;";
	private final static String SELECT_VOLUME_TIMES_BY_DAY="SELECT EPVHOUR,TRUNCATE(SUM(TOTCPUTM),2),SUM(CTRANS) from mtrnd.cicsdayh "
			+ " WHERE SYSTEM=? and EPVDATE=? and APPLVTNAME=?"
			+ " GROUP BY EPVHOUR ORDER BY EPVHOUR ASC;";
	private final static String SELECT_DISTINCT_SYSTEM="SELECT DISTINCT SYSTEM FROM smfacc.r113_2_hour";
	private final static String SELECT_DISTINCT_SYSTEM_MQ="SELECT DISTINCT(SYSTEM) FROM  mtrnd.mqmdayh where system<>'FSYC' and system<>'CSY3'";
    private final static String SELECT_BATCH_INTERVAL="SELECT SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,TOT,EXECTM,ELAPSED"+
	                                                       ",DISKIO,DISKIOTM,ZIPTM,CPUTIME,TAPEIO,TAPEIOTM FROM "+TABLE_PARAMETER_STRING+" where DATET10=? and SYSTEM=?";
	private final static String SELECT_STC_INTERVAL="SELECT SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,TOT,EXECTM,ELAPSED"+
            ",DISKIO,DISKIOTM,ZIPTM,CPUTIME,TAPEIO,TAPEIOTM FROM smfacc.epv030_23_intrvl_t10_rm_STC where DATET10=? and SYSTEM=?";
    private final static String SELECT_CPU_MF_BY_DAY_DATATABLES=
    		"SELECT SYSTEM,aaaammgg,CPU,CLASS,`sum(INTSEC)` as DURATION,MIPS,CPI,L1MP,RNI,L2P,L3P,L4LP,L4RP,MEMP,PRB_STATE,AVG_UTIL"+
            " FROM smfacc.r113_resume_ctrl WHERE aaaammgg=? ORDER BY SYSTEM,CPU";
    private final static String SELECT_CPU_MF_BY_DAY_DATATABLES_REALE=
    		"SELECT SYSTEM,aaaammgg,CPU,CLASS,`sum(INTSEC)` as DURATION,MIPS,CPI,L1MP,RNI,L2P,L3P,L4LP,L4RP,MEMP,PRB_STATE,AVG_UTIL"+
            " FROM smfacc.r113_resume_ctrl WHERE aaaammgg=? and (SYSTEM='SIES' or SYSTEM='SIGE') ORDER BY SYSTEM,CPU";
    private final static String SELECT_WKL_LAST_30_DAY="SELECT substring(DATA_INT10,1,13) ,SYSTEM,WKLOADNAME, sum(CPUTIME)*1007.6/3600 from "+TABLE_PARAMETER_STRING+" where SYSTEM=?"+
            " and datediff(?,date(DATA_INT10))<=(?-1) and datediff(?,date(DATA_INT10))>-1  group by substring(DATA_INT10,1,13),WKLOADNAME order by WKLOADNAME,DATA_INT10 ASC";
	private final static String SELECT_VOLUMES_TIMES_INFORMATION="SELECT DATE(START_005),SUM(CPUTIME),COUNT(*) from CR00515.EPV110_1_TRXACCT"+
								" where SYSTEM=? and TRAN_001 = ?" +
								" group by DATE(START_005) order by DATE(START_005) ASC;";
	public final static String SELECT_TRANSACTION_BY_DATE="SELECT TRAN_001,USERID_089,TOT,truncate(CPUTIME,3),truncate(ELAPSED,3),truncate(J8CPUT_260TM,3),truncate(KY8CPUT_263TM,3),truncate(L8CPUT_259TM,3),truncate(MSCPUT_258TM,3),SCUSRHWM_106,truncate(QRDISPT_255TM,3),truncate(S8CPUT_261TM,3),DB2REQCT_180,ABCODEO_113,ABCODEC_114 FROM "+TABLE_PARAMETER_STRING+" where system=? and START_010=? order by TOT DESC";
	private final static String SELECT_WKL_BY_DAY="SELECT DATA_INT10 ,SYSTEM,WKLOADNAME, sum(CPUTIME)*1007.6/600 from "+TABLE_PARAMETER_STRING+" where SYSTEM=?"+
            " and date(DATA_INT10)=? group by DATA_INT10,WKLOADNAME order by WKLOADNAME,DATA_INT10 ASC ";
    private static final String SELECT_CPI_MIPS_LAST_N_DAYS = "SELECT * FROM("+
            "SELECT SYSTEM,aaaammgg,SM113CPT,SUM(B0)/SUM(B1),SUM(B1)/(1000000*24*3600) as MIPS FROM smfacc.r113_2_hour "+
            "where SYSTEM=? and SM113CPT=? and aaaammgg<=?"+
            " group by SYSTEM,aaaammgg,SM113CPT order by aaaammgg DESC LIMIT ?) sub "+
            " order by aaaammgg ASC";

        private static final String SELECT_YESTERDAY_MIPS="SELECT SERIAL,SYSTEM,EPVDATE,EPVHOUR,MIPS,cast(MIPLPAR as UNSIGNED) as MIPLPAR FROM support.merge_cecmips_lparcpu where EPVDATE=DATE(current_date-1) and EPVHOUR=(SELECT EPVHOUR from support.merge_cecmips_lparcpu where EPVDATE=DATE(current_date-1) group by EPVHOUR order by SUM(MIPLPAR) DESC LIMIT 1) order by SERIAL";
	private static final String SELECT_YESTERDAY_CPI="SELECT SYSTEM,(SUM(B0)/(SUM(B1)))  as CPI FROM smfacc.r113_2_hour"+
            " where SM113CPT='Standard CP' and aaaammgg=? group by SYSTEM,aaaammgg,SM113CPT";
        private static final String SELECT_SYSTEM_HOURLY_REPORT = "SELECT h as ora,SUM(B0)/SUM(B1) as CPI,SUM(B1)/1000000/3600 as MIPS,count(distinct SM113CPU) as numberCpu  FROM smfacc.r113_2_hour  where aaaammgg=? and SYSTEM=? and SM113CPT=? group by SM113CPT,h";
	private static final String SELECT_CPU_NUMBER_YESTERDAY="SELECT SYSTEM,aaaammgg,h,count(distinct SM113CPU) as numberCpu  FROM smfacc.r113_2_hour  where aaaammgg=? and SM113CPT='Standard CP' group by SYSTEM,h order by SYSTEM,h";
	private static final String SELECT_BATCH_ABEND_WINDOWS_TIME = "SELECT date(DATET10),sum(TOT),truncate(sum(CPUTIME), 3)  FROM "+TABLE_PARAMETER_STRING+" where "
                +"SYSTEM=? and datediff(current_date, date(DATET10) )<=? and datediff(current_date, date(DATET10) )>? and CONDCODE>=8 group by date(DATET10) order by date(DATET10)";
	private static final String SELECT_BATCH_ABEND = "SELECT SUBSTRING(DATET10,12) AS hour,SMF30JBN,CONDCODE,TOT,truncate(CPUTIME,3),truncate(ZIPTM,3),truncate(ELAPSED,3),SMF30RUD FROM "+TABLE_PARAMETER_STRING+" where CONDCODE>=8  and SYSTEM=? and date(DATET10)=? order by CPUTIME desc";
        private static final String SELECT_TRANSACTION_ABEND="SELECT SUBSTRING(START_010,12) AS hour,TRAN_001,ABCODEC_114,TOT,truncate(CPUTIME,3),DB2REQCT_180,truncate(ELAPSED,3),USERID_089 FROM "+TABLE_PARAMETER_STRING+" where ABCODEC_114<>\"\""+ 
                                              "and SYSTEM=? and date(START_010)=? order by CPUTIME desc ";
         private static final String SELECT_TRANSACTION_ABEND_WINDOWS_TIME="SELECT date(START_010),sum(TOT),truncate(sum(CPUTIME), 3)  FROM "+TABLE_PARAMETER_STRING+" where"+
                                             " SYSTEM=? and datediff(current_date, date(START_010) )<=? and datediff(current_date, date(START_010) )>? and ABCODEC_114<>\"\""+
    		                                 " group by date(START_010) order by date(START_010)";
        
        private static final String SELECT_VIRTUAL_TAPE_MOUNT_TIME="SELECT EPVDATE , CASE "
    		+ " WHEN VTCS13VMT='mount sl scratch VTV' or VTCS13VMT='mount existing VTV as scratch' then 'SCRATCH'"
    		+ " WHEN VTCS13VMT='mount existing VTV' and VTCS13RCI='mounted after a recall' then 'EXISTING-N'"
    		+ " WHEN VTCS13VMT='mount existing VTV' and VTCS13RCI='mounted without a recall' then 'EXISTING-Y' end as TYPE"
    		+ " ,sum(VTCS13MTM)/sum(NMOU) as AVG_MOUNT  FROM mresa.vsmmount"
    		+ " where (VTID='VSMA' or VTID='VSMA1' or VTID='VSMA2')  and datediff(current_date,EPVDATE)>=1 "
    		+ " group by SITE,EPVDATE,TYPE  order by TYPE,EPVDATE ASC";
	
    private static final String SELECT_VOLUMES_TIMES_TRANSACTION_STRING="SELECT START_010,sum(CPUTIME),sum(TOT)"
    		+ " from "+TABLE_PARAMETER_STRING
    		+ " where TRAN_001=? and datediff( current_date,date(START_010))=? "
    		+ " group by TRAN_001,START_010 order by START_010 ASC";

    private static final String SELECT_JOB_INTERVAL_BY_JOBNAME_MONTH="SELECT substr(INITIALTIME, 1 , 16),substr(ENDTIME, 1 , 16) from CR00515.EPV30_5_JOBTERM "
                                               +    "WHERE SMF30JBN=? AND MONTH(INITIALTIME)=? AND CPUTIME>0";
    
    private static final String SELECT_MQ_BY_MONTH_SYSTEM="SELECT day(EPVDATE), SUM( TOTALPUTS ) AS PUT , SUM( TOTALGETS)   AS GET,case "+ 
    		"when dayOfWeek(EPVDATE)=1 then 'DOM' "+
    		"when dayOfWeek(EPVDATE)=2 then 'LUN' "+
    		"when dayOfWeek(EPVDATE)=3 then 'MAR' "+
    		"when dayOfWeek(EPVDATE)=4 then 'MER' "+
    		"when dayOfWeek(EPVDATE)=5 then 'GIO' "+
    		"when dayOfWeek(EPVDATE)=6 then 'VEN' "+
    		"when dayOfWeek(EPVDATE)=7 then 'SAB' "+
    		"end as dayWeek"+
    		" 	FROM mtrnd.mqmdayh WHERE SYSTEM = ? and month(EPVDATE)=? and year(EPVDATE)=? and SMF30WID='CICS' GROUP BY day(EPVDATE),dayWeek order by EPVDATE";
    private static final String SELECT_MQ_BY_DAY_SYSTEM="SELECT EPVHOUR, SUM( TOTALPUTS ) AS PUT , SUM( TOTALGETS)    AS GET 	FROM mtrnd.mqmdayh WHERE SYSTEM = ? and day(EPVDATE)=? and month(EPVDATE)=? and year(EPVDATE)=? and SMF30WID='CICS' GROUP BY EPVHOUR order by EPVHOUR";
    private Connection conn=null;
	private PreparedStatement st=null;
	private ResultSet rs=null;

	public static final HashMap<String, String> mapSystemTransactionTableHashMap=initializeMapSystemTransactionTable();
	public static final HashMap<String, String> mapSystemWorloadTableHashMap=initializeMapSystemWorloadTable();
        public static final HashMap<String, String> mapSystemBatchTableHashMap=initializeMapBatchTable();

	private static final String SELECT_STEP_BY_JESNUM = "SELECT SMF30JBN , JESNUM , SMF30STM , SMF30STN , SMF30RUD , READTIME , ENDTIME ,"+
			" ROUND(CPUTIME, 2) AS CPUTIME, ZIPTM , ELAPSED , DISKIO , DISKIOTM , CONDCODE ,SMF30CL8 as class, SMF30PGM  "+
			" FROM CR00515.EPV30_4_STEP"+
			" WHERE SYSTEM = ? AND SMF30WID = 'JES2' AND SMF30JBN= ? AND JESNUM= ?"+
			" ORDER BY READTIME ";
	private static final String SELECT_MQ_BY_DAY_STRING="SELECT Intervallo,nGet,LATENZAMSG FROM smfacc.epv116_qa_LXECG100 where datediff( current_date,date(INTERVALLO))=? order by INTERVALLO asc;";
	private final static String SELECT_LOGIN="SELECT password,profilo,email,lastAccess FROM smfacc.users WHERE abilitato=1 and email=?";
	private final static String SELECT_ACTIVATION="SELECT count(*) FROM smfacc.users WHERE abilitato=0 and email=? and activationCode=?";
	private static final String ACTIVATION_UPDATE = "UPDATE smfacc.users SET abilitato=1 where email=?";


	private static final String EPV_DB_PROPERTIES = "datalayer.db2";
	public User verifyUser(String user, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
		connection(SELECT_LOGIN);
		st.setString(1, user);
		rs=st.executeQuery();
		if(rs.next()&&PasswordUtility.validatePassword(password, rs.getString(1))){ //se ha trovato un account abilitato
		    User u=new User(rs.getString(3),rs.getString(2),rs.getTimestamp(4) );
			disconnect();
		return u;
		}
		disconnect();
		return null;
	}
	public  void updateLastAccess(String userid) throws ClassNotFoundException, SQLException{
		connection(UPDATE_LAST_ACCESS);
		java.util.Date date= new java.util.Date();
		st.setTimestamp(1, new Timestamp(date.getTime()));
		st.setString(2, userid);
		st.executeUpdate();
		disconnect();
	}

	public Collection<VolumeSMF> getVolumiSMF() throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES, SELECT_VOLUMI_SMF);
		rs=st.executeQuery();
		Collection <VolumeSMF> coll=new ArrayList<VolumeSMF>();
		while(rs.next()){
			VolumeSMF el=new VolumeSMF();
			el.setDate(rs.getString(1));
			el.setVolumeGB(rs.getInt(2));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	
	
	public boolean isThereAnyEmptySlot(String system, String date) throws ClassNotFoundException, SQLException{
		connection(SELECT_ERROR_COUNT_WKL.replace(TABLE_PARAMETER_STRING,mapSystemWorloadTableHashMap.get(system)));
		st.setString(1,system);
		st.setString(2, date);
		rs=st.executeQuery();
		rs.next();
		if(rs.getInt(1)>0){
			disconnect();
			return true;}
		else{
			disconnect();
			return false;}
		
	}
	public Collection<SondaWorkloadInterval> getSondaWkl(String system, String date) throws ClassNotFoundException, SQLException{
		connection(SELECT_SONDA_WORKLOAD.replace(TABLE_PARAMETER_STRING,mapSystemWorloadTableHashMap.get(system)));
		st.setString(1,system);
		st.setString(2, date);
		rs=st.executeQuery();
		Collection<SondaWorkloadInterval> collection=new ArrayList<SondaWorkloadInterval>();
		while(rs.next()){
			SondaWorkloadInterval el=new SondaWorkloadInterval();
			el.setSystem(system);
			el.setDate(date);
			el.setInterval(rs.getString(1));
			if(rs.getString(2).equals("GREEN")) el.setState(0);
			else
				if (rs.getString(2).equals("YELLOW")) el.setState(1);
				else
					el.setState(2);
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public boolean activateUser(String user, String activationCode) throws SQLException, ClassNotFoundException{
		connection(SELECT_ACTIVATION);
		st.setString(1, user);
		st.setString(2, activationCode);
		rs=st.executeQuery();
		rs.next();
		if(rs.getInt(1)==1){
			PreparedStatement update=conn.prepareStatement(ACTIVATION_UPDATE);
			update.setString(1, user);
			update.executeUpdate();
			update.close();
			disconnect();
			return true;
		}
		else{
			disconnect();
			return false;
		}
	}

	
	public Collection<SystemConsumptionsReport> get30DaysConsumptionsReport(String system) throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_SYSTEM_CONSUMPTION_LAST30_DAY);
		PreparedStatement st2=conn.prepareStatement(SELECT_SYSTEM_CONSUMPTION_ZIIP_LAST30_DAY);
		st2.setString(1, system);
		st.setString(1, system);
        ResultSet rs2=st2.executeQuery();
		rs=st.executeQuery();
		Collection<SystemConsumptionsReport> coll=new ArrayList<SystemConsumptionsReport>();
		while(rs.next()&&rs2.next()){
			SystemConsumptionsReport el=new SystemConsumptionsReport();
			el.setDate(rs.getString(1));
			el.setHour(rs.getInt(2));
			el.setMipsCpu(rs.getFloat(3));
			el.setMipsZiip(rs2.getFloat(3));
			el.setMsu4hra(rs.getFloat(4));
			coll.add(el);
		}
		rs2.close();
		st2.close();
		disconnect();
		return coll;
	}
	public Collection<MQQuequeReport> getMqByDay(int offset) throws ClassNotFoundException, SQLException{
		connection(SELECT_MQ_BY_DAY_STRING);
		st.setInt(1, offset);
		rs=st.executeQuery();
		Collection<MQQuequeReport> collection=new ArrayList<MQQuequeReport>();
		while(rs.next()){
			MQQuequeReport el=new MQQuequeReport();
			el.setDate(rs.getString(1));
			el.setCountOperations(rs.getLong(2)); //nGet
			el.setLatencyMSG(rs.getFloat(3));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<VolumeTimeInformation> getDailyVolumesTimesInformation(String system,String date) throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_VOLUME_TIMES_DAILY);
		st.setString(1, system);
		st.setString(2, date);
		rs=st.executeQuery();
		Collection<VolumeTimeInformation> coll=new ArrayList<VolumeTimeInformation>();
		while(rs.next()){
			VolumeTimeInformation el=new VolumeTimeInformation();
			el.setApplvtname(rs.getString(1));
			el.setCpuTime(rs.getFloat(2));
			el.setVolume(rs.getInt(3));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public Collection<VolumeTimeInformation> getVolumesTimesInformationByDay(String system,String date, String applvtname) throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_VOLUME_TIMES_BY_DAY);
		st.setString(1, system);
		st.setString(2, date);
		st.setString(3, applvtname);
		rs=st.executeQuery();
		Collection<VolumeTimeInformation> coll=new ArrayList<VolumeTimeInformation>();
		while(rs.next()){
			VolumeTimeInformation el=new VolumeTimeInformation();
			el.setHour(rs.getInt(1));
			el.setCpuTime(rs.getFloat(2));
			el.setVolume(rs.getInt(3));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public Collection<VolumeTimeInformation> getVolumesTimesInformation(String system, String transName) throws ClassNotFoundException, SQLException{
		connectionIDAA(SELECT_VOLUMES_TIMES_INFORMATION);
		st.setString(1, system);
		st.setString(2, transName);
		rs=st.executeQuery();
		Collection<VolumeTimeInformation> coll=new ArrayList<VolumeTimeInformation>();
		while(rs.next()){
			VolumeTimeInformation el=new VolumeTimeInformation();
			el.setCpuTime(rs.getFloat(2));
			el.setVolume(rs.getInt(3));
			el.setData(rs.getString(1));
			coll.add(el);
		}
		disconnect();
		return coll;
			}
        
    private static HashMap<String, String> initializeMapBatchTable() {
       
                HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "smfacc.epv030_5_jobterm_t10_rm");
		map.put("SIGE", "smfacc.epv030_5_jobterm_t10_rm");
                map.put("ASDN", "smfacc.epv030_5_jobterm_t10_carige");
		map.put("ASSV", "smfacc.epv030_5_jobterm_t10_carige");
		return map;
        
    }
	
	private static HashMap<String, String> initializeMapSystemTransactionTable() {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "smfacc.epv110_1_trxacct_t10_rm");
		map.put("SIGE", "smfacc.epv110_1_trxacct_t10_rm");
                map.put("ASDN", "smfacc.epv110_1_trxacct_t10_carige");
		map.put("ASSV", "smfacc.epv110_1_trxacct_t10_carige");
		map.put("GSY7", "smfacc.epv110_1_trxacct_t10_sy7");
		return map;
	}
	private static HashMap<String, String> initializeMapSystemWorloadTable() {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "smfacc.workload_view");
		map.put("SIGE", "smfacc.workload_view");
                map.put("ASSV", "smfacc.workload_view_carige");
		map.put("ASDN", "smfacc.workload_view_carige");
		map.put("GSY7", "smfacc.workload_view_sy7");
		map.put("ZSY5", "smfacc.workload_view_sy5");
		map.put("CSY3", "smfacc.workload_view_sy5");
		map.put("BSY2", "smfacc.workload_view_sy2");
		return map;
	}
	public Collection<CECReport> getWLCReport(int month,int year) throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_WLC_BY_MONTH);
		String monthStart=String.format("%02d", month);
		String yearStart=String.valueOf(year);
                String monthEnd;
                String yearEnd;
		if(month!=12)
			{
			monthEnd=String.format("%02d", month+1);
			yearEnd=String.valueOf(year);
			}
		else{
			monthEnd="01";
			yearEnd=String.valueOf(year+1);
		}
			st.setString(1, yearStart+"-"+monthStart+"-01");
			st.setString(2, yearEnd+"-"+monthEnd+"-01");
		rs=st.executeQuery();
		Collection<CECReport> collection=new ArrayList<CECReport>();
		while(rs.next()){
			CECReport el=new CECReport();
			el.setDate(rs.getString(1));
			el.setSerial(rs.getString(2));
			el.setMsu4hra(rs.getInt(3));
			collection.add(el);
		}
		return collection;
	}
	public Collection<TapeReport> getTapeMountTimes() throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_VIRTUAL_TAPE_MOUNT_TIME);
		rs=st.executeQuery();
		Collection<TapeReport> collection=new ArrayList<TapeReport>();
		while(rs.next()){
			TapeReport el=new TapeReport();
			el.setEpvdate(rs.getString(1));
			el.setType(rs.getString(2));
			el.setAvg_mount_time(rs.getFloat(3));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<StepReport> getStepByJesnum(String system,String jobname,String jesnum) throws ClassNotFoundException, SQLException{
		connectionIDAA(SELECT_STEP_BY_JESNUM);
		st.setString(1, system);
		st.setString(2, jobname);
		st.setString(3, jesnum);
		rs=st.executeQuery();
		Collection<StepReport>collection=new ArrayList<StepReport>();
		while(rs.next()){
			StepReport el=new StepReport();
			el.setJobName(jobname);
			el.setJesNumber(rs.getString(2));
			el.setStepNameString(rs.getString(3));
			el.setStepNumber(rs.getString(4));
			el.setRacfUserIdString(rs.getString(5));
			el.setReadTime(rs.getString(6));
			el.setEndTime(rs.getString(7));
			el.setCpuTime(rs.getFloat(8));
			el.setZipTime(rs.getFloat(9));
			el.setElapsed(rs.getFloat(10));
			el.setDiskio(rs.getFloat(11));
			el.setDiskioTime(rs.getFloat(12));
			el.setConditionCode(rs.getString(13));
			el.setClass8(rs.getString(14));
			el.setProgramName(rs.getString(15));
		   collection.add(el);		}
		disconnect();
		return collection;
	}
	public Collection<BatchReport> getJobByName(String query,ArrayList<String> params) throws ClassNotFoundException, SQLException {
		connectionIDAA(query);
		int i=1;
		for(String p: params){
			st.setString(i, p);
			i++;
		}
		rs=st.executeQuery();
		Collection<BatchReport>collection=new ArrayList<BatchReport>();
		while(rs.next()){
			BatchReport el=new BatchReport();
			el.setJobName(rs.getString(1));
			el.setJesNumber(rs.getString(2));
			el.setRacfUserIdString(rs.getString(3));
			el.setReadTime(rs.getString(4));
			el.setEndTime(rs.getString(5));
			el.setCpuTime(rs.getFloat(6));
			el.setZipTime(rs.getFloat(7));
			el.setElapsed(rs.getFloat(8));
			el.setDiskio(rs.getDouble(9));
			el.setDiskioTime(rs.getFloat(10));
			el.setConditionCode(rs.getString(11));
			el.setClass8(rs.getString(12));
			el.setJesInputPriorityString(rs.getDouble(13));
			el.setReportClassString(rs.getString(14));
			collection.add(el);
		}
		disconnect();
		return collection;
		
	}
	public Collection<TransactionReport> getTransactionInAbendInWindowTime(String system, int minDate,int maxDate) throws ClassNotFoundException, SQLException{
		connection(SELECT_TRANSACTION_ABEND_WINDOWS_TIME.replace(TABLE_PARAMETER_STRING, mapSystemTransactionTableHashMap.get(system)));
		st.setString(1, system);
		st.setInt(2, minDate);
		st.setInt(3, maxDate);
		rs=st.executeQuery();
		Collection<TransactionReport>collection=new ArrayList<TransactionReport>();
		while (rs.next()) {
			TransactionReport el=new TransactionReport();
			el.setDateString(rs.getString(1));
			el.setTransactionCount(rs.getInt(2));
			el.setCpuSecond(rs.getFloat(3));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<TransactionReport> getTransactionInAbend(String system, String date, int limit) throws ClassNotFoundException, SQLException{
		
            if(limit!=0)
			connection(SELECT_TRANSACTION_ABEND.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system))+" LIMIT "+String.valueOf(limit));
		else
			connection(SELECT_TRANSACTION_ABEND.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system)));
		st.setString(1, system);
		st.setString(2, date);
		rs=st.executeQuery();
		Collection<TransactionReport>collection=new ArrayList<TransactionReport>();
		while (rs.next()) {
			TransactionReport el=new TransactionReport();
			el.setDateString(rs.getString(1));
			el.setTransaction(rs.getString(2));
			el.setAbend1(rs.getString(3));
			el.setTransactionCount(rs.getInt(4));
			el.setCpuSecond(rs.getFloat(5));
			el.setDb2req(rs.getInt(6));
			el.setElapsed(rs.getFloat(7));
			el.setUserID(rs.getString(8));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<WorkloadInterval> getLast30dayWorkload(String system,int limit,int offset) throws ClassNotFoundException, SQLException{
		String date=UtilityDate.conversionToFormat("yyyy-MM-dd", UtilityDate.getDate(offset));
		String queryString=SELECT_WKL_LAST_30_DAY.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system));
		connection(queryString);
        st.setString(1, system);
        st.setString(2, date);
        st.setInt(3, limit);
        st.setString(4, date);
        rs=st.executeQuery();
		Collection<WorkloadInterval> coll=new ArrayList<WorkloadInterval>();
		while(rs.next()){
			WorkloadInterval el=new WorkloadInterval();
			el.setDate_interval_start(rs.getString(1));
			el.setSystem(rs.getString(2));
			el.setWorkloadName(rs.getString(3));
			el.setMipsCpu(rs.getFloat(4));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public Collection<BatchReport> getBatchByDate(String date,String system) throws ClassNotFoundException, SQLException{
		connection(SELECT_BATCH_INTERVAL.replace(TABLE_PARAMETER_STRING, mapSystemBatchTableHashMap.get(system)));
		st.setString(2, system);
		st.setString(1, date);
		rs=st.executeQuery();
		Collection<BatchReport> collection=new ArrayList<BatchReport>();
		while(rs.next()){
			BatchReport report=new BatchReport();
			 //SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,TOT,EXECTM,ELAPSED
             //DISKIO,DISKIOTM,ZIPTM,CPUTIME,TAPEIO,TAPEIOTM
			report.setSystem(system);
			report.setDateInterval(date);
			report.setJobName(rs.getString(1));
			report.setConditionCode(rs.getString(2));
			report.setJobClasString(rs.getString(3));
			report.setJesInputPriorityString(rs.getDouble(4));
			report.setRacfUserIdString(rs.getString(5));
			report.setCount(rs.getInt(6));
			report.setExecTime(rs.getInt(7));
			report.setElapsed(rs.getFloat(8));
			report.setDiskio(rs.getDouble(9));
			report.setDiskioTime(rs.getFloat(10));
			report.setZipTime(rs.getFloat(11));
			report.setCpuTime(rs.getFloat(12));
			report.setTapeIO(rs.getDouble(13));
			report.setTapeIOtime(rs.getFloat(14));
			collection.add(report);
		}
		disconnect();
		return collection;
	}
	public Collection<TransactionReport> getTransactionByDate(String date,String system) throws ClassNotFoundException, SQLException{
		String queryString=SELECT_TRANSACTION_BY_DATE.replace(TABLE_PARAMETER_STRING, mapSystemTransactionTableHashMap.get(system));
		connection(queryString);
		st.setString(1, system);
		st.setString(2, date);
		rs=st.executeQuery();
		Collection<TransactionReport> collection=new ArrayList<TransactionReport>();
		while (rs.next()) {
			TransactionReport report=new TransactionReport();
			report.setSystem(system);
			report.setDateString(date);
			report.setTransaction(rs.getString(1));
			report.setUserID(rs.getString(2));
			report.setTransactionCount(rs.getInt(3));
			report.setCpuSecond(rs.getFloat(4));
			report.setElapsed(rs.getFloat(5));
			report.setJ8(rs.getFloat(6));
			report.setK8(rs.getFloat(7));
			report.setL8(rs.getFloat(8));
			report.setMs(rs.getFloat(9));
			report.setMem(rs.getLong(10));
			report.setQr(rs.getFloat(11));
			report.setS8(rs.getFloat(12));
			report.setDb2req(rs.getInt(13));
			report.setAbend1(rs.getString(14));
			report.setAbend2(rs.getString(15));
			collection.add(report);
		}
		disconnect();
		return collection;
	}
	public Collection<WorkloadInterval> getWorkloadByDay(String system,String day) throws ClassNotFoundException, SQLException{
		String query=SELECT_WKL_BY_DAY.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system));
		connection(query);
		st.setString(1, system);
		st.setString(2, day);
		rs=st.executeQuery();
		Collection<WorkloadInterval> coll=new ArrayList<WorkloadInterval>();
		while(rs.next()){
			WorkloadInterval el=new WorkloadInterval();
			el.setDate_interval_start(rs.getString(1));
			el.setSystem(rs.getString(2));
			el.setWorkloadName(rs.getString(3));
			el.setMipsCpu(rs.getFloat(4));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
    public Collection<String> getSystems() throws ClassNotFoundException, SQLException {
		ArrayList<String> systems=new ArrayList<String>();
		connection(SELECT_DISTINCT_SYSTEM);
		rs=st.executeQuery();
		while(rs.next())
			systems.add(rs.getString(1));
		disconnect();
		return systems;
	}
	private void connection(String query) throws ClassNotFoundException, SQLException{
		 ResourceBundle rb =   ResourceBundle.getBundle("datalayer.db");
		 Class.forName(rb.getString("driver"));
		 conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		 st=conn.prepareStatement(query);
	}
	private void connection(String fileProp,String query) throws ClassNotFoundException, SQLException{
		 ResourceBundle rb =   ResourceBundle.getBundle(fileProp);
		 Class.forName(rb.getString("driver"));
		 conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		 st=conn.prepareStatement(query);	 
	}
	private void connectionIDAA(String query) throws ClassNotFoundException, SQLException{
		 ResourceBundle rb =   ResourceBundle.getBundle("datalayer.idaa");
		 Class.forName(rb.getString("driver"));		
		 conn=DriverManager.getConnection(rb.getString("url"));
		 st=conn.prepareStatement(query);
	}
	private void disconnect() throws SQLException{
		if(rs!=null)rs.close();
		if(st!=null)st.close();
		if(conn!=null)conn.close();
	}
	public Collection<SystemReport> getLastdaysSystemReport(int limit,
			String system, String cpuClass, int offset) throws ClassNotFoundException, SQLException {
		String dateStart=UtilityDate.conversionToDBformat(UtilityDate.getDate(offset));
		connection(SELECT_CPI_MIPS_LAST_N_DAYS);
		st.setString(1, system);
		st.setString(2, cpuClass);
		st.setString(3, dateStart);
		st.setInt(4, limit);
		rs=st.executeQuery();
		Collection <SystemReport> coll=new ArrayList<SystemReport>();
		while(rs.next()){
			SystemReport el=new SystemReport();
			el.setSystem(rs.getString(1));
			el.setDate(rs.getString(2));
			el.setCpuClass(rs.getString(3));
			el.setCpi(rs.getFloat(4));
			el.setMips(rs.getInt(5));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public Collection<SystemHourReport> getYesterdaySystemsCPUnumbers() throws ClassNotFoundException, SQLException{
		connection(SELECT_CPU_NUMBER_YESTERDAY);
		st.setString(1, UtilityDate.conversionToDBformat(UtilityDate.getDate(-1)));
		rs=st.executeQuery();
		Collection <SystemHourReport> coll=new ArrayList<SystemHourReport>();
		while(rs.next()){
			SystemHourReport el=new SystemHourReport();
			el.setSystem(rs.getString(1));
			el.setNumberCPU(rs.getInt(4));
			el.setFrom_hour(rs.getString(3));
			coll.add(el);
		
		}
		disconnect();
		return coll;
		
	}
	public Collection<SystemReport> getYesterdayMips() throws ClassNotFoundException, SQLException {
		connection(EPV_DB_PROPERTIES,SELECT_YESTERDAY_MIPS);
		rs=st.executeQuery();
		Collection<SystemReport> coll=new ArrayList<SystemReport>();
		while(rs.next()){
			SystemReport sys=new SystemReport();
			sys.setSystem(rs.getString(1));
			sys.setMips(rs.getInt(2));
			coll.add(sys);
		}
		disconnect();
		return coll;
	}
    public Collection<SystemReport> getYesterdayCPI() throws ClassNotFoundException, SQLException {
    	connection(SELECT_YESTERDAY_CPI);
		st.setString(1, UtilityDate.conversionToDBformat(UtilityDate.getDate(-1)));
		rs=st.executeQuery();
		Collection<SystemReport> coll=new ArrayList<SystemReport>();
		while(rs.next()){
			SystemReport sys=new SystemReport();
			sys.setSystem(rs.getString(1));
			sys.setCpi(rs.getFloat(2));
			coll.add(sys);
		}
		disconnect();
		return coll;
	}
	public Collection<SystemHourReport> getSystemReportByDate(String date,
			String system, String cpuClass) throws ClassNotFoundException, SQLException {
		connection(SELECT_SYSTEM_HOURLY_REPORT);
		st.setString(2, system);
		st.setString(3, cpuClass);
		st.setString(1, date);
		rs=st.executeQuery();
		Collection<SystemHourReport> coll=new ArrayList<SystemHourReport>();
		while(rs.next()){
			SystemHourReport el=new SystemHourReport();
			el.setSystem(system);
			el.setDate(date);
			el.setCpuClass(cpuClass);
			el.setCpi(rs.getFloat(2));
			el.setMips(rs.getInt(3));
			el.setNumberCPU(rs.getInt(4));
			el.setFrom_hour(rs.getString(1));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public Collection<CPUReport> getCPUReportsByDay(String day,String profile) throws ClassNotFoundException, SQLException {
		ArrayList<CPUReport> reports=new ArrayList<CPUReport>();
		if(!profile.equals("REALE"))
			connection(SELECT_CPU_MF_BY_DAY_DATATABLES);
		else
			connection(SELECT_CPU_MF_BY_DAY_DATATABLES_REALE);
		st.setString(1, day);
		rs=st.executeQuery();
		while(rs.next()){
			CPUReport cpu= new CPUReport();
			cpu.setSystem(rs.getString(1));
			cpu.setDate(day);
			cpu.setCpuID(rs.getInt(3));
			cpu.setCpuClass(rs.getString(4));
			cpu.setDurationTime(rs.getFloat(5));
			cpu.setMips(rs.getInt(6));
			cpu.setCpi(rs.getFloat(7));
			cpu.setL1mp(rs.getFloat(8));
			cpu.setL2p(rs.getFloat(10));
			cpu.setL3p(rs.getFloat(11));
			cpu.setL4lp(rs.getFloat(12));
			cpu.setL4rp(rs.getFloat(13));
			cpu.setMemp(rs.getFloat(14));
			cpu.setProblemStatePercent(rs.getFloat(15));
			cpu.setAverageUtil(rs.getFloat(16));
			reports.add(cpu);
					}
		disconnect();
		return reports;

	}
     public ResultSet executeQuery(String queryString, String resourceDbPath) throws ClassNotFoundException, SQLException {
		connection(resourceDbPath);
		st=conn.prepareStatement(queryString);
		return st.executeQuery();
	}
	public Collection<BatchReport> getBatchInAbendInWindowTime(String system,
			int minDate, int maxDate) throws ClassNotFoundException, SQLException {
		connection(SELECT_BATCH_ABEND_WINDOWS_TIME.replace(TABLE_PARAMETER_STRING, mapSystemBatchTableHashMap.get(system)));
		st.setString(1, system);
		st.setInt(2, minDate);
		st.setInt(3, maxDate);
		rs=st.executeQuery();
		Collection<BatchReport>collection=new ArrayList<BatchReport>();
		while (rs.next()) {
			BatchReport el=new BatchReport();
			el.setDateInterval(rs.getString(1));
			el.setCount(rs.getInt(2));
			el.setCpuTime(rs.getFloat(3));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<BatchReport> getBatchInAbend(String system, String date,
			int limit)throws ClassNotFoundException, SQLException{
				if(limit!=0)
					connection(SELECT_BATCH_ABEND.replace(TABLE_PARAMETER_STRING, mapSystemBatchTableHashMap.get(system))+" LIMIT "+String.valueOf(limit));
				else
					connection(SELECT_BATCH_ABEND.replace(TABLE_PARAMETER_STRING, mapSystemBatchTableHashMap.get(system)));
				st.setString(1, system);
				st.setString(2, date);
				rs=st.executeQuery();
				Collection<BatchReport>collection=new ArrayList<BatchReport>();
				while (rs.next()) {
					BatchReport el=new BatchReport();
					el.setDateInterval(rs.getString(1));
					el.setJobName(rs.getString(2));
					el.setConditionCode(rs.getString(3));
					el.setCount(rs.getInt(4));
					el.setCpuTime(rs.getFloat(5));
					el.setZipTime(rs.getFloat(6));
					el.setElapsed(rs.getFloat(7));
					el.setRacfUserIdString(rs.getString(8));
					collection.add(el);
				}
				disconnect();
				return collection;
	}

	public Collection<BatchReport> getSTCByDate(String date, String system) throws ClassNotFoundException, SQLException {
		connection(SELECT_STC_INTERVAL);
		st.setString(2, system);
		st.setString(1, date);
		rs=st.executeQuery();
		Collection<BatchReport> collection=new ArrayList<BatchReport>();
		while(rs.next()){
			BatchReport report=new BatchReport();
			 //SMF30JBN,CONDCODE,SMF30CLS,SMF30JPT,SMF30RUD,TOT,EXECTM,ELAPSED
             //DISKIO,DISKIOTM,ZIPTM,CPUTIME,TAPEIO,TAPEIOTM
			report.setSystem(system);
			report.setDateInterval(date);
			report.setJobName(rs.getString(1));
			report.setConditionCode(rs.getString(2));
			report.setJobClasString(rs.getString(3));
			report.setJesInputPriorityString(rs.getDouble(4));
			report.setRacfUserIdString(rs.getString(5));
			report.setCount(rs.getInt(6));
			report.setExecTime(rs.getInt(7));
			report.setElapsed(rs.getFloat(8));
			report.setDiskio(rs.getDouble(9));
			report.setDiskioTime(rs.getFloat(10));
			report.setZipTime(rs.getFloat(11));
			report.setCpuTime(rs.getFloat(12));
			report.setTapeIO(rs.getDouble(13));
			report.setTapeIOtime(rs.getFloat(14));
			collection.add(report);
		}
		disconnect();
		return collection;
	}
	public Collection<TransactionReport> getVolumesTimesTransaction(
			String transaction, String system, int offset) throws ClassNotFoundException, SQLException {
		String query=SELECT_VOLUMES_TIMES_TRANSACTION_STRING.replace(TABLE_PARAMETER_STRING, mapSystemTransactionTableHashMap.get(system));
		connection(query);
		st.setString(1, transaction);
		st.setInt(2, offset);
		rs=st.executeQuery();
		Collection<TransactionReport> tReports=new ArrayList<TransactionReport>();
		while(rs.next()){
			TransactionReport el=new TransactionReport();
			el.setDateString(rs.getString(1));
			el.setCpuSecond(rs.getFloat(2));
			el.setTransactionCount(rs.getInt(3));
			tReports.add(el);
		}
		disconnect();
		return tReports;
	}
	public Collection<BatchReport> getBatchByNameMonth(int month, String jobname) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		connectionIDAA(SELECT_JOB_INTERVAL_BY_JOBNAME_MONTH);
		st.setString(1, jobname);
		st.setInt(2, month);
		rs=st.executeQuery();
		Collection<BatchReport> collection=new ArrayList<BatchReport>();
		while(rs.next()){
			BatchReport el=new BatchReport();
			el.setInitialTitme(rs.getString(1));
			el.setEndTime(rs.getString(2));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<String> getMQSystems() throws ClassNotFoundException, SQLException {
		connection(EPV_DB_PROPERTIES,SELECT_DISTINCT_SYSTEM_MQ);
		ArrayList<String> systems=new ArrayList<String>();
		rs=st.executeQuery();
		while(rs.next())
			systems.add(rs.getString(1));
		disconnect();
		return systems;
	}
	public Collection<MQQuequeReport> getMqByMonthSystem(int year,int month, String system) throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_MQ_BY_MONTH_SYSTEM);
		Collection<MQQuequeReport> coll=new ArrayList<MQQuequeReport>();
		st.setString(1, system);
		st.setInt(2,month);
		st.setInt(3,year);
		rs=st.executeQuery();
		while(rs.next()){
			MQQuequeReport el=new MQQuequeReport();
			el.setDate(rs.getString(1));;
			el.setPutBytes(rs.getLong(2));
			el.setGetBytes(rs.getLong(3));
			el.setDayOfWeek(rs.getString(4));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public Collection<MQQuequeReport> getMqByDaySystem(int year,int month,int day, String system) throws ClassNotFoundException, SQLException{
		connection(EPV_DB_PROPERTIES,SELECT_MQ_BY_DAY_SYSTEM);
		Collection<MQQuequeReport> coll=new ArrayList<MQQuequeReport>();
		st.setString(1, system);
		st.setInt(2,day);
		st.setInt(3,month);
		st.setInt(4,year);
		rs=st.executeQuery();
		while(rs.next()){
			MQQuequeReport el=new MQQuequeReport();
			el.setHour(rs.getInt(1));;
			el.setPutBytes(rs.getLong(2));
			el.setGetBytes(rs.getLong(3));
			coll.add(el);
		}
		disconnect();
		return coll;
	}
	public static void main(String[] args) {
		 System.out.println("07-2015".substring(3, 7));
		 }

	
}
