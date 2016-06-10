

<div data-role="page" class="pageSwipe" id="page3-hb1"  data-next="page3-hb2" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
	<a href="#nav-panel3" data-icon="bars" data-iconpos="notext">Menu</a>
	<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a>

	</div><!-- /header -->
	
	<div role="main" class="ui-content"> 
   	 <p>      
  			<div style="width: 100%;">
				<img src="../kiroom/img/S01HB.png" alt="" style="width: 100%;">
			</div>
			

    </p>
    </div><!-- /content -->
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panel3" />

	</jsp:include>
	

<div data-position="fixed" data-role="footer">

<h1>
<a href="http://www.cedacri.it/cedacri/it/index.html">
<img alt="logo" src="img/logo.png">
</a>
</h1>
<a href="#page3-hb2" class="ui-btn-right" data-role="button" data-icon="arrow-r" data-iconpos="notext" data-theme="d">Next</a>

</div><!-- /footer -->
	</div><!-- /page -->
	<div data-role="page" class="pageSwipe" id="page3-hb2" data-prev="page3-hb1" data-dom-cache="true" >
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>Dashboard </h1>
	<a href="#nav-panel32" data-icon="bars" data-iconpos="notext">Menu</a>
	<a data-ajax="false" href="index.jsp" data-role="button" class="ui-btn-right"  data-icon="delete">
	Logout</a>

	</div><!-- /header -->
	
	<div role="main" class="ui-content"> 
   	 <p>      
  			<div style="width: 100%;">
				<img src="../kiroom/img/S01HBAT.png" alt="" style="width: 100%;">
			</div>
			

    </p>
    </div><!-- /content -->
   
    <jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panel32" />

	</jsp:include>
	

<div data-position="fixed" data-role="footer">
<h1>
<a href="http://www.cedacri.it/cedacri/it/index.html">
<img alt="logo" src="img/logo.png">
</a>
</h1>
<a href="#page3-hb1" class="ui-btn-left" data-role="button" data-icon="arrow-l" data-iconpos="notext" data-theme="d">Next</a>

</div><!-- /footer -->
	</div><!-- /page -->
