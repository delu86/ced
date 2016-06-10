<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
if(request.getSession().getAttribute("user")==null&&request.getCookies()!=null){
Cookie cookie=null;
Cookie[] cookiesUtente = request.getCookies();
int indice = 0;
while (indice < cookiesUtente.length) {

	  // esegue il ciclo fino a quando ci sono elementi in cookieUtente
	  if (cookiesUtente[indice].getName().equals("user")){
		break;
	  }
	  indice++; 
	  
	  // se trova un cookie con il nome che stiamo cercando esce dal ciclo
	  
	}// while 
if (indice < cookiesUtente.length){
	  cookie = cookiesUtente[indice];
      request.getSession().setAttribute("user", cookie.getValue());
      response.sendRedirect("dashboard.jsp"); 
}   
}else{
if(request.getParameter("logout")!=null){
Cookie cookie = new Cookie("user", "");
cookie.setMaxAge(0); 
cookie.setPath("/");
response.addCookie(cookie);
request.getSession().removeAttribute("user");
}
if(request.getSession().getAttribute("user")!=null)
response.sendRedirect("dashboard.jsp");
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%ArrayList<String> tasti=new ArrayList<String>();
  tasti.add(0, "0");
  tasti.add(1, "1");
  tasti.add(2, "2");
  tasti.add(3, "3");
  tasti.add(4, "4");
  tasti.add(5, "5");
  tasti.add(6, "6");
  tasti.add(7, "7");
  tasti.add(8, "8");
  tasti.add(9, "9");
  Collections.shuffle(tasti);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.css" />
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.js"></script>
<title>Cedacri Dashboard Mobile Edition</title>
<style type="text/css">
table{
    table-layout: fixed;
    width: 300px;
}
</style>
</head>
<%String message=(String) request.getAttribute("message");
 String messageReg=(String) request.getAttribute("errorReg");%>
<body>
<div data-role="page">
<script src="scripts/pin.js">
</script>
	<div data-position="fixed" data-role="header" data-theme="b"><h1>Login</h1></div><!-- /header -->
	<div role="main" class="ui-content">
	<%if(message!=null){%><h3><font color="blue"><%out.print(message); %></font></h3><% }%> <!--messaggio di registrazione andato a buon fine -->
	<%if(messageReg!=null){%><h3><font color="red"><%out.print(messageReg); %></font></h3><% }%><!--messaggio di registrazione fallita o login fallito -->
	<form data-ajax="false"  method="POST" action="LoginServlet">
	<input type="text" name="email" id="text-basic" value="" placeholder="email@dominio.it">
	<table border=0 style="border-collapse:collapse" align="center">
		<tr><td colspan=3><input type="password" name="pin" id="text-basic"
		readonly="readonly" value="" placeholder="usa tastierino per PIN"></td></tr>
		<tr><td><input type="button" value=<%=tasti.get(7) %> onclick="accoda(this)"></td>
		<td><input type="button" value=<%=tasti.get(8) %>    onclick="accoda(this)"></td>
		<td><input type="button" value=<%=tasti.get(9) %>     onclick="accoda(this)"></td>
													</tr>
	<tr>
		<td><input type="button" value=<%=tasti.get(4) %> onclick="accoda(this)"></td> 
		<td><input type="button" value=<%=tasti.get(5) %> onclick="accoda(this)"></td> 
		<td><input type="button" value=<%=tasti.get(6) %> onclick="accoda(this)"></td>
	</tr>
	<tr>  
		<td><input type="button" value=<%=tasti.get(1) %> onclick="accoda(this)"></td>
		<td><input type="button" value=<%=tasti.get(2) %> onclick="accoda(this)"></td> 
		<td><input type="button" value=<%=tasti.get(3) %> onclick="accoda(this)"></td>
	</tr>
	<tr> 
		<td><input type="submit" value="ok"></td>
		<td><input type="button" value=<%=tasti.get(0) %>  onclick="accoda(this)"></td>
		<td><input type="button" value="del" id="delete" onclick="cancella(this)"></td></tr>
</table> 
	</form>
	<h3><a href="registration.jsp">Registrati</a></h3>
	<h3><a href="pinRecovery.jsp">PIN dimenticato?</a></h3>
	</div><!-- /content -->
<jsp:include page="footer.jsp"/>
</div><!-- /page -->
</body>
</html>