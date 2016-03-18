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
        <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto"></div>
    </body>
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
    <script>
         var chart;
         var optionsChart={
           chart: {
            renderTo: 'container'
        },
        xAxis: [{
                tickmarkPlacement: 'on',
                labels: {
    	                rotation: -45},
    	        	gridLineWidth: 1,
    	        	tickInterval:1,
            categories:[],
            crosshair: true,
            title:{
    	           text:'APPLID'
    	        	}
        }],
     yAxis: [{
     	            min: 0,
     	            title: {
     	                text: 'Volumi',
     	                style: {
     	                    color: Highcharts.getOptions().colors[0]
     	                }
     	            }, labels: {
     	                format: '{value}',
     	                style: {
     	                    color: Highcharts.getOptions().colors[0]
     	                }
     	            },
     	            stackLabels: {
     	                enabled: true,
     	                style: {
     	                    fontWeight: 'bold',
     	                    color: Highcharts.getOptions().colors[0]
     	                }
     	            }
     	        },{ min: 0,
     	        	allowDecimals: false,
     	            labels: {
     	                format: '{value}',
     	                style: {
     	                    color: Highcharts.getOptions().colors[1]
     	                }
     	            },
     	            title: {
     	                text: 'CPU time',
     	                style: {
     	                    color: Highcharts.getOptions().colors[1]
     	                }
     	            },opposite: true
     	            
     	        }],
     series:[{name:'Volumi',type:'column',
     	    	 data:[]
     	            },{
     	            	name:'Cpu Time',yAxis:1,type:'spline',data:[]
     	            }]
         };
         $(function(){
          $.getJSON('../queryResolver?id=cicsEfficienza&system=SIES&date=2016-03-15',function(json){
            var categories=[];
            var volumi=[];
            var tempi=[];
            json.data.forEach(function(element){
                  categories.push(element[0]);
                  volumi.push(parseInt(element[2]));
                  tempi.push(parseInt(element[1]));
              });
              optionsChart.xAxis[0].categories=categories;
              optionsChart.series[0].data=volumi;
              console.log(optionsChart.series[0].data);
              optionsChart.series[1].data=tempi;
              chart= new Highcharts.Chart(optionsChart);
          });   
         });
    </script>

</html>
