/**
 * Codice per la creazione dei grafici di workload di Reale Mutua 
 * 
 */
var dataGeneralCharts=[]; //in questa variabile vengono salvati i dati per la creazione del grafico generale(caricati tramite AJAX)
var dataDrillDownCharts=[];
var limit=7;
var offset=-1;
var chart;
var system=$( "button[autofocus='true']" ).val();
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



var tableBatch = $('#dataTables-batch').DataTable( {
    paging: true,
    processing:true,
    responsive: true,
    columns:[
             { "data": "SMF30JBN" },
             { "data": "CONDCODE" },
             { "data": "SMF30CLS" },
             { "data": "SMF30JPT" },
             { "data": "SMF30RUD" },
             { "data": "EXECTM" },
             { "data": "ELAPSED" },
             { "data": "DISKIO" },
             { "data": "DISKIOTM" },
             { "data": "ZIPTM" },
             { "data": "CPUTIME" }
             
             ]});
var tableSTC = $('#dataTables-stc').DataTable( {
    paging: true,
    processing:true,
    responsive: true,
    columns:[
             { "data": "SMF30JBN" },
             { "data": "CONDCODE" },
             { "data": "SMF30CLS" },
             { "data": "SMF30JPT" },
             { "data": "SMF30RUD" },
             { "data": "EXECTM" },
             { "data": "ELAPSED" },
             { "data": "DISKIO" },
             { "data": "DISKIOTM" },
             { "data": "ZIPTM" },
             { "data": "CPUTIME" }
             
             ]});
var tableCics = $('#dataTables-cics').DataTable( {
    paging: true,
    processing:true,
    responsive: true
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
                    		$('#loading').show();
                   $(".target").hide();
                   $("#info").hide();
                   $(".shift").hide();
                   
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
                            		
                            		$("a#excel-export-interval").attr("href","transactionIntervalExcel?system="+system+"&date="+e.point.category.toString());
                            		$("#container").hide(500);
                            		$("#expo").hide();
                            		$("#baseline_value").hide();
                            		$(".panel-table").show(500);
                            		$("#heading").html(system+" "+Highcharts.dateFormat("%A, %b %e, %H:%M",e.point.category)+
                            "<a href=\""+"transactionIntervalExcel?system="+system+"&date="+e.point.category.toString()+"\" id=\"excel-export-interval\"><img alt=\"excel\" src=\"img/xls-48.png\" height=\"24\" width=\"24\"></a>"
                                            	);
                            		tableBatch.ajax.url("batchDetail?type=job&system="+system+'&day='+e.point.category.toString()).load();
                            		tableSTC.ajax.url("batchDetail?type=stc&system="+system+'&day='+e.point.category.toString()).load();
                            		tableCics.ajax.url("transactionDetail?system="+system+'&day='+e.point.category.toString()).load();

                           	 
                            		drillCount+=1;
                            	}
                            	}};
                	 chart = new Highcharts.Chart(optionsDrillDown);
                	 chart.yAxis[0].addPlotLine(optionsPlotline);

                	 $(".goUp").show();
                    	 drillCount+=1;
                    	 $('#loading').hide();
                    	 return true;
               		 
                    	}
                 
                 );}                      
        }}}},
        series: []
        };
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    $('.table:visible').each( function(e) {
   
      $(this).DataTable().columns.adjust().responsive.recalc();
    });
  });
function setDateInterval(){
	var d=new Date();
 	var d1=new Date();
 	d1.setDate(d.getDate()+offset);
 	d.setDate(d.getDate()+offset-limit+1);
 	var mydate = new Date();
 	$.datepicker.formatDate('yy-mm-dd', d);
 	$("#date-interval").val($.datepicker.formatDate('dd/mm/yy', d)+'-'+$.datepicker.formatDate('dd/mm/yy', d1));
}
$(function () {
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
			 $('#loading').hide();	 
		 }); }
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
		 return true;
		 }
	$( ".goUp" ).click(function() {
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
			$("#baseline_value").show();
			$("#expo").show();
		   	$("#container").show(500);
			$(".panel-table").hide(500);
			}
		drillCount+=-1;
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
				chart.yAxis[0].addPlotLine(optionsPlotline);
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
		setDateInterval();
	});
});