/**
 * 
 */

$(function () {
	var options={
			chart: {
				 renderTo: 'container'
			},
			title: {
	            text: 'Virtual Tapes: tempo medio di mount',
	            x: -20 //center
	        },
	        subtitle: {
	            text: 'VSMA, VSMA1, VSMA2',
	            x: -20
	        },
	        xAxis: {
	            type:'datetime'
	        },
	        yAxis: {
	            title: {
	                text: 'Average mount time (sec.)'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: ' sec'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: []
	    	
	}
	$('#loading').show();
   $.getJSON('tapeReport', function(d){
	 
	 for(i=0;i<d[0].length;i++){
		var serie={
				name: d[0][i],
				data: d[1][i]
		} 
		options.series.push(serie);
	 }
	 chart = new Highcharts.Chart(options);
	 $('#loading').hide();
	 return true;
   })
        
});