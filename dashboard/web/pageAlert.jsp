
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="servlets.UtilityServlet"%>
<%@page import="java.sql.ResultSet"%>
<%ResultSet gruppi=UtilityServlet.getGroups();
%>
<div data-role="page" class="pageSwipe" id="pageAlert"  data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
	<a href="#nav-panelAlert" data-icon="bars" data-iconpos="notext">Menu</a>
	<a data-ajax="false" href="index.jsp?logout=true" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a>

	</div><!-- /header -->
<%if(gruppi.next()){ %>	
	<div role="main" class="ui-content"> 
   	 <p>        			
	 <form method="POST" action="AlertServlet">
	 <div data-role="fieldcontain">
    <label for="message">Alert da inviare</label>
	<textarea name="message" id="message"></textarea>
	<label for="group" class="select">Gruppo da allertare</label>
   <select name="group" id="group">
   <%do{ %>
   <option value="<%=gruppi.getString(1)%>"><%out.print(gruppi.getString(1));%></option>
   <%}while(gruppi.next()); %>
</select>
	<label for="submit-button"></label>
   <input name="submit-button" type="submit" value="Invia" />
   <a href="whatsapp://send?abid=45&text=Hello%2C%20World!">whatsapp</a>
</div>
	</form>		

    </p>
    </div><!-- /content --><%}else{ %>
    <div role="main" class="ui-content"> 
   	 <p> 
   	 <h3>Nessun gruppo di alert presente</h3>       
   	 </p>			
    <%} %>
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelAlert" />

	</jsp:include>
<div data-position="fixed" data-role="footer">
<h1>
<a href="http://www.cedacri.it/cedacri/it/index.html">
<img alt="logo" src="img/logo.png">
</a>
</h1>
</div><!-- /footer -->
	</div><!-- /page -->
	<%
	Statement s=gruppi.getStatement();
	Connection conn=s.getConnection();
	if(gruppi!=null) try{gruppi.close();}catch(Exception e){}
	if(s!=null) try{s.close();}catch(Exception e){}
	if(conn!=null) try{conn.close();}catch(Exception e){}
	%>
	