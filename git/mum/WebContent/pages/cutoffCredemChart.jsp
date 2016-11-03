<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE html>
<%@page import="object.User"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    	if(!(profile.equals("CED")||profile.equals("CREDEM"))){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
    
%>
    <title>Cedacri Statistics</title>
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- JqueryUI CSS -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    <!-- JQUERY UI --> 
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        .ui-datepicker {
            border: 0px ;
        }
        .ui-datepicker-calendar {
        display: none;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <div id="wrapper">
            <!-- Navigation -->
       <jsp:include page="nav/navbar.jsp">
       <jsp:param name="profile" value="<%=profile %>" />
       </jsp:include>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
      <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Grafico statistica cutoff</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
             <div class="row">
                <div class="col-lg-6">
                 <input class="input-search form-control" id="autocomplete" placeholder="Search for JOB name ..." >
                 </div>
                 <div class="col-lg-6">
                     <div id="datepicker"></div>
                 </div>
                 <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->       
               <div class="row">
                   <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto"></div>
                    <div class="col-lg-12">
                       
                                    </div>
                    <!-- /.col-lg -->  
                                   </div>
                   <!-- /.row -->  
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
     </div>
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- jQueryUI -->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../js/highcharts.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/exporting.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <script src="../js/ced/urlUtility.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script type="text/javascript"> 
        var period=getUrlParameter("period");
        var jobname=getUrlParameter("jobname");
        var hashmapMinutesOccurences={};
        var optionsChart={
         chart:{   type: 'column',
            renderTo:'container',
            zoomType: 'x'
        },
        title: {
            text: ''
        },
        xAxis: {
            plotLines: [{
                label: {
                    text: '',
                    style: {
                        color: 'blue',
                        fontWeight: 'bold'
                    }},   
                color: 'red', // Color value
                dashStyle: 'longdashdot', // Style of the plot line. Default to solid
                value: 0, // Value of where the line will appear
                width: 2 // Width of the line    
           }],
            tickInterval:60,
            categories: [],
            crosshair: true
        },
        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: ' occorrenze'
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: 'occorrenze',
            data: []}]
    };
        function getSuggest(request,response){
    	    	$.getJSON("../queryResolver?id=cutoff/cutoffSuggest&&query="+request.term.replace("#","%23")+'%25',
                function (json){
    	    	    var data=[];
                    json.data.forEach(function(element){
                       data.push(element[0]); 
                    });
                    response(data);});}
            
        function onSelect( event, ui ) {
                jobname=ui.item.value.replace("#","%23");
                search();
    		   }
        function search(){
            hashmapMinutesOccurences={};
            initializeHashMap(hashmapMinutesOccurences);
            $.getJSON("../queryResolver?id=cutoff/cutoffChart&period="+period+"&jobname="+jobname,drawChart);
        }           
        function onChangePeriod(year, month, inst) {
                mm=month.toString();
                mm=mm[1]?mm:"0"+mm[0];
                $("#datepicker").datepicker('setDate',mm +'/01/'+year);
                period=$("#datepicker").datepicker("getDate").yyyymmdd().substr(0,7);
                typeof jobname!=='undefined'&&search(); 
            }
       function initializeHashMap(hashmapMinutesOccurences){
            for(var hour=0;hour<24;hour++){
                for(var minute=0;minute<60;minute++){
                    hour=zeroPad(hour.toString());
                    minute=zeroPad(minute.toString());
                    hashmapMinutesOccurences[hour.toString()+":"+minute.toString()]=0;
                }
            }           
       }
       function drawChart(json){
           json.data.forEach(function(element){
               hashmapMinutesOccurences[element[0]]=parseInt(element[1]);
           });
           optionsChart.xAxis.categories=[];
           optionsChart.series[0].data=[]; 
           for(var key in hashmapMinutesOccurences){
               optionsChart.xAxis.categories.push(key);
               optionsChart.series[0].data.push(hashmapMinutesOccurences[key]);
           }
          if(json.data.length!==0){
          optionsChart.xAxis.plotLines[0].value=optionsChart.xAxis.categories.indexOf(json.data[0][2]);
          optionsChart.xAxis.plotLines[0].label.text="Cutoff "+json.data[0][2];}
          optionsChart.title.text=jobname+" "+period;
          chart = new Highcharts.Chart(optionsChart);
       }
       function zeroPad(el){
           return el[1]?el:"0"+el[0];
       }
        $(function(){
            $("#datepicker").datepicker({
                    onChangeMonthYear: onChangePeriod });
            $("#autocomplete").autocomplete({
                    minlength:0,
                    delay:0,
                    source: getSuggest ,
                    select: onSelect});
            if(jobname===undefined){
                period=$("#datepicker").datepicker("getDate").yyyymmdd().substr(0,7);
            }
            else{
                //console.log(period.substr(0,4)+"/01/"+period.substr(5,7));
                $("#datepicker").datepicker('setDate',period.substr(5,7)+"/01"+"/"+period.substr(0,4));
                search();
            }
                
        });

    </script>
</body>
<%} %>
</html>
