<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    
%>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Epv</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

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
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">   
                        <h1 class="page-header"> <img src=img/logo-epvtech.jpg></h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
                
                
                <div class="panel panel-default">
                        <div class="panel-heading">
                       	zOS Performance Analysis
                        </div>
                        <div class="panel-body">
                 <%if(profile.equals("CED")){ %>
                              
                    <div class="col-lg-4">
                            <div class="panel panel-primary">
                        <div class="panel-heading">
                            EPV performance analysis -   All SID 
                        </div>
                        <div class="panel-body">
                           <img src=img/Cedacri.jpg width=240 height=90> 
                        </div>
                        <div class="panel-footer">
                              <a href="http://10.99.252.22/work1/EPVROOT/USERPROFILE/CED/COMMON/HTM/START.HTML" > Report & Graph  </a>  
                        </div>
              		      </div>
                    </div>
                    <% } %>
                     <%if(profile.equals("CED")||profile.equals("CREDEM")){ %>
                       
                       <div class="col-lg-4">
                          <div class="panel panel-green">
                        <div class="panel-heading">
                            EPV performance analysis- Exctract
                        </div>
                        <div class="panel-body">
                         <img src=img/Credem.png width=90 height=90>
                       </div>
                        <div class="panel-footer">
                              <a href="http://10.99.252.22/work1/EPVROOT/USERPROFILE/CRED/COMMON/HTM/START.HTML" > Report & Graph  </a>  
                        </div>
                    	</div>
                    </div>
                    <% } %>
                     <%if(profile.equals("CED")||profile.equals("REALE")){ %>
                       
                       <div class="col-lg-4">
                      <div class="panel panel-info">
                        <div class="panel-heading">
                               EPV performance analysis - Exctract
                        </div>
                        <div class="panel-body">
                     	   <img src=img/reale.gif width=90 height=90>
                            </div>
                        <div class="panel-footer">
                                <a href="http://10.99.252.22/work1/EPVROOT/USERPROFILE/REALE/COMMON/HTM/START.HTML" > Report & Graph  </a>  
                        </div>
                    </div>
                    </div>
<% } %>
                     <%if(profile.equals("CED")||profile.equals("CARIGE")){ %>
                       
                       <div class="col-lg-4">
                          <div class="panel panel-green">
                        <div class="panel-heading">
                            EPV performance analysis- Exctract
                        </div>
                        <div class="panel-body">
                         <img src=img/carige_ass.jpg width=90 height=90>
                       </div>
                        <div class="panel-footer">
                              <a href="http://10.99.252.22/work1/EPVROOT/USERPROFILE/CARIGEASS/COMMON/HTM/START.HTML" > Report & Graph  </a>  
                        </div>
                    	</div>
                    </div>
<%} %>
                    
                          </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->   
             
        </div>
        <!-- /#page-wrapper -->
            
          

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>





</body>
<%} %>
</html>
