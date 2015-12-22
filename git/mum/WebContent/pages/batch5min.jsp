<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ page import="java.sql.*,java.util.*,java.io.*, java.text.*;" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%



String DATA    = request.getParameter("DATA");
String SIST = request.getParameter("SIST");;

if (SIST == null | SIST.length() == 0) 
	{
	SIST  = "GSY7";
	}

%>
<!DOCTYPE html>
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

    <title>batch trend</title>

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

	<style>
table {
    border-collapse: collapse;
}

table, td, th {
padding: 5px;
    border: 1px solid black;
}

th {
    background-color: green;
    color: white;
}

table.stat th {  
  font-size : 77%;
  font-family : "Myriad Web",Verdana,Helvetica,Arial,sans-serif;
  background : #efe none; color : #630; }

table.stat td {
	text-align: right;  
  font-size : 77%;
  font-family : "Myriad Web",Verdana,Helvetica,Arial,sans-serif;
  color : black;  
 }
 
p.rosso {   color: red;}
p.verde {   color: green;}
</style>

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
                        <h3 class="page-header">batch trend monthly</h3>
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
                            ...  from <b>I</b>BM <b>D</b>B2 <b>A</b>nalytics <b>A</b>ccelerator for zOS on SYA 
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">


<button onclick="goBack()">Go Back</button>

<script>
function goBack() {
    window.history.back();
}
</script>


<br>

<%

Statement stmt = null;
Connection con = null ;
String sql = "";


		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			}
			catch(java.lang.ClassNotFoundException e)
         {
               System.out.println("Driver non trovati");
         }


try {
	
con =  DriverManager.getConnection("jdbc:db2://sya.ced.it:5036/ITCDNDBEM:user=CRIDA00;password=CRIDA000;specialRegisters=CURRENT QUERY ACCELERATION=ELIGIBLE,CURRENT GET_ACCEL_ARCHIVE=YES;");
stmt = con.createStatement();

	
sql = " SELECT  CASE DAYOFWEEK_ISO ( TO_DATE( SUBSTR( INITIALTIME , 1 ,  10 ) , 'YYYY-MM-DD'  ) )    "  +
"           WHEN 1 THEN 'Lu' "  +
"           WHEN 2 THEN 'Ma' "  +
"           WHEN 3 THEN 'Me' "  +
"           WHEN 4 THEN 'Gi' "  +
"           WHEN 5 THEN 'Ve' " +
"           WHEN 6 THEN 'Sa' " +
"           WHEN 7 THEN 'Do' "  +        
"      END AS gg, " +
    " initialtime, endtime, JESNUM  , SMF30JBN AS jobname , SMF30PGM as pgm , "  + 
      "  ROUND( CPUTIME / 60 , 2 ) as cputime  , ROUND(  ZIPTM / 60 , 2)   as ziptime ,   ROUND( ELAPSED / 60 , 2)    as elapsed   " + 
    " FROM CR00515.EPV30_4_STEP " +
     " where  " + 
	 " SYSTEM = '" + SIST + "'" +  
	 " and SMF30WID='JES2' " + 
	 " And   SUBSTR( INITIALTIME  , 1 ,  10 )   =  '" +DATA + "'" +                 
               " and CPUTIME  >= 300 "  +
               " order by CPUTIME DESC  ; " 
;

ResultSet rs =  stmt.executeQuery(sql);
ResultSetMetaData rsmd = rs.getMetaData() ;
 int numberOfColumns = rsmd.getColumnCount();
 
int i = 0 ;
String var="";
out.print("<br>Sid:" + SIST + " Data: " + DATA + "  Job(S) Step(S)  min. CPU" );
//out.print(sql);
out.print("<table class=\"stat\">");

			out.print("<tr>" ); 			
						for (int h = 1;h<=numberOfColumns;h++)
							out.println("<th>"+  rsmd.getColumnLabel(h)  + "</th> " );
			out.print("</tr>" );			
			
			
      while(rs.next()) 
			{    
			if (rs.getString("gg").equals("Do") ||  rs.getString("gg").equals("Sa")) 
				out.print("<tr bgcolor=#c0c0c0>" ); 
			else
				out.print("<tr>" ); 
			
	

						for (int h = 1;h<=numberOfColumns;h++)
		    	 		out.println("<td>"+  rs.getString(h) + "</td> " );

			out.print("</tr>" );			
		i++;
		}
out.print("</table>" );		
		
 }catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace() ;
			System.out.println("se "+se.getErrorCode()+ " " +  se );
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
			System.out.println( e );
		}
		finally {
					con.close();
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

</body>
<%} %>
</html>
