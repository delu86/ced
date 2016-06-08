<%-- 
    Document   : dashboard
    Created on : 5-nov-2015, 14.46.23
    Author     : CRE0260
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java"  %> 

<%
    
    
Calendar calToday = Calendar.getInstance();


              
Date dateTodayd = calToday.getTime();             
SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
String date1 = format1.format(dateTodayd);        
    String SELECT_AGENT="SELECT count(*) tot_stopped from epv_parserconfig13.agent_resource where agent_status<>'RUNNING';";
    String SELECT_SWITCH_DB="SELECT date(ARCHIVE_DATETIME),t1.DbName,STageDb_Next,t3.DbName,Status from" +
                            " ((SELECT * FROM epv_parserconfig13.archive_stagedb order by ARCHIVE_DATETIME DESC LIMIT 1) t1"+
                            " LEFT JOIN epv_parserconfig13.anag_stagedb  on t1.DbName=stageDB_dbName) left join"+
                            " (SELECT * FROM epv_parserconfig13.loader_status) t3 on StageDb_Next=t3.DbName";
    String SELECT_VOLUMI="SELECT round(sum(size)/1000000000,1) as GB,date(dataContent) as data"+
                         " from support.nfstransfer where date(dataContent)=date(now()) group by date(dataContent) order by date(dataContent) desc ";
    String SELECT_EPVFOCALPOINT_ERROR="SELECT count(*) from support.epvfocalPoint where status in ('Ok','Running') and date=date(now()) ;";
    
    String SELECT_PROCESS_LIST="SELECT count(*) FROM INFORMATION_SCHEMA.PROCESSLIST where time>1800 and command<>'Sleep';";
    
    Connection conn=null;
    Statement st1=null;
    Statement st2=null;
    Statement st3=null;
    Statement st4=null;
    Statement st5=null;
    ResultSet rs1=null;
    ResultSet rs2=null;
    ResultSet rs3=null;
    ResultSet rs4=null;
    ResultSet rs5=null;
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://10.99.252.22/","epv","");
    st1=conn.createStatement();
    st2=conn.createStatement();
    st3=conn.createStatement();
    st4=conn.createStatement();
    st5=conn.createStatement();
    rs1=st1.executeQuery(SELECT_AGENT);
    rs2=st2.executeQuery(SELECT_SWITCH_DB);
    rs3=st3.executeQuery(SELECT_VOLUMI);
    rs4=st4.executeQuery(SELECT_EPVFOCALPOINT_ERROR);
    rs5=st5.executeQuery(SELECT_PROCESS_LIST);
    rs1.next();
    rs2.next();
    rs3.next();
    rs4.next();
    rs5.next();
%>

	<div data-role="page" id="infoPage" class="pageSwipe" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>EpvMonitor</h1>
		<a href="#nav-panelInfo" data-icon="bars" data-iconpos="notext">Menu</a>
		
        </div>
  <div data-role="content" >
     <table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          <th>Servizio</th>
          <th>Status</th>
          <th>Info</th>
        </tr>
      </thead>
      <tbody>
	
	<tr>
            <td><a href="#focalPointPage">EpvFocalPoint</a></td>
            <%if(rs4.getInt(1)==3){%>
            <td><img src="img/b_green.png" alt="OK" ></td>
            <td><%out.print("Nessun errore"); %></td>
            <%}else{%>
            <td><img src="img/b_alert.png" alt="KO" ></td>
            <td><%out.print("Errore o log assenti"); %></td>
            <%}%>	</tr>		
        
        <tr bgcolor="#D0D0D0">
            <td><a href="#agentPage">Perl Agent</a></td>
            <%if(rs1.getInt(1)==0){%>
            <td><img src="img/b_green.png" alt="OK" ></td>
            <td>15 processi attivi</td>
            <%}else{%>
            <td><img src="img/b_alert.png" alt="KO" ></td>
            <td>Uno o più processi non attivi!</td>
            <%}%>
        </tr>
        <tr>
            <td><a href="#smfPage">Volumi Elaborati</a></td>
            <td><%out.print(rs3.getString(1));%>GB</td>
            <td><%out.print(rs3.getString(2));%></td>	        
		</tr>
        <tr bgcolor="#D0D0D0">
            <td><a href="#switchPage"> Switch DB</a></td>
            <%if(rs2.getString(5).equals("PARSER")&&rs2.getString(1).equals(date1)){%>
            <td><img src="img/b_green.png" alt="OK" ></td>
            <td><%out.print(rs2.getString(4));%></td>
            <%}else{%>
            <td><img src="img/b_alert.png" alt="KO" ></td>
            <td>IL DATABASE NON HA SWITCHATO!</td>
            <%}%>	        
		</tr>
                <tr>
            <td><a href="#mysqlPage"> MySQL Process List</a></td>
            <%if(rs5.getInt(1)==0){%>
            <td><img src="img/b_green.png" alt="OK" ></td>
            <td>OK</td>
            <%}else{%>
            <td><img src="img/b_alert.png" alt="KO" ></td>
            <td>Processo in macchina da più di 30 min.</td>
            <%}%>                   
		</tr>
	 
    </table>
 

 </div>
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelInfo" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
	</div><!-- /page -->
        <% rs1.close();
           rs2.close();
           rs3.close();
           rs4.close();
           rs5.close();
           st1.close();
           st2.close();
           st3.close();
           st4.close();
           st5.close();
           conn.close();%>