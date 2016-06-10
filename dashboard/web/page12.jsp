<div data-role="page" class="pageSwipe" id="page-feu1"  data-next="page-feu2" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
	<a href="#nav-panel121" data-icon="bars" data-iconpos="notext">Menu</a>
	<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a>

	</div><!-- /header -->
	
	<div role="main" class="ui-content"> 
   	 <p>      
  			<div style="width: 100%;">
			<img src="../kiroom/img/S03F1.png" alt="" style="width: 100%;">
			
			</div>
			

    </p>
    </div><!-- /content -->
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panel121" />

	</jsp:include>
	<div data-position="fixed" data-role="footer">

<h1>

<a href="http://www.cedacri.it/cedacri/it/index.html">
<img alt="logo" src="img/logo.png">
</a>
</h1>
<a href="#page-feu2" class="ui-btn-right" data-role="button" data-icon="arrow-r" data-iconpos="notext" data-theme="d">Next</a>

</div><!-- /footer -->
	</div><!-- /page -->
	<div data-role="page" class="pageSwipe" id="page-feu2" data-prev="page-feu1" data-dom-cache="true" >
	<div data-role="header" data-theme="b"><h1>Dashboard </h1>
	<a href="#nav-panel122" data-icon="bars" data-iconpos="notext">Menu</a>
	<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a>

	</div><!-- /header -->
	
	<div role="main" class="ui-content"> 
   	 <p>      
  			<div style="width: 100%;">
					<img src="../kiroom/img/S03F2.png" alt="" style="width: 100%;">
			</div>
			

    </p>
    </div><!-- /content -->
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panel122" />

	</jsp:include>
	

<div data-position="fixed" data-role="footer">
<h1>

<a href="http://www.cedacri.it/cedacri/it/index.html">
<img alt="logo" src="img/logo.png">
</a>
</h1>
<a href="#page-feu1" class="ui-btn-left" data-role="button" data-icon="arrow-l" data-iconpos="notext" data-theme="d">Next</a>

</div><!-- /footer -->
	</div><!-- /page -->