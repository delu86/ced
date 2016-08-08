/**
 * 
 */
var i=0;
var offset=0;
var wind=15;
var system=$( "button[autofocus='true']" ).val();

var   tableDrill=
    $('#dataTables-drill').DataTable({
	responsive: true,
	processing:true,
	order: [[ 1, "asc" ]]
}); 

var optionsChart={
            chart: {
            	zoomType: 'x',
   	        renderTo: 'container'
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
   	        }],          	            		xAxis: {
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
    if(system==="SIES"||system==="SIGE"){
               urlTable="../queryResolver?id=abend/reale/transactionAbend&system="+system+'&date='+date;
               urlExporter="exporter?title=cicsAbend&id=abend/reale/transactionAbend&system="+system+'&date='+date;
           }   
        else{
               urlTable="../queryResolver?id=abend/carige/transactionAbend&system="+system+'&date='+date;
               urlExporter="exporter?title=cicsAbend&id=abend/carige/transactionAbend&system="+system+'&date='+date;
        }
	$("#heading-detail").html("CICS abend "+system+" "+date+ 
	"<a href=\""+urlExporter+"\" id=\"excel-export-interval\"><img alt=\"excel\" src=\"img/xls-48.png\" height=\"24\" width=\"24\"></a>");
    $(".goUp").show();
    $(".target").hide();
    $("#first-level").hide();
    $("#second-level").show();
    tableDrill.ajax.url(urlTable).load();
    tableDrill.columns.adjust().responsive.recalc();
};
function drawChart(){
	   $('#loading').show();
           var url;
           if(system==="SIES"||system==="SIGE")
               url="../queryResolver?id=abend/reale/transactionAbendChart&system="+system+'&offset='+offset+"&windowLength="+wind;
           else
               url="../queryResolver?id=abend/carige/transactionAbendChart&system="+system+'&offset='+offset+"&windowLength="+wind;
           
	 $.getJSON(url, function(json){
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
