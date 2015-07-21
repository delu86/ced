/**
 * 
 */

  $(function(){

	  $.ui.autocomplete.prototype._renderItem = function (ul, item) {
	        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span style='font-weight:bold;color:Blue;'>$1</span>");
	        return $("<li></li>")
	                .data("item.autocomplete", item)
	                .append("<a>" + item.label + "</a>")
	                .appendTo(ul);
	    };
	  var options={
	            chart: {
	    	        renderTo: 'container',
	    	        },
	    	        yAxis: [{
	    	            min: 0,
	    	            title: {
	    	                text: 'Volumi',
	    	                style: {
	    	                    color: Highcharts.getOptions().colors[0]
	    	                }
	    	            }, labels: {
	    	                format: '{value}',
	    	                style: {
	    	                    color: Highcharts.getOptions().colors[0]
	    	                }
	    	            },
	    	            stackLabels: {
	    	                enabled: true,
	    	                style: {
	    	                    fontWeight: 'bold',
	    	                    color: Highcharts.getOptions().colors[0]
	    	                }
	    	            }
	    	        },{ min: 0,
	    	        	allowDecimals: false,
	    	            labels: {
	    	                format: '{value}',
	    	                style: {
	    	                    color: Highcharts.getOptions().colors[1]
	    	                }
	    	            },
	    	            title: {
	    	                text: 'CPU time',
	    	                style: {
	    	                    color: Highcharts.getOptions().colors[1]
	    	                }
	    	            },opposite: true
	    	            
	    	        }],  xAxis: {
	    	        	gridLineWidth: 1,
	    	        	tickInterval:1,
	    	            tickmarkPlacement: 'on',
	    	         categories: []
	    	     },    title: {
	    	    	 text:'',
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
	    	     series: [{name:'Volumi',type:'column',
	    	    	 data:[]
	    	            },{
	    	            	name:'Cpu Time',yAxis:1,type:'spline',data:[]
	    	            }]};
	  searchCICS();  
	    $("#autocomplete").keyup(function(){
	    	searchCICS();
	    });
	    
	    function searchCICS(){
  	$("#autocomplete").autocomplete({
  		minlength:0,
  	    delay:0,
  	    source:function(request,response){
  	    	$.getJSON("getSuggestionsCics?query="+request.term,function (data){
  	    		response(data);})},
  	       select: function( event, ui ) {
          	 $('#loading').show();

  		   trans=ui.item.value;
  		   var parameter=ui.item.value.replace("#","%23");
  		 $.getJSON("volumesTimes?system=GSY7&transName="+parameter,function (data){
  			options.xAxis.categories=data[0];
  			options.series[0].data=data[1][0];
  			options.series[1].data=data[1][1];
  			var chart = new Highcharts.Chart(options);
        	 $('#loading').hide();
			return true;
  		 })}
  	});}	  

  
  
  
  })