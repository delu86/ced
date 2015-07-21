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
   <div class="panel panel-info panel-guida" style="display: none" >
      <div class="panel-heading">
        <h3 class="panel-title">Guida Jooble</h3>
      </div>
      <div class="panel-body">
      <h3>ATTENZIONE: VERSIONE BETA TESTING</h3>
      
        <b>Ricerca per jobname</b><br>
        <p><i>jobname</i> [options] --> ricerca i job con quel nome;<br>
          Options:<br> 
          <b>-u</b>  <i>username</i>  --> ricerca per utente <br>
          <b>-f </b><i>YYYYMMDD</i>   --> ricerca dalla data <br>
		  <b>-t </b> <i>YYYYMMDD</i>  --> ricerca fino alla data <br>
		  <b>-d </b> <i>YYYYMMDD</i>  --> ricerca nella data<br>
		  <b>-a </b> <i>conditioncode</i> --> ricerca con conditioncode<br>
		  <b>-c </b> <i>sec.</i><i>cputime</i> --> ricerca con cputime > sec. <br>
          <b>Ricerca TOP consumer</b><br>
          <b>TOP</b>   [options] --> ricerca 10 job che hanno consumato pi&ugrave CPUtime. Di default esegue la ricerca nel giorno precedente alla data corrente
         <br>Options:<br>
         <b>-d </b> <i>YYYYMMDD</i> -->ricerca top consumer nella data <br>
         <b>-a </b> -->ricerca top consumer andati in abend <br>
         <b>-m </b> <i>M</i> -->ricerca top consumer del mese indicato (formato numero) <br>
         <b>-j </b> <i>jobname</i>  -->ricerca top consumer per jobname<br>
         <b>-l </b> <i>num</i>  -->ricerca gl N jobs che hanno consumato pi&ugrave CPU <br>
         </p>
      </div>
      </div>
                <div class="col-lg-4">
                </div>
                <!-- /.col-lg-4 -->
                    <div class="col-lg-6">
                        <img src="img/jooble.png"></img>
                    </div>
                    <div class="col-lg-2">
               <a id="lastUpdate"></a>
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
                    <input class="input-search form-control" id="autocomplete" placeholder="Search for JOB name ..." >
                   
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
                <div class="col-lg-3">
                
                </div>
                
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-5">
                    Cerca in: 
                 <label class="radio-inline"><input type="radio" name="optradio" value="GSY7" checked="checked">GSY7</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="BSY2">BSY2</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="CSY3">CSY3</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ZSY5">ZSY5</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ESYA">ESYA</label>
                    </div>
                    <!-- /.col-lg-8 -->
                    <div class="col-lg-3">
                    <a href="#" id="open-info"><i class="fa fa-question fa fw"></i></a>
                    </div>
                    <!-- /.col-lg-2 -->
                    </div>
                    <!-- /.row -->
               <div class="row">
                    <div class="col-lg-12">
                    <div>
                        
                        <a  style="display:none" id="excelExporter" href="#"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a>
                                <!-- /.col-lg-4 -->
                        </div>
                      <div class="dataTable_wrapper" style="display: none">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-batch">
                                    <thead>
                                        <tr>
                                            <th class="desktop ">JOBname</th>
                                            <th class="desktop ">JESNUM</th>
                                            <th class="desktop ">USER</th>
                                            <th class="desktop ">INITIALTIME</th>
                                            <th class="desktop ">ENDTIME</th>
                                            <th class="desktop ">CPUTIME</th>
                                            <th class="desktop ">ZIPTIME</th>
                                            <th class="desktop ">Elapsed</th>
                                            <th class="desktop ">CONCODE</th>
                                            <th>CLASS</th>
                                            <th>PRIORITY</th>
                                            <th>REPORT_CLASS</th>
                                         </tr>
                                    </thead>
                                    </table>
                                    </div> 
                                    
                                    </div>
                                   </div>
                    <!-- /.dataTable_wrapper -->
                    <div class="row">
                    <div class="col-lg-12">
                                          <div class="dataTable_wrapper_step"style="display: none" >
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-step">
                                    <thead>
                                        <tr>
                                            <th class="desktop ">JOBname</th>
                                            <th class="desktop ">JESNUM</th>
                                            <th class="desktop ">STEP NAME</th>
                                            <th class="desktop ">STEP NUMBER</th>
                                            <th class="desktop ">SMF30PGM</th>
                                            <th class="desktop ">USER</th>
                                            <th class="desktop ">READTIME</th>
                                            <th class="desktop ">ENDTIME</th>
                                            <th class="desktop ">CPUTIME</th>
                                            <th class="desktop ">ZIPTIME</th>
                                            <th class="desktop ">Elapsed</th>
                                            <th >DISKIO</th>
                                            <th >DISKIOTM</th>
                                            <th>CLASS</th>
                                         </tr>
                                    </thead>
                                    </table>
                                    </div>       
                    <!-- /.dataTable_wrapper -->
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
    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script src="../js/jooble.js"></script>
    <script type="text/javascript">
    $("#open-info").click(function(){
    	$(".panel-guida").animate({
            height: 'toggle'
        });
    });
    </script>
</body>
<%} %>
</html>
