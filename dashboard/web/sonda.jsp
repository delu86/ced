<%@page import="servlets.UtilityServlet,java.util.Calendar"%>
<%               Calendar calendar=Calendar.getInstance();
				int hour=calendar.get(Calendar.HOUR_OF_DAY);
				int hour_1=hour-1;
				int hour_2=hour-2;
				if(hour_1==-1) hour_1=23;
				if(hour_2==-1) hour_2=23;
				if(hour_2==-2) hour_2=22;
    boolean[] status88=new boolean[4];
    boolean[] status102=new boolean[4];
	UtilityServlet.initialize(status88,status102);
%>

	<div data-role="page" id="pagesonda" class="pageSwipe" data-next="pagesms"data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
		<a href="#nav-panelsms" data-icon="bars" data-iconpos="notext">Menu</a>
		<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a></div><!-- /header -->
	
	<div role="main" class="ui-content"> 
    	<table data-role="table" class="ui-responsive">
      <thead>
        <tr>
          <th>Ora</th>
          <th>P2</th>
          <th>P1</th>
        </tr>
      </thead>
      <tbody>
	  <%int i=0;
	  while(i<4){
	  %>
	  <tr <%if(i%2==0){%>bgcolor="#D0D0D0" <%}%>>
	  <td><%switch(i){
	    case 0:out.print(String.valueOf(hour_1)+":30"); break; 
		case 1:out.print(String.valueOf(hour_1)+":00"); break;
	  case 2:out.print(String.valueOf(hour_2)+":30"); break;
	  case 3:out.print(String.valueOf(hour_2)+":00"); break;
	  }%></td>
	  <td><%if(status88[i]){%><img src="img/b_green.png" alt="green" ><%}else{%><img src="img/b_alert.png" alt="alert" ><%}%></td>
	  <td><%if(status102[i]){%><img src="img/b_green.png" alt="green" ><%}else{%><img src="img/b_alert.png" alt="alert" ><%}%></td>
	  </tr>
	  <%i++;}%>
	  </tbody>
</table>
	</div><!-- /content -->
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelsms" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
	</div><!-- /page -->
		<div data-role="page" id="pagesms" class="pageSwipe" data-prev="pagesonda" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
		<a href="#nav-panel1" data-icon="bars" data-iconpos="notext">Menu</a>
		<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a></div><!-- /header -->
	
	<div role="main" class="ui-content"> 
    	<img src="../kiroom/img/S09SMCR.png" alt="" style="width: 100%;">
	</div><!-- /content -->
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panel1" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
	</div><!-- /page -->