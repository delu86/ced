<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@page import="utility.UtilityDate"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp");
    else{
    	String profile=user.getProfile();
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
    <link href="../CSS/dashboard.css" rel="stylesheet">
    <style>
    .fa-question{
    	float:right
    }
    </style>

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
                        <h3 class="page-header">Statistics Dashboard</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                 <img src=img/Epv.png>
                                <!--     <i class="fa fa-bar-chart-o fa-5x"></i>-->
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"> <%if(profile.equals("CED")){ %>4<%}else{ %>1<%} %></div>
                                    <div>EPV</div>
                                </div>
                            </div>
                        </div>
                        <a href="epv.jsp">
                            <div class="panel-footer">
                                <span class="pull-left">zOS Performance Analysis</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                 <%if(profile.equals("CED")){ %>
                       <div class="col-lg-3 col-md-6">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa  fa-bar-chart-o fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">0</div>
                                    <div>Analysis</div>
                                </div>
                            </div>
                        </div>
                        <a href="analysis.jsp">
                            <div class="panel-footer">
                                <span class="pull-left"> White paper, Screenshot, ... </span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div><%} %>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-folder fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">8</div>
                                    <div> Guide</div>
                                </div>
                            </div>
                        </div>
                        <a href="documents.jsp">
                            <div class="panel-footer">
                                <span class="pull-left">User's Guide & Books</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                 <%if(profile.equals("CED")){ %>
                       
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa  fa-dashboard fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">2</div>
                                    <div>Cast</div>
                                </div>
                            </div>
                        </div>
                        <a href="cast.jsp">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div><%} %>
            </div>
            <!-- /.row -->
             <%if(profile.equals("CED")){ %>
                       
             <div class="row">
             <div class="col-lg-6">
             <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i>Consumo MIPS <%out.print(UtilityDate.conversionToVisulaformat(UtilityDate.getDate(-1)));%>
                        <i class="fa fa-question fa fw"></i>
                        </div>
                        <div class="panel-body">
                            <div id="container">
                            <img class="loading" alt="loading" src="img/loading.gif">
                            </div>    
                        </div>
                        <!-- /.panel-body -->
             </div>
             </div>
             <!-- /.col-lg-6 -->   
             <div class="col-lg-6">
             <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> CPI <%out.print(UtilityDate.conversionToVisulaformat(UtilityDate.getDate(-1)));%>
                          <i class="fa fa-question fa fw"></i>
                          </div>
                        <div class="panel-body">
                            <div id="container2">
                            <img class="loading" alt="loading" src="img/loading.gif">
                            </div>
                            
                        </div>
                        <!-- /.panel-body -->
             </div>
             </div>
             <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
           <!--     
            <div>
             
            <div class="col-lg-12">
             <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> CPU number <%out.print(UtilityDate.conversionToVisulaformat(UtilityDate.getDate(-1)));%>
                          <i class="fa fa-question fa fw"></i>
                          </div>
                        <div class="panel-body">
                            <div id="container3">
                            <img class="loading" alt="loading" src="img/loading.gif">
                            </div>
                            
                        </div>
                        
             </div>
             </div>
                         
            
            </div>
                          -->
            <!-- /.row -->
            <%} %>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- jQuery UI-->
    <script src="../bower_components/jquery/dist/jquery-ui.min.js"></script>
    

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
     
     <script src="../js/highcharts.js"></script>
     
     <script src="../js/data.js"></script>
     
     <script src="../js/exporting.js"></script>
     <!-- Charts  JavaScript -->
     <script src="../js/charts/lastDayMips.js" charset="utf-8"></script>
     <script src="../js/charts/lastDayCpi.js"></script>
     <script src="../js/charts/lastDayCpu.js"> </script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>
