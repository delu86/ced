<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.*,java.util.*,java.text.*;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp");
    else{
    	String profile=user.getProfile();
    
%>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

<title>jsfm ISE</title>

 <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->

<link rel="stylesheet" href="../js/themes/blue/style.css" type="text/css" id="" media="print, projection, screen" />
	
<style> 
	body { background-color: #ffffff;font-family:Courier,  Arial, helvetica ;color:#004080;font-size: 10px;}
	.comment  { font-size: 10px;color:#004080;font-family:Courier ;}
	.goback { font-size: 14px;color:#004080;font-family:Courier ;}
	h2 { font-size: 12px;font-family:Arial ;}
	h3 { font-size: 14px;font-family:Arial ;}
	table {  border-collapse: collapse; }
	table  {   border: 1px solid gray;font-family:Arial;	} 
	td {font-family:Arial; font-size: 12px; padding: 3px; color:black}
	th {font-family:Arial; font-size: 12px; color: black; background-color: lightgray ;padding: 3px; }
	p {font-family:Arial; font-size: 12px; color: gray; }
	
	input[type=text] {padding:5px; border:2px solid #EBF5F0; 
-webkit-border-radius: 5px;
    border-radius: 5px;
}
input[type=text]:focus {border-color:#EBF5F0; }

input[type=submit] {padding:5px 15px; background:#EBF5F0; border:0 none;
    cursor:pointer;
    -webkit-border-radius: 5px;
    border-radius: 5px; }
</style>

<style>
#overlay {
	top: 100px;
	left: 50%;
	position: absolute;
	margin-left: -100px;
	width: 200px;
	text-align: center;
	display: none;
	margin-top: -10px;
	background: #000;
	color: #FFF;
}
</style>
</head>
<%
String sito;
String sitoDesc ="" ;
sito=request.getParameter("sito");
%>
<body>
<%
if (sito == null) {
    sito="001";
}
%>


 <div id="wrapper">
           <!-- Navigation -->
       <jsp:include page="nav/navbar.jsp"> 
	   <jsp:param name="profile" value="<%=profile %>" />  
       </jsp:include>  

                <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="page-header">Cod. ISE Istituti</h3>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <b>ISE</b> Codifica 3n 2n 2a
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">

<form action="jIse.jsp" method="get">
<p>Cod. Ise :&nbsp;
<% 
 if ( sito.equals("001") ) 
     out.print(" <input type=\"radio\" name=\"sito\" value=\"001\" checked=\"checked\"> P1 &nbsp; ");
  else
	  out.print(" <input type=\"radio\" name=\"sito\" value=\"001\" > P1 &nbsp;");
  
  if  ( sito.equals("002") )
  out.print(" <input type=\"radio\" name=\"sito\" value=\"002\" checked=\"checked\"> P2 &nbsp;");
    else 
    	out.print(" <input type=\"radio\" name=\"sito\" value=\"002\" > P2 &nbsp;");
  %>
  <input type="submit" value="&nbsp;Go&nbsp;">
</p>
</form>

<%
    if ( sito.equals("001") ) 
			    	 sitoDesc="p1";
	if ( sito.equals("002") ) 
			    	 sitoDesc="p2";
	// out.println("<h3>ise: <b>" +sitoDesc.toUpperCase() + "</b></h3>" );



try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println(" MySQL JDBC Driver not Found");
				e.printStackTrace();
				return;
			}
			Connection connection = null;
		 
			try {
				connection = DriverManager
				.getConnection("jdbc:mysql://10.99.252.23/smfacc","cedacri", "cedacri");
		 
			} catch (SQLException e) {
				e.printStackTrace();
				out.print(e.getMessage()); 
				return;
			}
		 
			if (connection != null) {
				System.out.println("Connect");
			} else {
				System.out.println("Failed to make connection!");
			}
	 
			%>
			
			<%
			Statement stmt = null;
			ResultSet rs = null;
			ResultSetMetaData rsData = null;
			try {
			    stmt = connection.createStatement();
			   
			
			    
			    String sql =" " + 
			   " SELECT ist as a2,a3n, descr as istituto " +
			 " FROM smfacc.ise_" +sitoDesc + " order by descr";
			    // out.println(sql);
			    rs = stmt.executeQuery(sql);
			     rsData = rs.getMetaData();
			     int numberOfColumns = rsData.getColumnCount();
			     out.print("<table border=1 id=\"myTable\" class=\"tablesorter\"><thead>");
			     out.print("<tr>");
		    	 for (int k = 1;k<=numberOfColumns;k++)
		    	 		out.println("<th>"+ rsData.getColumnLabel(k).toUpperCase()  + "</th> " );
		    	 out.print("</tr></thead>");
				
		    	 out.print("<tbody>");
				     
		    	 while(rs.next()) {
			    	 out.print("<tr>");
			    	 for (int k = 1;k<=numberOfColumns;k++)
			    	 {	 out.println("<td > " +  rs.getString(k) + "</td>");
		    	 }
			    	 out.print("</tr>");
			        	}
		    	  out.print("</tbody>");
		    	 out.print("</table>");
			     // Now do something with the ResultSet ....
			}
			catch (SQLException ex){
			    // handle any errors
			    out.println("SQLException: " + ex.getMessage());
			    out.println("SQLState: " + ex.getSQLState());
			    out.println("VendorError: " + ex.getErrorCode());
			} finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed
			    if (rs != null) {
			        try {
			            rs.close();
			        } catch (SQLException sqlEx) { } // ignore
			rs = null; }
			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { } // ignore
			stmt = null; }
			}
			
			%>


</div>
                                                            
                                        </div>
                                    </div>
                                </div>
				  </div>
                            </div>
                        </div>
                        <!-- .panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        
        </div>
        <!-- /#page-wrapper -->
            
          

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
			
  <script type="text/javascript" src="../js/jquery.tablesorter.js"></script>
  <script type="text/javascript" id="js">$(document).ready(function() {
        // call the tablesorter plugin, the magic happens in the markup
        $("table").tablesorter();
        //assign the sortStart event
        $("table").bind("sortStart",function() {
                $("#overlay").show();
        }).bind("sortEnd",function() {
                $("#overlay").hide();
        });
}); </script>

			
</body>
<%} %>
</html>
