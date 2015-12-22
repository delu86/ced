/* 
 * 
 */
Highcharts.setOptions({
	lang: {
            decimalPoint:','
	}
});
var system= $("input[type='radio'][name='optradio']:checked").val();
var job;
var queryType=$( "button[autofocus='true']" ).val();
var chart;

$.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span style='font-weight:bold;color:Blue;'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a>" + item.label + "</a>")
                .appendTo(ul);
    };
    $("input[type='radio']").click(function(){
    	system= $("input[type='radio'][name='optradio']:checked").val();
    });
function drawChart(){
      $("#loading").show(0);
     $.getJSON('jobAnalysis?queryType='+queryType+'&system='+system+'&query='+job, function (data) {
     $("#loading").hide(0);
        // create the chart
       chart= $('#containerChart').highcharts('StockChart', {
            chart: {
                alignTicks: false
            },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y:.2f}</b><br/>',
            valueSuffix: ' sec.',
            shared: true
        },
            rangeSelector: {
                selected: 3,
                buttons: [
  
                    {
	type: 'hour',
	count: 1,
	text: '1H'
},                   {
	type: 'day',
	count: 1,
	text: '1Day'
},{
	type: 'week',
	count: 1,
	text: '1week'
}, {
	type: 'all',
	text: 'All'
}]
            },

            title: {
                text: ''
            },
            yAxis: {
                    
                labels: {
                align:'left',
                formatter: function () {
                    return this.value + ' CPU sec.';
                }
            }},

            series: [{
                type: 'column',
                name: 'Cpu sec.',
                data: data.data,
                dataGrouping: {
                groupPixelWidth:50,    
                    
                    units: [
 [
	'minute',
	[10]
],[
	'hour',
	[1]
],['week',[1] ]
                ]
                }
            }]
        });
    });  
}
 $(function(){
     
     $("#autocomplete").autocomplete({
    		minlength:0,
    	    delay:0,
    	    source:function(request,response){
    	    	$.getJSON("jobAnalisysSuggest?queryType="+queryType+"&system="+system+"&query="+request.term.replace("#","%23"),function (data){
    	    		response(data);
                });},
    	       select: function( event, ui ) {
    	    	   job=ui.item.value;
    		   var parameter=ui.item.value.replace("#","%23");
                   drawChart();
                      		     	} 	});
    
    $('#autocomplete').on('keypress', function(e){
    if (e.which === 13) {
        e.preventDefault();
        job=$('.input-search').val();
        drawChart();
    }
});
     $( ".target" ).click(function() {
	   queryType=$(this).val();
         $(this).siblings().removeClass('active');
	 $(this).addClass('active');
           
	   });     
 });