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


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>big data</title>


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
table  {   border: 1px solid gray;font-family:Arial; } 
td {font-family:Arial; font-size: 14px; padding: 3px; color:black}
th {font-family:Arial; font-size: 14px; color: black; background-color: lightgray ;padding: 3px; }
p {font-family:Arial; font-size: 14px; color: gray; }



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
                        <h1 class="page-header"> </h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->


                <div class="panel panel-default">
                        <div class="panel-heading">
                        SMF Data 
                        </div>
                        <div class="panel-body">

                    <div class="col-lg-12">
                            <div class="panel panel-primary">
                        <div class="panel-heading">
                           #DATA IDAA  
                        </div>
                        <div class="panel-body">




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
.getConnection("jdbc:mysql://localhost:3306/support","epv", "epv");
 


} catch (SQLException e) {
e.printStackTrace();
out.print(e.getMessage()); 
return;
}
String connesso = "";
if (connection != null) {
connesso="ok";
} else {
connesso="Failed to make connection!";
out.println(connesso);
}
 


%>



<%
Statement stmt = null;
ResultSet rs = null;
Statement stmt1 = null;
ResultSet rs1 = null;



Statement stmt0 = null;
ResultSet rs0 = null;



ResultSetMetaData rsData = null;
try {
    stmt = connection.createStatement();
    stmt1 = connection.createStatement();
    stmt0 = connection.createStatement();  



   


    String sql="";    
    sql =" select * from bigdata; " ;
     

// out.println(sql);
   


   

  java.util.Date Oggi = Calendar.getInstance().getTime();
  SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss");
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
   


 



String rosso= "<span style=\"color:red;\">";
String verde = "<span style=\"color:green;\">";
String blu = "<span style=\"color:blue;\">";
String spanSID ="";
String spanHH ="";




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
    {
    out.println("<td align=right> " +  rs.getString(k) + "</td>");
      }
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

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

</body>
<%} %>
</html>





