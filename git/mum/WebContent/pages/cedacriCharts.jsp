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

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <link href="../CSS/dashboard.css" rel="stylesheet">
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <link href="../CSS/dashboard.css" rel="stylesheet">
           <style type="text/css">
        #info{
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
                        <h3 class="page-header">Cedacri Charts
                       </h3>
                    </div>
                    
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                            <div class="row">
                     <div class="col-lg-4">
                          <div title="Seleziona sistema" class="btn-group" role="group" aria-label="...">
                              <button type="button"autofocus="true" class="btn btn-default target" value="GSY7"> GSY7</button>
                              <button type="button" class="btn btn-default target" value="BSY2"> BSY2</button>
                              <button type="button" class="btn btn-default target" value="CSY3"> CSY3</button>
                              <button type="button" class="btn btn-default target" value="ZSY5"> ZSY5</button>                  
                         </div>
                 <div id="expo">
                        <h5 >Excel exporter
                        <a  title="Esporta grafico in excel" id="excelExporter" href="#"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a></h5>
                                                      </div>
                        </div>
                        <div class="col-lg-7">
                        <div class="form-inline">
                               <button type="button" title="Vai indietro di una settimana" class="shift shift-left btn btn-default btn-circle"><i class="fa fa-angle-double-left"></i></button>
                               <button type="button" title="Vai indietro di un giorno" class="shift shift-left-one btn btn-default btn-circle"><i class="fa fa-angle-left"></i></button>
                               <h4 id="info"><input type="text" class="form-control"id="date-interval" size="22" readonly='true'></h4>
                               <button type="button" title="Vai avanti di una settimana" class="shift shift-right-one btn btn-default btn-circle"><i class="fa fa-angle-right"></i></button>
                               <button type="button" title="Vai avanti di un giorno" class="shift shift-right btn btn-default btn-circle"><i class="fa fa-angle-double-right"></i></button>
                               <input type="number" id="baseline_value" placeholder="Baseline">
                           </div>
                           </div>
                                
            <!-- /.col-lg-10 -->
            <div class="col-lg-1">
            <button title="Ritorna al grafico precedente" type="button" class="btn btn-default goUp" style="display:none"> <i class="fa fa-level-up fa-fw"></i></button>
            </div>
            <!-- /.col-lg-2 -->
            </div>
            <!-- /.row-->
               <div class="row">
               <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                                <div style="display:none" id="loading"></div>
                                <div id="container" style="min-width: 310px; height: 500px; margin: 0 auto">
                                </div>
                     <!-- /.panel-body -->            
                        </div>
                    <!-- /.panel -->    
                      </div>
                    
                </div>
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
        <!-- DataTables JavaScript -->
    <script src="../js/charts/cedacriWorkload.js" charset="utf-8"></script>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>
