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

<script language="JavaScript" 
   type="text/JavaScript">
function changePage(newLoc)
 {
   nextPage = newLoc.options[newLoc.selectedIndex].value
		
   if (nextPage != "")
   {
      document.location.href = nextPage
   }
 }
</script>

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
<%
String Anno = "";
String Mese = "";
if (request.getParameter("AAAAMM") == null) {
    Anno="";
} else {
   Anno= request. getParameter("AAAAMM").substring(0,4);
   Mese= request. getParameter("AAAAMM").substring(4,6);
}
%>

<%

try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
		out.println(" MySQL JDBC Driver not Found");
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
		 
			if (connection == null) {
				out.println("Failed to make connection!");
				System.exit(1);
			}
	 
			%>
			
			
			<br>
			<!--  form action="LouisP.jsp"-->
			<form method="GET" name="menu" class="select-style">
			
			<%
			Statement stmt0 = null;
			ResultSet rs0 = null;
			   String sql0 =""+ 
   " SELECT DISTINCT ( concat( SUBSTRING(INTERVALLO,1,4), SUBSTRING( INTERVALLO,6,2)  ) )   AS AAAAMM "
                                            + " FROM `smfacc`.`epv116_qa_LXECG100` ORDER BY 1 DESC"
                                            ;
		
	//		   out.print(sql0); 
				try {
					  	stmt0 = connection.createStatement();
					  
					    rs0 = stmt0.executeQuery(sql0);
						//out.print("<select name=AAAAMM>");
						 %>
						 <select name="selectedPage" onChange="changePage(this.form.selectedPage)">
					<% 
					String topt="";
					    int count=1;
						while(rs0.next()) 
					    {
					    	 topt =" <option value=err.jsp?AAAAMM=" + rs0.getString(1) ;
					     
					   	if (request.getParameter("AAAAMM") == null && count==1) 	 
					   	{
					   	  Anno= rs0.getString(1).substring(0,4);
					      Mese= rs0.getString(1).substring(4,6);
					   	}
					    if ( rs0.getString(1).equals((Anno +""+ Mese)) )
					    		 topt+=" selected=selected " ;
					     
					    	topt+= "> " + 
					     getMonth(
					    		 Integer.parseInt(rs0.getString(1).substring(4,6))
					   			)  + " " +
					   			rs0.getString(1).substring(0,4)
					    		+" </option>";
					    		out.print(topt);
					    count++;
					    }
					   out.print("</select>");
						
			    }
				catch (SQLException ex){
				    // handle any errors
				    out.println("SQLException: " + ex.getMessage());
				    out.println("SQLState: " + ex.getSQLState());
				    out.println("VendorError: " + ex.getErrorCode());
				}
						    
			%>
			<!--input type="submit" value="Go"-->
			
			
			<br>
			</form> 
			
			<%
			
			Statement stmt = null;
			
			ResultSet rs = null;
			ResultSetMetaData rsData = null;
			try {
				
			    stmt = connection.createStatement(	ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
			   
String sql = "" ;

sql = "select DISTINCT substring(INTERVALLO,1,10) as Date  from epv116_qa_LXECG100 " + 
" where   SUBSTRING(INTERVALLO,1,4) = " + Anno  + " AND   SUBSTRING(INTERVALLO,6,2)  = " + Mese +   
 "  order by 1" ; 
 
			   //  out.print(sql);
			  rs = stmt.executeQuery(sql);
			  rsData = rs.getMetaData();
			  int numberOfColumns = rsData.getColumnCount();
			    
			  out.print("<br>");
			     
			  rs.first();
			  String SID= "GSY7";
			  out.print("<p class=fred>" +  SID + "</p>");
			  rs.previous();
			     
			  out.print("<table border=1 class=table>");
			  out.print("<thead><tr>");
		    	 for (int k = 1;k<=numberOfColumns;k++)
		    	 		out.println("<th>"+ rsData.getColumnName(k)+ "</th> " );
		    	 out.print("</tr></thead>");
				
		    	 int dname=0;		
		    	 int countR=1;
		    	 while(rs.next()) {
		    		 
		    	 out.print("<tbody><tr>");
			    	 
		    	 for (int k = 1;k<=numberOfColumns;k++)
			    	 {	 
			    	if (k==1) 
			    		 // out.println("<td > " + rs.getString(k) + "</td>");
			    		  out.println("<td > "  + "<a href=r116_detail.jsp?SID=" + SID  +
					 "&ELABDATA=" +  rs.getString(k) + ">" + rs.getString(k) + "</a>" + "</td>");
			    	else
			    		{
			    			if ( k > 4)
			    			 out.println("<td align=right> " +  
			    		//	 NumberFormat.getNumberInstance(Locale.ITALY).format(Float.parseFloat(rs.getString(k))) 
			    		 rs.getString(k)  + "</td>");
			    			else
			    	  	         out.println("<td align=right> " +  rs.getString(k) + "</td>");
			    		}
			    	}
			    	 out.print("</tr>");
			        	countR++;
		    	 	}
			     out.print("</tbody></table>");
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
			

<%!

public int getDay(int Anno,int Mese,int Giorno ){

	
	Calendar calendar = new GregorianCalendar(Anno,Mese,Giorno);
	 
	int year       = calendar.get(Calendar.YEAR);
	int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
	int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
	int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
	int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);
 
	/*System.out.println("year \t\t: " + year);
	System.out.println("month \t\t: " + month);
	System.out.println("dayOfMonth \t: " + dayOfMonth);
	System.out.println("dayOfWeek \t: " + dayOfWeek);
	System.out.println("weekOfYear \t: " + weekOfYear);
	System.out.println("weekOfMonth \t: " + weekOfMonth);
*/
	return dayOfWeek;
}

%>
<%!
private String getDayOfWeek(int value){
    String day = "";
    switch(value){
    case 1:
        day="Dom";
        break;
    case 2:
        day="Lun";
        break;
    case 3:
        day="Mar";
        break;
    case 4:
        day="Mer";
        break;
    case 5:
        day="Gio";
        break;
    case 6:
        day="Ven";
        break;
    case 7:
        day="Sab";
        break;
    }
    return day;
}
%>

<%!
private String getMonth(int value){
    String mm = "";
    switch(value){
    case 1:
        mm="Gen";
        break;
    case 2:
        mm="Feb";
            break;
    case 3:
        mm="Mar";
         break;
    case 4:
        mm="Apr";
        break;
    case 5:
        mm="Mag";
        break;
    case 6:
        mm="Giu";
        break;
    case 7:
        mm="Lug";
        break;
    case 8:
        mm="Ago";
        break;
    case 9:
        mm="Set";
        break;
    case 10:
        mm="Ott";
        break;
    case 11:
        mm="Nov";
        break;
    case 12:
        mm="Dic";
        break;
  
    }
    return mm;
}
%>

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

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>
