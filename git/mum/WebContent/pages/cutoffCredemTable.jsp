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
    <meta charset="utf-8">
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
    	if(!(profile.equals("CED")||profile.equals("CREDEM"))){
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
    <!-- JQUERY UI --> 
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        .div-exporter{
            padding-top: 10px;
        }
        .ui-datepicker {
            border: 0px ;
        }
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
        <div id="page-wrapper">
            <div class="container-fluid">
      <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Cutoff report mensile</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
             <div class="row">
                <div class="col-lg-6">
                     <div id="datepicker"></div>
                              </div>
                <!-- /.col-lg-6 -->               
            </div>
            <div class="row div-exporter">
                <div class="col-lg-6">
                     <a id="exporter" href="#">Esporta in Excel
                         <img alt="excel" src="img/xls-48.png" height="24" width="24"></a>
                              </div>                
            </div>
            <!-- /.row -->       
               <div class="row">
                    <div class="col-lg-12">
                        
                            <div class="dataTable_wrapper">
                                <table id="table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>Jobname</th>
                                            <th>Mese</th>
                                            <th>Procedura</th>
                                            <th>Ora cutoff</th>
                                            <th>#eventi chiusi a meno di 30min dal cutoff</th>
                                            <th>#eventi oltre il cutoff</th>
                                            <th>%eventi oltre il cutoff</th>
                                            <th>%eventi chiusi 2 ore prima cutoff</th>
                                            <th>totale eventi</th>
                                         </tr>
                                    </thead>
                                    </table>
                                    </div> 
                    <!-- /.dataTable_wrapper -->
                        
                                    </div>
                    <!-- /.col-lg -->  
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
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script type="text/javascript"> 
        var period;
        var table = $('#table').DataTable( {
            paging: false,
            processing:true,
            responsive: true,
               "columnDefs": [
            {
                // The `data` parameter refers to the data for the cell (defined by the
                // `data` option, which defaults to the column being worked with, in
                // this case `data: 0`.
                "render": function ( data, type, row ) {
                    return "<a href='cutoffCredemChart.jsp?jobname="+data+"&period="+period+"' target='_blank'>"+data+"</a>" ;
                },
                "targets": 0
            }
        ]
    
        });

        
        function updateTable(){
            period=$("#datepicker").datepicker("getDate").yyyymmdd().substr(0,7);
            table.ajax.url("../queryResolver?id=cutoff/cutoffReport&period="+period).load();
            $("#exporter").prop('href','exporter?title=cutoff_'+period+'&id=cutoff/cutoffReport&period='+period);
        };
       
        $(function(){
                 $("#datepicker").datepicker({
                    onChangeMonthYear: function (year, month, inst) {
                                  mm=month.toString();
                                  mm=mm[1]?mm:"0"+mm[0];
                                  $("#datepicker").datepicker('setDate',mm +'/01/'+year);
                                  updateTable();
                                    }
                });
                updateTable();}
                
        );

    </script>
</body>
<%} %>
</html>
