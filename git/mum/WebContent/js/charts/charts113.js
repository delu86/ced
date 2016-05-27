/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var offset=0; 
var limit=7;
var system=$( "button[autofocus='true']" ).val();
var url="../queryResolver?id="+type+"BySystem&";
var url_drilldown="../queryResolver?id=113BySystemDate&system=";
//opzioni per il grafico primario
var optionsChart={
	    chart: {
	        renderTo: 'container',
                type:'spline'
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
	              categories:[]
	     },    title: {
	         text: '',
	         x: -20 //center
	     },tooltip: {
	         formatter: function () {
	             var s = '<b>' + this.x + '</b>';
	             $.each(this.points, function () {
	                 s += '<br/>' + '<span style="color:' + this.series.color + '"> ?</span>' + ' ' + this.series.name + ': ' + this.y ;
	             });
	             return s;
	         },
	         shared: true
	     },
	     series: [{name:'',data:[]
	     	 }]};
//opzioni per il grafico di drilldown
var optionsDrillDown={
            chart: {
    	        renderTo: 'container'
    	        },yAxis: [{
    	            min: 0,
    	            title: {
    	                text: ''
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
    	                 s += '<br/>' + '<span style="color:' + this.series.color + '"> ?</span>' + ' ' + this.series.name + ': ' + this.y ;
    	             });
    	             return s;
    	         },
    	         shared: true
    	     },  	     
    	     series: [{name:'',type:'spline',data:[]
    	            },{
    	            	name:'Number of CPUs',yAxis:1,step:'right', color: Highcharts.getOptions().colors[2],
                        data:[]
    	            }]};
$(function(){

    $(".goUp").hide();
    //switch per scegliere tra visuale di una settimana e visuale di 30 gg
    $("[name='my-checkbox']").bootstrapSwitch();
    $("[name='my-checkbox']").on('switchChange.bootstrapSwitch', function(event, state) {
			   if(state)
				   limit=7;
			   else
				   limit=30;
			   offset=0;
			   $("#next").prop('disabled',true);
			   setDateInterval();
			   draw113Chart(type,url);
			   $(".goUp").hide();
			 });
    draw113Chart(type,url);
    setDateInterval();
});
//disegna il grafico primario
function draw113Chart(type,url){
    $('#loading').show();
    var date=new Date();
    date.setDate(date.getDate()+offset);
    optionsChart.title.text=type.toUpperCase()+' Standard CPU '+system;
    optionsChart.series[0].name=type.toUpperCase();
    if(type==='mips')
        optionsChart.chart.type='column';
    $.getJSON(url+'system='+system+'&cpuType=Standard%20CP&date='+date.yyyymmdd().substring(0,10)+'&limit='+limit, function(json){
        optionsChart.series[0].name=json.dataLabel[1];
        optionsChart.series[0].data=[];
        //funzione di drilldown
        optionsChart.series[0].point={
			events: {
			click: function (e) {
				$('#loading').show();
				$.getJSON(url_drilldown+system+'&date='+e.point.category+'&cpuType=Standard%20CP', function(json2) {
				            $(".goUp").show();
                                            draw113ChartDrillDown(type,json2,e.point.category);
				            $('#loading').hide();
                                });}}};

        optionsChart.xAxis.categories=[];
        json.data.forEach(function(element){
           optionsChart.xAxis.categories.push(element[0]);
           optionsChart.series[0].data.push(Number(element[1]));
        });
        chart = new Highcharts.Chart(optionsChart);    
        $('#loading').hide();
    });
 
        
}
   //disegna il grafico di drilldown
function draw113ChartDrillDown(type,json,date){
    optionsDrillDown.xAxis.categories=[];
    optionsDrillDown.series[0].data=[];
    optionsDrillDown.series[1].data=[];
    json.data.forEach(function(element){
        optionsDrillDown.xAxis.categories.push(element[0]);
    optionsDrillDown.series[0].name=type.toUpperCase();    
    //dati sul numero di cpu
    optionsDrillDown.series[1].data.push(Number(element[3]));
    if(type==='mips'){
        optionsDrillDown.series[0].data.push(Number(element[2])); 
        optionsDrillDown.series[0].type='column';
                }
        else{
                optionsDrillDown.series[0].data.push(Number(element[1]));
            }
            });
        
        optionsDrillDown.title.text=type.toUpperCase()+' Standard CPU '+system+': '+date;
        var chart2 = new Highcharts.Chart(optionsDrillDown);
}
	 $( ".goUp" ).click(function() {
			   	  chart = new Highcharts.Chart(optionsChart);  
			   	 $(".goUp").hide();
			});
	$("#next").click(function() {
			   offset+=limit;
			   if(offset===0)
				   $("#next").prop('disabled',true);
			       setDateInterval();
			       draw113Chart(type,url);       
                               $(".goUp").hide();
		   });
	$("#prev").click(function() {
			   if(offset===0)
				   $("#next").prop('disabled',false);
			           offset+=-limit;
			           setDateInterval();
			           draw113Chart(type,url);
                                   $(".goUp").hide();
		});
   $( ".target" ).click(function() {
                system=$(this).val();
		draw113Chart(type,url);
		$(".goUp").hide();
		});
//impostazione intervallo di selezione da visualizzare nella pagina
function setDateInterval(){
	        	var d=new Date();
	         	var d1=new Date();
	         	d1.setDate(d.getDate()+offset);
	         	d.setDate(d.getDate()+offset-limit+1);
	         	$.datepicker.formatDate('yy-mm-dd', d);
	         	$("#date-interval").text($.datepicker.formatDate('dd/mm/yy', d)+'-'+$.datepicker.formatDate('dd/mm/yy', d1));
	        }


