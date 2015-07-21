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
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    	if(!(profile.equals("CED")||profile.equals("REALE"))){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
    
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

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- JQuery UI -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <style type="text/css">
        #date-interval{
        display:inline-block;
        }
        .shift{
        display:inline-block;
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
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">Andamento transazione: APA01</h3>
                        </div>
                        <!-- /.col-lg-12 -->
                        </div>
                        <!-- /.row -->
                    <div class="row">
                    <div class="col-lg-5">
                    </div>
                       <!-- /.col-lg-4 -->
                    <div class="col-lg-4">
                              <div class="form-inline">
                               <button type="button" class="shift shift-left-one btn btn-default btn-circle"><i class="fa fa-angle-left"></i></button>
                               <h5 id="date-interval"></h5>
                               <button type="button" class="shift shift-right-one btn btn-default btn-circle"><i class="fa fa-angle-right"></i></button>
                               </div>
                               </div>
                       <!-- /.col-lg-4 -->
                       <div class="col-lg-3">
                              </div>
                       <!-- /.col-lg-4 -->
                        </div>
                        <!-- /.row -->
                    <div class="row">
                    <div class="col-lg-12">                    
                    <div id="container" style="min-width: 310px; height: 500px; margin: 0 auto"></div>
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
    <script src="../js/charts/realeTrans.js" charset="utf-8"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>
