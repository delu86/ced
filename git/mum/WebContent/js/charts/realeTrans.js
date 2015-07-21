/**
 * Tempi e volumi transazioni reale mutua
 */
$(function () {
	var offset=1;
	setDate();
	$(".shift-right-one").prop('disabled',true);
    var options={
        chart: {
        	renderTo:'container'
        },
        title: {
            text: ''
        },
        xAxis: [{
        	type:'datetime',
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
        	min : 0,
        	labels: {
                format: '{value} sec.',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: 'CPU time',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
        	min : 0,
            title: {
                text: 'Numero esecuzioni',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: 'Volumi',
            type: 'column',
            yAxis: 1,
            data: []
               }, {
            name: 'Tempi',
            type: 'spline',
            data: [],
            tooltip: {
                valueSuffix: ' sec.'
            }
        }]};
    createChart();
    $(".shift-left-one").click(function(){
    	offset+=1;
    	setDate();
    	if(offset==2)
    		$(".shift-right-one").prop('disabled',false);
    	createChart();
    });
    $(".shift-right-one").click(function(){
    	offset-=1;
    	setDate();
    	if(offset==1)
    		$(".shift-right-one").prop('disabled',true);
    	createChart();
    });
    function setDate(){
    	var d1=new Date();
     	d1.setDate(d1.getDate()-offset);
     	$("#date-interval").text($.datepicker.formatDate('dd/mm/yy', d1));
    };
    function createChart(){
    $.getJSON('volTimeTran?offset='+offset+"&system=SIES&tname=APA0", function(d){
    	console.log(offset);
    	options.series[0].data=d[0];
    	options.series[1].data=d[1];
   	 chart = new Highcharts.Chart(options);
   	 return true;
      });
    }
});