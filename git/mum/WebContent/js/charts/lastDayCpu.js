	/**
 * Codice per la creazione del grafico dell'andamento del numero di CPU di ieri
 */
      $(function () {
    	    var options={
    	        chart: {
    	            type: 'column',
    	            renderTo:'container3'
    	        },
    	        title: {
    	            text: ''
    	        },
    	        xAxis: {
    	            categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
    	        },
    	        yAxis: {
    	            min: 0,
    	            title: {
    	                text: '#CPU'
    	            },
    	            stackLabels: {
    	                enabled: true,
    	                style: {
    	                    fontWeight: 'bold',
    	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
    	                }
    	            }
    	        },
    	        legend: {
    	            align: 'center',
    	            x: -30,
    	            verticalAlign: 'bottom',
    	            y: 25,
    	            floating: true,
    	            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
    	            borderColor: '#CCC',
    	            borderWidth: 1,
    	            shadow: false
    	        },
    	        tooltip: {
    	            formatter: function () {
    	                return '<b>' + this.x + '</b><br/>' +
    	                    this.series.name + ': ' + this.y + '<br/>' +
    	                    'Total: ' + this.point.stackTotal;
    	            }
    	        },
    	        plotOptions: {
    	            column: {
    	                stacking: 'normal',
    	                dataLabels: {
    	                    enabled: false,
    	                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
    	                    style: {
    	                        textShadow: '0 0 3px black'
    	                    }
    	                }
    	            }
    	        },
    	        series: []
    	        };
    	    $.getJSON('lastDayCpu',function(data){
    	    	var series=[];
    	    	for(i=0;i<data[0].length;i++){
    	    		var serie={
    	    				name:data[0][i],
    	    		        data:data[i+1]
    	    		
    	    		};
    	    		series.push(serie);
    	    	}
    	    	options.series=series;
    	    	var chart= new Highcharts.Chart(options);
    	    });
    	    
    	    
    	    });