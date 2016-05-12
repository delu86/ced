/**
 * 
 */
var system= $("input[type='radio'][name='optradio']:checked").val();
var job;
var tableBatch = $('#dataTables-batch').DataTable( {
    paging: true,
    processing:true,
    responsive: true,
    "dom": '<"top"i>rt<"bottom"flp><"clear">',
    columns:[
             { "data": "JOBname" },
             { "data": "JESNUM" },
             { "data": "USER" },
             { "data": "READTIME" },
             { "data": "ENDTIME" },
             { "data": "CPUTIME" },
             { "data": "ZIPTIME" },
             { "data": "ELAPSED" },
             { "data": "CONCODE" },
             { "data": "CLASS" },
             { "data": "PRIORITY" },
             { "data": "REPORT_CLASS" },
             { "data": "SERVICE_CLASS" }]
});
var tableStep = $('#dataTables-step').DataTable( {
	"dom": '<"top"i>rt<"bottom"flp><"clear">',
	paging: true,
    processing:true,
    responsive: true,
    columns:[
             { "data": "JOBname" },
             { "data": "JESNUM" },
             { "data": "SMF30STM" },
             { "data": "SMF30STN" },
             { "data": "SMF30PGM" },
             { "data": "USER" },
             { "data": "READTIME" },
             { "data": "ENDTIME" },
             { "data": "CPUTIME" },
             { "data": "ZIPTIME" },
             { "data": "ELAPSED" },
             { "data": "DISKIO" },
             { "data": "DISKIOTM" },
             { "data": "CLASS" }
       ]
});
$('#dataTables-batch tbody').on('click', 'td', function () {
	 var index = $(this).index();
	 
	 if(index>0){
	   $(".goUp").show(200);
	   var jobname = $(this).parent().find("td").first().text();
	   var jesnum = $(this).parent().find("td").first().next().text();
	   $(".dataTable_wrapper").hide(500);
	   $(".dataTable_wrapper_step").show(1000);
	   jobname=jobname.replace("#","%23");
	   $("#excelExporter").attr("href","joobleStepExcel?system="+system+"&jobname="+jobname.replace(" ","%20")+"&jesnum="+jesnum);
	   tableStep.ajax.url("getStep?system="+system+"&jobname="+jobname+"&jesnum="+jesnum).load();
	   tableStep.columns.adjust().responsive.recalc();
	 }
});
$.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span style='font-weight:bold;color:Blue;'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a>" + item.label + "</a>")
                .appendTo(ul);
    };   
    $("input[type='radio']").click(function(){
    	system= $("input[type='radio'][name='optradio']:checked").val();
    	$(".dataTable_wrapper_step").hide(500);
    	$(".dataTable_wrapper").hide(500);
    	$("#excelExporter").hide(500);
    });
    $(".goUp").click(function (){
    	$(".goUp").hide(0);
    	$(".dataTable_wrapper_step").hide(500);
    	$(".dataTable_wrapper").show(1000);
    	$("#excelExporter").attr("href","joobleExcel?system="+system+"&jobname="+job.replace("#","%23").toUpperCase());
    });
    $('.input-search').on('keyup', function(e) {
        if (e.keyCode === 13) {
        	 $("#excelExporter").show(1000);
        	 job=$('.input-search').val();
        	 $("#excelExporter").attr("href","joobleExcel?system="+system+"&jobname="+$('.input-search').val().replace("#","%23").replace(" ","%20").toUpperCase());
        	 $(".dataTable_wrapper_step").hide(500); 
        	 $(".dataTable_wrapper").show(1000);
        	tableBatch.ajax.url("getJobByName?system="+system+"&jobname="+$('.input-search').val().replace("#","%23").toUpperCase()).load();
        	tableBatch.columns.adjust().responsive.recalc();
        }
        });
function searchJobs(){
	   $(".goUp").hide(0);
	   
    	$("#autocomplete").autocomplete({
    		minlength:0,
    	    delay:0,
    	    source:function(request,response){
    	    	$.getJSON("getSuggest?system="+system+"&query="+request.term.replace("#","%23"),function (data){
    	    		response(data);})},
    	       select: function( event, ui ) {
    	    	   $("#excelExporter").attr("href","joobleExcel?system="+system+"&jobname="+ui.item.value.replace("#","%23"));
    		   job=ui.item.value;
    		   var parameter=ui.item.value.replace("#","%23");
    		   }
    	});
    };
    $("#autocomplete").keyup(function(){
    	searchJobs();
    });

   $(function(){
	   
	   var x =[];
	   $.getJSON("../../work/SHARE/ds04ap1x/json/RESUME_r30.json", function(json){
		   for(var i=0;i<json.data.length;i++){
			   x.push(json.data[i]);
		   }
		   $("#lastUpdate").html("Last update: "+x[getIndex(x,"GSY7")].lastUpdate);
		   $("[type=radio]").tooltip({title: function(){
			   var el=x[getIndex(x,$(this).val())];
			   return el.sys+" <br /> Dati presenti<br /> dal: "+el.firstUpdate.substring(0,10)+"<br /> al:"+el.lastUpdate.substring(0,10)+"<br /> #Record: "+el.total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, "'")}
		   , html:true});
	   } );
	   $(".goUp").hide(0);
	   $("#excelExporter").hide(0);
	   $(".dataTable_wrapper").hide(0);
	   $(".dataTable_wrapper_step").hide(0);
	   searchJobs();
	   function getIndex(arr,sysname){
		   var i=0;
		   while(i<arr.length){
			   if (arr[i].sys==sysname){
				   return i;
			   }
		   i++;
		   }
		   return -1;
		   
	   }
   });