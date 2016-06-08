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
    final String  SELECT="SELECT * from epv_parserconfig13.agent_resource;";
    Connection conn=null;
    Statement statement=null;
    ResultSet rs=null;
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://10.99.252.22/","epv","");
    statement=conn.createStatement();
    rs=statement.executeQuery(SELECT);
 

%>
	<div data-role="page" id="agentPage" class="pageSwipe" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>EpvMonitor</h1>
		<a href="#nav-panelAgent" data-icon="bars" data-iconpos="notext">Menu</a>
		
        </div>
  <div data-role="content" >
     <table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          <th>Agent ID</th>
          <th>Agent Status</th>
                    
        </tr>
      </thead>
      <tbody>
          <%int i=0; while(rs.next()){%>
          <tr <% if(i%2==0){%>bgcolor="#D0D0D0"<% }%> >
              <td><%out.print(rs.getString(1));%></td>
              <td><%if(rs.getString(2).equals("RUNNING")){%><img src="img/b_green.png" alt="OK" >
                  <%}else{%><img src="img/b_alert.png" alt="OK" ><%}%></td>
              
              
          </tr>
          <% i++;}%>
      </tbody>
     </table>
  </div><!-- end content-->
        
<jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelAgent" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
<%   rs.close();
     statement.close();
     conn.close();
          %>
	</div><!-- /page -->