<%-- 
    Document   : gallery
    Created on : 3-feb-2016, 10.42.49
    Author     : CRE0260
--%>

<%@page contentType="text/html" pageEncoding="windows-1250"%>
<!DOCTYPE html>
	<div data-role="page" id="galleryPage" class="pageSwipe" data-dom-cache="true">
	<div data-role="header" data-theme="b" data-position="fixed" data-fullscreen="false" data-id="hdr" data-tap-toggle="false"><h1>EpvMonitor</h1>
		<a href="#nav-panelGallery" data-icon="bars" data-iconpos="notext">Menu</a>
		
        </div>
  <div data-role="content" >
      <img src="img/cpuGSY7.svg">
  </div><!-- end content-->
            
<jsp:include page="menu_panel.jsp">
	   <jsp:param name="menu" value="nav-panelGallery" />
	</jsp:include>

<jsp:include page="footer.jsp"/>
	</div><!-- /page -->