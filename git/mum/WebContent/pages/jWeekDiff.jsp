<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, javax.sql.*,java.lang.*, java.io.*, javax.naming.*,java.util.*,java.text.*;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	User user=(User)request.getSession().getAttribute("user");
    if(user==null)
    	response.sendRedirect("login.jsp");
    else{
    	String profile=user.getProfile();
    
%>

	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<title>jsfm Workload Trend Week</title>
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
   <script src="../bower_components/jquery/dist/jquery.min.js"></script>
	
<!--  <script type="text/javascript" src="js/jquery-1.8.2.js"></script> -->
<link rel="stylesheet" type="text/css" href="jWeekDiff.css">
<script type="text/javascript" src="jWeekDiff.js" ></script>
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
                        <h3 class="page-header">Overlap Workload</h3>
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
                            <b>(Week / kMIPS )</b> 
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">
<!-- <p class=title align=center>Overlap Workload  </p> -->
<!-- <p class=subtitle align=center>(Week / kMIPS )</p> -->

<%
String CodSys;
String kWeek;
String Week, WeekPrec;
CodSys=request.getParameter("CodSys");
kWeek=request.getParameter("kWeek");
Week=request.getParameter("Week");
WeekPrec=request.getParameter("WeekPrec");

if (CodSys == null) {
CodSys="GSY7";
}

%>

<script>
$(function(){
  // bind change event to select
  $('#dynamic_select').on('change', function () {
	  var url = $(this).val(); // get selected value
	  if (url) { // require a URL
		  window.location = url; // redirect
	  }
	  return false;
  });
});
</script>
<script>
$(function(){
  // bind change event to select
  $('#dynamic_select1').on('change', function () {
	  var url = $(this).val(); // get selected value
	  if (url) { // require a URL
		  window.location = url; // redirect
	  }
	  return false;
  });
});
</script>
<script>
$(function(){
  // bind change event to select
  $('#dynamic_select2').on('change', function () {
	  var url = $(this).val(); // get selected value
	  if (url) { // require a URL
		  window.location = url; // redirect
	  }
	  return false;
  });
});
</script>

<script src="../js/highcharts.js"></script>
<div id="result"></div>
<table id="table-sparkline">  
<%

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
				.getConnection("jdbc:mysql://localhost:3306/mtrnd","epv", "epv");		 
		} catch (SQLException e) {
			e.printStackTrace();
			out.print(e.getMessage()); 
			return;
		}
	 
		if (connection != null) {
			System.out.println("Connect");
		} else {
			out.println("Failed to make connection!");
		}
 
		%>
		
		<%
		Statement stmt = null;
		ResultSet rs = null;
	
		Statement stmt0 = null;
		ResultSet rs0 = null;
	
		Statement stmt1 = null;
		ResultSet rs1 = null;
	
		ResultSetMetaData rsData = null;
		ResultSetMetaData rsData0 = null;
		try {
			
			 stmt = connection.createStatement(); 
			 stmt0 = connection.createStatement(); 
			 stmt1 = connection.createStatement(); 
				  
String	sql1=  "SELECT    week(EPVDATE,1) as Week, "+ 
"cast(EPVDATE as date)  as DDay  from `mtrnd`.`workdayh`   "+
"where SYSTEM = '"+CodSys+"'  "+
" and WEEKDAY(EPVDATE)  = '0' "  + " and  week(EPVDATE,1) < " +  " (select max(week(EPVDATE,1)) from mtrnd.workdayh)  " +
" group by  week(EPVDATE,1),cast(EPVDATE as date) order by 2 DESC ; ";

//			 System.out.println(sql1);
									rs1 = stmt1.executeQuery(sql1);
									
									//  
									
									if (Week == null)
									{  while(rs1.next())
									{  	
											  Week = rs1.getString("Week"); 	        
											break;
									}}
									if (WeekPrec == null)
									{ 		//  rs1.next();
										if (!rs1.next())
											 WeekPrec=Week;	
										else	
											  WeekPrec = rs1.getString("Week"); 	        
									}
									rs1.beforeFirst(); 
%>
<center>
<span style="font-family: Arial;font-size:12px;color:#004080"> Sid</span>:<select id="dynamic_select">
<% 
if (CodSys.equals("GSY7"))
	out.print("<option value=\"jWeekDiff.jsp?CodSys=GSY7&Week=" + Week + "&WeekPrec="  + WeekPrec + "\" selected>SY7</option>" );
else
	out.print("<option value=\"jWeekDiff.jsp?CodSys=GSY7&Week=" + Week + "&WeekPrec="  + WeekPrec + "\" >SY7</option>" );

if (CodSys.equals("BSY2"))
	out.print("<option value=\"jWeekDiff.jsp?CodSys=BSY2&Week=" + Week + "&WeekPrec="  + WeekPrec + " \"selected>SY2</option>" );
else
	out.print("<option value=\"jWeekDiff.jsp?CodSys=BSY2&Week=" + Week + "&WeekPrec="  + WeekPrec + " \">SY2</option>" );

if (CodSys.equals("CSY3"))
	out.print("<option value=\"jWeekDiff.jsp?CodSys=CSY3&Week=" + Week + "&WeekPrec="  + WeekPrec + " \"selected>SY3</option>" );
else
	out.print("<option value=\"jWeekDiff.jsp?CodSys=CSY3&Week=" + Week + "&WeekPrec="  + WeekPrec + " \">SY3</option>" );

if (CodSys.equals("ZSY5"))
	out.print("<option value=\"jWeekDiff.jsp?CodSys=ZSY5&Week=" + Week + "&WeekPrec="  + WeekPrec + " \"selected>SY5</option>" );
else
	out.print("<option value=\"jWeekDiff.jsp?CodSys=ZSY5&Week=" + Week + "&WeekPrec="  + WeekPrec + " \">SY5</option>" );
%>  
</select>
<% 								     
									out.print("<span style=\"font-family: Arial;Arial;font-size:12px;color:#004080\"> Week</span>: <select id=\"dynamic_select1\">");
								//	out.print("<option value=\"\" selected>Week</option>");
									while(rs1.next())
									{
										String  WeekRead=rs1.getString("Week");
										if (WeekRead.equals(WeekPrec) )		
											 out.print("<option value=\"jWeekDiff.jsp?CodSys=" + CodSys +
													 "&Week="+Week+	"&WeekPrec="+WeekRead+ " \"  selected > " +
														WeekRead + " (" + rs1.getString("DDay") + ")"  +
																"</option>");
										else
											 out.print("<option value=\"jWeekDiff.jsp?CodSys=" + CodSys +
													 "&Week="+Week+		"&WeekPrec="+WeekRead+ " \"  > " +
														WeekRead + " (" + rs1.getString("DDay") + ")"  +
																"</option>");
									}
								   
									out.print("</select>");
									
									rs1.beforeFirst(); 
									out.print("<span style=\"font-family: Arial;Arial;font-size:12px;color:#004080\"> vs </span> <select id=\"dynamic_select2\">");
								   while(rs1.next())
									{
										String  WeekRead=rs1.getString("Week");
											if (WeekRead.equals(Week) )			
											  out.print("<option value=\"jWeekDiff.jsp?CodSys=" + CodSys +
													  "&WeekPrec="+WeekPrec+   		"&Week="+WeekRead+ " \"  selected > " +
														WeekRead + " (" + rs1.getString("DDay") + ")"  +
																"</option>");
											else
												 out.print("<option value=\"jWeekDiff.jsp?CodSys=" + CodSys +
														 "&WeekPrec="+WeekPrec+		"&Week="+WeekRead+ " \"  > " +
															WeekRead + " (" + rs1.getString("DDay") + ")"  +
																	"</option>");
									   
									}
								   
									out.print("</select>");
%>
</center>
<br>
<% 			   
// out.print("---> " + WeekPrec  + " vs " + Week);
	  

String  sql = 
" select wk0.WKL as wkl ,wk1.da as d1 ,wk0.da as d2 , "  +
" case when wk0.dw = \"0\" then \"lu\" else case when wk0.dw = \"1\" then \"ma\" else case when wk0.dw = \"2\" then \"me\" else "+
"  case when wk0.dw = \"3\" then \"gi\" else case when wk0.dw = \"4\" then \"ve\" else case when wk0.dw = \"5\" then \"sa\" else  "+
" case when wk0.dw = \"6\" then \"do\" end end end end end end end as dw , "+
"   wk1.we as we1, wk0.we as we0,  wk1.CP as cp1, "+
" wk0.CP as cp2, wk0.CP - wk1.CP as Delta "+
" from ( "+
" SELECT WKL, week(EPVDATE,1) as we, WEEKDAY(EPVDATE) as dw,  "+
" EPVDATE as da, ROUND ( sum(MIP) ,2 ) as CP,  " +
" 0 as ZIP "+
" FROM `mtrnd`.`workdayh` where SYSTEM = '" + CodSys + "' and week(EPVDATE,1) = "  +  Week  +  " "+
" group by WKL,week(EPVDATE,1),WEEKDAY(EPVDATE),EPVDATE ) as wk0  "+
" join "+
" 	( SELECT WKL, week(EPVDATE,1) as we , WEEKDAY(EPVDATE) as dw, EPVDATE as da,  "+
" ROUND ( sum(MIP) ,2 ) as CP, 0 as ZIP  "+
" 	FROM `mtrnd`.`workdayh` where SYSTEM = '" + CodSys + "' and "+
" week(EPVDATE,1) = "  +   WeekPrec +  "  "+
" group by WKL,week(EPVDATE,1), "+
" WEEKDAY(EPVDATE),EPVDATE ) as wk1 on wk0.dw=wk1.dw and wk0.WKL = wk1.WKL; ";

		   System.out.println(sql);
			rs = stmt.executeQuery(sql);

		 
			 java.util.Date data_giorno_d= null;
			 String data="";
			 String inutile="";
			 rsData = rs.getMetaData();
			 int numberOfColumns = rsData.getColumnCount();
			 
			 //out.print("<table border=1>");
			// out.print("<tr>");
			// for (int k = 1;k<=numberOfColumns;k++)
			// 		out.println("<th>"+ rsData.getColumnLabel(k)  + "</th> " );
			// out.print("</tr>");
			
			Locale locale = new Locale("en", "UK");
			DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
			symbols.setDecimalSeparator('.');
			symbols.setGroupingSeparator(':');
			
			 String com_wkl = "";
			 String com_we1 = "";
			 String com_we2 = "";
			 String[] yArr = new String[7];
			 String[] yArr2 = new String[7];
			 int i=0;
			 double tot1 = 0d;
			 double tot2 = 0d;
				
			 while(rs.next()) {
				 if (rs.getString("wkl").equals(com_wkl)) 
					 {
					 yArr[i]=rs.getString("cp1");
					 yArr[i]=""+ (Double.parseDouble(yArr[i]))/1000;
					yArr2[i]=rs.getString("cp2");
					yArr2[i]=""+ (Float.parseFloat(yArr2[i]))/1000;
						
					 }
				else
					 {
					if ((com_wkl.equals("")) )
	
					 {
						com_we1=rs.getString("we1");
						com_we2=rs.getString("we0");
						out.print("<thead>" +
						 "<tr>" + 
							"<th>Workload</th>" +
							"<th>Tot("+ com_we1+ ")</th>" +
							"<th>Week(" + com_we1 + ")</th>" +
							"<th>Tot(" + com_we2 + ")</th>" + 
							"<th>Week(" + com_we2+ ")</th>" +
							"<th>Delta</th>" + 
							"<th>L M M G V S D</th>" +
						"</tr>" + 
					"</thead>" + 
					"<tbody id=\"tbody-sparkline\">");
					 }
					else
					 {
						out.println("<tr>\n");
						out.println("<th>" + com_wkl + "</th>\n");
						for (int k=0;k<7;k++)
						 {
							tot1+= Float.parseFloat(yArr[k]) ;
							tot2+= Float.parseFloat(yArr2[k]) ;
						 }
						out.print(" <td <span style=\"color:gray;font-size:12px\">" + String.format( "%.2f",tot1).replace(',', '.') + " </td>");
						Double value1=0d;
						Double value1Perc=0d;
						String ytArr="";
						String ytArrT="";
						
						for (int k=0;k<7;k++)
							{
								value1= Double.parseDouble( yArr[k]) ;
								value1Perc=value1;
								ytArr+= String.format( "%.2f", value1 ).replace(',', '.') + " , ";
							}
						ytArr = ytArr.substring( 0, ytArr.length() - 2);
						out.print ( "<td data-sparkline=\"" + ytArr +  "\"/> ");
						out.print(" <td> <span style=\"color:gray;font-size:12px\">" + String.format( "%.2f",tot2).replace(',', '.') + "</span> </td>\n");
						
						String ytArr2="";
						Double value2=0d;
						for (int k=0;k<7;k++)
							{
							value2= Double.parseDouble( yArr2[k]) ;
							ytArr2+=String.format( "%.2f", value2 ).replace(',', '.') + " , ";
							}
						ytArr2 = ytArr2.substring( 0, ytArr2.length() - 2);	
						out.print ( "<td data-sparkline=\"" + ytArr2 +  "\"/> ");
					//	out.print("<td>" + String.format( "%.2f",(tot2 - tot1)).replace(',', '.')  + "</td>");
					
					if (tot1==0)
						out.print("<td><span style=\"color:black;font-size:12px\">+" + String.format( "%.2f",0 ).replace(',', '.')  + "%</span></td>");
					
					else	
					if ((tot2-tot1)>0)
						out.print("<td><span style=\"color:#910000;font-size:12px\">+" + String.format( "%.2f",(tot2 - tot1)/tot1 * 100 ).replace(',', '.')  + "%</span></td>");
					else
						out.print("<td><span style=\"color:#009185;font-size:12px\">" + String.format( "%.2f",(tot2 - tot1)/tot1 * 100 ).replace(',', '.')  + "%</span></td>");
					
						Double value=0.0d;
						String ytArrD ="";
						for (int k=0;k<7;k++)
						{
							value=   (Double.parseDouble( yArr2[k]) -Double.parseDouble( yArr[k]))    ;
							ytArrD+= String.format( "%.2f", value ).replace(',', '.') + " , ";
						}
						ytArrD = ytArrD.substring( 0, ytArrD.length() - 2 ) + "; column";	
						out.print ( "<td data-sparkline=\"" + ytArrD  +   "\"/> ");
						out.println("</tr>");
					
					 }	
					i=0;
					yArr[i]=rs.getString("cp1");
					yArr[i]=""+( Double.parseDouble(yArr[i]) )/1000;
					
					yArr2[i]=rs.getString("cp2");
					yArr2[i]="" +( Double.parseDouble(yArr2[i]) )/1000;
					
						com_wkl=rs.getString("wkl");
					
						tot1=0d;
						tot2=0d;
					 }
				 i++;
				/* out.print("<tr>");
				 for (int k = 1;k<=numberOfColumns;k++)
				 {	 out.println("<td > " +  rs.getString(k) + "</td>"); }
				 out.print("</tr>");
				 */
					}
	  
			 
		   
			 
				out.print("</tbody>");
%>			
</table>

	<%		}
		catch (SQLException ex){
			// handle any errors
			out.println("SQLException: " + ex.getMessage());
			out.println("SQLState: " + ex.getSQLState());
			out.println("VendorError: " + ex.getErrorCode());
		} finally {
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


    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
		
		</body>
<%} %>
</html>