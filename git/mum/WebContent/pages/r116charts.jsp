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
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    	if(!profile.equals("CED")){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
    
%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Cedacri Statistics</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- JQuery UI -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <link rel="stylesheet" href="../CSS/dashboard.css">
    <style type="text/css">
        #date-interval{
        display:inline-block;
        }
        .shift{
        display:inline-block;
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
        <div style="display:none" id="loading"></div> 
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">MQ LXECG100 SY7</h3>
                        </div>
                        <!-- /.col-lg-12 -->
                        </div>
                        <!-- /.row -->
                    <div class="row">
                    <div class="col-lg-5">
                     <h5 >Orario UTC</h5>
                              </div>
                       <!-- /.col-lg-4 -->
                    <div class="col-lg-4">
                              <div class="form-inline">
                               <button type="button" class="shift shift-left-one btn btn-default btn-circle"><i class="fa fa-angle-left"></i></button>
                               <h5 id="date-interval"></h5>
                               <button type="button" class="shift shift-right-one btn btn-default btn-circle"><i class="fa fa-angle-right"></i></button>
                               </div>
                               </div>
                       <!-- /.col-lg-4 -->
                       <div class="col-lg-3">
                              </div>
                       <!-- /.col-lg-4 -->
                        </div>
                        <!-- /.row -->
                    <div class="row">
                    <div class="col-lg-12">                    
                    <div id="container" style="min-width: 310px; height: 500px; margin: 0 auto">
                    </div>
                    </div>
                    <!-- /.col-lg-12 -->
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
    <!-- jQuery UI (per datepicker)-->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../js/highcharts.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <script src="../js/exporting.js"></script>
    <script>
        $(function () {
	Highcharts.setOptions({
        lang: {
            numericSymbols: null,
            thousandsSep: '\''
        }
    });
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
                text: 'LatenzaMSG',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
        	min : 0,
            title: {
                text: 'Numero get',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
            	format: '{value:.0f}',
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
    	$('#loading').show();
    $.getJSON('../queryResolver?id=mq/mqMediolanum&offset='+offset, function(json){
    	options.series[0].data=[];
        options.series[1].data=[];
        json.data.forEach(function(element){
            
            options.series[0].data.push([dateToUTC(element[0]).getTime(),Number(element[1])]);
            options.series[1].data.push([dateToUTC(element[0]).getTime(),Number(element[2])]);
        });
        chart = new Highcharts.Chart(options);
   	$('#loading').hide();
   	 return true;
      });
    }
});
    </script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>
