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
                <div class='row'>
                <div class="col-lg-3">
                </div>
                <!-- /.col-lg-4 -->
                    <div class="col-lg-6">
                        <img src="img/column_finder_logo.png"></img>
                    </div>
                    <div class="col-lg-3">
               
                </div>
                    <!-- /.col-lg-4 -->
                </div>
                <!-- /.row -->
                <div class="row">
                <div class="col-lg-2">
                </div>
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-8">
                    <div class="form-jooble">
                    <input class="input-search form-control" id="autocomplete" placeholder="Search for column ..." >
                   
                    </div>
                    </div>
                    <!-- /.col-lg-8 -->
                    <div class="col-lg-2">
                    <button type="button" class="btn btn-default goUp"  style="display:none"> <i class="fa fa-level-up fa-fw"></i></button>
                    </div>
                    <!-- /.col-lg-2 -->
                    </div>
                    <!-- /.row -->
              
               <div class="row">
                    <div class="col-lg-12">
                    
                      <div class="dataTable" style="display: none">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTable">
                                    <thead>
                                        <tr>
                                            <th class="desktop ">Table Schema</th>
                                            <th class="desktop ">Table Name</th>
                                            <th class="desktop ">Column Name</th>
                                            <th class="desktop ">Data Type</th>
                                            <th class="desktop ">Column Comment</th>
                                         </tr>
                                    </thead>
                                    </table>
                                    </div> 
                    <!-- /.dataTable_wrapper -->                
                                    </div>
                                   </div>
                  </div>
            <!-- /#container-fluid -->
              </div>
            <!-- /#page-wrapper -->     
            </div>
            <!-- /.wrapper -->
      
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- jQueryUI -->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <script src="../js/columnfinder.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    
</body>
<%} %>
</html>
