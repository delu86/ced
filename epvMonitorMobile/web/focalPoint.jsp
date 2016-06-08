<%-- 
    Document   : focalPoit
    Created on : 9-nov-2015, 15.26.53
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
    

    
    final String  SELECT= "SELECT * from support.epvfocalPoint where date=date(now());";
    Connection conn=null;
    Statement statement=null;
    ResultSet rs=null;
    Class.forName("com.mysql.jdbc.Driver");
    conn=DriverManager.getConnection("jdbc:mysql://10.99.252.22/","epv","");
    statement=conn.createStatement();
    rs=statement.executeQuery(SELECT);
    

%>

<div data-role="page" id="focalPointPage" class="pageSwipe" data-dom-cache="true">
<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>EpvMonitor</h1>
		<a href="#nav-panelFocalPoint" data-icon="bars" data-iconpos="notext">Menu</a>
		
        </div>
  <div data-role="content" >
     <table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          
          <th>Epv Product</th>
          <th>Status</th>
          <th>Last change</th>
        </tr>
      </thead>
      <tbody>
          
          <% int i=0;while(rs.next()){ %>
          <tr <% if(i%2==0){%>bgcolor="#D0D0D0"<% }%>>
            
            <td><%out.print(rs.getString(2));%></td>
            <%if(rs.getString(3).equals("Ok")||rs.getString(3).equals("Running")){%>
            <td><img src="img/b_green.png" alt="OK" ><% out.print(rs.getString(3));%></td>
           
            <%}else{%>
            <td><img src="img/b_alert.png" alt="KO" ><% out.print(rs.getString(3));%></td>
            
            <%}%>	        
            <td><%out.print(rs.getString(4));%></td>
		</tr>
                <%i++;}%>
      </tbody>
     </table>
  
  
  
  </div> <%--end content--%>




   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelFocalPoint" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
<%   rs.close();
     statement.close();
     conn.close();
          %>
	</div><!-- /page -->