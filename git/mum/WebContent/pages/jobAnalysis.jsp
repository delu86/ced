<%-- 
    Document   : jobAnalisys
    Created on : 25-nov-2015, 9.55.25
    Author     : CRE0260
--%>

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
     <link href="../CSS/dashboard.css" rel="stylesheet">
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
        .target{
        outline: none !important;}
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
   
                <div class='row'>
                <div class="col-lg-2">
                </div>
                <!-- /.col-lg-4 -->
                    <div class="col-lg-8">
                        <img src="img/jobAnalysis.png"></img>
                    </div>
                    <div class="col-lg-2">
               <a id="lastUpdate"></a>
                </div>
                    <!-- /.col-lg-4 -->
               </div>
                <!-- /.row -->
                <div class="row">
                <div class="col-lg-2">
                     <div title="Seleziona cosa cercare" class="btn-group" role="group" aria-label="...">
                              <button  type="button"autofocus="true" class="active btn btn-default target" value="job">Job</button>
                              <button type="button" class="btn btn-default target" value="program">Prg</button>
                        </div>
                </div>
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-8">
                    <div class="form-jooble">
                    <input class="input-search form-control" id="autocomplete" placeholder="Search for JOB name ..." >
                   
                    </div>
                    </div>
                    <!-- /.col-lg-8 -->
                    <div class="col-lg-2">
                    </div>
                    <!-- /.col-lg-2 -->
                    </div>
                    <!-- /.row -->
                          
                <div class="row">
                <div class="col-lg-2">
                
                </div>
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-10">
                    Cerca in: 
                 <label class="radio-inline"><input type="radio" name="optradio" value="GSY7" checked="checked">GSY7</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="BSY2">BSY2</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="CSY3">CSY3</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ZSY5">ZSY5</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ESYA">ESYA</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="MVSA">MVSA</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="MVSB">MVSB</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ICT1">ICT1</label>
                    </div>
                    <!-- /.col-lg-10 -->
                    </div>
                    <!-- /.row -->
               <div class="row">
                    <div class="col-lg-12">
                        <div id="containerChart" style="min-width: 510px; height: 500px; margin: 0 auto"    ></div>
                       
                    <!-- /#containerChart -->                
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
    <!-- jQueryUI -->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
   
   <script src="http://code.highcharts.com/stock/highstock.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/exporting.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script src="../js/charts/jobAnalysis.js"></script>
</body>
<%} %>
</html>
