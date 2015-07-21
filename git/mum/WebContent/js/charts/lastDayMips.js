/**
 * Codice Javascript per la creazione del grafico sul picco di MIPS di ieri.
 */
  $(function () 
    		{
    	        var chart;
    			var categories;
    			$.getJSON('lastDayMips',function(data){
    				var colors = Highcharts.getOptions().colors;
    				    cats=data[0],
    				    dataChart=[];
    				    var tot=0
    				for(i=0;i<cats.length;i++){
    					var total=0;//totale di ogni categoria
    					
    					for(n=0;n<data[i+cats.length+1].length;n++)
    					    total+=data[i+cats.length+1][n];
    					if(i<cats.length-1)
    						tot+=total;
    					dataChart.push({
    						y:total,
    						color:colors[i*2],
    						drilldown:{
    							name:cats[i],
    							categories:data[i+1],
    							color:colors[i*2],
    							data:data[i+cats.length+1]
    						}});
    						
    					}
    				  var machineData = [],
    				      systemData = [],
    				      dataLen = dataChart.length,
    				      i,j,
    				      drillDataLen,
    		              brightness;
    			for (i = 0; i < dataLen; i += 1) {
    				 machineData.push({
    		            name: cats[i],
    		            y: dataChart[i].y,
    		            color: dataChart[i].color
    		        });
    		         drillDataLen = dataChart[i].drilldown.data.length;
    		        for (j = 0; j < drillDataLen; j += 1) {
    		            brightness = 0.2 - (j / drillDataLen ) / 5;
    		            systemData.push({
    		                name: dataChart[i].drilldown.categories[j],
    		                y: dataChart[i].drilldown.data[j],
    		                color: Highcharts.Color(dataChart[i].color).brighten(brightness).get()
    		            });}
    		            
    		        }
    		        var options={
    		            	  chart: {
    		            renderTo:'container',
    		            type: 'pie'
    		            
    		        },
    		        title: {
    		            text: 'Consumo totale: '+tot+' /  Ora di picco: '+data[data.length-1][0],
    		            align:'left',
    		            style:{ "color": "#333333", "fontSize": "11px" }
    		        },
    		        plotOptions: {
    		            pie: {
    		                shadow: false,
    		                center: ['50%', '50%']
    		            }
    		        },
    		        tooltip: {
    		            valueSuffix: ' MIPS',
    		            pointFormat:'<span style="color:{point.color}">\u25CF</span> {series.name}: <b>{point.y:.0f}</b><br/>'
    		        },
    		        series: [{
    		            name: 'Machine',
    		            data: machineData,
    		            size: '60%',
    		            dataLabels: {
    		                formatter: function () {
    		                    return this.point.name;
    		                },
    		                color: 'white',
    		                distance: -30
    		            }
    		        }, {
    		            name: 'System',
    		            data: systemData,
    		            size: '80%',
    		            innerSize: '60%',
    		            dataLabels: {
    		                formatter: function () {
    		                     return this.y > 1000 ? '<b><span style="color:'+this.point.color +'">■</span>' + this.point.name + ':</b> ' + this.y   : null;
    		                }
    		             
    		            }
    		        }]
    		            };
    		          chart = new Highcharts.Chart(options);
    				   
    				
    				    
    			  });
    			  

    		});