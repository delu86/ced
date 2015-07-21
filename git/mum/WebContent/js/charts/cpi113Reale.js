/**
 * CPI chart 
 */
var offset=0; 
var limit=7;
var system='SIES';
var cpiData=[];
var optionsChart={
	    chart: {
	        renderTo: 'container',
	         	     },yAxis: [{
	         min: 0}],  xAxis: {
	         	gridLineWidth: 1,
	         	tickInterval:1,
	             tickmarkPlacement: 'on',
	         	labels: {
	                 rotation: -45,
	                 style: {
	                     fontSize: '13px',
	                     fontFamily: 'Verdana, sans-serif'
	                 }},
	         categories: []
	     },    title: {
	         text: 'CPI Standard CPU '+system,
	         x: -20 //center
	     },tooltip: {
	         formatter: function () {
	             var s = '<b>' + this.x + '</b>';
	             $.each(this.points, function () {
	                 s += '<br/>' + '<span style="color:' + this.series.color + '"> ●</span>' + ' ' + this.series.name + ': ' + this.y ;
	             });
	             return s;
	         },
	         shared: true
	     },
	     series: [{name:'CPI',type:'spline'
	     	 }]};
    var optionsDrillDown={
            chart: {
    	        renderTo: 'container',
    	        },yAxis: [{
    	            min: 0,
    	            title: {
    	                text: 'CPI'
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
    	                format: '{value}',
    	                style: {
    	                    color: Highcharts.getOptions().colors[2]
    	                }
    	            },
    	            title: {
    	                text: '#CPU',
    	                style: {
    	                    color: Highcharts.getOptions().colors[2]
    	                }
    	            },opposite: true
    	            
    	        }],  xAxis: {
    	        	gridLineWidth: 1,
    	        	tickInterval:1,
    	            tickmarkPlacement: 'on',
    	         categories: []
    	     },    title: {
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
    	     series: [{name:'CPI',type:'spline',
    	            },{
    	            	name:'Number of CPUs',yAxis:1,step:'right', color: Highcharts.getOptions().colors[2]
    	            }]};
	
    $(function () {
		 $(".goUp").hide();
		 loadData();
		  $("[name='my-checkbox']").bootstrapSwitch();
		  $("[name='my-checkbox']").on('switchChange.bootstrapSwitch', function(event, state) {
			   if(state)
				   limit=7;
			   else
				   limit=30;
			   offset=0;
			   $("#next").prop('disabled',true);
			   setDateInterval();
			   loadData();
			   $(".goUp").hide();
			 });
		setDateInterval();	   
	     function setDateInterval(){
	        	var d=new Date();
	         	var d1=new Date();
	         	d1.setDate(d.getDate()+offset);
	         	d.setDate(d.getDate()+offset-limit+1);
	         	var mydate = new Date();
	         	$.datepicker.formatDate('yy-mm-dd', d);
	         	$("#date-interval").text($.datepicker.formatDate('dd/mm/yy', d)+'-'+$.datepicker.formatDate('dd/mm/yy', d1));
	        }
		 function loadData(){
			 $('#loading').show();
			 $.getJSON('systemJSON?system='+system+'&cpuClass=Standard%20CP'+'&offset='+offset.toString()+'&limit='+limit.toString(), function(data) {
				 cpiData=data;
				 drawChart();
				    });
		 }
		 function drawChart(){
			 optionsChart.title.text='CPI Standard CPU '+system;
			 optionsChart.series[0].data = cpiData[0];
			 optionsChart.xAxis.categories= cpiData[1];
			 optionsChart.series[0].point={
				        events: {
				            click: function (e) {
				            	$('#loading').show();
				            	$.getJSON('HourlyWorkloadJSONServlet?system='+system+'&cpuClass=Standard%20CP&date='+e.point.category.substr(5,11), function(data) {
				            		$(".goUp").show();
				            		optionsDrillDown.series[0].data = data[0];
				            		optionsDrillDown.xAxis.categories= data[1];
				            		optionsDrillDown.title.text='CPI Standard CPU '+system+': '+e.point.category.substr(5,11);
				            		optionsDrillDown.series[1].data = data[3];
				            		   var chart2 = new Highcharts.Chart(optionsDrillDown);
				            		   $('#loading').hide();
				            		});				            }
			 }
			 }
			 var chart = new Highcharts.Chart(optionsChart);
			 $('#loading').hide();
		   return true;
		 }
		 $( ".goUp" ).click(function() {
			   	  drawChart();
			   	 $(".goUp").hide();
			});
		 $("#next").click(function() {
			   offset+=limit;
			   if(offset==0)
				   $("#next").prop('disabled',true);
			       setDateInterval();
			       loadData();
			       $(".goUp").hide();
		   });
		   $("#prev").click(function() {
			   if(offset==0)
				   $("#next").prop('disabled',false);
			   offset+=-limit;
			   setDateInterval();
			   loadData();
			   $(".goUp").hide();
			   
		   });
		   $( ".target" ).click(function() {
			   
			   system=$(this).val();
			   loadData();
			   $(".goUp").hide();
		   
		});
			})
