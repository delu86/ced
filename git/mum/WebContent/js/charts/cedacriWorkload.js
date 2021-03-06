/**
 * Codice per la creazione dei grafici di workload di Reale Mutua 
 * 
 */

$(function () {
	var baseline_value;
	var dataGeneralCharts=[]; //in questa variabile vengono salvati i dati per la creazione del grafico generale(caricati tramite AJAX)
	var dataDrillDownCharts=[];
	var limit=7;
	var offset=-1;
	var chart;
	var system='GSY7';
	var drillCount=0;
	var 	optionsPlotline={
			zIndex: 5,
			color:'#e300e5',
			 id:'plotLine',
			 
                width: 1.5,
                value: 0	
	};


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
	var optionsDrillDown={
			chart: {
	            type: 'areaspline',
	            zoomType: 'x',
	            renderTo: 'container'
	        },
	        title: {
	            text: system
	        },
	             xAxis: {
	            	 minRange:600*1000,
	            	 minTickInterval:600*1000,
	            type: 'datetime',
	            dateTimeLabelFormats: { // don't display the dummy year
	                month: '%e. %b',
	                year: '%b'
	            },
	            title: {
	                text: 'Date'
	            }
	        },
	        yAxis: {
	        	labels: {
	                format: '{value:,.0f}'
	            },
	            title: {
	                text: 'MIPS'
	            },
	            min: 0
	        },
	        tooltip: {
	            shared:true,
	     	    formatter: function() {
	 	    var s = '<b>'+ Highcharts.dateFormat("%A, %b %e, %H:%M",this.x) +'</b>';
	        var total=0;
	        $.each(this.points, function(i, point) {
	            s += '<br/><span style="color:'+ point.series.color +'">\u25CF</span>: ' + point.series.name + ': ' + point.y;
	            total+=point.y;
	        });
	        s += '<br/><span>\u25CF</span>: Total: ' + Math.floor(total);
	        return s;
	    }
	        },
	        plotOptions: {
	               areaspline: {
	                stacking: 'normal',
	                lineColor: '#666666',
	                lineWidth: 1,
	                marker: {
	                    radius: 2
	                }
	            },
	            series: {
	                point: {
	                    events: {
	                    	click: function (e) {
	                  }                      
	        }}}},
	        series: []
	        };
	var optionsGeneral={
	        chart: {
	            type: 'areaspline',
	            zoomType: 'x',
	            renderTo: 'container'
	        },
	        style: {
	        	position: 'absolute',
	        	backgroundImage: 'loading.gif',
	        	opacity: 0.5,
	        	textAlign: 'center'
	        },
	        title: {
	            text: system
	        },
	             xAxis: {
	            	 minTickInterval:3600*1000,
	                 minRange:3600*1000,
	            type: 'datetime',
	            dateTimeLabelFormats: { // don't display the dummy year
	                month: '%e. %b',
	                year: '%b'
	            },
	            title: {
	                text: 'Giornate'
	            }
	        },
	        yAxis: {
	        	 labels: {
	                 format: '{value:,.0f}'
	             },
	            title: {
	                text: 'MIPS'
	            },
	            min: 0
	        },
	        tooltip: {
	            shared:true,
	     	    formatter: function() {
	 	    var s = '<b>'+ Highcharts.dateFormat("%A, %b %e, %H:%M",this.x) +'</b>';
	        var total=0;
	        $.each(this.points, function(i, point) {
	            s += '<br/><span style="color:'+ point.series.color +'">\u25CF</span>: ' + point.series.name + ': ' + point.y;
	            total+=point.y;
	        });
	         s += '<br/><span>\u25CF</span>: Total: ' + Math.floor(total);
	        return s;
	    }
	        },
	        plotOptions: {
	               areaspline: {
	                stacking: 'normal',
	                lineColor: '#666666',
	                lineWidth: 1,
	                marker: {
	                    radius: 2
	                }
	            },
	            series: {
	                point: {
	                    events: {
	                    	click: function (e) {
	                   $(".target").hide();
	                   $("#info").hide();
	                   $(".shift").hide();
	               	$('#loading').show();

	                   $.getJSON('workloadByDayJSON?system='+system+'&day='+e.point.category.toString(),function(data){
	                	 dataDrillDownCharts=data;
	                	var cat=data[0];
	                	var dates=data[1];
	                	$("#excelExporter").attr("href","workloadByDayExporter?system="+system+"&date="+Highcharts.dateFormat("%Y-%m-%d",e.point.category));
	                	optionsDrillDown.title.text=system+" "+Highcharts.dateFormat("%Y-%m-%d",e.point.category)+" (dettaglio 10 minuti)";
	             		optionsDrillDown.series=[];
	             		for(i=0;i<cat.length;i++){
	             			var serie={
	             				name: cat[i],
	             				data: dates[i]
	             			};
	             			optionsDrillDown.series.push(serie);
	             			}
	             		optionsDrillDown.plotOptions.series.point={
	             				events: {
	                            	click: function (e) {
	                            		
	                            	}
	                            	}};
	                	 chart = new Highcharts.Chart(optionsDrillDown);
	                	 chart.yAxis[0].addPlotLine(optionsPlotline);
	                    	 $(".goUp").show();
	                    	 drillCount+=1;
	                    	 $('#loading').hide();
	                    	 return true;
	                 	
 
	                   });}                      
	        }}}},
	        series: []
	        };


	 Highcharts.setOptions({
	        lang: {
	            numericSymbols: null,
	            thousandsSep: '\''
	        }
	    });
	 $("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
	 $(".shift-right").prop('disabled',true);
	 $(".shift-right-one").prop('disabled',true);
	 $(".panel-table").hide();
	 $(".goUp").hide();
    loadGeneralData();
    setDateInterval();
    function loadGeneralData(){
    	$('#loading').show();
		 $.getJSON('workload?system='+system+'&limit='+limit+'&offset='+offset,function(data){
			 
			 dataGeneralCharts=data;
			 optionsGeneral.title.text=system+" (dettaglio orario)";
			 drawGeneralChart();
			 if(offset<-1){
				$(".shift-right").prop('disabled',false);
				$(".shift-right-one").prop('disabled',false);
				
			 }	
			});
		 	 
    }
	//disegna il grafico dell'andamento del workload generale
	function drawGeneralChart(){
		var cat=dataGeneralCharts[0];
		var dates=dataGeneralCharts[1];
		optionsGeneral.series=[];
		for(i=0;i<cat.length;i++){
			var serie={
				name: cat[i],
				data: dates[i]
			};
			optionsGeneral.series.push(serie);
		}
		 chart = new Highcharts.Chart(optionsGeneral);
		 chart.yAxis[0].addPlotLine(optionsPlotline);
		 $('#loading').hide();
		 return true;
		 }
	$( ".goUp" ).click(function() {
		$('#loading').show();
		if(drillCount==1){
			
     		drawGeneralChart();
    		chart.yAxis[0].addPlotLine(optionsPlotline);

     		$(".goUp").hide();
	        $(".target").show();
	        $(".shift").show();
	        $("#info").show();
	        $("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
		}
		else{
		   	$("#container").show();
}
		drillCount+=-1;
		$('#loading').hide();
	});
	$( "#date-interval" ).datepicker({showOn: "button",
			buttonImage: "img/calendar.gif",
			buttonImageOnly: true,
			buttonText: "Select date",
			beforeShowDay:
			       function(dt) {
				var today=new Date();
				var diff=Math.ceil((today.getTime()-dt.getTime()) / (1000 * 3600 * 24));
				return [diff>=2,""];
			       },
			onSelect: function (dateText, inst) {
				var today=new Date();
				var selected=new Date(dateText);
				var timeDiff = Math.abs(today.getTime() - selected.getTime());
				offset =-( Math.ceil(timeDiff / (1000 * 3600 * 24))-1); 
				setDateInterval();
				loadGeneralData();
    	    }});
	$( ".target" ).click(function() {
	   system=$(this).val();
	   $("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
	   loadGeneralData();
	   });
	$(".shift-left").click(function(){
		offset+=-limit;
		$("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
		$(".shift-right").prop('disabled',false);
		$(".shift-right-one").prop('disabled',false);
		setDateInterval();
		loadGeneralData();
	});
	$(".shift-right").click(function(){
		offset+=limit;	
		if(offset>=-1){
			$(".shift-right").prop('disabled',true);
			$(".shift-right-one").prop('disabled',true);
			offset=-1;
		}
		$("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
		loadGeneralData();
		setDateInterval();
	});
	$(".shift-left-one").click(function(){
		offset+=-1;
		$(".shift-right").prop('disabled',false);
		$(".shift-right-one").prop('disabled',false);
		$("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
		setDateInterval();
		loadGeneralData();
	});
	$(".shift-right-one").click(function(){
		offset+=1;
		if(offset==-1){
			$(".shift-right").prop('disabled',true);
			$(".shift-right-one").prop('disabled',true);
		}
		$("#excelExporter").attr("href","workloadExporter?system="+system+"&limit="+limit+"&offset="+offset);
		loadGeneralData();
		chart.yAxis[0].addPlotLine(optionsPlotline);
		setDateInterval();
	});
	function setDateInterval(){
		var d=new Date();
	 	var d1=new Date();
	 	d1.setDate(d.getDate()+offset+1);
	 	d.setDate(d.getDate()+offset-limit+2);
	 	var mydate = new Date();
	 	$.datepicker.formatDate('yy-mm-dd', d);
	 	$("#date-interval").val($.datepicker.formatDate('dd/mm/yy', d)+'-'+$.datepicker.formatDate('dd/mm/yy', d1));}
});