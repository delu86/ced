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


String[] cal_sel = new String[MESI];


month = cal.get(Calendar.MONTH)+1;
anno = cal.get(Calendar.YEAR);
MeseN= ""+ month;
AnnoN= "" + anno;
if (month<10) 
	MeseN="0" + month;
	
cal_sel[0] =  AnnoN   + "-" + MeseN;

// System.out.print(cal_sel[0]);
for (int j= 1 ; j<MESI; j++)
{
cal.add(Calendar.MONTH, -1);
month = cal.get(Calendar.MONTH) + 1;
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

table, td, th {
    border-collapse: collapse;
    padding: 6px;
    border: 1pt solid gray;
}

th {
    background-color: green;
    color: white;
}

table.stat th {  
  font-size : 100%;
  font-variant: small-caps;
  font-family : Verdana , Arial, "Myriad Web",Verdana,Helvetica,Arial,sans-serif;
	  background : #E5E4E2 none; color : #625D5D ; }

table.stat td {
	text-align: right;  
  font-size : 77%;
  font-family : Verdana, Arial,"Myriad Web",Verdana,Helvetica,sans-serif;
  color : black;  
 }


select.cOpt  
{     font-size : 12px ; color: #1569C7   }

input.bOpt {
  font-size : 12px ;
  background: lightgray;
  border: 1px solid white;
  color: white;
  width: 100px;
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
                        <h3 class="page-header">Cics trend monthly</h3>
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
                            <b>CICS</b> Full Outsourcing analysis  
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">

<br>
	<form action="consumiCICS.jsp">


		<select name="SIST" class="cOpt">
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
		</select>


		<select name="AAAAMM" class="cOpt">
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
		
		
		<input type="submit" value="Change" class="bOpt" >
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
	

con= DriverManager.getConnection( "jdbc:mysql://10.99.252.23/smfacc","cedacri", "cedacri" );
stmt = con.createStatement();

sql = "SELECT SID,  " +  
    " ggSett, DATA,  volumi, Cputime, CpuAVG,CpuDevSTD,CpuMin, CpuMax,Elapsed,ElapsedAVG,ElapsedMin, ElapsedMax, L8, " + 
"   avgL8, db2mil FROM smfacc.TrendCics  "  + 
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
						out.println("<th colspan=1>"+ "#"+ "</th> " );						
						out.println("<th colspan=5>"+ " cpu "+ "</th> " );
						out.println("<th colspan=4>"+ " elapsed "+ "</th> " );						
						out.println("<th colspan=2>"+ " l8 "+ "</th> " );						
						out.println("<th colspan=1>"+ " db2 "+ "</th> " );						
			out.print("</tr>" );			
			
			out.print("<tr bgcolor=yellow>" ); 			
				 		out.println("<th  colspan=2>"+ "day"+ "</th> " );
						out.println("<th  >"+ "Volume"+ "</th> " );
						out.println("<th  >"+ "sum"+ "</th> " );
						out.println("<th  >"+ "avg"+ "</th> " );					
						out.println("<th  >"+ "dev std"+ "</th> " );
						out.println("<th >"+ "min"+ "</th> " );
						out.println("<th  >"+ "max"+ "</th> " );
						out.println("<th  >"+ "sum"+ "</th> " );
						out.println("<th  >"+ "avg"+ "</th> " );
						out.println("<th   >"+ "min"+ "</th> " );
						out.println("<th  >"+ "max"+ "</th> " );
						out.println("<th   >"+ "sum"+ "</th> " );
						out.println("<th   >"+ "avg"+ "</th> " );
						out.println("<th   >"+ "#"+ "</th> " );						
						//out.println("<th bgcolor=red>"+  + "</th> " );						
			out.print("</tr>" );			
			
			out.print("<tbody>" );			
      while(rs.next()) 
			{    
			if (rs.getString("ggSett").equals("Do") ||  rs.getString("ggSett").equals("Sa")) 
				out.print("<tr bgcolor=#E5E4E2>" ); 
			else
				out.print("<tr>" ); 
				 		out.println("<td>"+  rs.getString("ggSett") + "</td> " );
						out.println("<td >"+  "<a href=cicsday.jsp?SIST=" + SIST +  "&DATA=" + rs.getString("DATA")  + ">" + rs.getString("DATA") +  "" + "</a>" + "</td> " );
//						out.println("<td>"+  rs.getString("DATA") + "</td> " );
						out.println("<td>"+  rs.getString("volumi") +  "</td> " );
						out.println("<td>"+  rs.getString("Cputime") +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.4f",rs.getDouble("CpuAVG") )  +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.4f",rs.getDouble("CpuDevSTD") ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("CpuMin") ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("CpuMax") )+  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("Elapsed") )+  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.4f",rs.getDouble("ElapsedAVG") ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("ElapsedMin")  ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("ElapsedMax")  ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("L8")  )+  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.4f",rs.getDouble("avgL8")  ) +  "</td> " );
						out.println("<td>"+  String.format(Locale.ITALIAN, "%.2f",rs.getDouble("db2mil") )+  "</td> " );

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
