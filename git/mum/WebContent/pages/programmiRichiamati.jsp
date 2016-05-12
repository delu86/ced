<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE html>
<%@page import="object.User"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
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
    <title>Cedacri Statistics</title>
    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- JqueryUI CSS -->
    <link rel="stylesheet" href="../CSS/jquery-ui.css">
    <!-- DataTables CSS -->
    <link href="../CSS/datatables-bootstrap.css" rel="stylesheet">
    <!-- DataTables Responsive CSS -->
    <link href="../CSS/datatables-responsive.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
    .input-search {
      padding-left: 10px;
      border: solid 5px #c9c9c9;
      transition: border 0.3s;
    }
        .input-search:focus {
       border: solid 5px #969696;
        outline: 0;
   }
   .hidden{
   	display:none;
   	}
    </style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                <div class='row'>
                <div class="col-lg-4">
                </div>
                <!-- /.col-lg-4 -->
                   </div>
                <!-- /.row -->
                <div class="row">
                <div class="col-lg-2">
                </div>
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-8">
                    <div class="form-jooble">
                    <input class="input-search form-control" id="autocomplete" placeholder="Search for JOB name ..." >
                    </div>
                    </div>
                    <!-- /.col-lg-8 -->
                    </div>
                    <!-- /.row -->
                <div class="row">
                <div class="col-lg-3">
                </div>
                    <!-- /.col-lg-2 -->
                    <div class="col-lg-5">
                    Cerca in: 
                 <label class="radio-inline"><input type="radio" name="optradio" value="GSY7" checked="checked">GSY7</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="BSY2">BSY2</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ZSY5">ZSY5</label>
                 <label class="radio-inline"><input type="radio" name="optradio" value="ESYA">ESYA</label>
                    </div>
                    <!-- /.col-lg-8 -->
                    </div>
                    <!-- /.row -->
               <div class="row">
                   <div class="col-lg-12">
                                             <div class="dataTable_wrapper" >
                                <table class="table table-striped table-bordered table-hover" cellspacing="0" width="100%" id="dataTables-program">
                                    <thead>
                                        <tr>
                                            <th class="desktop ">SYSTEM</th>
                                            <th class="desktop ">PRGNAME</th>
                                            <th class="desktop ">VTAM APPLICATION NAME</th>
                                            <th class="desktop ">LIBRARY</th>
                                            <th class="desktop ">TIME USED SINCE RESET</th>
                                            <th class="desktop ">LAST USE</th>
                                         </tr>
                                    </thead>
                                    </table>
                                    </div>
                   </div> 
                    <!-- /.col-lg-12 -->
              </div>
             <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
     </div>
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>
    <!-- jQueryUI -->
    <script src="../js/jquery-ui.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js">
    </script>
    <script src="../js/dataTables.bootstrap.js"></script>
    <script src="../js/dataTables.responsive.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    <script src="../js/ced/tableJS.js"></script>
    <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js">
    </script>
    <script type="text/javascript" language="javascript" src="//cdn.datatables.net/buttons/1.1.2/js/buttons.flash.min.js">
    </script>
    <script src="../js/jszip2.min.js"></script>
    <script type="text/javascript" language="javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js">
    </script>
    <script type="text/javascript" language="javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js">
    </script>
    <script type="text/javascript" language="javascript" src="//cdn.datatables.net/buttons/1.1.2/js/buttons.html5.min.js">
    </script>
    <script type="text/javascript" language="javascript" src="//cdn.datatables.net/buttons/1.1.2/js/buttons.print.min.js">
    </script>
    <script type="text/javascript" language="javascript" 
            src="//cdn.datatables.net/plug-ins/1.10.11/api/processing().js">
    </script>
    <script>
        var system= $("input[type='radio'][name='optradio']:checked").val();
        var programName;
        $.ui.autocomplete.prototype._renderItem = function (ul, item) {
        item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<span style='font-weight:bold;color:Blue;'>$1</span>");
        return $("<li></li>")
                .data("item.autocomplete", item)
                .append("<a>" + item.label + "</a>")
                .appendTo(ul);
         };
         function searchProgram(){
	   $("#autocomplete").autocomplete({
    		minlength:0,
                delay:0,
                source:function(request,response){
    	    	$.getJSON("../queryResolver?id=programmiRichiamatiSuggest&prgName="+request.term.replace("#","%23")+"%25&system="+system,function (json){
    	    		     var data=[];
                             json.data.forEach(function(entry){
                                 data.push(entry[0]);
                             });
                            response(data);});},
    	           select: function( event, ui ) {
    		   programName=ui.item.value;
    		   var parameter=ui.item.value.replace("#","%23");
                   console.log('queryResolver?id=programmiRichiamati&prgName='
                             +parameter+'&system='+system);
                   loadTable('#dataTables-program','../queryResolver?id=programmiRichiamati&prgName='
                             +parameter+'&system='+system);
                   
    	}});
        }
        $("input[type='radio']").click(function(){
    	   system= $("input[type='radio'][name='optradio']:checked").val();
        });
           $(function(){
             searchProgram();  
           });
    </script>
    </body>
<%} %>
</html>

