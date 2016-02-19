<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%String message=(String)request.getAttribute("message");%>
<%String messageOK=(String)request.getAttribute("message_ok");%>
<%
	request.getSession().removeAttribute("user");
    

%>
<head>
    <meta charset="utf-8">
    <!-- This file has been downloaded from Bootsnipp.com. Enjoy! -->
    <title>Portale Statistiche Cedacri</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../CSS/login.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <style type="text/css">
    .message{
    color: #d60000
    }
    .messageOK{
    color:#2eeea3
    }
    </style>
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
           <h1 class="title">Portale Statistiche Cedacri </h1>
           <h1 class="subtitle"><small class="subtitle">Login</small></h1>
           </div>
        <%if(message!=null){ %><h3 class="message"><%out.print(message); %></h3><%} %>
        <%if(messageOK!=null){ %><h3 class="messageOK"><%out.print(messageOK); %></h3><%} %>
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <div class="account-wall">
                <div id="my-tab-content" class="tab-content">
						<div class="tab-pane active" id="login">
               		    <img class="profile-img" src="img/icon-login.png"
                    alt="">
               			<form class="form-signin" action="LoginControlServlet" method="POST">
               				<input type="text" name="username" class="form-control" placeholder="Email Address ..." required autofocus>
               				<input type="password" name="password" class="form-control" placeholder="Password" required>
               				<input type="submit" class="btn btn-lg btn-default btn-block" value="Sign In" />
               				<input type="hidden" name="url_req" value="<%=request.getParameter("url_req")%>">
               		<!-- 	<label><input type="checkbox" name="cookie" value="yes">Mantieni sessione</label>  -->	
               			</form>
               			<div id="tabs" data-tabs="tabs">
               				<p class="text-center"><a href="#register" data-toggle="tab">Registrati</a></p>
              				<p class="text-center"><a href="#passwordRecovery" data-toggle="tab">Recupera password</a></p>
              				</div>
						</div>
						<div class="tab-pane" id="register">
                                                    <h5>Utilizzare email aziendale per registrarsi  </h5>
							<form class="form-signin" action="CreateNewAccountServlet" method="POST">
								<input type="email" name="email" class="form-control" placeholder="Email Address ..." required autofocus>
								<input type="password" id="pwd" name="password" class="form-control" placeholder="Password ...(min. 6 chars)" required onkeyup="controlPwd()">
								<input type="submit" id="submit" disabled="disabled" class="btn btn-lg btn-default btn-block" value="Sign Up" />
							</form>
							<div id="tabs" data-tabs="tabs">
               			<p class="text-center"><a href="#login" data-toggle="tab">Login</a></p>
               			<p class="text-center"><a href="#passwordRecovery" data-toggle="tab">Recupera password</a></p>
              			</div>
						</div>
						<div class="tab-pane" id="passwordRecovery">
						<p class="text-center"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span><h5>Insert your registration email. You will receive a link where you can recovery your 
						       password.</h5></p>
							<form class="form-signin" action="recovery" method="POST">
								<input type="email" name="email" class="form-control" placeholder="Email Address ..." required autofocus>
								<input type="submit" class="btn btn-lg btn-default btn-block" value="Send" />
							</form>
							<div id="tabs" data-tabs="tabs">
               			<p class="text-center"><a href="#login" data-toggle="tab">Login</a></p>
               			<p class="text-center"><a href="#register" data-toggle="tab">Registrati</a></p>
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
    <script >
    //placeholder su internet Explorer
   var isExplorer= false || !!document.documentMode;
   if(isExplorer){
   $('input[placeholder]').each(function(){ 
    	 
        var input = $(this);        
 
        $(input).val(input.attr('placeholder'));
 
        $(input).focus(function(){
             if (input.val() == input.attr('placeholder')) {
                 input.val('');
             }
        });
 
        $(input).blur(function(){
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.val(input.attr('placeholder'));
            }
        });
    });}
    </script>
</body>
</html>
