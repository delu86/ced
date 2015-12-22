<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ page import="java.sql.*,java.util.*,java.io.*, java.text.*;" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%

String JDBC_DRIVER = "";
String DB_URL = "";
String USER_DB = "";
String PASS_DB= "" ;
int MESI = 0;
int SISTEMI = 0;
String[] SID  = new String[4];
		
		try {
			String currentDir = System.getProperty("user.dir");
			String webRootPath = getServletContext().getRealPath("/").replace('\\', '/');
			File file = new File( webRootPath + File.separator  + "config.properties");
			System.out.println( webRootPath + File.separator  + "config.properties" );
			
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);			
		
			JDBC_DRIVER=properties.getProperty("JDBC_DRIVER");
			DB_URL=properties.getProperty("DB_URL");
			USER_DB=properties.getProperty("USER");
			PASS_DB=properties.getProperty("PASS");
			MESI =Integer.parseInt(properties.getProperty("MESI"));
			SISTEMI =Integer.parseInt(properties.getProperty("SISTEMI"));
			SID[0] =properties.getProperty("SID1");
			SID[1] = properties.getProperty("SID2");
			SID[2] = properties.getProperty("SID3");
			SID[3] = properties.getProperty("SID4");
			fileInput.close();
			/*
			 * Enumeration enuKeys = properties.keys(); while
			 * (enuKeys.hasMoreElements()) { String key = (String)
			 * enuKeys.nextElement(); String value =
			 * properties.getProperty(key); System.out.println(key + ": " +
			 * value); }
			 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	

String AnnoN  = "";
String AnnoN1  = "";
String AnnoN2  = "";
String AnnoN3  = "";
String MeseN  = "";
String MeseN1  = "";
String MeseN2  = "";
String MeseN3  = "";

String sq= " --- ";

Calendar cal = Calendar.getInstance();
System.out.println("Before "+cal.getTime()); 
int month = 0;
int anno = 0;

/*
*/


String[] cal_sel = new String[MESI];

cal.add(Calendar.MONTH, 1);
month = cal.get(Calendar.MONTH);
anno = cal.get(Calendar.YEAR);

MeseN= ""+ month;
AnnoN= "" + anno;
if (month<10) 
	MeseN="0" + month;
if ((month==0) || anno==2016 )	
  {
	AnnoN="2015";
	MeseN="12";
  }	
cal_sel[0] =  AnnoN   + "-" + MeseN;

// System.out.print(cal_sel[0]);
for (int j= 1 ; j<MESI; j++)
{
cal.add(Calendar.MONTH, -1);
month = cal.get(Calendar.MONTH);
anno = cal.get(Calendar.YEAR);
MeseN = ""+ month;
if (month<10) 
	MeseN="0" + month;
AnnoN= "" + anno;
cal_sel[j] =  AnnoN   + "-" + MeseN;
// System.out.print(cal_sel[j]);
 }
 

String AAAAMM    = request.getParameter("AAAAMM");
String SIST = request.getParameter("SIST");

if (SIST == null || SIST.length() == 0) 
	{
	SIST  = SID[0];
	}

if  ( (AAAAMM == null || AAAAMM.length() == 0) ) {
	// AAAAMM=AnnoN +"-"+ MeseN;
	AAAAMM  = cal_sel[0];
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
                            <b>J</b>ob batch Full Outsourcing analysis  
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">

<br>
	<form action="consumi.jsp">

		<select name="SIST">
				<%
				for (int h=0;h<SISTEMI;h++) 
				{
				System.out.print(SID[h]);
				if (SIST.equals(SID[h]) )
					out.print("<option value=" + SID[h]  + "  selected > " + SID[h] + "</option>");
				else	
					out.print("<option value=" + SID[h]  + "  > " + SID[h] + "</option>");
				}
			%>			
		<%
		/*
		if (SIST.equals("GSY7") )
			out.print("<option value=\"GSY7\" selected>GSY7</option>");
		else	
			out.print("<option value=\"GSY7\">GSY7</option>");
		
		if (SIST.equals("BSY2") )
			out.print("<option value=\"BSY2\" selected>BSY2</option>");
		else	
			out.print("<option value=\"BSY2\">BSY2</option>");
					
		if (SIST.equals("sy5") )
			out.print("<option value=\"ZSY5\" selected>ZSY5</option>");
		else	
			out.print("<option value=\"ZSY5\">ZSY5</option>");
		*/
		%>	
		</select>


		<select name="AAAAMM">
		<%
		for (int k=0;k<MESI;k++)
		{ 
		if (AAAAMM.equals(cal_sel[k]) )
			out.print("<option value=\"" + cal_sel[k] +  "\" selected>" + cal_sel[k] +  " </option>");
		else	
			out.print("<option value=\"" + cal_sel[k] + "\">" + cal_sel[k] + "</option>");
		}
		
		%>	
		</select>
		
		
		<input type="submit" value="Change" >
	</form>


<%

Statement stmt = null;
Connection con = null ;
String sql = "";

		try {
			Class.forName(JDBC_DRIVER);
			}
			catch(java.lang.ClassNotFoundException e)
         {
               System.out.println("Driver non trovati");
         }


try {
	
//con =  DriverManager.getConnection("jdbc:db2://sya.ced.it:5036/ITCDNDBEM:user=CRIDA00;password=CRIDA000;specialRegisters=CURRENT QUERY ACCELERATION=ELIGIBLE,CURRENT GET_ACCEL_ARCHIVE=YES;");
// con =  DriverManager.getConnection(DB_URL + ":user=" + USER_DB+";password=" + PASS_DB + ";specialRegisters=CURRENT QUERY ACCELERATION=ELIGIBLE,CURRENT GET_ACCEL_ARCHIVE=YES;");
// con =  DriverManager.getConnection(DB_URL ,  USER_DB , PASS_DB );

 con= DriverManager.getConnection( "jdbc:mysql://10.99.252.23/smfacc","cedacri", "cedacri" );
stmt = con.createStatement();
sql =  "SELECT   SID , " + 
      " ggSett , " + 
      " DATA , " + 
      " NroSS , " + 
      " totJOBS , " + 
      " jobsDist ," +
     "  UserDist , pjobsDist , totERR , ERR , errCPU , " + 
     "  tot1MIN , j1min , j1minCPU , tot1TO5min , j1to5min , " +
     "  j1TO5minCPU , tot5min , j5min , j5minCPU , " + 
     "  cputime , ziptime , elapsed , totDay , totNight " + 
" FROM  smfacc.TrendBatch " +  
 "    where SUBSTRING(DATA , 1 , 7 )  = '" + AAAAMM + "'  AND SID  = '" + SIST  +  "'" + 
 "   order by DATA;  "; 
	
 ResultSet rs =  stmt.executeQuery(sql);

ResultSetMetaData rsmd = rs.getMetaData() ;
 int numberOfColumns = rsmd.getColumnCount();
 
int i = 0 ;
String var="";
//out.print("SID:" + SIST);
// out.print(sql);
out.print("<table class=\"stat\">");
%>  

	
<%
			out.print("<tr >" ); 			
				 		out.println("<th colspan=2><B>"+ SIST+ "</B></th> " );
						out.println("<th colspan=6>"+ "#jobs"+ "</th> " );						
						out.println("<th colspan=3>"+ " minute "+ "</th> " );
						out.println("<th colspan=6>"+ "% cpu"+ "</th> " );						
			out.print("</tr>" );			
			
			out.print("<tr bgcolor=yellow>" ); 			
				 		out.println("<th  colspan=2>"+ "day"+ "</th> " );
						out.println("<th  >"+ "total"+ "</th> " );
						out.println("<th  >"+ "distinct"+ "</th> " );
						out.println("<th  >"+ "err"+ "</th> " );					
						out.println("<th  >"+ "<=1 mi"+ "</th> " );
						out.println("<th >"+ "1to5 mi"+ "</th> " );
						out.println("<th  >"+ ">=5 mi"+ "</th> " );
						out.println("<th  >"+ "cpu"+ "</th> " );
						out.println("<th  >"+ "zip"+ "</th> " );
						out.println("<th   >"+ "elapsed"+ "</th> " );
						out.println("<th  >"+ "day"+ "</th> " );
						out.println("<th   >"+ "night"+ "</th> " );
						out.println("<th   >"+ "<=1 mi"+ "</th> " );
						out.println("<th   >"+ "1to5 mi"+ "</th> " );
						out.println("<th   >"+ ">=5 mi"+ "</th> " );
						out.println("<th   >"+ "err"+ "</th> " );

						//out.println("<th bgcolor=red>"+  + "</th> " );						
						
			out.print("</tr>" );			
			
 
			out.print("<tbody>" );			
      while(rs.next()) 
			{    
			if (rs.getString("GGSETT").equals("Do") ||  rs.getString("GGSETT").equals("Sa")) 
				out.print("<tr bgcolor=#c0c0c0>" ); 
			else
				out.print("<tr>" ); 
				 		out.println("<td>"+  rs.getString("GGSETT") + "</td> " );
						out.println("<td>"+  rs.getString("DATA") + "</td> " );
						out.println("<td>"+  rs.getString("TOTJOBS") +  "</td> " );
						out.println("<td>"+  rs.getString("JOBSDIST") + " (" + rs.getString("pJOBSDIST")  + "%)"  +  "</td> " );
						out.println("<td>"+  rs.getString("totERR") + "</td> " );						
						out.println("<td  >"+  rs.getString("J1MIN") +  "%</td> " );
out.println("<td  >"+  rs.getString("J1TO5MIN") +  "%</td> " );
// out.println("<td >"+  rs.getString("J5MIN") +  "%</td> " );
out.println("<td >"+  
"<a href=batch5min.jsp?SIST=" + SIST 
+  "&DATA=" + rs.getString("DATA")  +
">" + 
rs.getString("J5MIN") +  "%" + "</a>" + "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("CPUTIME") / 60 )   +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f", rs.getDouble("ZIPTIME") / 60 ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("ELAPSED") / 60 ) +  "</td> " );
out.println("<td >"+  rs.getString("TOTDAY") +  "%</td> " );
out.println("<td >"+  rs.getString("TOTNIGHT") +  "%</td> " );

out.println("<td  >"+  rs.getString("J1MINCPU") +  "%</td> " );
out.println("<td  >"+  rs.getString("J1TO5MINCPU") +  "%</td> " );
out.println("<td  >"+  rs.getString("J5MINCPU") +  "%</td> " );
out.println("<td  >"+  rs.getString("errCPU") +  "%</td> " );

	
  

	//					for (int h = 2;h<=numberOfColumns;h++)
	//	    	 		out.println("<td>"+  rs.getString(h) + "</td> " );


			out.print("</tr>" );			
		i++;
		}
		out.print("</tbody>" );			
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
