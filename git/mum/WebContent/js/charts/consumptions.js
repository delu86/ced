/**
 * 
 */
    $(function () {
    	var system=$( "button[autofocus='true']" ).val();
    	var chart;
    	var optionsPlotline;
    	$("#baseline_value").on('keyup change', function(e){
    		
    		optionsPlotline={
    				zIndex: 5,
    				color:'#e300e5',
    				 id:'plotLine',
    				 
    	                width: 1.5,
    	                value: $("#baseline_value").val()	
    		};
    		chart.yAxis[1].removePlotLine('plotLine');
    		chart.yAxis[1].addPlotLine(optionsPlotline);
    	});

        var options={
            chart: {
                type: 'spline',
                zoomType: 'x',
                renderTo:'container'
            },
            title: {
                text: ''
            },
            xAxis: {
           	 minTickInterval:3600*1000,
             minRange:3600*1000,
             type: 'datetime',
           dateTimeLabelFormats: { // don't display the dummy year
               month: '%e. %b',
               year: '%b'
           }},
            yAxis: [{
                title: {
                    text: 'MIPS'
                },
                min: 0
            },{ // Secondary yAxis
            	min : 0,
                title: {
                    text: 'MSU',
                    style: {
                        color: Highcharts.getOptions().colors[3]
                    }
                },
                labels: {
                	format: '{value:.0f}',
                    style: {
                        color: Highcharts.getOptions().colors[3]
                    }
                },
                opposite: true
            }],
            series: []
        };
        $('#loading').show();
        drawChart();
        $( ".target" ).click(function() {
 		   system=$(this).val();
 		   $("#excelExporter").attr("href","consumptionsExporter?system="+system);
                   drawChart();
 		   chart.yAxis[1].addPlotLine(optionsPlotline);
 	});
        function drawChart(){
         	$('#loading').show();
            $.getJSON('systemComsumptions?system='+system, function(data){
        	options.series=data.series;
        	options.series[2].color=Highcharts.getOptions().colors[3];
        	options.title.text='Consumi '+system;
        	chart = new Highcharts.Chart(options);
        	$('#loading').hide();
        	return true;
        });}
    });