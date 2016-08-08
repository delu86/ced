/**
 * 
 */
    $(function () {
    	var system=$( "button[autofocus='true']" ).val();
        $("#excelExporter").attr("href","exporter?title=smf70&id=smf70/smf70bySystem&system="+system);
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
            series: [{name:"MIPS CPU",data:[]},
                     {name:"MIPS zIIP",data:[]},
                     {name:"MSU4HRA",yAxis:1,color:"",data:[]}]
        };
        $('#loading').show();
        drawChart();
        $( ".target" ).click(function() {
 		   system=$(this).val();
                   if(system==='ALL'){
                     $("#excelExporter").attr("href","exporter?title=smf70&id=smf70/smf70RealeAll");
                }else{
                     $("#excelExporter").attr("href","exporter?title=smf70&id=smf70/smf70bySystem&system="+system);
                }
 		  
                   drawChart();
 		   chart.yAxis[1].addPlotLine(optionsPlotline);
 	});
        function drawChart(){
         	$('#loading').show();
                var url;
                if(system==='ALL'){
                    url='../queryResolver?id=smf70/smf70RealeAll';
                }else{
                    url='../queryResolver?id=smf70/smf70bySystem&system='+system;
                }
            $.getJSON(url, function(json){
        	options.series.forEach(function(element){
                    element.data=[];
                });
                json.data.forEach(function(element){
                var date=dateToUTC(element[0]+" "+element[1]).getTime();
                for(var i=0;i<options.series.length;i++){
                        options.series[i].data.push([date ,Number(element[i+2])]);
                    }
                    
                });
                options.series[2].color=Highcharts.getOptions().colors[3];
        	options.title.text='Consumi '+system;
        	chart = new Highcharts.Chart(options);
        	$('#loading').hide();
        	return true;
        });}
    });