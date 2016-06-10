<%@ page language="java" import="java.sql.*, java.util.*,java.util.Date,java.util.Calendar,java.io.*,java.text.*" %> 
<%
Calendar cal = Calendar.getInstance();
Calendar calToday = Calendar.getInstance();
cal.add(Calendar.DATE, -1);

Date date = cal.getTime();             
Date dateTodayd = calToday.getTime();             
SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
String datiBancll="";
String date1 = format1.format(date);            
String dateToday = format1.format(dateTodayd);
String bullet="";
String SELECT_BANCLL="SELECT sum(if(hour(endtime)<7  ,1 ,0)) as green_counter,"+
	  " sum(if(hour(endtime)>=7 and hour(endtime)<8 ,1 ,0)) as yellow_counter,"+
	  " sum(if(hour(endtime)>=8,1,0)) as red_counter "+
         " FROM support.bancll where date(endtime)=current_date;";
    
String LastUpdate="";
String datiDiOggi1="";		
String datiDiOggi2="";		
String datiDiOggi3="";		
String datiDiOggi4="";		
String datiDiOggi5="";	
String datiDiOggi6="";		

Connection conn = null;
Connection conn1 = null;
Connection conn2 = null;
Connection conn3 = null;

Statement stmt = null;
Statement stmt1 = null;
Statement stmt2 = null;
Statement stmt3 = null;
Statement stmt4 = null;
Statement stmt5 = null;
Statement stmt88=null;
Statement stmt102=null;
Statement stmbancll=null;

ResultSet rs = null;
ResultSet rs1 = null;
ResultSet rs2 = null;
ResultSet rs3 = null;
ResultSet rs4 = null;
ResultSet rs5 = null;
ResultSet rs88=null;
ResultSet rs102=null;
ResultSet rsbancll=null;
int conta = 0;
String md_lastupdate =	"";
try 
	{
	Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
	}
catch(java.lang.ClassNotFoundException e)
	{
	out.print("ClassNotFoundException: ");
	out.print(e.getMessage());
	}

 
try {

	conn1 = DriverManager.getConnection("jdbc:microsoft:sqlserver://10.100.1.78:1433;user=utenteStat;password=UtenteStat;DatabaseName=cbiLog;" );
	conn2 = DriverManager.getConnection("jdbc:microsoft:sqlserver://PRD01.CED.IT:1433;user=UtenteRTT;password=UtenteRTT;DatabaseName=S000SMS00;" );	
	
        stmt = conn1.createStatement();
	stmt1 = conn1.createStatement();
	stmt2 = conn2.createStatement();
	stmt3 = conn2.createStatement();
	stmt4 = conn1.createStatement();
	stmt5 = conn1.createStatement();
	stmt102=conn2.createStatement();
	stmt88= conn2.createStatement();
        String tsql1c1 = "SELECT TOP 1 [MD_HB_T001_TIMESTAMP_END],[MD_HB_T001_SUM_TIME]  as tottime FROM [cbiLog].[dbo].[MD_HB_T001V] "  +
	"order by [MD_HB_T001_TIMESTAMP_END] DESC";
	
	String tsql1c2 = " SELECT ora, data , percentuale FROM [S000SMS00].[dbo].[LAST_UPDATE] " ;
	Calendar calendar=Calendar.getInstance();
				int hour=calendar.get(Calendar.HOUR_OF_DAY);
				int hour_1=(hour!=0)?hour-1:23;
	String sel_88 ="SELECT send1, send2, ora from sonda_hh where ist='88' and ora='"+hour_1+"'";
        String sel_102="SELECT send1, send2, ora from sonda_hh where ist='102' and ora='"+hour_1+"'";
	rs2 = stmt2.executeQuery( tsql1c2);
	rs88=stmt88.executeQuery(sel_88);
	rs102=stmt102.executeQuery(sel_102);
	
	LastUpdate+="";
	while (rs2.next())
	{  					
		LastUpdate+=""+rs2.getString(2).substring(6,8)+"-"+rs2.getString(2).substring(4,6)+"-"+rs2.getString(2).substring(0,4)+ " " + rs2.getString(1).substring(0,2) +":"+ rs2.getString(1).substring(2,4)+":" + rs2.getString(1).substring(4,6);			
	

	datiDiOggi3+="<td><a href='#pagesms'> " +  "SMS Alert"  + "</a></td>";
	
		datiDiOggi3+="<td> " +  LastUpdate   + "</td>" ;						
		Double aDouble = 90.9; 
		if (aDouble >= 90.00 ) 	  
		bullet= "<img src=\"img/b_green.png\" alt=\"\" >	"; 
		else
		bullet= "<img src=\"img/b_alert.png\" alt=\"\" >	"; 		
		datiDiOggi3+=  "<td>  " + bullet  + "  (" +  rs2.getString(3) + ")</td>";
	}							//SMS polling
					while(rs88.next()&&rs102.next()){
					LastUpdate=String.valueOf(hour-1)+":36";
					boolean bool88=(rs88.getString(1)!=null&&rs88.getString(2)!=null&&rs88.getInt(2)-rs88.getInt(1)<45);
					boolean bool102=(rs102.getString(1)!=null&&rs102.getString(2)!=null&&rs102.getInt(2)-rs102.getInt(1)<45);
						datiDiOggi6+="<td><a href='#pagesonda'> " +  "SMS Polling"  + "</a></td>";
		 datiDiOggi6+="<td> " +  LastUpdate   + "</td>" ;						
		if (bool102&&bool88) 	  
		bullet= "<img src=\"img/b_green.png\" alt=\"\" >	"; 
		else
		bullet= "<img src=\"img/b_alert.png\" alt=\"\" >	"; 		
		datiDiOggi6+=  "<td>  " + bullet  + " </td>";
			}

	
	rs1 = stmt1.executeQuery( tsql1c1);
	datiDiOggi1+="<td><a href='#page3-hb1'> " +  "HBi"  + "</a></td>";
	while (rs1.next())
		{  				
		datiDiOggi1+="<td> " + rs1.getString(1).substring(8,10) +"-"+ rs1.getString(1).substring(5,7) +"-"+rs1.getString(1).substring(0,4) +rs1.getString(1).substring(10,19)+"</td>"  ;						
		Double aDouble1 = Double.parseDouble(rs1.getString(2)); 
		if (aDouble1 <= 0.8 ) 	  
		bullet= "<img src=\"img/b_green.png\" alt=\"\" >	"; 
		else
		bullet= "<img src=\"img/b_alert.png\" alt=\"\" >	"; 		
		datiDiOggi1+=  " <td> " + bullet  + "  (" +  rs1.getString(2) + ")</td>";
			
	}			
    
	String tsql4 = "SELECT TOP 1 [MD_ATM_T001_TIMESTAMP_END], [MD_ATM_T001_PROBL_HW] as ProblemiHW  FROM [atm].[dbo].[MD_ATM_T001V] order by  MD_ATM_T001_TIMESTAMP_END DESC"	;     
	rs4 = stmt4.executeQuery( tsql4);
	
	ResultSetMetaData metaData4 = rs4.getMetaData();	   	   
	int columnCount4 = metaData4.getColumnCount();	   
	int k4 = 0;	
	datiDiOggi4+="<td><a href='#page7-atm1'> " +  "ATM"  + "</a></td>";
	while (rs4.next())
		{  				
		datiDiOggi4+="<td> " + rs4.getString(1).substring(8,10) +"-"+ rs4.getString(1).substring(5,7) +"-"+rs4.getString(1).substring(0,4) +rs4.getString(1).substring(10,19)+"</td>"  ;							
		Double aDouble = Double.parseDouble(rs4.getString(2)); 
		if (aDouble <= 250 ) 	  
		bullet= "<img src=\"img/b_green.png\" alt=\"\" >	"; 
		else
		bullet= "<img src=\"img/b_alert.png\" alt=\"\" >	"; 		
		datiDiOggi4 +=  " <td> " + bullet  + "  (" +  rs4.getString(2) + ")</td>";
			k4++;
		}

	String tsql5 = "SELECT TOP 1 [MD_PWS_T001_TIMESTAMP_END],  [MD_PWS_T001_AVG_TIME] as tottime FROM [cbiLog].[dbo].[MD_PWS_T001] ORDER BY [MD_PWS_T001_TIMESTAMP_END] DESC"	;     
	rs5 = stmt4.executeQuery( tsql5);
	
	ResultSetMetaData metaData5 = rs5.getMetaData();	   	   
	int columnCount5 = metaData5.getColumnCount();	   
	int k5 = 0;	
	datiDiOggi5+="<td> <a href='#page-psw1'>" +  "PWS"  + "</a></td>";
	while (rs5.next())
		{  				
		datiDiOggi5+="<td> " + rs5.getString(1).substring(8,10) +"-"+ rs5.getString(1).substring(5,7) +"-"+rs5.getString(1).substring(0,4) +rs5.getString(1).substring(10,19)+"</td>"  ;							
		Double aDouble = Double.parseDouble(rs5.getString(2)); 
		if (aDouble <= 0.35 ) 	  
		bullet= "<img src=\"img/b_green.png\" alt=\"\" >	"; 
		else
		bullet= "<img src=\"img/b_alert.png\" alt=\"\" >	"; 		
		datiDiOggi5 +=  " <td> " + bullet  + "  (" +  rs5.getString(2) + ")</td>";
			k4++;
		}

		
		
    String tsql = "SELECT TOP 1 [MD_CB_T001_TIMESTAMP_END] , cast( [MD_CB_T001_SUM_TIME] as decimal(10,2)) as tottime  " +
	" FROM [cbiLog].[dbo].[MD_CB_T001_TODAY]  order by [MD_CB_T001_TIMESTAMP_END] DESC"	;     
	rs = stmt.executeQuery( tsql);
	
	ResultSetMetaData metaData = rs.getMetaData();	   	   
	int columnCount = metaData.getColumnCount();	   
	int k = 0;
	
	datiDiOggi2+="<td><a href='#page5-cb1'> " +  "CBi"  + "</a></td>";
	while (rs.next())
		{  				
		datiDiOggi2+="<td> " + rs.getString(1).substring(8,10) +"-"+ rs.getString(1).substring(5,7) +"-"+rs.getString(1).substring(0,4) +rs.getString(1).substring(10,19)+"</td>"  ;							
		Double aDouble = Double.parseDouble(rs.getString(2)); 
		if (aDouble <= 1.1 ) 	  
		bullet= "<img src=\"img/b_green.png\" alt=\"\" >	"; 
		else
		bullet= "<img src=\"img/b_alert.png\" alt=\"\" >	"; 		
		datiDiOggi2+=  " <td> " + bullet  + "  (" +  rs.getString(2) + ")</td>";
			k++;
		}

			
		
	} catch (SQLException ex) 
		{
		out.println("SQLException: " + ex.getMessage());
		out.println("SQLState: " + ex.getSQLState());
		out.print("VendorError: " + ex.getErrorCode()) ;
		}
	finally {
    try { if (rs != null) rs.close(); } catch (Exception e) {};
	    try { if (rs1 != null) rs1.close(); } catch (Exception e) {};
		    try { if (rs2 != null) rs2.close(); } catch (Exception e) {};
			    try { if (rs3 != null) rs3.close(); } catch (Exception e) {};
				    try { if (rs4 != null) rs4.close(); } catch (Exception e) {};
					    try { if (rs5 != null) rs5.close(); } catch (Exception e) {};
						try { if (rs88 != null) rs88.close(); } catch (Exception e) {};
						try { if (rs102 != null) rs102.close(); } catch (Exception e) {};
    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (stmt1 != null) stmt1.close(); } catch (Exception e) {};
		    try { if (stmt2 != null) stmt2.close(); } catch (Exception e) {};
			    try { if (stmt3 != null) stmt3.close(); } catch (Exception e) {};
				    try { if (stmt4 != null) stmt4.close(); } catch (Exception e) {};
	    try { if (stmt5 != null) stmt5.close(); } catch (Exception e) {};
		try { if (stmt88 != null) stmt88.close(); } catch (Exception e) {};
		try { if (stmt102 != null) stmt102.close(); } catch (Exception e) {};
	
    try { if (conn != null) conn.close(); } catch (Exception e) {};
	try { if (conn1 != null) conn1.close(); } catch (Exception e) {};
	try { if (conn2 != null) conn2.close(); } catch (Exception e) {};
      }		



	
	%>
	<div data-role="page" id="infoPage" class="pageSwipe" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
		<a href="#nav-panelInfo" data-icon="bars" data-iconpos="notext">Menu</a>
		<a data-ajax="false" href="index.jsp?logout=true" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a></div><!-- /header -->
	
  <div data-role="content" >
      
     <table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          <th>Servizio</th>
          <th>LastUpdate</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <tr>
			<%out.println(datiDiOggi6);%>
        </tr>
        <tr bgcolor="#D0D0D0">
			<%out.println(datiDiOggi3);%>
        </tr>
        <tr>
			<%out.println(datiDiOggi1);%>        
		</tr>
        <tr bgcolor="#D0D0D0">
			<%out.println(datiDiOggi2);%>        
		</tr>
	     <tr>
			<%out.println(datiDiOggi4);%>        
		</tr>
	    <tr bgcolor="#D0D0D0">
			<%out.println(datiDiOggi5);%>        
			</tr>
      </tbody>
    </table>
<br><br> 

 </div>
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelInfo" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
	</div><!-- /page -->