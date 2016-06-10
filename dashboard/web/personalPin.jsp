<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<%if(session.getAttribute("user")==null) response.sendRedirect("index.jsp"); %>
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

<body>
<div data-role="page">
<script><!--script per l'inserimento dei valori nel codice pin -->
var i=0;
function accoda(v){
   v.form.pin.value+=v.value;
   i+=1;
    if( i>4){
    	$('[type="submit"]').button('refresh');
    	$('[type="submit"]').button('enable');	
    }
};
function cancella(v){
    v.form.pin.value='';
    i=0;
    $('[type="submit"]').button('disable');
}
</script>
</head>
	<div data-role="header" data-theme="b"><h1>Login</h1></div><!-- /header -->
	<div role="main" class="ui-content">
<h3>Immetti un nuovo PIN per confermare la registrazione(min 5 cifre)</h3>
	<form data-ajax="false" name="Myform" action="CompletePinRecoveryServlet">
	<table border=0 style="border-collapse:collapse" align="center">
		<tr><td colspan=3><input type="password"  name="pin" id="text-basic"
		readonly="readonly" value="" placeholder="usa tastierino per PIN" ></td></tr>
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
		<td><input type="submit" value="ok" disabled></td>
		<td><input type="button" value=<%=tasti.get(0) %>  onclick="accoda(this)"></td>
		<td><input type="button" value="del" onclick="cancella(this)"></td></tr>
</table> 
	</form>
		</div><!-- /content -->
<jsp:include page="footer.jsp"/>
</div><!-- /page -->
</body>
</html>