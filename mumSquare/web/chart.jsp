<%-- 
    Document   : chart
    Created on : 9-feb-2016, 11.10.05
    Author     : CRE0260
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>Chart</title>
        <style>
            #container{
                min-width: 310px; 
                height: 500px; 
                margin: 0 auto
            }
        </style>
    </head>
    <body>
        <h1><c:out value="${requestScope.id}"></c:out></h1>
        <div id="container"></div>
    </body>
    <script src="javascript/jquery.js"></script>
    <script src="javascript/highcharts.js"></script>
    <script src="json/createChart.js"></script>
    <script>
        $(function(){
           createChart('first','container',0); 
        });
    </script>
</html>
