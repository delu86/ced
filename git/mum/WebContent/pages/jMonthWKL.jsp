<%@page import="object.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*, javax.sql.*,java.lang.*, java.io.*, javax.naming.*,java.util.*,java.text.*;" %>

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
<title>jsmf Trend Month Wkl</title>
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
   <script src="../bower_components/jquery/dist/jquery.min.js"></script>
   <script src="../js/highcharts.js"></script>  
   
   <script src="../js/highcharts-more.js"></script>   
   <script src="../js/exporting.js"></script>

</head>
<body>
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
                        <h3 class="page-header">Trend Month</h3>
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
                            <b>Workload</b> Trend Month
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default">
                                    <div id="collapseOne" class="panel-collapse collapse in">
                                        <div class="panel-body">

<span style="font-family: Arial;font-size:12px;color:#004080"> Sid</span>:<select id="dynamic_select">
<%
String CodSys="";
CodSys=request.getParameter("CodSys");
String Workload="";
Workload=request.getParameter("Wkl");
if (Workload == null) {
Workload="CICS";
}


String Festivi ="N";
if (CodSys == null) {
CodSys="GSY7";
}


if (CodSys.equals("GSY7"))
	out.print("<option value=\"jMonthWKL.jsp?CodSys=GSY7&Wkl=" + Workload  + "\" selected>SY7</option>" );
else
	out.print("<option value=\"jMonthWKL.jsp?CodSys=GSY7&Wkl=" + Workload + "\" >SY7</option>" );

if (CodSys.equals("BSY2"))
	out.print("<option value=\"jMonthWKL.jsp?CodSys=BSY2&Wkl=" + Workload  + " \"selected>SY2</option>" );
else
	out.print("<option value=\"jMonthWKL.jsp?CodSys=BSY2&Wkl=" + Workload  + " \">SY2</option>" );

if (CodSys.equals("CSY3"))
	out.print("<option value=\"jMonthWKL.jsp?CodSys=CSY3&Wkl=" + Workload  + " \"selected>SY3</option>" );
else
	out.print("<option value=\"jMonthWKL.jsp?CodSys=CSY3&Wkl=" + Workload + " \">SY3</option>" );

if (CodSys.equals("ZSY5"))
	out.print("<option value=\"jMonthWKL.jsp?CodSys=ZSY5&Wkl=" + Workload + " \"selected>SY5</option>" );
else
	out.print("<option value=\"jMonthWKL.jsp?CodSys=ZSY5&Wkl=" + Workload + " \">SY5</option>" );



if (CodSys.equals("MVSA"))
	out.print("<option value=\"jMonthWKL.jsp?CodSys=MVSA&Wkl=" + Workload + " \"selected>MVSA</option>" );
else
	out.print("<option value=\"jMonthWKL.jsp?CodSys=MVSA&Wkl=" + Workload + " \">MVSA</option>" );


out.print("</select>");

			String DataAVG="";
			String DataRange="";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
			System.out.println(" MySQL JDBC Driver not Found");
			e.printStackTrace();
			return;
			}
		Connection connection = null; 
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtrnd13","epv", "epv");		 
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
		
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs0 = null;
		Statement stmt0 = null;
		ResultSetMetaData rsData = null;

		try {
			
			String	sql0 = " select  distinct WKL  from mtrnd13.workdayh " +			
			" where SYSTEM = '" + CodSys + "' " ; 
			stmt0 = connection.createStatement(); 
						 rs0 = stmt0.executeQuery(sql0);
				out.print("<span style=\"font-family: Arial;Arial;font-size:12px;color:#004080\"> Workload</span>: <select id=\"dynamic_select1\">");
									while(rs0.next())
									{
										String  WklRead=rs0.getString("WKL");
										if (WklRead.equals(Workload) )		
											out.print("<option value=\"jMonthWKL.jsp?CodSys="  +CodSys +"&Wkl=" + Workload + "\" selected >"  +Workload+ "</option>" );
										else
											out.print("<option value=\"jMonthWKL.jsp?CodSys=" + CodSys + "&Wkl=" + WklRead + "\"  >"  +WklRead+ "</option>" );
								}
			out.print("</select>");
			
			
			stmt = connection.createStatement(); 
				  
			String	sql = " select  EPVDATE, UNIX_TIMESTAMP( CONCAT(EPVDATE,\" 12:00:00\") )*1000  as DDay" +
			" , ROUND( AVG(MIP) , 2) as AVGDay , ROUND( MIN(MIP) ,2)  as MINDay , ROUND( MAX(MIP) ,2)  as MaxDay from mtrnd13.workdayh  "  +
			" where SYSTEM = '" + CodSys + "' and WKL='"+ Workload + "'  "  ;
			if (Workload.equals("CICS"))
				sql = sql + " AND CAST( EPVHOUR AS UNSIGNED) BETWEEN 9 AND 17 " ;
 			if (Workload.equals("JOB"))
                          if (CodSys.equals("MVSA"))
				sql = sql + "";
			  else	
				sql = sql + " AND  ( CAST( EPVHOUR AS UNSIGNED) BETWEEN 19 AND 23  OR   CAST( EPVHOUR AS UNSIGNED) BETWEEN 0 AND 6 ) " ;
			if (Festivi.equals("N"))
				sql = sql + " AND WEEKDAY(EPVDATE)<5 " ;
			
			sql = sql + " AND DATEDIFF( NOW() , EPVDATE) < 300 "  ;
			
			sql = sql + " AND EPVDATE NOT IN ( SELECT FDATE FROM support.calendar where FESTIVO = 'Y' ) " + 
						"group by EPVDATE  order by 1" ;
	// 	         sql = sql + 	" group by EPVDATE  order by 1" ;

//			out.println(sql);
			 rs = stmt.executeQuery(sql);
			 rsData = rs.getMetaData();
			 int numberOfColumns = rsData.getColumnCount();
			 
			 while(rs.next()) {
			 
					DataRange= DataRange  +
					"["+ rs.getString("DDay") +"," +  rs.getDouble("MINDay")  +"," +rs.getDouble("MAXDay") +"]," ;
					DataAVG=DataAVG + 					
					"["+ rs.getString("DDay") +"," + rs.getString("AVGDay") + "],";
					 }
					DataRange=  DataRange.substring( 0, DataRange.length() - 1);
					DataAVG=  DataAVG.substring( 0, DataAVG.length() - 1);
%>

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


		 <%
		 String TitleChart="";
			if (Workload.equals("CICS"))
				TitleChart="CICS   (9:00 17:59)";
			else 
			if (Workload.equals("JOB"))
				if (CodSys.equals("MVSA") )
 				 TitleChart="JOB ";
				else
 				 TitleChart="JOB   (19:00 06:59)";
			else
				TitleChart=Workload;
		%>		
<div id="container" style="width: 750px; height: 400px; margin: 0 auto"></div>
<script language="JavaScript">
$(document).ready(function() {  
   var ranges = [ <%=DataRange%> ];
   var averages = [ <%=DataAVG%> ];

   var title = {
      text: '<%=TitleChart%>'   
   };    
   var subtitle = {
            text: '* Lu-Ve Non festivi',
            align: 'right',
            x: -10
        };
   var xAxis = {
      type: 'datetime' ,
	      dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            }
	
   };
   var yAxis = {
      title: {
         text: null
      },
		min: 0      
   };
   var tooltip = {
       shared: true,
	   crosshairs: true,
       valueSuffix: ' Mips'
   };
   var legend = {      enabled: false
   }    
   var series= [{
        name: 'Mips',
            data: averages,
            zIndex: 1,
            marker: {
                fillColor: 'white',
                lineWidth: 2,
                lineColor: Highcharts.getOptions().colors[0]
            }
        }, {
            name: 'Range',
            data: ranges,
            type: 'arearange',
            lineWidth: 0,
            linkedTo: ':previous',
            color: Highcharts.getOptions().colors[0],
            fillOpacity: 0.3,
            zIndex: 0
      }
   ];  
   var json = {};   
   json.title = title;    
   json.subtitle= subtitle;
   json.xAxis = xAxis;
   json.yAxis = yAxis;
   json.tooltip = tooltip;
   json.legend = legend;     
   json.series = series;
   $('#container').highcharts(json); 
});
</script>
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
