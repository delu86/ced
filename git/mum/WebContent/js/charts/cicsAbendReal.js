/**
 * 
 */
var i=0;
var offset=0;
var wind=15;
var system="SIES";
var limit='10';
var table = $('#dataTables-top10').DataTable( {
	        paging: false,
	        bInfo: false,
	        processing:true,
	        searching:false,
	        order: [[ 3, "desc" ]],
		    responsive: true,
		    columns:[
	            { "data": "hour" },
	            { "data": "abend" },
	            { "data": "trans" },
	            { "data": "cpu_sec" },
	            { "data": "count" },
	            { "data": "db2" }
		    ]
			});
var tableDrill=$('#dataTables-drill').DataTable({
	responsive: true,
	processing:true,
	order: [[ 1, "asc" ]],
    columns:[
        { "data": "trans" },
        { "data": "hour" },
        { "data": "count" },
        { "data": "cpu_sec" },
        { "data": "response" },
        { "data": "db2" },
        { "data": "abend" },
        { "data": "user" }
    ]
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
	       	            		  mouseOver:function(e){
	       	            			$("#heading-table").text("Top10 consumer "+system+" "+e.currentTarget.category);
		            				table.ajax.url( 'transactionAbendJSON?system='+system +'&date='+e.currentTarget.category+"&limit="+limit).load();
		          	       	            		},
		          	       	        click:function(e){
		          	       	              drilldown(e.currentTarget.category);

		          	       	        }
   	       	            	  }}
  	    	        }]
   };
function drilldown(date){
	$("#heading-detail").html("CICS abend "+system+" "+date+ 
	"<a href=\""+"transactionAbendExcel?system="+system+"&date="+date+"\" id=\"excel-export-interval\"><img alt=\"excel\" src=\"img/xls-48.png\" height=\"24\" width=\"24\"></a>");
    $(".goUp").show();
    $(".target").hide();
    $("#first-level").hide();
    $("#second-level").show();
    tableDrill.ajax.url( 'transactionAbendJSON?system='+system +'&date='+date+"&limit=0").load();
    tableDrill.columns.adjust().responsive.recalc();
};
function drawChart(){
	   $('#loading').show();

	 $.getJSON('abendJSON?system='+system+'&offset='+offset+"&window="+wind, function(data){
		 optionsChart.xAxis.categories=data[0];
		 optionsChart.series[0].data=data[1][0];
		 optionsChart.series[1].data=data[1][1];
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
