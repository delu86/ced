<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE html>
<%@page import="object.User"%>
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
    	String prof=request.getParameter("profile");
    
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
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
    

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    
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
    <style type="text/css">
    td{
    font-size: 12px
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
                        <h3 class="page-header">CPU Measurements</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            DataTables CPU MF counters                           
                        <a href=""><img alt="excel" src="img/xls-48.png" height="24" width="24"></a>
                        </div>
                        <!-- /.panel-heading -->   
                        <div class="panel-body">
                     Date: <input type="text" id="datepicker" placeholder="aaaaMMgg" name="data"></p>
                       <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="tableMF">
                                    <thead>
                                        <tr>
                                            <th>System</th>
                                            <th>Date</th>
                                            <th>Cpu</th>
                                            <th>CLASS</th>
                                            <th>Duration</th>
                                            <th>MIPS</th>
                                            <th>CPI</th>
                                            <th>L1MP%</th>
                                            <th>RNI</th>
                                            <th>L2P</th>
                                            <th>L3P</th>
                                            <th>L4LP</th>
                                            <th>L4RP</th>
                                            <th>MEMP</th>
                                            <th>PRB_STATE</th>
                                            <th>AVG_UTIL</th>
                                        </tr>
                                    </thead>
                                    </table>
                                    </div>
                        
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
    <script src="../bower_components/jquery/dist/jquery-ui.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <script>
  	$(function() {
                    var date=new Date();
                    date.setDate(date.getDate()-1);
                    var d=date.yyyymmdd().replace("-","").replace("-","").substr(0,9);
                    var table = $('#tableMF').DataTable( {
                    paging: true,
                    processing:true,
                    responsive: true
        });
        $('.panel-heading')
                .html('<a  title="Esporta  in excel" id="excelExporter" href="'+
                "exporter?title=cpuMf&id=smf113/mfCounters&date="+d
                +'"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a></h5>'+
                'CPU MF counters');
                table.ajax.url("../queryResolver?id=smf113/mfCounters&date="+d).load();
        	$( "#datepicker" ).datepicker({ dateFormat: 'yymmdd', 
    			onSelect: function () {
                            $('.panel-heading')
                .html('<a  title="Esporta  in excel" id="excelExporter" href="'+
                "exporter?title=cpuMf&id=smf113/mfCounters&date="+d
                +'"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a></h5>'+
                'CPU MF counters');
  				table.ajax.url("../queryResolver?id=smf113/mfCounters&date="+$(this).val()).load();

    	    } }
);
  				});
  	</script>
    
<%} %>
</body>

</html>
