<%-- 
    Document   : cicsEfficienza
    Created on : 17-mar-2016, 14.12.34
    Author     : CRE0260
--%>

<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>JSP Page</title>
    </head>
    <body>
        <button value="SIES" class="systemSelector" autofocus="true">SIES</button>
        <button value="SIGE" class="systemSelector">SIGE</button>
        <button value="ALL"  class="systemSelector">ALL</button>
        <button value="-1"  class="dateshift" id="dateshiftBackward">Indietro</button>
        <button value="1"  class="dateshift" id="dateshiftForward">Avanti</button>
        <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto"></div>
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
    <script src="../js/chartsJS.js"></script>
    <script>
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
         $(".systemSelector").click(function(){
             system=$(this).val();
             draw();
         });
         $(".dateshift").click(function(){
             shiftDate(parseInt($(this).val()));
         });

$(function(){
       draw();     
        });
        
     function draw(){
         system==='ALL' ?
            drawWorkload(optionsChart,
                                '../queryResolver?id=workloadReale7daysAll&date='+date.yyyymmdd()+'&limit=7') 
                                :      
            drawWorkload(optionsChart,
                            '../queryResolver?id=workloadReale7daysBySystem&system='+system+'&date='+date.yyyymmdd()+'&limit=7'); 
        
        setUpDrillDown();
     }
     function shiftDate(amount){
         offset+=amount;
         offset===0 ? $('#dateshiftForward').prop('disabled',true) : $('#dateshiftForward').prop('disabled',false);
         date.setDate(date.getDate()+amount);
         draw();
     }
     function setUpDrillDown(){
                  optionsChart.plotOptions.series={
                point: {
                    events: {
                    	click: function (e) {
                            if(system==='ALL'){
            drawWorkload(optionsChartDrillDown,
                                '../queryResolver?id=workloadRealeByDay&date='
                                +Highcharts.dateFormat("%Y-%m-%d",e.point.category)
                          );                 }
              else{
                  drawWorkload(optionsChartDrillDown,
                                '../queryResolver?id=workloadRealeByDaySystem&system='+system+'&date='
                                +Highcharts.dateFormat("%Y-%m-%d",e.point.category)
                          );
              }
                                            }}}};
     }
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
