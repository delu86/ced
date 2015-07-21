/**
 * 
 */


	$(document).ready(function() {
		var d=new Date();
		var monthPar=d.getMonth(); //i mesi in js vanno da 0 a 11
		if(monthPar==0){ 
			monthPar=12;}
		$.ui.autocomplete.prototype._renderItem = function (ul, item) {
	        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span style='font-weight:bold;color:Blue;'>$1</span>");
	        return $("<li></li>")
	                .data("item.autocomplete", item)
	                .append("<a>" + item.label + "</a>")
	                .appendTo(ul);
	    };   

    	$("#autocomplete").autocomplete({
    		
    		minlength:0,
    	    delay:0,
    	    source:function(request,response){
    	    	
    	    	$.getJSON("getSuggestCalendarAccenture?query="+request.term,function (data){
    	    		response(data);})},
    	       select: function( event, ui ) {
    	    	   var parameter=ui.item.value.replace("#","%23");
    	    	   $('#loading').show();
    	    		$.getJSON("jobsCalendar?jobname="+parameter+"&month="+monthPar,function(json){
    	    			$('#calendar').fullCalendar('destroy');
    	    			$('#calendar').fullCalendar({
    	    				
    	    				timeFormat: 'HH(:mm)', 
    	    				header: {
    	    					left:'',
    	    					center: 'title',
    	    					right: ''
    	    				},
    	    				defaultDate: calculateDefaultDay(),
    	    				editable: false,
    	    				eventLimit: true, // allow "more" link when too many events
    	    				displayEventEnd: true,
    	    				events: json.data
    	    			
    	    			});
    	    			$('#loading').hide();
    	    			
    	    		}) 
    	       
    	       }
    	});
    	//funzione per calcolare la data di default del calendario( primo giorno del mese precedente a quello attuale
    	function calculateDefaultDay(){
    		var date=new Date();
    		
    		var actualMonth=date.getMonth();
    		switch(actualMonth){
    		case 1: month="12";
    		case 12:month="11";
    		case 11:month="10";
    		default: month="0"+(actualMonth);
    		}
    		
    		var year = date.getFullYear();
    		return year+"-"+month+"-01";
    	}
	});


