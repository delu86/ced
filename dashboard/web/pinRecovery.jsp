<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.css" />
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.js"></script>
<title>Cedacri Dashboard Mobile Edition</title>
</head>
<body>
<% String messageReg=(String) request.getAttribute("errorReg");%>
<div data-role="page">
	<div data-role="header" data-theme="b"  data-add-back-btn="true" ><h1>Recupero PIN</h1></div><!-- /header -->
	<div role="main" class="ui-content"> 
	   <%if(messageReg!=null){%><h3><font color="red"><%out.print(messageReg); %></font></h3><% }%><!--messaggio di registrazione fallita o login fallito -->
	  <h3><font color="blue">Inserisci l'email con cui ti sei registrato. Ti verrà fornito un nuovo pin via email.</font></h3>
		<form method="POST" name="form" action="PinRecoveryServlet">
	    <input type="text" name="email" id="text-basic" value="" placeholder="email@dominio.it">
	    <input type="submit" value="Invia">
	    </form>
	</div><!-- /content -->
<jsp:include page="footer.jsp"/>
</div><!-- /page -->
</body>
</html>