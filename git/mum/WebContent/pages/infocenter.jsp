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
    	if(!profile.equals("CED")){
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
                        <h3 class="page-header">Infocenter</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <p class="text-primary">Seleziona periodo di riferimento</p>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                      <form action="infocenterExporter" method="POST">
                         <div class="form-inline">
                         <div class="input-group">
                         <span class="input-group-addon" id="sizing-addon1"><i class="fa fa-calendar"></i> </span> 
                         <input type="text"name="date-start" class="form-control"  placeholder="Dal..." size="12" id="datepicker-start">
                         <input type="text" name="date-end" disabled  class="form-control"  placeholder="Al..." size="12" id="datepicker-end">
                         </div>
                         </div>
                         <button type="submit" id="submit" disabled class="btn btn-default">Export in .xls</button>
                         </form>
                
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
    <!-- Custom Theme JavaScript -->
    <script type="text/javascript">
    $(function() {
    	var start_date;
        $( "#datepicker-start" ).datepicker({
        	    dateFormat: 'yy-mm-dd',
        		onSelect: function (dateText, inst) {
    				start_date=dateText;
        			$( "#datepicker-end" ).prop('disabled',false);	
    			},
        beforeShowDay:
		       function(dt) {
			var date=new Date("2015-03-01");
			var today=new Date();
			var diff=Math.ceil((date.getTime()-dt.getTime()) / (1000 * 3600 * 24));
			var diffToday=Math.ceil((today.getTime()-dt.getTime()) / (1000 * 3600 * 24));

			return [diff<=1 && diffToday>=2,""];
		       }
        });
        $( "#datepicker-end" ).datepicker({
    	dateFormat: 'yy-mm-dd',
    	onSelect: function (dateText, inst) {
    		$( "#submit" ).prop('disabled',false);
    	},
        beforeShowDay:
		       function(dt) {
			var date=new Date(start_date);
			var today=new Date();
			var diff=Math.ceil((date.getTime()-dt.getTime()) / (1000 * 3600 * 24));
			var diffToday=Math.ceil((today.getTime()-dt.getTime()) / (1000 * 3600 * 24));

			return [diff<=1 && diffToday>=2 ,""];
		       
    }});
     });
    </script>
    <script src="../dist/js/sb-admin-2.js"></script>
    
</body>
<%} %>
</html>
