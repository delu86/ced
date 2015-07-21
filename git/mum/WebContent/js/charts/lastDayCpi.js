/**
 *  * Codice Javascript per la creazione del grafico sul picco di CPI di ieri.

 */
      $(function () {
    	  var chart;
    	  var options={
    	        chart: {
                	renderTo: 'container2',
    	            type: 'bar'
    	        },
    	        title: {
    	            text: ''
    	        },
    	        xAxis: {
    	            title: {
    	                text: null
    	            }
    	        },
    	        yAxis: {
    	            min: 0,
    	            title: {
    	                text: 'Clock per Instruction',
    	                align: 'high'
    	            },
    	            labels: {
    	                overflow: 'justify'
    	            }
    	        },
    	        tooltip: {
    	            valueSuffix: ' CPI'
    	        },
    	        plotOptions: {
    	            bar: {
    	                dataLabels: {
    	                    enabled: true
    	                }
    	            }
    	        },
    	       
    	        credits: {
    	            enabled: false
    	        },
    	        series: [{
    	        	showInLegend:false,
    	            name: 'Yesterday CPI',
    	            
    	        }]
    	    };
    	  
    	  $.getJSON('lastDayCpi', function(data) {
    		 options.xAxis.categories=data[0];
    		 options.series[0].data=data[1];
    		 chart = new Highcharts.Chart(options);
 		    return true;
    	  });
    	  
    	    
    	});
