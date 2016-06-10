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
						<a href="#page7-atm1"<%if(idPanel.contains("nav-panel7")){%>data-rel="close"<%}%>>ATM
						<div class="icon">
						<img src="img/favicon.ico" />
						<img src="img/favicon3.ico"/>
						</div>
						</a>
						</li>
							<li>		
							<a href="#page3-hb1"<%if(idPanel.contains("nav-panel3")){%>data-rel="close"<%}%>>Home Banking
							<div class="icon">
						<img src="img/favicon.ico" />
						<img src="img/favicon3.ico"/>
						</div>
							</a>
						</li>
							<a href="#page5-cb1" <%if(idPanel.contains("nav-panel5")){%>data-rel="close"<%}%>>Corporate Banking
							<div class="icon">
						<img src="img/favicon.ico" />
						<img src="img/favicon3.ico"/>
						</div>
							</a>
						</li>
												<li>		
							<a href="#page-cc"<%if(idPanel.contains("nav-panelcc")){%>data-rel="close"<%}%>>Call Center
							<div class="icon">
						<img src="img/favicon.ico" />
						<img src="img/favicon3.ico"/>
						</div>
							</a>
						</li>
												<li>		
						
							<a href="#page6-sp1"<%if(idPanel.contains("nav-panel6")){%>data-rel="close"<%}%>>Sportello
							<div class="icon">
						<img src="img/favicon.ico" />
						<img src="img/favicon3.ico"/>
						</div>
							</a>
						</li>
												<li>		
						
							<a href="#page-psw1">PWS
							<div class="icon">
						<img src="img/favicon.ico" />
						</div></a>
						</li>
												<li>		
						
							<a href="#page-feu1"<%if(idPanel.contains("nav-panel12")){%>data-rel="close"<%}%>>FEU
							<div class="icon">
						<img src="img/favicon.ico" />
						</div>
							</a>
						</li>
												<li>		
							<a href="#pagesonda"<%if(idPanel.equals("nav-panelS")||idPanel.equals("nav-panel1")||idPanel.equals("nav-panel2")
							||idPanel.equals("nav-panelsms")){%>data-rel="close"<%}%>>SMSAlert
							<div class="icon">
						<img src="img/favicon.ico" />
						</div></a>
						</li>
						<li>		
							<a href="#page4-pw1"<%if(idPanel.contains("nav-panel4")){%>data-rel="close"<%}%>>Ribes
						<div class="icon">
						<img src="img/ribes.png" height="16" width="16" />
						</div>
							</a>
						</li>
				</ul>
     <!-- panel content goes here -->
	</div><!-- /panel -->