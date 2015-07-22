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
    	if(!profile.equals("CED")&&!profile.equals("REALE")){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
    
%>
    <title>Cedacri Statistics</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    <link rel="stylesheet" href="../CSS/dashboard.css">    
    

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <style type="text/css">
        table{
        font-size: 10px
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
                <div style="display:none" id="loading"></div> 
        
        <div id="page-wrapper">
            <div class="container-fluid">
                                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">Batch error</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                            <div class="row" >
                  <div class="col-lg-11">
                          <div class="btn-group" role="group" aria-label="...">
                              <button type="button"autofocus="true" class="btn btn-default target" value="SIES"> SIES</button>
                              <button type="button" class="btn btn-default target" value="SIGE"> SIGE</button>                  
                         </div>
                   </div>
                   <!-- /.col-lg-11 -->
                               <div class="col-lg-1">
                             <button type="button" class="btn btn-default goUp" style="display:none;"> 
                             <i class="fa fa-level-up fa-fw"></i></button>
                              </div>
                    <!-- /.col-lg-1 -->
                   </div>
                   <!-- /.row -->
                <div class="row" id="first-level">
                <div class="col-lg-8">
                <div class="panel panel-default">
                      <div class="panel-heading" id="heading-graph">
                    Abend SIES
                    </div>
                    <!-- /.panel -heading -->
                    <div class="panel-body">
                    
                        
                    <div id="container" style="min-width: 310px; height: 500px; margin: 0 auto">
                    </div>
                    </div>
                    <!-- /.col-lg-8 -->
                    </div>
                    </div>
                    <div class="col-lg-4">
                    
                     <div class="panel panel-default">
                      <div class="panel-heading" id="heading-table">
                    Top 10 ABEND consumer
                    </div>
                    <!-- /.panel -heading -->
                    <div class="panel-body">
                                      
                    <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-top10">
                                      <thead>
            <tr>
                <th>Ora</th>
                <th>CONDCODE</th>
                <th>JOB</th>
                <th>CPUTIME</th>
                <th>COUNT</th>
                <th>ZIPTIME</th>
            </tr>
        </thead>

                                    </table>
                                    </div>
                                    
                    </div>
                    <!-- /.body -->
                  </div>
                  <!-- /.panel -->  
                    </div>
                    <!-- /.col-lg-4 -->
                </div>
                <!-- /.row -->
               <div class=row id="second-level" style="display:none;">
                   <div class="col-lg-12">
                                      <div class="panel panel-default panel-table">
                        <div class="panel-heading" id="heading-detail">
                        <a href="#" id="excel-export-interval"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a>
                        </div>
                        <!-- /.panel-heading -->   
                        <div class="panel-body">
        
                                <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-drill">
                                    <thead>
                                        <tr>
                                            <th>JOB</th>
                                            <th>Ora</th>
                                            <th>Count</th>
                                            <th>Cpu time.</th>
                                            <th>Resp. time</th>               
                                            <th>ZIPTIME</th>
                                            <th>CONDCODE</th>
                                            <th>User</th>

                                         </tr>
                                    </thead>
                                    </table>
                                    </div>
                                    </div>
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

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
        <script src="../js/highcharts.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/exporting.js"></script>

    
    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    
    <script src="../js/charts/batchAbend.js" charset="utf-8"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
</body>
<%} %>
</html>