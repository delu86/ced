<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<html>
    <head>
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
    <style type="text/css">
        #info{
        display:inline-block;
        }
        .dateshift{
        display:inline-block;
        }
    </style>
            <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="expo">
                        <h5 >Excel exporter
                        <a  title="Esporta grafico in excel" id="excelExporter" href="#"><img alt="excel" src="img/xls-48.png" height="24" width="24"></a></h5>
       </div>
        <div id="chartUI">
        <button value="SIES" class="btn btn-outline btn-primary systemSelector" autofocus="true">SIES</button>
        <button value="SIGE" class="btn btn-outline btn-primary systemSelector">SIGE</button>
        <button value="ALL"  class="btn btn-outline btn-primary systemSelector">ALL</button>
        <button value="-1"  class="btn btn-default dateshift" id="dateshiftBackward">Indietro</button>
        <button value="1"  class="btn btn-default dateshift" id="dateshiftForward">Avanti</button>
        <input type="number" id="baseline" placeholder="Baseline">
        <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto"></div>
        </div>
        <div id="tableDrillDown" style="display:none">
            <button id="back">Go back</button>
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
         var istituto='reale';
         var chart;
         var limit=7;
         var offset=0;
         var date=new Date();
         //console.log(date);
         var chart;
         var system=$( ".systemSelector[autofocus='true']" ).val();
         var optionsChart=getOptionsChartWorkload();
         var optionsChartDrillDown=getOptionsChartWorkload();
             optionsChartDrillDown.xAxis.minRange=600*1000;
             optionsChartDrillDown.xAxis.minTickInterval=600*1000;
         $('#dateshiftForward').prop('disabled',true);    
         setUpDrillDown();
         setUpGoBack();
         $("#baseline").on('keyup change', function(e){
             drawBaseline(chart,$("#baseline").val());
});
         $(".systemSelector").click(function(){
             system=$(this).val();
             draw(WEEK_WINDOWS_TIME);
         });
         $(".dateshift").click(function(){
             offset+=parseInt($(this).val());
             offset===0 ? $('#dateshiftForward').prop('disabled',true) : $('#dateshiftForward').prop('disabled',false);
             shiftDate(parseInt($(this).val()),date,draw,WEEK_WINDOWS_TIME);
         });
         $("#back").click(function(){
             $("#tableDrillDown").hide(500);
             $("#chartUI").show();
         });
$(function(){
       draw(WEEK_WINDOWS_TIME);     
        });
     function draw(windowTime){
          drawWorkload(windowTime,optionsChart,istituto,system,'&date='+date.yyyymmdd().substring(0,10)+'&limit=7'); 
          setUpDrillDown();
     }
     function setUpDrillDown(){
                  optionsChart.plotOptions.series={
                point: {
                    events: {
                    	click: function (e) {
                            drawWorkload(DAY_WINDOWS_TIME,optionsChartDrillDown,istituto,system,'&date='+date.yyyymmdd().substring(0,10)+'&limit=7');
                                            }}}};
        optionsChartDrillDown.plotOptions.series={
                point: {
                    events: {
                    	click: function (e) {
                            if(!(system==='ALL')){
                             $("#chartUI").hide(500);
                             $("#tableDrillDown").show();
                            }
                                            }}}};                        
     }
    //go back to the first chart from the second chart
     function setUpGoBack(){
         optionsChartDrillDown.exporting={buttons: {
     	              backButton: {
     	                  text: '< <b>Back',
     	                  onclick: function () {
     	                  	chart = new Highcharts.Chart(optionsChart);
     	             	   	 return true;
     	                  }}}};
     }
    </script>
