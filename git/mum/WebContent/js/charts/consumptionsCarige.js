/**
 * 
 */
    $(function () {
    	var system=$( "button[autofocus='true']" ).val();
    	var chart, chartMIPS;
    	var optionsPlotline;
    	$("#baseline_value").on('keyup change', function(e){
    		
    		optionsPlotline={
    				zIndex: 5,
    				color:'#e300e5',
    				 id:'plotLine',
    				 
    	                width: 1.5,
    	                value: $("#baseline_value").val()	
    		};
    		chart.yAxis[0].removePlotLine('plotLine');
    		chart.yAxis[0].addPlotLine(optionsPlotline);
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
            yAxis: [{ // Secondary yAxis
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
                }
               
            }],
            series: []
        };
                var optionsMIPS={
            chart: {
                type: 'spline',
                zoomType: 'x',
                renderTo:'containerMIPS'
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
            }],
            series: []
        };
        $('#loading').show();
        drawChart();
        $( ".target" ).click(function() {
 		   system=$(this).val();
 		   $("#excelExporter").attr("href","consumptionsExporter?system="+system);
                   drawChart();
 		   chart.yAxis[0].addPlotLine(optionsPlotline);
 	});
        function drawChart(){
         	$('#loading').show();
            $.getJSON('systemComsumptions?system='+system, function(data){
        	options.series=[];
                optionsMIPS.series=[];
                options.series.push(data.series[2]);
                options.series[0].yAxis=0;
                optionsMIPS.series.push(data.series[0]);
                optionsMIPS.series.push(data.series[1]);
        	optionsMIPS.series[1].color=Highcharts.getOptions().colors[1];
                
        	options.series[0].color=Highcharts.getOptions().colors[3];
        	options.title.text='Consumi '+system;
        	chart = new Highcharts.Chart(options);
                 chartMIPS = new Highcharts.Chart(optionsMIPS);
        	$('#loading').hide();
        	return true;
        });}
    });