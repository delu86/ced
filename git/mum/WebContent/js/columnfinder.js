/* 
 * 
 * 
 */
var table=$('#dataTable').DataTable( {
    paging: true,
    processing:true,
    responsive: true
    });
    $.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" 
                + $.ui.autocomplete.escapeRegex(this.term) + 
                ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span style='font-weight:bold;color:Blue;'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a>" + item.label + "</a>")
                .appendTo(ul);
    };
        $('.input-search').on('keyup', function(e) {
        if (e.keyCode === 13) {
        	 $(".dataTable").show(1000);
        	 table.ajax.url("../queryResolver?id=columnFinder/column&query="+$('.input-search').val().replace("#","%23")+'%25').load();
        	 }
        });
        function searchColumns(){
	$("#autocomplete").autocomplete({
    	    minlength:4,
    	    delay:0,
    	    source:function(request,response){
                if(request.term.length>2){
    	    	$.getJSON("../queryResolver?id=columnFinder/column&query="
                        +request.term.replace("#","%23")+'%25',function (json){
                    var data=[];    
                    json.data.forEach(function(element){
                       data.push(element[0]); 
                    });
                    response(data);}
                            );}},
    	       select: function( event, ui ) {
    	    	   
    	       $(".dataTable").show(1000);
    		   var parameter=ui.item.value.replace("#","%23");
                 table.ajax.url("../queryResolver?id=columnFinder/column&query="+$('.input-search').val().replace("#","%23")+'%25').load();}

    	});}
    $("#autocomplete").keyup(function(){
    	searchColumns();
    });
       $(function(){
            $(".dataTable").hide(0);
            searchColumns();
       });
