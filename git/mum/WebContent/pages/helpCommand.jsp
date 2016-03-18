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
    	if(!profile.equals("CED")&&!profile.equals("REALE")){
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
    <link href="../CSS/dashboard.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
            <div class="row">
           <div class="col-lg-12">
                        <h3 class="page-header">Lista comandi disponibili</h3>
                    </div> 
            </div> 
            <div class="row">
                <div class="panel panel-default">
                        
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Comando(i)</th>
                                            <th>Descrizione</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>dash</td>
                                            <td>vai alla dashboard</td>
                                        </tr>
                                        <tr>
                                            <td>help</td>
                                            <td>vai all'help dei comandi</td>
                                        </tr>
                                        <tr>
                                            <td>exit</td>
                                            <td>effettua il logout</td>
                                        </tr>
                                        <tr>
                                            <td>cpi</td>
                                            <td>grafico del CPI (SMF113)</td>
                                        </tr>
                                        <tr>
                                            <td>mips</td>
                                            <td>grafico dei MIPS (SMF113)</td>
                                        </tr>
                                        <tr>
                                            <td>wkl</td>
                                            <td>grafico dei workload Cedacri</td>
                                        </tr>
                                        <tr>
                                            <td>wkla</td>
                                            <td>grafico dei workload Carige</td>
                                        </tr>
                                        <tr>
                                            <td>wklr</td>
                                            <td>grafico dei workload Reale</td>
                                        </tr>
                                        <tr>
                                            <td>jbl , jooble</td>
                                            <td>lancia jooble</td>
                                        </tr>
                                        <tr>
                                            <td>jba</td>
                                            <td>job analysis</td>
                                        </tr>
                                        <tr>
                                            <td>jbtr</td>
                                            <td>job trend</td>
                                        </tr>
                                        <tr>
                                            <td>atm</td>
                                            <td>statistiche ATM</td>
                                        </tr>
                                        <tr>
                                            <td>sla</td>
                                            <td>service level agree</td>
                                        </tr>
                                        <tr>
                                            <td>epv</td>
                                            <td>epv Cedacri</td>
                                        </tr>
                                        <tr>
                                            <td>epva</td>
                                            <td>epv Carige</td>
                                        </tr>
                                        <tr>
                                            <td>epvr</td>
                                            <td>epv Reale</td>
                                        </tr>
                                        <tr>
                                            <td>epvc</td>
                                            <td>epv Credem</td>
                                        </tr>
                                        <tr>
                                            <td>admin</td>
                                            <td>console amministrazione server</td>
                                        </tr>
                                        <tr>
                                            <td>nav , file</td>
                                            <td>file browser</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
        </div>
        <!-- /#row -->
    </div>
    
        </div>
        <!-- /#page-wrapper -->
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>
