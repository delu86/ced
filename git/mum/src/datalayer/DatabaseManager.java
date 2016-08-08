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
import object.User;
import object.WorkloadInterval;
import utility.PasswordUtility;
import utility.UtilityDate;
public class DatabaseManager {
	
	public static String UPDATE_LAST_ACCESS="UPDATE smfacc.users  SET lastAccess=? where email=?  ";
        private final static String TABLE_PARAMETER_STRING="$table_name";
	public final static String SELECT_STC_INTERVAL_REALE="SELECT tData,SMF30JBN,JESNUM,SMF30STM,SMF30STN,"+
            "SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX,CONDCODE,ABEND FROM "+TABLE_PARAMETER_STRING+" where tData=? and SYSTEM=?";
	public final static String SELECT_BATCH_INTERVAL_REALE="SELECT tData,SMF30JBN,JESNUM,SMF30STM,SMF30STN,"+
	                                                          " SMF30PSN,SMF30PGM,SMF30RUD,CPUTIME,SMF30SRV_L,SMF30TEX, CONDCODE,ABEND "
                                                               + "FROM "+TABLE_PARAMETER_STRING+" where tData=? and SYSTEM=?";
	
	private final static String SELECT_DISTINCT_SYSTEM="SELECT DISTINCT SYSTEM FROM smfacc.r113_2_hour";
	private final static String SELECT_DISTINCT_SYSTEM_MQ="SELECT DISTINCT(SYSTEM) FROM  mtrnd13.mqmdayh where system<>'FSYC' and system<>'CSY3'";
       
        private final static String SELECT_WKL_LAST_30_DAY="SELECT substring(DATA_INT10,1,13) ,SYSTEM,WKLOADNAME, sum(CPUTIME)*1102.4/3600 from "+TABLE_PARAMETER_STRING+" where SYSTEM=? and"+
            "  datediff(?,date(DATA_INT10))<=(?-2) and datediff(?,date(DATA_INT10))>-2  group by substring(DATA_INT10,1,13),WKLOADNAME order by WKLOADNAME,DATA_INT10 ASC";
        private final static String SELECT_WKL_LAST_30_DAY_CARIGE="SELECT substring(DATA_INT10,1,13) ,SYSTEM,WKLOADNAME, sum(CPUTIME) from smfacc.workload_view_carige where SYSTEM=?"+
            " and datediff(?,date(DATA_INT10))<=(?-2) and datediff(?,date(DATA_INT10))>-2  group by substring(DATA_INT10,1,13),WKLOADNAME order by WKLOADNAME,DATA_INT10 ASC";
	private final static String SELECT_WKL_BY_DAY="SELECT DATA_INT10 ,SYSTEM,WKLOADNAME, sum(CPUTIME)*1102.4/600 from "+TABLE_PARAMETER_STRING+" where SYSTEM=?"+
            " and date(DATA_INT10)=? group by DATA_INT10,WKLOADNAME order by WKLOADNAME,DATA_INT10 ASC ";   
        private final static String SELECT_WKL_BY_DAY_CARIGE="SELECT CONCAT(substring(DATA_INT10,1,13),\":00\") ,SYSTEM,WKLOADNAME, sum(CPUTIME) from "+TABLE_PARAMETER_STRING+" where SYSTEM=?"+
            " and date(DATA_INT10)=? group by substring(DATA_INT10,1,13),WKLOADNAME order by WKLOADNAME,substring(DATA_INT10,1,13) ASC ";
        private Connection conn=null;
	private PreparedStatement st=null;
	private ResultSet rs=null;

	public static final HashMap<String, String> mapSystemWorloadTableHashMap=initializeMapSystemWorloadTable();
        
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
	private static HashMap<String, String> initializeMapSystemWorloadTable() {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "realebis_ctrl.workload_view");
		map.put("SIGE", "realebis_ctrl.workload_view");
                map.put("ALL", "realebis_ctrl.workload_view");
                map.put("ASSV", "smfacc.workload_view_carige");
		map.put("ASDN", "smfacc.workload_view_carige");
		map.put("GSY7", "smfacc.workload_view_sy7");
		map.put("ZSY5", "smfacc.workload_view_sy5");
		map.put("CSY3", "smfacc.workload_view_sy5");
		map.put("BSY2", "smfacc.workload_view_sy2");
		return map;
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
                        el.setServiceClassString(rs.getString(15));
			collection.add(el);
		}
		disconnect();
		return collection;
	}
	public Collection<WorkloadInterval> getLast30dayWorkload(String system,int limit,int offset) throws ClassNotFoundException, SQLException{
		String date=UtilityDate.conversionToFormat("yyyy-MM-dd", UtilityDate.getDate(offset));
		String queryString;
                if(!(system.equals("ASDN")||system.equals("ASSV"))){
                queryString=SELECT_WKL_LAST_30_DAY.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system));
                if(system.equals("ALL"))
                    connection(EPV_DB_PROPERTIES,queryString.replace("SYSTEM=? and", ""));
                else{
                if(system.equals("SIES")||system.equals("SIGE"))   
                    connection(EPV_DB_PROPERTIES,queryString);
                else
                    connection(queryString);
                }
                }
                else{
                    queryString=SELECT_WKL_LAST_30_DAY_CARIGE;
                    connection(queryString);
                }
        if(!system.equals("ALL")){
            st.setString(1, system);
            st.setString(2, date);
            st.setInt(3, limit);
            st.setString(4, date);
        }
        else{
            st.setString(1, date);
            st.setInt(2, limit);
            st.setString(3, date);
        }
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
	public Collection<WorkloadInterval> getWorkloadByDay(String system,String day) throws ClassNotFoundException, SQLException{
            String query;
            if(system.equals("ASDN")||system.equals("ASSV"))            {        
             query=SELECT_WKL_BY_DAY_CARIGE.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system));
             connection(query);
             
            }else
                query=SELECT_WKL_BY_DAY.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system));
            {if(system.equals("SIES")||system.equals("SIGE"))   
                    connection(EPV_DB_PROPERTIES,query);
                else
                if(system.equals("ALL"))
                    connection(EPV_DB_PROPERTIES,query.replace("SYSTEM=? and", ""));
                    else
                      connection(query);
            }  
            if(!system.equals("ALL")){
                st.setString(1, system);
		st.setString(2, day);}
            else{
                st.setString(1, day);
            }
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
        public ResultSet executeQuery(String queryString, String resourceDbPath) throws ClassNotFoundException, SQLException {
		connection(resourceDbPath);
		st=conn.prepareStatement(queryString);
		return st.executeQuery();
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
}
