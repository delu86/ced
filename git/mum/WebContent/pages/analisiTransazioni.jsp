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
    	if(!profile.equals("CED")&&!profile.equals("REALE")){
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

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- JQUERY UI --> 
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
   
    </head>
    <body>
            <div id="wrapper">
            <!-- Navigation -->
       <jsp:include page="nav/navbar.jsp">
       <jsp:param name="profile" value="<%=profile %>" />
       </jsp:include>

         <div id="page-wrapper">
            <div class="container-fluid">
                                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">Analisi transazioni SIES</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
        <input  id="tranName" type="text" placeholder="Trans.name(press ENTER)"/>
        <button  disabled="true" value="1" class="btn btn-outline btn-primary target typeSelector optionsChart" autofocus="true">NumTransazioni/SlotTDR/NumAbend</button>
        <button disabled="true" value="2" class="btn btn-outline btn-primary target typeSelector optionsChart">CpuTime/SlotCPU/Efficienza</button>
        <button disabled="true" value="3"  class="btn btn-outline btn-primary target typeSelector optionsChart">NumTransazioni/SlotCPU/TDRMedio</button>
        <button disabled="true" value="-1"  class="btn btn-default dateshift optionsChart" id="dateshiftBackward">Giorno prec.</button>
        <button disabled="true" value="1"  class="btn btn-default dateshift optionsChart" id="dateshiftForward"> Giorno succ. ></button>
        </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
        <div id="container" style="min-width: 510px; height: 500px; margin: 0 auto"></div>
        </div>
     </div>
             <!-- /.container-fluid -->
            </div>
        <!-- /#page-wrapper -->    
        
                   
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../js/highcharts.js"></script>
    <script src="../js/exporting.js"></script>
    <!-- DataTables JavaScript -->
    <script src="../js/ced/chartsJS.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <script>
        var tranName;
        var chart;
        var offset=0;
        var typeChart=1;
        var date=new Date();
        var optionsChart=getOptionsChartTransactionAnalysis();
            $('#dateshiftForward').prop('disabled',true);    
        function draw(){
        optionsChart.title.text=tranName+' '+date.yyyymmdd().substring(0,10);
        
        drawTransactionAnalysis(optionsChart,
                                "../queryResolver?id=analisiTransazioni"+typeChart+"&tranName="+
                                    tranName.replace("#","%23").toUpperCase()+"&system=SIES&date="+date.yyyymmdd().substring(0,10));
                    }
      $('#tranName').on('keyup', function(e) {
        if (e.keyCode === 13){
            tranName=$('#tranName').val().toUpperCase();
            $(".optionsChart").prop('disabled',false);
            offset===0 ? $('#dateshiftForward').prop('disabled',true) : $('#dateshiftForward').prop('disabled',false);
            draw();
        }
    });
    $("#back").click(function(){
        $("#tableDrillDown").hide();
        $("#chartUI").show(500);
    });                
    $(".typeSelector").click(function(){
        typeChart=$(this).val();
        draw();
    });
    $(".dateshift").click(function(){
             offset+=parseInt($(this).val());
             offset===0 ? $('#dateshiftForward').prop('disabled',true) : $('#dateshiftForward').prop('disabled',false);
             shiftDate(parseInt($(this).val()),date,draw);
         });

    </script>
    <script src="../dist/js/sb-admin-2.js"></script>
    </body>
    <%} %>
</html>
