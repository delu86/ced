<%-- 
    Document   : analisiTransazioni
    Created on : 6-apr-2016, 10.57.21
    Author     : CRE0260
--%>
<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<!DOCTYPE html>
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- JQuery UI -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <link href="../CSS/dashboard.css" rel="stylesheet">
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="chartUI">
        <input  id="tranName" type="text" placeholder="Trans.name(press ENTER)">
        <button  disabled="true" value="1" class="btn btn-outline btn-primary target typeSelector optionsChart" autofocus="true">NumTransazioni/SlotTDR/NumAbend</button>
        <button disabled="true" value="2" class="btn btn-outline btn-primary target typeSelector optionsChart">CpuTime/SlotCPU/Efficienza</button>
        <button disabled="true" value="3"  class="btn btn-outline btn-primary target typeSelector optionsChart">NumTransazioni/SlotCPU/TDRMedio</button>
        <button disabled="true" value="-1"  class="btn btn-default dateshift optionsChart" id="dateshiftBackward">< Giorno prec.</button>
        <button disabled="true" value="1"  class="btn btn-default dateshift optionsChart" id="dateshiftForward"> Giorno succ. ></button>
        <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto"></div>
        </div>
        <div id="tableDrillDown" style="display:none">
            <button id="back">Go back</button>
            <div class="dataTable_wrapper" >
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-cics">
                                    <thead>
                                        <tr>
                                            <th>Tran.</th>
                                            <th>Utente</th>
                                            <th>Count</th>
                                            <th>CPU time</th>
                                            <th>Elapsed</th>
                                            <th>DB2 Req.</th>
                                            <th>J8</th>
                                            <th>K8</th>
                                            <th>L8</th>
                                            <th>MS</th>
                                            <th>Memoria</th>
                                            <th>Qr</th>
                                            <th>S8</th>
                                            <th>SUSERID</th>
                                            <th>CMDUSER</th>
                                            <th>OUSERID</th>
                                         </tr>
                                    </thead>
                                    </table>
                                    </div>
                                    <!-- /.dataTable-wrapper -->
        </div>
    </body>
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../js/highcharts.js"></script>
    <script src="../js/exporting.js"></script>
    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <script src="../js/ced/chartsJS.js"></script>
    <script src="../js/ced/dateUtility.js"></script>
    <script>
        var tranName;
        var chart;
        var offset=0;
        var typeChart=1;
        var date=new Date();
        var optionsChart=getOptionsChartTransactionAnalysis();
            $('#dateshiftForward').prop('disabled',true);    
    function draw(){
        optionsChart.title.text=tranName+' '+date.yyyymmdd().substring(0,10);
        optionsChart.plotOptions.series.point.events.click=drilldown;
        drawTransactionAnalysis(optionsChart,
                                '../queryResolver?id=analisiTransazioni'+typeChart+'&tranName='+
                                    tranName.replace("#","%23").toUpperCase()+'&system=SIES&date='+date.yyyymmdd().substring(0,10));
                    }
    function drilldown(e){
        $("#chartUI").hide(500);
        $("#tableDrillDown").show();
        console.log(dateUTCtoString(e.point.category).yyyymmdd());
    }
    $('#tranName').on('keyup', function(e) {
        if (e.keyCode === 13){
            tranName=$('#tranName').val().toUpperCase();
            $(".optionsChart").prop('disabled',false);
            offset===0 ? $('#dateshiftForward').prop('disabled',true) : $('#dateshiftForward').prop('disabled',false);
            draw();
        }
    });
    
    $("#back").click(function(){
        $("#tableDrillDown").hide();
        $("#chartUI").show(500);
    });                
    $(".typeSelector").click(function(){
        typeChart=$(this).val();
        draw();
    });
    $(".dateshift").click(function(){
             offset+=parseInt($(this).val());
             offset===0 ? $('#dateshiftForward').prop('disabled',true) : $('#dateshiftForward').prop('disabled',false);
             shiftDate(parseInt($(this).val()),date,draw);
         });
        $(function(){
            
            
        });
    </script>
