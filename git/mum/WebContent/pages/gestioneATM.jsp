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
    <meta charset="iso-8859-1">
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
    	String username=user.getUser();
    	if(!(username.equals("andrea.poli@cedacri.it")||username.equals("simone.deluca@consulenti.cedacri.it")
                ||username.equals("angela.stevanin@c-global.it"))){
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
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- JQUERY UI --> 
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <style>
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
                        <h3 class="page-header">Gestione statistiche ATM</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                       
                <div class="col-lg-6">
                    
             <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="datepicker"></div>
                        </div>
                        <div class="panel-body">
                            <button id="updateAtmActive" class="btn btn-primary">Aggiorna ATM attivi</button>
                            <button id="generateReport" class="btn btn-primary">Rigenera report</button>
                            <button id="certificateReport" class="btn btn-primary">Pubblica/Nascondi</button>
                        </div>
                        <!-- /.panel-body -->
               </div>
                    <!-- /.panel-default -->
                </div>
                <!-- /.col-lg-12 -->
                       <div class="col-lg-3 col-md-6">
                    <div class="panel" id="sla7-panel">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge" id="sla7-value">--</div>
                                    <div>Sla7: Indisponibilità HW</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel" id="unavailable-panel">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge" id="unavailable-value">--</div>
                                    <div>Indisponibilità faro</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        </div><!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                            <div class="panel panel-default">
                        <div class="panel-heading">
                             Indisponibilità per giorno  Credem
                        </div>
                         <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTablesByDay">
                                
                                    <thead>
                                        <tr>
                                            <th>Data </th>
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
                                            <th>Pubbl?</th>
                                            </tr>
                                    </thead>
                                    </table>
                                    </div>
                        <!-- /.dataTable_wrapper -->
                            </div>
                        </div>
                         <!-- /.col-lg-12 -->
                        </div>
                                            <div class="row">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                        <div class="panel-heading">
                            Indisponibilità per ATM  Credem
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
                        
                        
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
        <!-- /#wrapper -->
    </div>    
    

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- jQuery UI (per datepicker)-->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <script type="text/javascript">
        var codAbi="03032";
        var annoMese;
        var tableDay = $('#dataTablesByDay').DataTable( {
            paging: true,
            processing:true,
            responsive: true
        });
        var tableAtm = $('#dataTablesByAtm').DataTable( {
            paging: true,
            processing:true,
            responsive: true
        });
        
        function updateTable(){
            annoMese=$("#datepicker").datepicker("getDate").yyyymmdd().substr(0,7);
            tableDay.ajax.url("../queryResolver?id=atm/atmCredemAdmin&codAbi="+codAbi+"&period="+annoMese)
                    .load(function(json){
                var totale_ore=0,totale_sla_7=0,totale_ore_ko=0;
                var indexSla7=json.dataLabel.indexOf("ko_sla7");
                var indexOreTotali=json.dataLabel.indexOf("ore_totali_faro");
                var indexOreKo=json.dataLabel.indexOf("ore_ko_faro");
                json.data.forEach(function(element){
                    totale_ore+=Number(element[indexOreTotali]);
                    totale_sla_7+=Number(element[indexSla7]);
                    totale_ore_ko+=Number(element[indexOreKo]);
                });
                var percSla7=totale_sla_7*100/totale_ore;
                var percIndispFaro=totale_ore_ko*100/totale_ore;
                $("#unavailable-value").text(Math.round(percIndispFaro * 100) / 100 +'%');
                $("#sla7-value").text(Math.round(percSla7 * 100) / 100 +'%');
                if(percSla7>=1.4){
                    $("#sla7-panel").removeClass();
                    $("#sla7-panel").addClass("panel panel-red");
                }else{
                    $("#sla7-panel").removeClass();
                    $("#sla7-panel").addClass("panel panel-green");
                }
                if(percIndispFaro>=2.0){
                    $("#unavailable-panel").removeClass();
                    $("#unavailable-panel").addClass("panel panel-red");
                }else{
                    $("#unavailable-panel").removeClass();
                    $("#unavailable-panel").addClass("panel panel-green");
                }
                                    });
            tableAtm.ajax.url("../queryResolver?id=atm/atmCredemPerSportello&codAbi="+codAbi+"&period="+annoMese
                    ).load();
        }
        $(function() {
                $("#datepicker").datepicker({
                    onChangeMonthYear: function (year, month, inst) {
                                  mm=month.toString();
                                  mm=mm[1]?mm:"0"+mm[0];
                                  $("#datepicker").datepicker('setDate',mm +'/01/'+year);
                                  updateTable();
                                    }
        });
                updateTable();
                $("#generateReport").click(function(){
                     alert("Riceverai una mail appena i report saranno calcolati");
                     $.ajax({url: "/pages/generateReport?annoMese="+annoMese, success: function(){}});
                });
                $("#certificateReport").click(function(){
                     $.ajax({url: "/pages/setCertification?annoMese="+annoMese, success: function(){
                             updateTable();
                     }}
                     );
                });
                $("#updateAtmActive").click(function(){
                    $.getJSON("/pages/updateAtmList",function(json){
                        alert(json.message);
                    }); 
                });
           });
    </script>
    <script src="../dist/js/sb-admin-2.js"></script>
</body>
<%} %>
</html>
