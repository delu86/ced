<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cedacri Dashboard Mobile Edition</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.css" />
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.4/jquery.mobile-1.4.4.min.js"></script>

</head>
<body>
<body>
<%String message=(String) request.getAttribute("errorReg"); %> 
<div data-role="page">
<script>
var i=0;
function accoda2(v){
   v.form.pin.value+=v.value;
   i+=1;
    if( i>4){
    	$('[type="submit"]').button('refresh');
    	$('[type="submit"]').button('enable');	
    }
};
function cancella2(v){
    v.form.pin.value='';
    i=0;
    $('[type="submit"]').button('disable');
}
</script>
	<div data-position="fixed" data-role="header" data-theme="b" data-add-back-btn="true"><h1>Registrazione</h1></div><!-- /header -->
	<div role="main" class="ui-content"> 
	<%if(message!=null){%><h3><font color="red"><%out.print(message); %></font></h3><% }%>
	<h3><font color="blue">Inserisci una mail del dominio e un pin di almeno 5 cifre </font>
	<form  method="POST" action="RegistrationServlet">
	<input type="text" name="email" id="text-basic" value="" placeholder="email@dominio.it">
	<table border=0 style="border-collapse:collapse" align="center">
		<tr><td colspan=3><input type="password" name="pin" id="text-basic"
		readonly="readonly" value="" placeholder="usa tastierino per PIN"></td></tr>
		<tr><td><input type="button" value=<%=tasti.get(7) %> onclick="accoda2(this)"></td>
		<td><input type="button" value=<%=tasti.get(8) %>    onclick="accoda2(this)"></td>
		<td><input type="button" value=<%=tasti.get(9) %>     onclick="accoda2(this)"></td>
													</tr>
	<tr>
		<td><input type="button" value=<%=tasti.get(4) %> onclick="accoda2(this)"></td> 
		<td><input type="button" value=<%=tasti.get(5) %> onclick="accoda2(this)"></td> 
		<td><input type="button" value=<%=tasti.get(6) %> onclick="accoda2(this)"></td>
	</tr>
	<tr>  
		<td><input type="button" value=<%=tasti.get(1) %> onclick="accoda2(this)"></td>
		<td><input type="button" value=<%=tasti.get(2) %> onclick="accoda2(this)"></td> 
		<td><input type="button" value=<%=tasti.get(3) %> onclick="accoda2(this)"></td>
	</tr>
	<tr> 
		<td><input type="submit" value="ok" disabled="disabled"></td>
		<td><input type="button" value=<%=tasti.get(0) %>  onclick="accoda2(this)"></td>
		<td><input type="button" value="del" id="delete" onclick="cancella2(this)"></td></tr>
</table> 
	</form>
	</h3>
		
	</div><!-- /content -->
<jsp:include page="footer.jsp"/>
</div><!-- /page -->
</body>
</body>
</html>