<%-- 
    Document   : heatmap
    Created on : 17-mar-2016, 10.00.24
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
        <div id="container" style="height: 310px; min-width: 500px; max-width: 800px; margin: 0 auto"></div>
    </body>
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/heatmap.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script>
       var options={

        chart: {
            renderTo:'container',
            type: 'heatmap',
            marginTop: 40,
            marginBottom: 20,
            plotBorderWidth: 1
        },


        title: {
            text: 'Sales per employee per weekday'
        },

        xAxis: {
         categories: ['09', '10', '11', '12', '13', '14', '15', '16', '17', '18']
            
        },

        yAxis: {
          categories: ['1', '2', '3', '4', '5', '6', '7', '8','9','10',
                      '11', '12', '13', '14', '15', '16', '17', '18','19','20',
                      '21', '22', '23', '24', '25', '26', '27', '28','29','30','31']
            
        },

        colorAxis: {
            stops: [
                [0, '#3060cf'],
                [0.5, '#fffbbc'],
                [0.7, '#c4463a'],
                [0.8, '#c4463a']
            ],
            min: 70,
            max: 150,
            startOnTick: false,
            endOnTick: false,
            labels: {
                format: '{value}'
            }
        },

        legend: {
            align: 'right',
            layout: 'vertical',
            margin: 0,
            verticalAlign: 'top',
            y: 25,
            symbolHeight: 280
        },

        tooltip: {
            formatter: function () {
                return '<b>' + this.series.xAxis.categories[this.point.x] + '</b> sold <br><b>' +
                    this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
            }
        },

        series: [{
            name: 'Sales per employee',
            borderWidth: 1,
            data: [],
            dataLabels: {
                enabled: true,
                color: '#000000'
            }
        }]

    };
       var chart;  
    $(function (){
            
        $.getJSON("../json/first.json",function(json){
            json.data.forEach(function(entry){
               entry[0]= parseInt(entry[0])-9;
               entry[1]= parseInt(entry[1])-1;
            });
            options.series[0].data=json.data;
            console.log(json.data[0]);
            chart = new Highcharts.Chart(options);
        });
        });
</script>


</html>
