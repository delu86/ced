<%-- 
    Document   : chart
    Created on : 9-feb-2016, 11.10.05
    Author     : CRE0260
--%>

<%@page import="javax.naming.Context"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.sql.DataSource"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
<html>
    <%
      Context initContext = new InitialContext();
      Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
      DataSource datasource=(DataSource) envContext.lookup("epvDB");
      ResultSet rs=datasource.getConnection().prepareStatement("SELECT tData from realebis_ctrl.epv030_23_intrvl_sies_stc where tData='2016-02-22 11:00' ").executeQuery();
      rs.next();
    %>
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
        <h1><% out.println(rs.getString(1));%></h1>
        <div id="container"></div>
    </body>
    <script src="js/jquery.js"></script>
    <script src="js/highcharts.js"></script>
    <script src="json/createChart.js"></script>
    <script>
        $(function(){
           createChart('first','container',0); 
        });
    </script>
</html>
