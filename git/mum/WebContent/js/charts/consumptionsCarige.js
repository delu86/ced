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
            series: [{name:"MSU4HRA",data:[]}]
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
            series: [{name:"MIPS Cpu",data:[]},{name:"MIPS zIIP",data:[]}]
        };
        $('#loading').show();
        drawChart();
        $( ".target" ).click(function() {
 		   system=$(this).val();
 		   $("#excelExporter").attr("href","exporter?id=smf70/smf70bySystem&system="+system);
                   drawChart();
 		   chart.yAxis[0].addPlotLine(optionsPlotline);
 	});
        function drawChart(){
         	$('#loading').show();
            $.getJSON('../queryResolver?id=smf70/smf70bySystem&system='+system, function(json){
        	options.series[0].data=[];
                optionsMIPS.series[0].data=[];
                optionsMIPS.series[1].data=[];
                options.series[0].yAxis=0;
                optionsMIPS.series[1].color=Highcharts.getOptions().colors[1];
                options.series[0].color=Highcharts.getOptions().colors[3];
        	options.title.text='Consumi '+system;
        	json.data.forEach(function(element){
                    var date=dateToUTC(element[0]+" "+element[1]).getTime();
                    options.series[0].data.push([date,Number(element[4])]);
                    optionsMIPS.series[0].data.push([date,Number(element[2])]);
                    optionsMIPS.series[1].data.push([date,Number(element[3])]);
                });
                
        	chart = new Highcharts.Chart(options);
                 chartMIPS = new Highcharts.Chart(optionsMIPS);
        	$('#loading').hide();
        	return true;
        });}
    });