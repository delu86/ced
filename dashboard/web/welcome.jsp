<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
Cookie cookie = new Cookie("user",(String) request.getSession().getAttribute("user"));
cookie.setMaxAge(30*24 * 60 * 60);  // 1 month. 
cookie.setPath("/");
response.addCookie(cookie);
response.sendRedirect("dashboard.jsp"); 
%>
<title>SMSAlert Mobile Edition</title>
</head>
<body>
</body>
</html>