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
			//File file = new File( currentDir + File.separator +"config.properties");
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
cal.add(Calendar.MONTH, 1);
month = cal.get(Calendar.MONTH);
anno = cal.get(Calendar.YEAR);
MeseN= ""+ month;
if (month<10) 
	MeseN="0" + month;
AnnoN= "" + anno;

cal.add(Calendar.MONTH, -1);
month = cal.get(Calendar.MONTH);
anno = cal.get(Calendar.YEAR);
MeseN1= ""+ month;
if (month<10) 
	MeseN1="0" + month;
AnnoN1= "" + anno;

cal.add(Calendar.MONTH, -1);
month = cal.get(Calendar.MONTH);
anno = cal.get(Calendar.YEAR);
MeseN2= ""+ month;
if (month<10) 
	MeseN2="0" + month;
AnnoN2= "" + anno;

String[] cal_sel = new String[3];
 cal_sel[0] =  AnnoN  + "-" + MeseN ;
 cal_sel[1] =   AnnoN1  + "-" +  MeseN1 ;
 cal_sel[2] =  AnnoN2 + "-"+   MeseN2 ;
*/


String[] cal_sel = new String[MESI];

cal.add(Calendar.MONTH, 1);
month = cal.get(Calendar.MONTH);
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
                            ...  from <b>I</b>BM <b>D</b>B2 <b>A</b>nalytics <b>A</b>ccelerator for zOS on SYA 
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
con =  DriverManager.getConnection(DB_URL + ":user=" + USER_DB+";password=" + PASS_DB + ";specialRegisters=CURRENT QUERY ACCELERATION=ELIGIBLE,CURRENT GET_ACCEL_ARCHIVE=YES;");
stmt = con.createStatement();

sql =   " SELECT YY.SID , ggSett , DATA , NroSS, totJOBS ,  totJOBSDistinti AS jobsDist, UserDist,  " + 
"  round ( totJOBSDistinti / double(totJOBS) * 100 , 1 )  as pjobsDist ,  " + 
"  totERR , round( double(totERR) / totJOBS *100 , 4)   as ERR ,  " + 
" round(  (totERRCPU / cputime * 100) , 2 ) as errCPU , tot1MIN , " + 
"round(  ( double(tot1min) / double(totjobs) * 100) , 1 ) as j1min,   " + 
"round(  (tot1mincpu/ cputime * 100) , 1 ) as j1minCPU ,  tot1TO5min ,  " + 
"round(  ( double(tot1TO5min) / double(totjobs) * 100) , 1 ) as j1to5min,   " + 
"round(  (tot1TO5minCPU/ cputime * 100) , 1 ) as j1TO5minCPU ,  tot5min ,  " +  
"round(  ( double(tot5min) / double(totjobs) * 100) , 1 ) as j5min,   " + 
"round(  (tot5minCPU / cputime * 100) , 1 ) as j5minCPU , " + 
" cputime , ziptime , elapsed ,  " + 
" round( totDay / cputime * 100 , 1 ) as totDay, " + 
" round( totNight / cputime * 100 , 1 ) as totNight   " + 
  " from (   " + 
"    SELECT  " + 
 "  	 SYSTEM as SID  ,  " + 
       	" CASE DAYOFWEEK_ISO ( TO_DATE( SUBSTR( ENDTIME , 1 ,  10 ) , 'YYYY-MM-DD'  ) )    " + 
"           WHEN 1 THEN 'Lu' " + 
"           WHEN 2 THEN 'Ma' " + 
"           WHEN 3 THEN 'Me' " + 
"           WHEN 4 THEN 'Gi' " + 
"           WHEN 5 THEN 'Ve' " + 
"           WHEN 6 THEN 'Sa' " + 
"           WHEN 7 THEN 'Do' " + 
"      END AS ggSett , " +      
"     SUBSTR(ENDTIME , 1 , 10 ) AS DATA  , COUNT(*) AS totJOBS,   " + 
"      COUNT(DISTINCT SMF30JBN ) AS totJOBSDistinti,  " + 
      "  count( DISTINCT SUBSTR( SMF30JBN , 1  , 2 )  ) AS NroSS, " + 
      "  count( DISTINCT SMF30RUD ) AS UserDist ,  " + 
      "  SUM(case when SUBSTR(INITIALTIME , 12 , 2 ) in ( '19', '20', '21' , '22' , '23' , '00' , '01', '02' , '03' , '04', '05', '06' , '07'   )   then CPUTIME else 0 end) as totNight , " +         
      "  SUM(case when SUBSTR(INITIALTIME , 12 , 2 ) in ( '08', '09', '10' , '11' , '12' , '13' , '14', '15' , '16' , '17', '18'   )        then CPUTIME else 0 end) as totDay , " + 
      "  SUM(case when LTRIM( CONDCODE ) > '4'  then 1 else 0 end) as totERR , " +       
      "   round (   SUM(case when LTRIM( CONDCODE ) > '4'  then CPUTIME else 0 end) , 3 ) as totERRcpu , " + 
      "   SUM(case when CPUTIME  <= 60   then 1 else 0 end) as tot1min ,  " + 
      "   SUM(case when CPUTIME  <= 60   then CPUTIME else 0 end) as tot1minCPU ,  " + 
      "   SUM(case when ( CPUTIME > 60   and  CPUTIME  < 300 ) then 1 else 0 end) as tot1TO5min ,  " + 
      "   SUM(case when ( CPUTIME > 60   and  CPUTIME  < 300 ) then CPUTIME else 0 end) as tot1TO5minCPU ,  " + 
      "   SUM(case when CPUTIME  >= 300   then 1 else 0 end) as tot5min ,  " + 
      "   SUM(case when CPUTIME  >= 300   then CPUTIME else 0 end) as tot5minCPU ,           " + 
      "	SUM ( CPUTIME ) as cputime ,        SUM( ZIPTM ) as ziptime ,       SUM( ELAPSED ) as elapsed   " + 
"   FROM CR00515.EPV30_5_JOBTERM  " + 
 "    where SUBSTR(ENDTIME , 1 , 7 )  = '" + AAAAMM + "'  AND SYSTEM = '" + SIST  + "'   and SMF30WID='JES2' And CPUTIME > 0        " + 
 "  GROUP BY  " + 
 "   SYSTEM   ,  SUBSTR(ENDTIME , 1 , 10 )      ) AS YY   " + 
 "   order by DATA;  "; 
	
 ResultSet rs =  stmt.executeQuery(sql);

ResultSetMetaData rsmd = rs.getMetaData() ;
 int numberOfColumns = rsmd.getColumnCount();
 
int i = 0 ;
String var="";
//out.print("SID:" + SIST);
//out.print(sql);
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
