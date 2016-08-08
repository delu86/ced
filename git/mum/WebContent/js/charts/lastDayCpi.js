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
    	            },
                        categories:[]
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
                    data:[]
    	            
    	        }]
    	    };
    	  
    	  $.getJSON('../queryResolver?id=smf113/yesterdayCPI', function(json) {
    		 json.data.forEach(function(el){
                    options.xAxis.categories.push(el[0]);
                    options.series[0].data.push(Number(el[1]));
    		 });
                 chart = new Highcharts.Chart(options);
 		    return true;
    	  });
    	  
    	    
    	});
