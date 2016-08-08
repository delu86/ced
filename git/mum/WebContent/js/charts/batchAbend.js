/**
 * 
 */
var i=0;
var offset=0;
var wind=15;
var system=$( "button[autofocus='true']" ).val();
var limit='10';
var tableDrill=$('#dataTables-drill').DataTable({
	processing:true,
	responsive: true,
	order: [[ 1, "asc" ]],
});
var optionsChart={
            chart: {
            	zoomType: 'x',
   	        renderTo: 'container',
   	        	     },yAxis: [{
   	            min: 0,
   	            title: {
   	                text: '#Errori'
   	            },
   	            stackLabels: {
   	                enabled: true,
   	                style: {
   	                    fontWeight: 'bold',
   	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
   	                }
   	            }   	            
   	        },{ min: 0,
   	        	allowDecimals: false,
   	            labels: {
   	                format: '{value} s.',
   	                style: {
   	                    color: Highcharts.getOptions().colors[3]
   	                }
   	            },
   	            title: {
   	                text: 'CPU time',
   	                style: {
   	                    color: Highcharts.getOptions().colors[3]
   	                }

   	                }
   	            ,opposite: true
   	        ,}],          	            		xAxis: {
      	         labels: {
      	        	 
    	             rotation: -45,
    	             style: {
    	                 fontSize: '13px',
    	                 fontFamily: 'Verdana, sans-serif'
    	             }},
       	        	gridLineWidth: 1,
       	        	tickInterval:1,
       	            tickmarkPlacement: 'on',
       	         categories: []
       	     },	          
   	        plotOptions:{
               	},  
    title: {
   	         text: '',
   	         x: -20 //center
   	     },tooltip: {
   	         formatter: function () {
   	             var s = '<b>' + this.x + '</b>';
   	             $.each(this.points, function () {
   	                 s += '<br/>' + '<span style="color:' + this.series.color + '"> ■</span>' + ' ' + this.series.name + ': ' + this.y ;
   	             });
   	             return s;
   	         },
   	         shared: true
   	     },
   	    series: [
   	              {name:'#Errori',type:'column',
   	            	point:{
     	            	  events:{
                                click:function(e){
                                	drilldown(e.currentTarget.category);
}  
     	            	  }}  },{
   	            	name:'CPU time',yAxis:1,type:'spline', color: Highcharts.getOptions().colors[3] ,
   	        	             point:{
	       	            	  events:{
	       	            		  
		          	       	        click:function(e){
		          	       	              drilldown(e.currentTarget.category);
		          	       	        }
   	       	            	  }}
  	    	        }]
   };
function drilldown(date){
var url_table,url_exporter;
    if(system==="SIES" || system==="SIGE"){
        url_table="../queryResolver?id=abend/reale/batchAbend"+system+"&date="+date;
        url_exporter="exporter?title=batchAbend&id=abend/reale/batchAbend"+system+"&date="+date;}
    else{
        url_table="../queryResolver?id=abend/carige/batchAbend&date="+date+"&system="+system;
        url_exporter="exporter?title=batchAbend&id=abend/carige/batchAbend&date="+date+"&system="+system;
    }
    $("#heading-detail").html("BATCH abend "+system+" "+date+ 
	"<a href=\""+url_exporter+"\" id=\"excel-export-interval\"><img alt=\"excel\" src=\"img/xls-48.png\" height=\"24\" width=\"24\"></a>");
    $(".goUp").show();
    $(".target").hide();
    $("#first-level").hide();
    $("#second-level").show();
    
    tableDrill.ajax.url(url_table).load();
    tableDrill.columns.adjust().responsive.recalc();
};
function drawChart(){
    var url_chart;
    if(system==="SIES" || system==="SIGE")
        url_chart="../queryResolver?id=abend/reale/batchAbend"+system+"Chart&offset="+offset+"&windowLength="+wind;
    else
        url_chart="../queryResolver?id=abend/carige/batchAbendChart&offset="+offset+"&windowLength="+wind+"&system="+system;
	   $('#loading').show();
           //console.log(url_chart);
	    $.getJSON(url_chart, function(json){
                  
		 optionsChart.xAxis.categories=[];
		 optionsChart.series[0].data=[];
		 optionsChart.series[1].data=[];
                 json.data.forEach(function(element){
                     optionsChart.xAxis.categories.push(element[0]);
                     optionsChart.series[0].data.push(Number(element[1]));
                     optionsChart.series[1].data.push(Number(element[2]));
                 });
		 var chart2 = new Highcharts.Chart(optionsChart);
  	    $('#loading').hide();

	})};
$(function () {
	drawChart();
	$(".goUp").click(function(){
	          $(".goUp").hide();
     	      $(".target").show();
     	      $("#first-level").show();
     	     $("#second-level").hide();
	});
	$( ".target" ).click(function() {
	    system=$(this).val();
	    $("#heading-graph").text("Abend "+system);
	   drawChart();
	   });
	 });
