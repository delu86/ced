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
    <meta charset="latin">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
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
    <title>Cedacri Statistics</title>
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- JqueryUI CSS -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
    .input-search {
      padding-left: 10px;
      border: solid 5px #c9c9c9;
      transition: border 0.3s;
    }
        .input-search:focus {
       border: solid 5px #969696;
        outline: 0;
   }
   .hidden{
   	display:none;
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
                        <h3 class="page-header">Statistiche ATM  ----- BETA TEST -----</h3>
                    </div>
                     <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <h4 class="description"><b>Mese: </b> Settembre 2015   <b>Istituto: </b> Credem  <b>Codice ABI : </b>03032</h4>
                    </div>
                    <div class="row">
                    <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-table fa-fw"></i> Riepilogo attività ATM
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                             <div class="dataTable_wrapper" >
                                <table id="table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-batch">
                                    <thead>
                                        <tr>
                                            <th >Codice ATM</th>
                                            <th >#Giorni</th>
                                            <th >#Ore OK</th>
                                            <th >#Ore KO</th>
                                            <th >%KO</th>
                                            <th >#Ore OK F.A.R.O.</th>
                                            <th >#Ore KO F.A.R.O.</th>
                                            <th >%KO F.A.R.O.</th>
                                            <th >#Ore KO SLA7</th>
                                            <th >%KO SLA7</th>
                                            <th >#Ore KO SLA8</th>
                                            <th >%KO SLA8</th>
                                        </tr>
                                    </thead>
                                    </table>
                                    </div> 
                    <!-- /.dataTable_wrapper -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                        <!-- /.panel-default -->
                    </div>
                        <!-- /.col-lg-8 -->
                      
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
    <!-- jQueryUI -->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
     <!-- Highcharts JavaScript -->
    <script src="../js/highcharts.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/exporting.js"></script>
    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script>
        $(function () {
        var table = $('#table').DataTable({
                 paging: true,
    processing:true,
    responsive: true,
    "dom": '<"top"i>rt<"bottom"flp><"clear">',
    columns:[
             { "data": "CODATM" },
             { "data": "GIORNI" },
             { "data": "ORE_OK" },
             { "data": "ORE_KO" },
             { "data": "PERC_KO" },
             { "data": "ORE_OK_FARO" },
             { "data": "ORE_KO_FARO" },
             { "data": "PERC_KO_FARO" },
             { "data": "ORE_KO_SLA7" },
             { "data": "PERC_KO_SLA7" },
             { "data": "ORE_KO_SLA8" },
             { "data": "PERC_KO_SLA8" }]
                });
                           table.ajax.url("atmStat?codAbi=03032&period=2015-09").load();     

       
       });

    </script>    
</body>
<%} %>
</html>
