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

    <title>cics trend</title>

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
                        <h3 class="page-header">cics trend monthly</h3>
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
Statement stmt0 = null;
Connection con = null ;
String sql = "";



		try {
//			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Class.forName("com.mysql.jdbc.Driver");
			}
			catch(java.lang.ClassNotFoundException e)
         {
               System.out.println("Driver non trovati");
         }


try {
	
//con =  DriverManager.getConnection("jdbc:db2://sya.ced.it:5036/ITCDNDBEM:user=CRIDA00;password=CRIDA000;specialRegisters=CURRENT QUERY ACCELERATION=ELIGIBLE,CURRENT GET_ACCEL_ARCHIVE=YES;");
con= DriverManager.getConnection( "jdbc:mysql://localhost:3306/support","epv", "epv" );

stmt0 = con.createStatement();
	
//cancellare
sql = "SELECT SYSTEM,  SUBSTR(START_005, 1, 10) AS GIORNO,   CASE DAYOFWEEK_ISO(TO_DATE(SUBSTR(START_005, 1, 10), 'YYYY-MM-DD')) " +
"      WHEN 1 THEN 'Lu' " +
"      WHEN 2 THEN 'Ma' " +
"       WHEN 3 THEN 'Me' " +
"      WHEN 4 THEN 'Gi' " +
"      WHEN 5 THEN 'Ve' " +
"      WHEN 6 THEN 'Sa' " +
"      WHEN 7 THEN 'Do' " +
"    END AS GGSETT, TRAN_001 AS TRAN ,  COUNT(*) AS VOLUMI,  " +
"  ROUND(SUM(DB2REQCT_180) / 1000000, 2) AS db2Req , " +
" ROUND(SUM(CPUTIME), 4) AS CPUtot, " +
"  ROUND(SUM(ELAPSED), 8) AS ELAPSEDtot, " +
"  ROUND(SUM(L8CPUT_259TM), 4) AS L8tot,  " +
"  ROUND(AVG(CPUTIME), 8) AS CPUavg, ROUND(STDDEV(CPUTIME), 8) AS CPUDevStd, " +
"  ROUND(MIN(CPUTIME), 8) AS CPUmin, ROUND(MAX(CPUTIME), 8) AS CPUmax, " +
"  ROUND(AVG(ELAPSED), 8) AS ElapsedAVG, " +
"  ROUND(MIN(CPUTIME), 8) AS ElapsedMin, ROUND(MAX(CPUTIME), 8) AS ElapsedMax , " +
"  ROUND(AVG(L8CPUT_259TM), 4) AS AvgL8  " +
"  FROM CR00515.EPQ110_1_TRXACCT " +
"      where PARTIZIONE IN (SELECT DISTINCT PARTIZIONE  FROM CR00515.EPQ110_1_TRXACCT_IX T  WHERE SUBSTR(T.STARTDAY, 1, 10) = '" + DATA+  "' ) " +
"   and SUBSTR(START_005, 1, 10) = '" + DATA +  "' AND SYSTEM = '" + SIST +"' " +
  " GROUP BY SYSTEM, SUBSTR(START_005, 1, 10), TRAN_001 " +
"  ORDER BY SYSTEM, SUBSTR(START_005, 1, 10), TRAN_001;" ;

sql = "SELECT SID, " +
  "    SDATA, " +
  "  GG, " +
  "  TRAN, " +
  "  VOLUMI, " +
  "  DB2REQ, " +
  "  cputot, " +
  "  elapsedtot, " +
  "  L8Tot, " +
  "  Cpumin, " +
  "  Cpumax, " +
  "  Elapsedmin, " +
  "  Elapsedmax "  +
" FROM cics_day " +
" WHERE SDATA= '" + DATA + "' AND " + " SID='" + SIST + "' ";

String sql0 = "  SELECT cics_acc.SID,  DATA,  ggSett,  PROC,  tran_dist,  ElapsedTot,  CputimeTot,  VolumiTot, " + 
"   CpuAVG,  ElapsedAVG,  CpuPerc,  VolPerc FROM support.cics_acc " +
" WHERE DATA= '" + DATA + "' AND " + " SID='" + SIST + "' ";

out.print("<br> " );
out.print("<br> - TOTALE PER PROCEDURA - SMF RECORD TYPE 110 " );
out.print("<br>Sid:" + SIST + " Data: " + DATA + "  Cics PROC CPU" );

//out.print(sql0);

ResultSet rs0 =  stmt0.executeQuery(sql0);
ResultSetMetaData rsmd0 = rs0.getMetaData() ;
int numberOfColumns0 = rsmd0.getColumnCount();
int count0=0;
out.print("<table class=\"stat\">");
                        out.print("<tr>" );
                                                for (int h = 4;h<=numberOfColumns0;h++)
                                                        out.println("<th>"+  rsmd0.getColumnLabel(h)  + "</th> " );
                        out.print("</tr>" );


      while(rs0.next())
                        {
                        out.print("<tr>" );
                        out.println("<td>"+  rs0.getString(4) + "</td> " );
                        out.println("<td>"+  String.format(Locale.ITALIAN, "%.0f",rs0.getDouble(5) )  +  "</td> " );
                        out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs0.getDouble(6) )  +  "</td> " );
                        out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs0.getDouble(7) )  +  "</td> " );
                        out.println("<td>"+  String.format(Locale.ITALIAN, "%.0f",rs0.getDouble(8) )  +  "</td> " );
                        out.println("<td>"+  String.format(Locale.ITALIAN, "%.5f",rs0.getDouble(9) )  +  "</td> " );
                        out.println("<td>"+  String.format(Locale.ITALIAN, "%.5f",rs0.getDouble(10) )  +  "</td> " );
                        for (int h = 11 ;h<=numberOfColumns0;h++)
                         out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs0.getDouble(h) )  +  "</td> " );
                        out.print("</tr>" );

                count0++;
                }
out.print("</table>" );



out.print("<br>"  );
out.print("<br> - TOTALE PER TRANSAZIONE - SMF RECORD TYPE 110 " );
out.print("<br>Sid:" + SIST + " Data: " + DATA + "  Cics TRAN  CPU" );


stmt = con.createStatement();
ResultSet rs =  stmt.executeQuery(sql);
ResultSetMetaData rsmd = rs.getMetaData() ;
int numberOfColumns = rsmd.getColumnCount();
 
int i = 0 ;
String var="";


out.print("<table class=\"stat\">");
			out.print("<tr>" ); 			
						for (int h = 4;h<=numberOfColumns;h++)
							out.println("<th>"+  rsmd.getColumnLabel(h)  + "</th> " );
			out.print("</tr>" );			
			
			
      while(rs.next()) 
			{    
			out.print("<tr>" ); 
			for (int h = 4;h<7;h++)
	    	 		out.println("<td>"+  rs.getString(h) + "</td> " );
			for (int h = 7;h<10 ;h++)
			 out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble(h) )  +  "</td> " );
			for (int h = 10 ;h<=numberOfColumns;h++)
			 out.println("<td>"+  String.format(Locale.ITALIAN, "%.5f",rs.getDouble(h) )  +  "</td> " );
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
