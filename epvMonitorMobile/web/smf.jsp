<%-- 
    Document   : agent
    Created on : 5-nov-2015, 16.21.19
    Author     : CRE0260
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<% 
    final String  SELECT="SELECT SYSTEM,count(*) as tot_file,round(sum(size)/1000000000,1) as GB,date(dataContent),MAX(dataContent) from support.nfstransfer RIGHT JOIN support.sid"+ 
                        " on substr(Q1,8,1)=sid where date(dataContent)=date(now()) group by system,date(dataContent) order by date(dataContent) DESC,system;";
    Connection conn=null;
    Statement statement=null;
    ResultSet rs=null;
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://10.99.252.22/","epv","");
    statement=conn.createStatement();
    rs=statement.executeQuery(SELECT);
 

%>
	<div data-role="page" id="smfPage" class="pageSwipe" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>EpvMonitor</h1>
		<a href="#nav-panelSMF" data-icon="bars" data-iconpos="notext">Menu</a>
		
        </div>
  <div data-role="content" >
     <table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          <th>Sistema</th>
          <th>#File elaborati</th>
          <th>Volumi elaborati</th>
          <th>Ultimo arrivo</th>
        </tr>
      </thead>
      <tbody>
          <%int i=0; while(rs.next()){%>
          <tr <% if(i%2==0){%>bgcolor="#D0D0D0"<% }%> >
              <td><b><%out.print(rs.getString(1));%></b></td>
              <td><%out.print(rs.getString(2));%></td>
              <td><%out.print(rs.getString(3));%>GB</td>
              <td><%out.print(rs.getString(5));%></td>
            
              
          </tr>
          <% i++;}%>
      </tbody>
     </table>
  </div><!-- end content-->
        
<jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelSMF" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
<%   rs.close();
     statement.close();
     conn.close();
          %>
	</div><!-- /page -->