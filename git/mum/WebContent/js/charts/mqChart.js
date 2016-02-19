/**
 * Grafico code mq
 */
$(function () {
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth();
	var system=$( "button[autofocus='true']" ).val();
	Highcharts.setOptions({
        lang: {
            numericSymbols: null,
            thousandsSep: '\''
        }
    });
	var optionsDrillDown={ chart: {
        renderTo:'container',
		type: 'column'
    },
    exporting: {
        buttons: {
            backButton: {
                text: '< <b>Back',
                onclick: function () {
                	chart = new Highcharts.Chart(options);
           	   	 return true;
             	
                }}}},
    title: {
        text: ''
    },
   
    xAxis: {
    	categories: [ ],
        crosshair: true,
        title:{
        	text:'Ore'
        }
    },
    yAxis: {
    	 title: {
    	        text: 'Total'
    	    },	
        min: 0,
        
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: []
};
	var options={ chart: {
        renderTo:'container',
		type: 'column'
    },
    title: {
        text: ''
    },
    
    xAxis: {
    	labels: {
            rotation: -45},
        categories: [ ],
        crosshair: true,
        title:{
        	text:'Giorni'
        }
    },
    yAxis: {
    	 title: {
    	        text: 'Total'
    	    },	
        min: 0,
        
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        
        column: {
        	  events: {
              	click: function (e) {
              		$('#loading').show();
              		var monthPar=month+1;
            		$.getJSON('mqChartDrilldown?day='+e.point.category.split(",")[0]+'&year='+year+'&month='+monthPar+'&system='+system,function(json){
            		optionsDrillDown.title.text=system+" "+e.point.category.split(",")[0]+"-"+monthPar+"-"+year+" (only CICS)";
            		optionsDrillDown.xAxis.categories=json.categories;
            		optionsDrillDown.series=json.series;
            		
            		chart = new Highcharts.Chart(optionsDrillDown);
            		$('#loading').hide(); 
            		return true;
              	
            		})
              	}},
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: []
};
	drawChart();
//	$("#selMonth").change(function(){
//	month=$(this).val();
//		drawChart();
//	});
	$(".target").click(function(){
		system=$(this).val();
		drawChart();
	});
	$( "#date-interval" ).datepicker({
		changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true,
	        dateFormat: 'MM yy',
	        onClose: function(dateText, inst) { 
	            month = parseInt($("#ui-datepicker-div .ui-datepicker-month :selected").val());
	            year = parseInt($("#ui-datepicker-div .ui-datepicker-year :selected").val());
	            
	            $(this).datepicker('setDate', new Date(year, month, 1));
	            drawChart();
	        }
	});
	$( "#date-interval" ).datepicker('setDate',new Date(year, month, 1));
	
	function drawChart(){
		$('#loading').show();
		var monthPar=month+1;
		$.getJSON('mqChartJSON?year='+year+'&month='+monthPar+'&system='+system,function(json){
		options.xAxis.categories=json.categories;
		options.series=json.series;
		options.title.text=system+" (only CICS)";
		chart = new Highcharts.Chart(options);
		$('#loading').hide(); 
		return true;
	});}
	
});

