<%-- 
    Document   : atmCredem
    Created on : 19-gen-2016, 11.25.43
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
<html lang="it">
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
    	if(!profile.equals("CED")&&!profile.equals("CREDEM")){
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
    <!-- JqueryUI CSS -->
     <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
 
    .input-search {
      padding-left: 10px;
      transition: border 0.3s;
    }
        .input-search:focus {
       border: solid 5px #969696;
        outline: 0;
   }
   .hidden{
   	display:none;
   	}
        
.ui-datepicker-calendar {
    display: none;
    }
</style>
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
                        <h3 class="page-header">Statistiche ATM</h3>
                    </div>
                     <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        
                         <!-- /.col-lg-12 -->
                         <div class="col-lg-2">
                    <input type="text" class="form-control"id="date-interval" size="22" readonly='true'>
                    </div>
                    <!-- /.col-lg-2 -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                        <div class="panel-heading">
                         <a  title="Esporta  in excel" id="excelExporter" href="#"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a></h5>
                        
                            IndisponibilitÓ per giorno  Credem
                        </div>
                         <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTablesByDay">
                                
                                    <thead>
                                        <tr>
                                            <th>Giorno</th>
                                            <th>TotAtm</th>
                                            <th>Ore faro</th>
                                            <th>Ore KO</th>
                                            <th>%KO</th>
                                            <th>%OK</th>
                                            <th>ORE KO SLA7</th>
                                            <th>%KO SLA7</th>
                                            <th>ORE KO SLA8</th>
                                            <th>%KO SLA8</th>
                                            </tr>
                                    </thead>
                                    </table>
                                    </div>
                        <!-- /.dataTable_wrapper -->
                        </div>
                         <!-- /.col-lg-12 -->
                        </div><!-- /.row -->
                                            <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                        <div class="panel-heading">
                         <a  title="Esporta  in excel" id="excelExporter2" href="#"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a></h5>
                        
                            IndisponibilitÓ per ATM  Credem
                        </div>
                         <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTablesByAtm">
                                
                                    <thead>
                                        <tr>
                                            <th class="desktop ">ATM</th>
                                            <th class="desktop ">MESE</th>
                                            <th class="desktop ">TOT GIORNI</th>
                                            <th class="desktop ">ORE FARO</th>
                                            <th class="desktop ">ORE KO</th>
                                            <th class="desktop ">%KO</th>
                                            <th class="desktop ">%OK</th>
                                            <th class="desktop ">ORE KO SLA7</th>
                                        </tr>
                                    </thead>
                                    </table>
                                    </div>
                        <!-- /.dataTable_wrapper -->
                        </div>
                         <!-- /.col-lg-12 -->
                        </div><!-- /.row -->
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
          function pad(n, width, z) {
             z = z || '0';
             n = n + '';
            return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
            }
        $(function () {
            
        var date=new Date();
	var year=date.getFullYear();
	var month;
        if(date.getMonth()===0){
            month=11;
            year+=-1;
        }
        else
            month=date.getMonth()-1;
                
        var codAbi='03032';
        var meseAnno=year.toString()+"-"+pad(month+1,2).toString();;
        var tableDay = $('#dataTablesByDay').DataTable({    
             bSort: false,
                 paging: false,
    processing:true,
    responsive: true,
    "dom": '<"top"i>rt<"bottom"flp><"clear">',
    columns:[
             { "data": "DATA" },
             { "data": "TotaleATM" },
             { "data": "ORE_OK_FARO" },
             { "data": "ORE_KO_FARO" },
             { "data": "PERC_KO" },
             { "data": "PERC_OK" },
             { "data": "KO_SLA7" },
             { "data": "PERC_SLA7" },
             { "data": "KO_SLA8" },
             { "data": "PERC_SLA8" }
             ]
                });
        var tableATM = $('#dataTablesByAtm').DataTable({    
             bSort: false,
                 paging: true,
    processing:true,
    responsive: true,
    "dom": '<"top"i>rt<"bottom"flp><"clear">',
    columns:[
             { "data": "ATM" },
             { "data": "ANNO_MESE" },
             { "data": "GIORNI_FARO" },
             { "data": "ORE_TOTALI" },
             { "data": "ORE_KO" },
             { "data": "PERC_KO" },
             { "data": "PERC_OK" },
             { "data": "KO_SLA7" }
             ]
                });
$( "#date-interval" ).datepicker({
		 "aaSorting": [],   
		changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true,
	        dateFormat: 'MM yy',
	        defaultDate: new Date(year, month, 01),
                onSelect: function(dateText, inst){
                    $( "#date-interval" ).datepicker('setDate',new Date(year, month, 1));
                },
                 onClose: function(dateText, inst) { 
                    
	            month = parseInt($("#ui-datepicker-div .ui-datepicker-month :selected").val());
	            year = parseInt($("#ui-datepicker-div .ui-datepicker-year :selected").val());
	            $(this).datepicker('setDate', new Date(year, month, 1));
                    setUpPage();
	            
	        }
	});
	$( "#date-interval" ).datepicker('setDate',new Date(year, month, 1));
       
	    
            function setUpPage(){
        $( "#date-interval" ).datepicker('setDate',new Date(year, month, 1));   
            meseAnno=year.toString()+"-"+pad(month+1,2).toString();
            $("#excelExporter").attr("href","atmExcel?type=day&codAbi="+codAbi+"&period="+meseAnno);
            $("#excelExporter2").attr("href","atmExcel?type=atm&codAbi="+codAbi+"&period="+meseAnno);
            tableATM.ajax.url("atmSportello?codAbi="+codAbi+"&period="+meseAnno).load();
            tableDay.ajax.url("atmStat?codAbi="+codAbi+"&period="+meseAnno).load();
       };
       setUpPage();
   });

    </script>    
</body>
<%} %>
</html>
