<%@page import="datalayer.DatabaseManager"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    	if(!profile.equals("CED")){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
  datalayer.DatabaseManager db=new DatabaseManager();  
%>
<%Collection<String> systems=db.getSystems(); %>

<head>

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
    
    <!-- Bootstrap Switch -->
    <link href="../CSS/bootstrap-switch.min.css" rel="stylesheet">
    <link href="../CSS/dashboard.css" rel="stylesheet">

        <style type="text/css">
        #info{
        display:inline-block;
        }
        #prev{
        display:inline-block;
        }
        #next{
        display:inline-block;
        }
        #date-interval{
        display:inline-block;
        }
 
        </style>

        
</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <!-- Navigation -->
       <jsp:include page="nav/navbar.jsp">
       <jsp:param name="profile" value="<%=profile %>" />
       </jsp:include>
        <!-- Page Content -->
       <div style="display:none" id="loading"></div> 
        <div id="page-wrapper">
            <div class="container-fluid">

                <div class="row">
                
                     <div class="col-lg-8">
                          <div class="btn-group" role="group" aria-label="...">
                    <%
                    int i=0;
                    for(String sys: systems){ %>
                              <button type="button" <%if(i==0){%>autofocus="true"<% i++;}%>class="btn btn-default target" value="<%=sys%>"> <%out.print(sys); %></button>
                  <%} %>
                         </div>
        		    <input type="checkbox" name="my-checkbox" checked data-off-color="warning" data-on-text="Week" data-off-text="Month">
                         
                    </div>
                    <!-- /.col-lg-8 -->
                    <div class="col-lg-4">
                    <div>
						<button type="button" id="prev" class="btn" aria-label="Settimana precedente" >
 						<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
						</button>
						<h4 id="info"><span class="label label-info" id="date-interval">Info</span></h4>
						<button type="button" id="next" disabled="disabled" class="btn" aria-label="Settimana successiva">
 					    <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
						</button>
						<button type="button" class="btn btn-default goUp" > <i class="fa fa-level-up fa-fw"></i></button>
					</div>
					</div>
                    <!-- /.col-lg-4 -->
               </div>
               <!-- /.row -->
                <div class="row">
               <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            
                                <div id="container" style="min-width: 310px; height: 500px; margin: 0 auto">
                               </div> 
                               </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
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

   <script src="../js/jquery-ui.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    
    <script src="../js/bootstrap-switch.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>    
    <script src="../js/highcharts.js"></script>
    <script src="../js/data.js"></script>
    <script src="../js/exporting.js"></script>
    <script src="../js/ced/dateJS.js"></script>
    <script> var type='mips';</script>
    <script src="../js/charts/charts113.js" charset="utf-8"></script>
 </body>
<%} %>
</html>