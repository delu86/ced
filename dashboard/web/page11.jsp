	<div data-role="page" class="pageSwipe" id="page-cc"  data-dom-cache="true" >
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
	<a href="#nav-panelcc" data-icon="bars" data-iconpos="notext">Menu</a>
	<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a>
	</div><!-- /header -->
	<div role="main" class="ui-content"> 
   	 <p>      
  		<div style="width: 100%;">
                    <img src="../kiroom/img/S01CCAT.png" alt="" style="width: 100%;">
	        </div> 
        </p>
    </div><!-- /content -->
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelcc" />
	</jsp:include>
<div data-position="fixed" data-role="footer">
<h1>
<a href="http://www.cedacri.it/cedacri/it/index.html">
<img alt="logo" src="img/logo.png">
</a>
</h1>
</div><!-- /footer -->
</div><!-- /page -->