<%-- 
    Document   : switchdb
    Created on : 5-nov-2015, 16.21.39
    Author     : CRE0260
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="windows-1250"%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<% 
    
    Calendar calToday = Calendar.getInstance();
    Date dateTodayd = calToday.getTime();             
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    String date1 = format1.format(dateTodayd);        
    
    
    final String  SELECT= "SELECT date(ARCHIVE_DATETIME),ARCHIVE_DATETIME,t1.DbName,STageDb_Next,t3.DbName,Status from "+
                          " ((SELECT * FROM epv_parserconfig13.archive_stagedb order by ARCHIVE_DATETIME DESC LIMIT 1) t1"+
                          " LEFT JOIN epv_parserconfig13.anag_stagedb  on t1.DbName=stageDB_dbName) left join"+
                          " (SELECT * FROM epv_parserconfig13.loader_status) t3 on StageDb_Next=t3.DbName";
    Connection conn=null;
    Statement statement=null;
    ResultSet rs=null;
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://10.99.252.22/","epv","");
    statement=conn.createStatement();
    rs=statement.executeQuery(SELECT);
    rs.next();

%>

<div data-role="page" id="switchPage" class="pageSwipe" data-dom-cache="true">
<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>EpvMonitor</h1>
		<a href="#nav-panelSwitch" data-icon="bars" data-iconpos="notext">Menu</a>
		
        </div>
  <div data-role="content" >
     <table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          <th>Data switch</th>
          <th>Parser attivo</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
          <tr bgcolor="#D0D0D0">
            <td><%out.print(rs.getString(2));%></td>
            <td><%out.print(rs.getString(5));%></td>
            <%if(rs.getString(6).equals("PARSER")&&rs.getString(1).equals(date1)){%>
            <td><img src="img/b_green.png" alt="OK" ></td>
           
            <%}else{%>
            <td><img src="img/b_alert.png" alt="KO" ></td>
            
            <%}%>	        
		</tr>
      </tbody>
     </table>
  
  
  
  </div> <%--end content--%>




   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelSwitch" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
<%   rs.close();
     statement.close();
     conn.close();
          %>
	</div><!-- /page -->