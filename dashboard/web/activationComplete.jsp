<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Cedacri Dashboard Mobile Edition</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.css" />
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.js"></script>

<%String message=(String)request.getAttribute("activation_message"); %>

</head>
<body>
<body>
<div data-role="page">
	<div data-role="header" data-theme="b"><h1>Attivazione account</h1></div><!-- /header -->
	<div role="main" class="ui-content"> 
 	<h3><%out.print(message); %></h3>
 	<h3><a href="index.jsp">Login</a></h3>
	</div><!-- /content -->
<jsp:include page="footer.jsp"/>
</div><!-- /page -->
</body>

</body>
</html>