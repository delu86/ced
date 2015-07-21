<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@page import="servlet.ActivationAccountServlet"%>
<%@page import="servlet.LoginControlServlet"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <meta charset="utf-8">
    <!-- This file has been downloaded from Bootsnipp.com. Enjoy! -->
    <title>Portale Statistiche Cedacri</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../CSS/login.css" rel="stylesheet">
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    function controlPwd() {
        if($('#pwd').val().length>=6){
        	$('#submit').prop('disabled',false);}
        else{
        	$('#submit').prop('disabled',true);
        }
    	}
    
    </script>
</head>
<body>
<div class="container">
    <div class="row">
    <div class="page-header text-center" >
           <h1 class="title">Portale Statistiche Cedacri</h1>
           <h1 class="subtitle"><small class="subtitle">Nuova password</small></h1>
           </div>
        <div class="col-sm-6 col-md-4 col-md-offset-4">
        <h5>Inserisci nuova passsword(min. 6 carattteri)</h5>
            <div class="account-wall">
            
                <div id="my-tab-content" class="tab-content">
						<div class="tab-pane active" id="recovery">
               		    <img class="profile-img" src="img/icon-login.png"
                    alt="">
               			<form class="form-signin" action="NewPasswordServlet" method="POST">
               				<input type="password" id="pwd" name="password" class="form-control" placeholder="New Password" required onkeyup="controlPwd()">
               				<input type="hidden" name="email" value="<%=request.getParameter("email")%>">
               				<input type="submit" id="submit" disabled="disabled" class="btn btn-lg btn-default btn-block" value="Save" />
               			</form>
               			<div id="tabs" data-tabs="tabs">
               				</div>
						</div>
										
					</div>
            </div>
        </div>
    </div>
</div>
 <div id="footer">
      <div class="container text-center">
       <a href="http://www.cedacri.it/cedacri/it/index.html"><img alt="" src="img/cedacri-logo.png"></a>
      </div>
    </div>
</body>
</html>
