<style>
   .icon{ display: inline; }
</style>
   <%String idPanel=request.getParameter("menu");%>
   <div data-role="panel" data-position="left" data-position-fixed="false" data-display="push" id=<%=idPanel%> data-theme="a">
					<ul data-role="listview" data-theme="a" data-divider-theme="a" style="margin-top:-16px;" class="nav-search">
						<li >
						
							<a href="#infoPage" <%if(idPanel.equals("nav-panelInfo")){%>data-rel="close"<%}%> >Summary</a>
						</li >
					<li>
						<a href="#focalPointPage"<%if(idPanel.contains("nav-panelFocalPoint")){%>data-rel="close"<%}%> >Epv Focal Point
						
						</a>
						</li>
						<li>
						<a href="#agentPage"<%if(idPanel.contains("nav-panelAgent")){%>data-rel="close"<%}%> >Perl Agent
						
						</a>
						</li>
                                                <li>
						<a href="#smfPage"<%if(idPanel.contains("nav-panelSMF")){%>data-rel="close"<%}%> >SMF Elaboarti
						
						</a>
						</li>
                                                <li>
						<a href="#switchPage"<%if(idPanel.contains("nav-panelSwitch")){%>data-rel="close"<%}%> >Switch DB
						
						</a>
						</li>
                                                        <li>
						<a href="#mysqlPage"<%if(idPanel.contains("nav-panelMysql")){%>data-rel="close"<%}%> >MySQL process list
						
						</a>
						</li>

						
				</ul>
     <!-- panel content goes here -->
	</div><!-- /panel -->