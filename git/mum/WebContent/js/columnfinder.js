/* 
 * 
 * 
 */
var table=$('#dataTable').DataTable( {
    paging: true,
    processing:true,
    responsive: true,
    "dom": '<"top"i>rt<"bottom"flp><"clear">',
    columns:[
             { "data": "schema" },
             { "data": "table" },
             { "data": "column" },
             { "data": "datatype" },
             { "data": "description" }
             ]});
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
        	 table.ajax.url("getColumns?query="+$('.input-search').val().replace("#","%23")).load();
        	 }
        });
        function searchColumns(){
	$("#autocomplete").autocomplete({
    	    minlength:4,
    	    delay:0,
    	    source:function(request,response){
                if(request.term.length>20){
    	    	$.getJSON("getSuggestedColumn?query="+request.term,function (data){
                        console.log(data);
    	    		response(data);
                    }
                            );}},
    	       select: function( event, ui ) {
    	    	   
    	       $(".dataTable").show(1000);
    		   var parameter=ui.item.value.replace("#","%23");
                 table.ajax.url("getColumns?query="+$('.input-search').val().replace("#","%23")).load();}

    	});}
    $("#autocomplete").keyup(function(){
    	searchColumns();
    });
       $(function(){
            $(".dataTable").hide(0);
            searchColumns();
       });
