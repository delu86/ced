<%@page import="utility.UtilityDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@page import="object.ProcessInformation"%>
<%@page import="object.DiskInformation"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    	if(!profile.equals("CED")){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
    
%>
<%
@SuppressWarnings("unchecked")
Collection<DiskInformation> disksInfo=(Collection<DiskInformation>)request.getAttribute("disksInformation");
@SuppressWarnings("unchecked")
Collection<ProcessInformation> processInfo=  (Collection<ProcessInformation>)request.getAttribute("process");
String datastage=(String) request.getAttribute("datastage");
@SuppressWarnings("unchecked")
HashMap<String,Boolean> map=(HashMap<String,Boolean>) request.getAttribute("mapSystemSlot");
%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Cedacri Statistics</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    
    <!-- DataTables CSS -->
    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="../bower_components/datatables-responsive/css/dataTables.responsive.css" rel="stylesheet">
    

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">
    
            <!-- Navigation -->
       <jsp:include page="nav/navbar.jsp">
       <jsp:param name="profile" value="<%=profile %>" />
       </jsp:include>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                
                    <div class="col-lg-12">
                        <h3 class="page-header">Admin Server</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
              <div class="row">
                    <div class="col-lg-12">
                              <div class="panel panel-default">
                        <div class="panel-heading">
                            Disks information
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                    <div class="table-responsive">
                     <table class="table table-hover">
					<thead>
						<tr>
						<th>File System</th>
						<th>Disk space</th>
						<th>Used space</th>
						<th>Available space</th>
						<th>Use%</th>
						<th>Mounted on</th>
						<th>State</th>
						</tr>
					</thead>
					<tbody>
					<%for(DiskInformation d :disksInfo){ 
  							float perc=d.getUsedPercent();
   															 %>
					<tr>
					<td><% out.print(d.getFileSystem());%> </td>
					<td><% out.print(d.getDiskSpace());%>KB </td>
					<td><% out.print(d.getUsedSpace());%>KB </td>
				    <td><% out.print(d.getAvailableSpace()); %>KB </td>
				    <td><% out.print(String.format("%.1f", perc));%>%</td>
					<td><% out.print(d.getMountedOn()); %> </td>
					<td><%if(perc<90){%><img alt="green" src="img/b_green.png"><%}else{%><img alt="alert" src="img/b_alert.png"><%} %></td>
					</tr>
										<%}%> 
				</tbody>
				</table>   
				    </div>
				    </div>
				    </div>
				    
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                                <!-- /.row -->
              <div class="row">
                    <div class="col-lg-12">
                              <div class="panel panel-default">
                        <div class="panel-heading">
                           Sonda Workload <%out.print(UtilityDate.conversionToVisulaformat(UtilityDate.getDate(-1)));%>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                    <div class="table-responsive">
                     <table class="table table-hover">
					<thead>
						<tr>
						<th>System</th>
						<th>Empty slot?</th>
						</thead>
						<tbody>
						<%    Iterator it = map.entrySet().iterator();
                      while (it.hasNext()) {
        Map.Entry <String,Boolean> entry = (Map.Entry)it.next();
        %>
						
					<tr>	
					<td><a href="sondaWorkload?system=<%=entry.getKey()%>&date=<%=UtilityDate.conversionToEpvformat(UtilityDate.getDate(-1)) %>"><%out.print(entry.getKey()); %></a></td>
					<td><%if(entry.getValue()==true){ %><img alt="red" src="img/b_alert.png"> <%}else{ %><img alt="red" src="img/b_green.png">  <%} %></td>
					</tr>
				<%} %>	
				</tbody>
				</table>   
				    </div>
				    </div>
				    </div>
				    
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                
                <div class="row">
                    <div class="col-lg-12">
                              <div class="panel panel-default">
                        <div class="panel-heading">
                            EPV log CEDACRI
                        </div>
                        <!-- /.panel-heading -->
                        <div  class="panel-body">
                        <iframe width="100%" height="300" src="http://10.99.252.22/datifs/LOGS/CEDACRI/LOGS/EPVFocalPoint.HTML"
  							frameBorder="0"
  							>
  							</iframe>
                        </div>
                        <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                        </div>
                        <!-- /.row -->
                                        <div class="row">
                    <div class="col-lg-12">
                              <div class="panel panel-default">
                        <div class="panel-heading">
                            EPV log CREDEM
                        </div>
                        <!-- /.panel-heading -->
                        <div  class="panel-body">
                        <iframe width="100%" height="300" src="http://10.99.252.22/datifs/LOGS/CREDEM/LOGS/EPVFocalPoint.HTML"
  							frameBorder="0"
  							>
  							</iframe>
                        </div>
                        <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                        </div>
                        <!-- /.row -->
                                        <div class="row">
                    <div class="col-lg-12">
                              <div class="panel panel-default">
                        <div class="panel-heading">
                            EPV log REALE
                        </div>
                        <!-- /.panel-heading -->
                        <div  class="panel-body">
                        <iframe width="100%" height="300" src="http://10.99.252.22/datifs/LOGS/REALE/LOGS/EPVFocalPoint.HTML"
  							frameBorder="0"
  							>
  							</iframe>
                        </div>
                        <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                        </div>
                        <!-- /.col-lg-12 -->
                        </div>
                        <!-- /.row -->

              <div class="row">
                    <div class="col-lg-12">
             <div class="panel panel-default">
                        <div class="panel-heading">
                            EPV process
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                    
                    <div class="table-responsive">
				<table class="table table-hover">
				<thead>
				<tr>
				<th>USER</th>
				<th>PID</th>
				<th>%CPU</th>
				<th>%MEM</th>
				<th>VSZ</th>
				<th>RSS</th>
				<th>TTY</th>
			    <th>STAT</th>
				<th>START</th>
				<th>TIME</th>
				<th>COMMAND</th>
				</tr>
				</thead>	
				<tbody>
				<%for(ProcessInformation p :processInfo){ 
   					 %>
					<tr>
					<td><% out.print(p.getUser());%></td>
					<td><% out.print(p.getPid());%></td>
					<td><% out.print(p.getCpuPerc());%></td>
					<td><% out.print(p.getMemoryPerc());%></td>
					<td><% out.print(p.getVsz());%></td>
				    <td><% out.print(p.getRss());%> </td>
				    <td><% out.print(p.getTty());%> </td>
					<td><% out.print(p.getStat());%> </td>
					<td><% out.print(p.getStart());%> </td>
					<td><% out.print(p.getTime());%> </td>
					<td><% out.print(p.getCommand());%> </td>
					</tr>
					<%}%> 
					</tbody>
					</table>                    
					</div>
					<!-- /.table-responsive -->
					</div>
					<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
					</div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                
                
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    

</body>
<%} %>
</html>
