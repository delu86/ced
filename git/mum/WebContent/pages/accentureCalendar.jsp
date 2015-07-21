<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
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
<head>
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
    <!-- JqueryUI CSS -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <link href="../CSS/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <link href="../CSS/dashboard.css" rel="stylesheet">
    <link href='../CSS/fullcalendar.css' rel='stylesheet' />
    <link href='../CSS/fullcalendar.print.css' rel='stylesheet' media='print' />
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
                <div class="col-lg-2">
                </div>
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-8">
             <h3 class="page-header">       <div class="form-jooble">
                    <input class="input-search form-control" id="autocomplete" placeholder="Search for JOB name ..." >
                   </div></h3>
                    </div>
                    <!-- /.col-lg-8 -->
                    <div class="col-lg-2">
                    </div>
                    <!-- /.col-lg-2 -->
                    </div>
                    <!-- /.row -->
                
                <div class="row">
                <div class="col-lg-12">
                <div id="calendar">
               <div style="display:none" id="loading"></div> 
                </div>
                </div>
                <!-- /.!col-lg-12 -->
                </div>
                <!-- /.row -->
    </div>
     <!-- /.container-fluid-->
     </div>
      <!-- /.page-wrapper -->
<%} %>
</body>
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- jQuery UI (per datepicker)-->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src='../js/moment.min.js'></script>
    <script src='../js/fullcalendar.min.js'></script>
    <script src="../js/charts/accentureCalendar.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</html>