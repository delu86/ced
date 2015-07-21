<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.*,java.util.*,java.text.*;" %>

<!DOCTYPE html>
<html lang="en">
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp?url_req="+request.getRequestURL());
    else{
    	String profile=user.getProfile();
    	if(!profile.equals("CED")){
    		request.getRequestDispatcher("no_authorization.jsp").forward(request, response);
    	}
    
%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Epv</title>

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

    <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
     <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    </head>
<body>
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
                        <h1 class="page-header"> </h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
                <div class="panel panel-default">
                        <div class="panel-heading">
                       	zOS Performance Analysis
                        </div>
                        <div class="panel-body">
                       
                    <div class="col-lg-12">
                            <div class="panel panel-primary">
                        <div class="panel-heading">
MQ LXECG100 SY7
                        </div>
                        <div class="panel-body">
                       
<input type="button" value="Back" onclick="window.history.back()" class="btn" /> 
<br>
<%
String DATAELAB = "";
String SID = "";
   DATAELAB= request. getParameter("ELABDATA");
   SID= request. getParameter("SID");


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
				.getConnection("jdbc:mysql://10.99.252.23:3306/smfacc","cedacri", "cedacri");
		 
			} catch (SQLException e) {
				e.printStackTrace();
				out.print(e.getMessage()); 
				return;
			}
		 
			if (connection == null) {
				out.println("Failed to make connection!");
				System.exit(1);
			}
	 
			
			Statement stmt = null;
			Statement stmt1 = null;
			
			ResultSet rs = null;
			ResultSetMetaData rsData = null;
			ResultSet rs1 = null;
			ResultSetMetaData rsData1 = null;
	
			try {
							   
			   stmt = connection.createStatement(	) ; 
			   out.println("<br>");
			   out.println("<p class=fred>");
			   // out.print(  SID);
                           String Anno =DATAELAB.substring(0,4);
	   		   String Mese = DATAELAB.substring(5,7); 						
	   		   String Giorno = DATAELAB.substring(8,10); 						
                             
			   out.print(" Anno :  " + "<b>" + 

			   		   DATAELAB.substring(0,4) +  					
			   		   " </b> Mese : <b>" +  DATAELAB.substring(5,7) +						
			   	           "</b>  Giorno :<b> " +  DATAELAB.substring(8,10) + "</b>"  
					   );	 		   
			   out.println("</p>");
			   out.println("<br>");
				   

String sql = 
" SELECT " + 
"  substring(   `epv116_qa_LXECG100`.`INTERVALLO`, 11, 6) as TIME , " + 
"    `epv116_qa_LXECG100`.`TOT_OPERAZIONI` as Operazioni, " +
"    `epv116_qa_LXECG100`.`TOT_CPUTIME` as CpuTime, " +
"    `epv116_qa_LXECG100`.`TOT_ELAPSED` as Elapsed, " + 
"   round( `epv116_qa_LXECG100`.`GET_BYTES`,0) as GetBytes, " +
"   round( `epv116_qa_LXECG100`.`PUT_BYTES` ,0) as PutBytes, " + 
"    `epv116_qa_LXECG100`.`LatenzaMSG`, " +
"    `epv116_qa_LXECG100`.`nOPEN`, " + 
"    `epv116_qa_LXECG100`.`nCLOSE`, " + 
"    `epv116_qa_LXECG100`.`nGET`, " +
"    `epv116_qa_LXECG100`.`nPUT`, " + 
"    `epv116_qa_LXECG100`.`nINQ`, " + 
"    `epv116_qa_LXECG100`.`nSET`, " + 
"    `epv116_qa_LXECG100`.`nPUT1`, " + 
/* "    `epv116_qa_LXECG100`.`cpuOPEN`, " +
"    `epv116_qa_LXECG100`.`cpuCLOSE`, " +
"    `epv116_qa_LXECG100`.`cpuGET`, " +
"    `epv116_qa_LXECG100`.`cpuPUT`, " +
"    `epv116_qa_LXECG100`.`cpuINQ`, " +
"    `epv116_qa_LXECG100`.`cpuSET`, " + 
"    `epv116_qa_LXECG100`.`cpuPUT1`, " +
"    `epv116_qa_LXECG100`.`elapOPEN`, " +
"    `epv116_qa_LXECG100`.`elapCLOSE`, " + 
"    `epv116_qa_LXECG100`.`elapGET`, " + 
"    `epv116_qa_LXECG100`.`elapPUT`, " + 
"    `epv116_qa_LXECG100`.`elapINQ`, " + 
"    `epv116_qa_LXECG100`.`elapSET`, " + 
"    `epv116_qa_LXECG100`.`elapPUT1`," +
*/
"    `epv116_qa_LXECG100`.`validGet`, " +
"    `epv116_qa_LXECG100`.`validPut` " +
" from  smfacc.epv116_qa_LXECG100  where SUBSTRING(INTERVALLO,1,4) = " + "'" +Anno + "'" + " AND SUBSTRING( INTERVALLO,6,2)= '" + Mese + "'"  +
" and SUBSTRING(INTERVALLO,9,2) = " + "'" +Giorno + "'"   +
 " order by 1 ";

   
			//     out.println(sql);
			    rs = stmt.executeQuery(sql);
			     rsData = rs.getMetaData();
			     int numberOfColumns = rsData.getColumnCount();
			    
			     
%>			     
		  <div class="dataTable_wrapper">
      <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
<%
			     out.print("<thead><tr>");
		    	 for (int k = 1;k<=numberOfColumns;k++)
		    	 		out.println("<th>"+ rsData.getColumnLabel(k)+ "</th> " );
		    	 out.print("</tr></thead>");
				
		    	 int countR=1;
			out.print("<tbody>");
		    	 while(rs.next()) {
			    	 out.print("<tr>"); 
			    	 for (int k = 1;k<=numberOfColumns;k++)
			    			 out.println("<td> " + rs.getString(k) + "</td>");
			    	out.print("</tr>");
			        countR++;
		    		}
			        out.print("</tbody>");
			        out.print("</table>");
			        
%>

<BR>			        
<!-- <a href="<%=request.getContextPath()%>/LouisPexcel?ELABDATA=<%=DATAELAB%>&SID=<%=SID%>"> Download R113 maximum detail counters  for SID:<b><%=SID %></b> DATA <b><%=DATAELAB%></b> &nbsp;&nbsp;(XLS format)</a> -->
			        
 <%
 /*
			        out.print("<br>");
					  
				    stmt1 = connection.createStatement(	) ; 
					   out.println("<br>");
					   out.println("<p class=fred>");
					   out.print(  SID);
					   out.print(" " + DATAELAB );	 		   
					   out.println("</p>");
					   out.println("<br>");
						   

					   String sql1 =""+ 
							   "SELECT * " +
								" FROM r113_resume" +
							   " WHERE " +  	    		
		   " SMF113DTE=" + "'" + DATAELAB +  "'" + " AND SMF113SID=" + "'" +  SID + "'" 
		   + "  order by 3,convert(CPU,UNSIGNED INTEGER)";
		   
					 //   out.println(sql);
					    rs1 = stmt.executeQuery(sql1);
					     rsData1 = rs1.getMetaData();
					     int numberOfColumns1 = rsData1.getColumnCount();
					    
					     
					     out.print("<table border=1>");
					     out.print("<tr>");
				    	 for (int k = 3;k<=numberOfColumns1;k++)
				    	 		out.println("<th>"+ rsData1.getColumnName(k)+ "</th> " );
				    	 out.print("</tr>");
						
				    	 int countRd=1;
				    	 while(rs1.next()) {
					    	 out.print("<tr>"); 
					    	 for (int k = 3;k<=numberOfColumns1;k++)
					    			 out.println("<td align=right> " + rs1.getString(k) + "</td>");
					    	out.print("</tr>");
					        countRd++;
				    		}
					        out.print("</table>");
*/			        
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
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->   
             
        </div>
        <!-- /#page-wrapper -->
            
          

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <script src="../bower_components/jquery/dist/jquery-ui.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="../js/jquery.dataTables.min.js"></script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('.table').DataTable({
                responsive: true
        });
    });
    </script>

</body>
<%} %>
</html>
