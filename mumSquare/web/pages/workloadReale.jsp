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
             
         };
         $(function(){
            $.getJSON('../queryResolver?id=workloadReale&system=SIES&date=2016-03-15&limit=7',function(json){
                var series=[];
                var wkl;
                var index=-1;
                for(var i=0;i<json.data.length;i++){
                    if(json.data[i][1]===wkl){
                        series[index].data.push([json.data[i][0],json.data[i][2]]);
                    }else{
                        wkl=json.data[i][1];
                        index++;
                        var obj={name:wkl , data:[[json.data[i][0],json.data[i][2]]]};
                        series.push(obj);
                    }
                }
                console.log(series[0]);
            }); 
         });
    </script>
