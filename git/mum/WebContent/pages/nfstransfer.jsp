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
<title>jnfs monitor</title>


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
td {font-family:Arial; font-size: 12px; padding: 3px; color:black}
th {font-family:Arial; font-size: 12px; color: black; background-color: lightgray ;padding: 3px; }
p {font-family:Arial; font-size: 12px; color: gray; }



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
                        zOS Performance Analysis
                        </div>
                        <div class="panel-body">

                    <div class="col-lg-12">
                            <div class="panel panel-primary">
                        <div class="panel-heading">
                            NFS data transfer 
                        </div>
                        <div class="panel-body">



<form action="nfstransfer.jsp" method="get">
<p>View :&nbsp;
<% 
 if ( sito.equals("001") ) 
     out.print(" <input type=\"radio\" name=\"sito\" value=\"001\" checked=\"checked\"> Time &nbsp; ");
  else
  out.print(" <input type=\"radio\" name=\"sito\" value=\"001\" > Time &nbsp;");
  


  if  ( sito.equals("002") )
  out.print(" <input type=\"radio\" name=\"sito\" value=\"002\" checked=\"checked\"> Volumi &nbsp;");
    else 
    out.print(" <input type=\"radio\" name=\"sito\" value=\"002\" > Volumi &nbsp;");
  


  if  ( sito.equals("003") )
  out.print(" <input type=\"radio\" name=\"sito\" value=\"003\" checked=\"checked\"> Mb &nbsp;");
    else 
    out.print(" <input type=\"radio\" name=\"sito\" value=\"003\" > Mb &nbsp;");
  %>
  <input type="submit" value="&nbsp;Go&nbsp;">
</p>
</form>


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
if (  sito.equals("001") )  
    sql =" " + 
"    select dw, DataContent, sid,    " +
"    max(IF(tipo='db2' ,  Max ,'-') )  db2 ," +
"    max(IF(tipo='cics' ,  Max ,'-') )  cics ," +
"    max(IF(tipo='altro' ,  Max ,'-') )  altro ," +
"    max(IF(tipo='alt79' ,  Max ,'-') )  rmf79 , " +
"    max(IF(tipo='alt69' ,  Max ,'-') )  file69 " +
    " from ( " +
    " select "  +
    " case when  weekday(cast( DataContent as date) )= \"0\" then \"lu\" else   " +
    " case when weekday(cast( DataContent as date) ) = \"1\" then \"ma\" else  " +
    "   case when weekday(cast( DataContent as date) )= \"2\" then \"me\" else   " +
    "   case when weekday(cast( DataContent as date) )= \"3\" then \"gi\" else   " +
    "   case when weekday(cast( DataContent as date) )= \"4\" then \"ve\" else   " +
    "   case when weekday(cast( DataContent as date) ) = \"5\" then \"sa\" else " +
    "   case when weekday(cast( DataContent as date) ) = \"6\" then \"do\" end  end end end end end end as dw  " +
    " , DATE_FORMAT(cast( DataContent as date),'%d %b %Y') as DataContent, " +
    "  DATE_FORMAT(Max( DataContent ),'%H:%i') as Max, " +
    "  case when  SUBSTRING(Q1, 8,1)= \"2\" then \"sy2\" else   " +
    " case when SUBSTRING(Q1, 8,1) = \"p\" then \"sy7\" else  " +
    "  case when SUBSTRING(Q1, 8,1)= \"3\" then \"sy3\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"5\" then \"sy5\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"a\" then \"sya\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"e\" then \"sige\" else " +
    " case when SUBSTRING(Q1, 8,1) = \"f\" then \"sies\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"q\" then \"mvsa\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"r\" then \"mvsb\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"j\" then \"asdn\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"k\" then \"assv\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"m\" then \"gtm\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"t\" then \"syt\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"x\" then \"ict1\"  else    " +
    " case when SUBSTRING(Q1, 8,1) = \"d\" then \"ipo1\"  else    " +
    " case when SUBSTRING(Q1, 8,1) = \"h\" then \"ipo4\"  else    " +
    "   SUBSTRING(Q1, 8,1) " +
      "  end end end end end end end  end end  end end end end end end end  as sid, " +
    " SUBSTRING(Q2,4,LENGTH(Q2)) as tipo, " +
    "  round( SUM(SIZE)*1.192092895508E-7 , 2) as Mb, " +
    "  COUNT(*) as tot " +
    "  from nfstransfer  AS A  " +
    " where cast(DataContent as date) = "+
    " (select max( cast( DataContent as date) ) from nfstransfer)"+
    "  GROUP BY cast( DataContent as date),SUBSTRING(Q1, 8,1) , Q2  " +
    "  ORDER BY 1,2,4 " +
    "  ) as nfstra " +
    "  group by dw,DataContent,sid " +
    "  ; ";



     


if (  sito.equals("002") )
    sql =" " + 
"    select dw, DataContent, sid,    " +
"    max(IF(tipo='db2' ,  tot , 0 ) )  db2 ," +
"    max(IF(tipo='cics' ,  tot ,0) )  cics ," +
"    max(IF(tipo='altro' ,  tot ,0) )  altro ," +
"    max(IF(tipo='alt79' ,  tot ,0) )  rmf79 , " +
"    max(IF(tipo='alt69' ,  tot ,0) )  file69 " +
    " from ( " +
    " select "  +
    " case when  weekday(cast( DataContent as date) )= \"0\" then \"lu\" else   " +
    " case when weekday(cast( DataContent as date) ) = \"1\" then \"ma\" else  " +
    "   case when weekday(cast( DataContent as date) )= \"2\" then \"me\" else   " +
    "   case when weekday(cast( DataContent as date) )= \"3\" then \"gi\" else   " +
    "   case when weekday(cast( DataContent as date) )= \"4\" then \"ve\" else   " +
    "   case when weekday(cast( DataContent as date) ) = \"5\" then \"sa\" else " +
    "   case when weekday(cast( DataContent as date) ) = \"6\" then \"do\" end  end end end end end end as dw  " +
    " , DATE_FORMAT(cast( DataContent as date),'%d %b %Y') as DataContent, " +
    "  DATE_FORMAT(Max( DataContent ),'%H:%i') as Max, " +
    "  case when  SUBSTRING(Q1, 8,1)= \"2\" then \"sy2\" else   " +
    " case when SUBSTRING(Q1, 8,1) = \"p\" then \"sy7\" else  " +
    "  case when SUBSTRING(Q1, 8,1)= \"3\" then \"sy3\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"5\" then \"sy5\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"a\" then \"sya\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"e\" then \"sige\" else " +
    " case when SUBSTRING(Q1, 8,1) = \"f\" then \"sies\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"q\" then \"mvsa\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"r\" then \"mvsb\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"j\" then \"asdn\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"k\" then \"assv\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"m\" then \"gtm\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"t\" then \"syt\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"x\" then \"ict1\"  else    " +
    " case when SUBSTRING(Q1, 8,1) = \"d\" then \"ipo1\"  else    " +
    " case when SUBSTRING(Q1, 8,1) = \"h\" then \"ipo4\"  else    " +
    "   SUBSTRING(Q1, 8,1) " +
      " end  end end end end end end end end  end end  end end end end end  as sid, " +
    " SUBSTRING(Q2,4,LENGTH(Q2)) as tipo, " +
    "  round( SUM(SIZE)*1.192092895508E-7 , 2) as Mb, " +
    "  COUNT(*) as tot " +
    "  from nfstransfer  AS A  " +
    " where cast(DataContent as date) = "+
    " (select max( cast( DataContent as date) ) from nfstransfer)"+
    "  GROUP BY cast( DataContent as date),SUBSTRING(Q1, 8,1) , Q2  " +
    "  ORDER BY 1,2,4 " +
    "  ) as nfstra " +
    "  group by dw,DataContent,sid " +
    "  ; ";
     


     


     


    if (  sito.equals("003") )
    sql =" " + 
"    select dw, DataContent, sid,    " +
"    max(IF(tipo='db2' ,  Mb , 0 ) )  db2 ," +
"    max(IF(tipo='cics' ,  Mb ,0) )  cics ," +
"    max(IF(tipo='altro' ,  Mb ,0) )  altro ," +
"    max(IF(tipo='alt79' ,  Mb ,0) )  rmf79 , " +
"    max(IF(tipo='alt69' ,  Mb ,0) )  file69 " +
    " from ( " +
    " select "  +
    " case when  weekday(cast( DataContent as date) )= \"0\" then \"lu\" else   " +
    " case when weekday(cast( DataContent as date) ) = \"1\" then \"ma\" else  " +
    "   case when weekday(cast( DataContent as date) )= \"2\" then \"me\" else   " +
    "   case when weekday(cast( DataContent as date) )= \"3\" then \"gi\" else   " +
    "   case when weekday(cast( DataContent as date) )= \"4\" then \"ve\" else   " +
    "   case when weekday(cast( DataContent as date) ) = \"5\" then \"sa\" else " +
    "   case when weekday(cast( DataContent as date) ) = \"6\" then \"do\" end  end end end end end end as dw  " +
    " , DATE_FORMAT(cast( DataContent as date),'%d %b %Y') as DataContent, " +
    "  DATE_FORMAT(Max( DataContent ),'%H:%i') as Max, " +
    "  case when  SUBSTRING(Q1, 8,1)= \"2\" then \"sy2\" else   " +
    " case when SUBSTRING(Q1, 8,1) = \"p\" then \"sy7\" else  " +
    "  case when SUBSTRING(Q1, 8,1)= \"3\" then \"sy3\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"5\" then \"sy5\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"a\" then \"sya\" else   " +
    "  case when SUBSTRING(Q1, 8,1)= \"e\" then \"sige\" else " +
    " case when SUBSTRING(Q1, 8,1) = \"f\" then \"sies\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"q\" then \"mvsa\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"r\" then \"mvsb\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"j\" then \"asdn\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"k\" then \"assv\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"m\" then \"gtm\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"t\" then \"syt\"  else  " +
    " case when SUBSTRING(Q1, 8,1) = \"x\" then \"ict1\"  else    " +
    " case when SUBSTRING(Q1, 8,1) = \"d\" then \"ipo1\"  else    " +
    " case when SUBSTRING(Q1, 8,1) = \"h\" then \"ipo4\"  else    " +
    "   SUBSTRING(Q1, 8,1) " +
      "  end end end end end end end  end end  end end end end end end end  as sid, " +
    " SUBSTRING(Q2,4,LENGTH(Q2)) as tipo, " +
    "  round( SUM(SIZE)*1.192092895508E-7 , 2) as Mb, " +
    "  COUNT(*) as tot " +
    "  from nfstransfer  AS A  " +
    " where cast(DataContent as date) = "+
    " (select max( cast( DataContent as date) ) from nfstransfer)"+
    "  GROUP BY cast( DataContent as date),SUBSTRING(Q1, 8,1) , Q2  " +
    "  ORDER BY 1,2,4 " +
    "  ) as nfstra " +
    "  group by dw,DataContent,sid " +
    "  ; ";
// out.println(sql);
   


    String sql1="select max( DataContent ) as DataContent from nfstransfer";
    rs1 = stmt1.executeQuery(sql1);
   


String DataView = null;



while(rs1.next())
    {   
          DataView= rs1.getString("DataContent");
  break;
    }
   





    String sql0= " select  count(distinct Q1) as totQ1 from nfstransfer  where cast(DataContent as date) =  " +
      " (select max( cast( DataContent as date) ) from nfstransfer)  "; 
    rs0 = stmt0.executeQuery(sql0);   
String countSID = ""; 
while(rs0.next())
    {   
        countSID= rs0.getString("totQ1");
  break;
    }



  java.util.Date Oggi = Calendar.getInstance().getTime();
  SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss");
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
   


  java.util.Date  UltimoAgg = formatter.parse(DataView);
 


  long diff = Oggi.getTime() - UltimoAgg.getTime();
  long diffHours = diff / (60 * 60 * 1000) % 24;
  long diffSeconds = diff / 1000 % 60;
  long diffMinutes = diff / (60 * 1000) % 60;



String rosso= "<span style=\"color:red;\">";
String verde = "<span style=\"color:green;\">";
String blu = "<span style=\"color:blue;\">";
String spanSID ="";
String spanHH ="";



if (countSID.equals("16"))
spanSID=verde;
else
spanSID=rosso;



    if (diffHours > 2)
    spanHH=rosso;
    else
    spanHH=verde;
   


  out.print("<p>Today: <b>"+ sdf.format(Oggi) +  "</b>   Last Update:<B>" + blu + " " + sdf.format( UltimoAgg  ) + "</span></B>  " + 
  "   HH:MM:SS FROM LAST UPDATE  " + spanHH + diffHours + " h " + diffMinutes + " m "  +diffSeconds 
  + " s " +  " </span> TOT. RILEVAZIONI:" + spanSID + countSID + "</span></p>");  
   


    rs = stmt.executeQuery(sql);
    rsData = rs.getMetaData();
    int numberOfColumns = rsData.getColumnCount();
    out.print("<table border=1 id=\"myTable\" class=\"tablesorter\"><thead>");
    out.print("<tr>");
    out.println("<th></th> " );
    for (int k = 3;k<=numberOfColumns;k++)
    out.println("<th>"+ rsData.getColumnLabel(k).toUpperCase()  + "</th> " );
    out.print("</tr></thead>");



    out.print("<tbody>");
int count=1;
    while(rs.next()) {
    out.print("<tr>");
    out.println("<td>"+ count + "</td> " );
    for (int k = 3;k<=numberOfColumns;k++)
    {
    out.println("<td > " +  rs.getString(k) + "</td>");
      }
    out.print("</tr>");
    count++;
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





