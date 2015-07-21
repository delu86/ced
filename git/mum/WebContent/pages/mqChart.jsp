<%@page import="java.util.Collection"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE html>
<%@page import="object.User"%>
<html lang="en">
<head>
<%
	User user=(User)request.getSession().getAttribute("user");
    	String profile=user.getProfile();
    	Collection<String> systems=(Collection<String>) request.getAttribute("systems");  
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

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">
     <!-- JqueryUI CSS -->
     <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <link rel="stylesheet" href="../CSS/dashboard.css"> 
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
.ui-datepicker-calendar {
    display: none;
    }
</style>
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
        <div style="display:none" id="loading"></div> 
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">Volumi MQ</h3>
                    
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                                <div class="row">
                    <div class="col-lg-10">
                    <div class="btn-group" role="group" aria-label="...">
                    <%
                    int i=0;
                    for(String sys: systems){ %>
                              <button type="button" <%if(i==0){%>autofocus="true"<% i++;}%>class="btn btn-default target" value="<%=sys%>"> <%out.print(sys); %></button>
                  <%} %>
                    </div>    
                    </div>
                    <!-- /.col-lg-9 -->
                    <div class="col-lg-2">
                    <input type="text" class="form-control"id="date-interval" size="22" readonly='true'>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                      <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto">
                      
                      </div>
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
    <!-- jQuery UI (per datepicker)-->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../js/highcharts.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/exporting.js"></script>
    <script src="../js/charts/mqChart.js" charset="utf-8"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
</body>

</html>
