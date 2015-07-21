/**
 * Codice per la creazione dei grafici di workload
 */
var dataGeneralCharts=[];
var dataDrillDownCharts=[];
var limit=8;
var offset=0;
var system=$( "button[autofocus='true']" ).val();
var drillCount=0;
var chart;
var tableBatch = $('#dataTables-batch').DataTable( {
    paging: true,
    responsive: true,
    columns:[
             { "data": "SMF30JBN" },
             { "data": "CONDCODE" },
             { "data": "SMF30CLS" },
             { "data": "SMF30JPT" },
             { "data": "SMF30RUD" },
             { "data": "TOT" },
             { "data": "EXECTM" },
             { "data": "ELAPSED" },
             { "data": "DISKIO" },
             { "data": "DISKIOTM" },
             { "data": "ZIPTM" },
             { "data": "CPUTIME" },
             { "data": "TAPEIO" },
             { "data": "TAPEIOTM" }
         ]});
var tableCics = $('#dataTables-cics').DataTable( {
    paging: true,
    responsive: true
});
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
            text: system+" "+limit+" days"
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
                   $(".target").hide();
                   $(".shift").hide();
                   loadDrillDownData(e.point.category.toString());
                    	}                      
        }}}},
        series: []
        };
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
function loadGeneralData(){
		 $.getJSON('workload?system='+system+'&limit='+limit+'&offset='+offset,function(data){
			 dataGeneralCharts=data;
			 optionsGeneral.title.text=system+" "+(limit-1)+"days";
			 drawGeneralChart();
			 		 });}
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
	 return true;
	 }
function setStartPage(){
	 $(".panel-table").hide();
	 $(".goUp").hide();
}
function loadDrillDownData(point){
    $.getJSON('workloadByDayJSON?system='+system+'&day='+point,function(data){
    	dataDrillDownCharts=data;
    	var cat=data[0];
    	var dates=data[1];
    	optionsDrillDown.title.text=system+" "+Highcharts.dateFormat("%Y-%m-%d",e.point.category);
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
                		$("#container").hide();
                		$(".panel-table").show();
                		console.log(e.point.category);
                		$("#heading").html(system+" "+Highcharts.dateFormat("%A, %b %e, %H:%M",e.point.category)+
                "<a href=\""+"transactionIntervalExcel?system="+system+"&date="+e.point.category.toString()+"\" id=\"excel-export-interval\"><img alt=\"excel\" src=\"img/xls-48.png\" height=\"24\" width=\"24\"></a>"
                                	);
                		tableBatch.ajax.url("batchDetail?system="+system+'&day='+e.point.category.toString()).load();

                		tableCics.ajax.url("transactionDetail?system="+system+'&day='+e.point.category.toString()).load();
                		drillCount+=1;
                	}
                	}};
    	 chart = new Highcharts.Chart(optionsDrillDown);
        	 $(".goUp").show();
        	 drillCount+=1;
   		 return true;
        	});
}

$(function () {
	setStartPage();
	loadGeneralData();
	});
	

