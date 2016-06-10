package servlets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;



public class UtilityServlet {
	private static final String DAILY_TABLE = "resoconto_giornaliero_gsm";
	private static final String DAILY_EXECUTION_TABLE="giornaliero_eseguiti_gsm";
	private static final String DAILY_TABLE_INSTITUTE="resoconto_giornaliero_gsm_per_istituto";
	private static final String VIEW_ORARIA = "giorno_ora";
	//ritorna tutti i mesi presenti nel database
	public static String  maxData(){
        Connection connection=UtilityServlet.getConnection();
	 
		if (connection != null) {
			System.out.println("Connection ok");
			String selectWid="SELECT TOP 30 data from "+DAILY_TABLE+" order by data desc";
			try {
				PreparedStatement p=connection.prepareStatement(selectWid);
				ResultSet result=p.executeQuery();
				int i=1;
				while(i<=30){result.next(); i++;}
				String res=result.getString(1);
				result.close();
				p.close();
				connection.close();
				return res;
				
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}
			e.printStackTrace();
	return null;
		}
			
		} else {
		
			System.out.println("Failed to make connection!");
			return null;
		}
		
	}
	public static ArrayList<int[]> lastConsegnatiEseguiti(int interval){
        Connection connection=UtilityServlet.getConnection();
	 
		if (connection != null) {
			System.out.println("Connection ok");
			String selectMaxData="SELECT TOP "+String.valueOf(interval)+" totale_consegnati,totale_eseguiti,percentuale"+ " from "+DAILY_TABLE+" "
					+ "ORDER BY data DESC";
			try {
				PreparedStatement p=connection.prepareStatement(selectMaxData);
			
				ResultSet result=p.executeQuery();
				p.close();
				int rowcount=interval;

				
				int[] consegnati=new int[rowcount];
				int[] eseguiti=new int[rowcount];
				

				
				while(result.next()){
				
					consegnati[rowcount-1]=result.getInt(1);
					eseguiti[rowcount-1]=result.getInt(2);
					rowcount--;
				}
				result.close();
			ArrayList<int[]> res=new ArrayList<int[]>();
			res.add(consegnati);
			res.add(eseguiti);
			connection.close();
		        return res;
				
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return null;

		}
			
		} else {
			System.err.println("Failed to make connection!");
			return null;
		}
		
		
		
	}
	public static ResultSet eseguitiOnDate(String data){
        Connection connection=UtilityServlet.getConnection();
		if (connection != null) {
			System.out.println("Connection ok");
			String select="SELECT meno5,meno15,meno30,piu30"+ " from "+DAILY_EXECUTION_TABLE+" "
					+ "where data=?";
			try {
				PreparedStatement p=connection.prepareStatement(select);
				p.setString(1,data);
				
				ResultSet result=p.executeQuery();
				p.close();
				connection.close();
				return result;
				
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return null;

		}
			
		} else {
			System.err.println("Failed to make connection!");
			return null;
		}
		
		
		
	}
	public static ResultSet andamentoOrario(String data){
		 Connection connection=UtilityServlet.getConnection();
			if (connection != null) {
				System.out.println("Connection ok");
				String select="SELECT ora,consegnati,eseguiti,accoppiati"+ " from "+VIEW_ORARIA+" "
						+ "where data=? order by ora ASC";
				try {
					PreparedStatement p=connection.prepareStatement(select);
					p.setString(1,data);
					ResultSet result=p.executeQuery();
					p.close();
					connection.close();
					result.next();
			        return result;
					
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				return null;

			}
				
			} else {
				System.err.println("Failed to make connection!");
				return null;
			}
			
			
			
		}
		 
	
	public static ResultSet eseguitiperIstitutoOnDate(String data){
        Connection connection=UtilityServlet.getConnection();
	 
		if (connection != null) {
			System.out.println("Connection ok");
			String select="SELECT istituto,percentuale"+ " from "+DAILY_TABLE_INSTITUTE+" "
					+ "where data=?";
			try {
				PreparedStatement p=connection.prepareStatement(select);
				p.setString(1,data);
				ResultSet result=p.executeQuery();
				p.close();
				connection.close();
		        return result;
				
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return null;

		}
			
		}
		else {
			System.err.println("Failed to make connection!");
			return null;
		}
		
		
		
	}
	public static Connection getConnection() {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			System.err.println(" MySQL JDBC Driver not Found");
			e.printStackTrace();
			return null;
			
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
	 
	try {
			connection = DriverManager
	             .getConnection("jdbc:microsoft:sqlserver://PRD01.CED.IT:1433;user=UtenteRTT;password=UtenteRTT;DatabaseName=S000SMS00;");
	 return connection;
		} catch (SQLException e) {
			System.err.println("Connection Failed! Check output console");
			
			e.printStackTrace();
			return null;
			
		}	
	}
	public static void initialize( boolean[] status88, boolean[] status102){
		Connection conn = null;
		Statement stm88=null;
		Statement stm102=null;
		ResultSet rs88=null;
		ResultSet rs102=null;
		String[] ora=new String [2];
			try {
				conn= UtilityServlet.getConnection();
				Calendar calendar=Calendar.getInstance();
				int hour=calendar.get(Calendar.HOUR_OF_DAY);
				int hour_1=hour-1;
				int hour_2=hour-2;
				if(hour_1==-1) hour_1=23;
				if(hour_2==-1) hour_2=23;
				if(hour_2==-2) hour_2=22;
				String s88=null;
				String s102=null;
				if(hour_1!=0){
				s88="SELECT send1, send2, ora from sonda_hh where ist='88' and (ora='"+hour_1+"' OR ora='"+hour_2+"') order by ora DESC";
				 s102="SELECT send1, send2 , ora from  sonda_hh where ist='102' and (ora='"+hour_1+"' OR ora='"+hour_2+"') order by ora DESC";
							}
					else{
						 s88="SELECT send1, send2, ora from sonda_hh where ist='88' and (ora='"+hour_1+"' OR ora='"+hour_2+"') order by ora ASC";
						 s102="SELECT send1, send2 , ora from  sonda_hh where ist='102' and (ora='"+hour_1+"' OR ora='"+hour_2+"') order by ora ASC";
					}

				stm88=conn.createStatement();
				stm102=conn.createStatement();
				rs88=stm88.executeQuery(s88);
				rs102=stm102.executeQuery(s102);
				int i=0;
				while(rs88.next()&&rs102.next()){
					boolean bool88=(rs88.getString(1)!=null&&rs88.getString(2)!=null&&rs88.getInt(2)-rs88.getInt(1)<45);
					boolean bool102=(rs102.getString(1)!=null&&rs102.getString(2)!=null&&rs102.getInt(2)-rs102.getInt(1)<45);
					if(i==0)ora[i]=rs88.getString(3);
					if(i==2)ora[i-1]=rs88.getString(3);
					status88[i]=(bool88)?true:false;

					status102[i]=(bool102)?true:false;
					i++;
					
					status88[i]=(bool88||(rs88.getString(1)!=null))?true:false;
					status102[i]=(bool102||(rs102.getString(1)!=null))?true:false;
			
				i++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			    try { if (rs88 != null) rs88.close(); } catch (Exception e) {};
			    try { if (rs102 != null) rs102.close(); } catch (Exception e) {};
			    try { if (stm88 != null) stm88.close(); } catch (Exception e) {};
			    try { if (stm102 != null) stm102.close(); } catch (Exception e) {};
			    try { if (conn != null) conn.close(); } catch (Exception e) {};
			}

		
		}
	public static ResultSet getGroups(){
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		String select="SELECT DISTINCT(gruppo) from utenti_alert";
		try{
			conn= UtilityServlet.getConnection();
			st=conn.createStatement();
			rs=st.executeQuery(select);
			return rs;
		}	catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

}
