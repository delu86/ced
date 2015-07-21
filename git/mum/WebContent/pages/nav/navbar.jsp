<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String profile=request.getParameter("profile");
%>
<!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                 <a class="navbar-brand" href="index.jsp"><img src=img/Cedacri.jpg width=90 height=30></a>
            </div>
            <!-- /.navbar-header -->
         <ul class="nav navbar-top-links navbar-right">
                 <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                         <li><a href="login.jsp"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    <!--
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="login.jsp"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>-->
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->
            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                       <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            <!-- /input-group -->
                        </li> 
                        <li>
                            <a href="index.jsp"><i class="fa fa-dashboard fa-fw"></i> Dashboard </a>
                        </li>
                        <%if(profile.equals("CED")){ %>
                          <li>
                            <a href="#"><i class="fa fa-institution fa-fw"></i> Cedacri<span class="fa arrow"></span></a>
                             <ul class="nav nav-second-level">
                              <li>
                              <a href="http://10.99.252.22/datifs/html/cedacri/HTM/START.HTML"><i class="fa fa-table fa-fw"></i>EPV</a>
                              </li>
                             <li>
                              <a href="cedacriCharts.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>Workload</a>
                              </li>
                              <li>
                               <a href="#"><i class="fa  fa-code"></i>JOB<span class="fa arrow"></span></a>
                              <ul class="nav nav-second-level">
                              <li>
                              <a href="jooble.jsp"><i class="fa fa-search fa-fw"></i>Jobs search</a>
                              </li>
                              <li>
                              <a href="err.jsp"><i class="fa  fa-exclamation-circle fa-fw"></i> Errori Batch </a>
                             </li>
                              </ul></li>
                               <li>
                                    <a href="#"><i class="fa fa-tasks fa-fw"></i>MQ  <span class="fa arrow"></span></a>
                                    <ul class="nav nav-third-level">
                                        <li>
                                    <a href="mqChart"><i class="fa fa-tasks fa-fw"></i>Volumi MQ</a>
                                       </li>
                                        
                                        <li>
                                              <a href="r116.jsp" ><i class="fa fa-table fa-fw"></i>Datasheet LXECG100</a>   
                                   	    </li>
                                        <li>
                                            <a href="r116charts.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>Charts LXECG100</a>
                                        </li>
                                                                              
                                    </ul>
                                    <!-- /.nav-third-level -->
                                </li>
                                <li>
                            <a href="#"> <i class="fa fa-gears fa-fw"></i>CPU MF<span class="fa arrow"></span></a>
                             <ul class="nav nav-third-level">
                             <li>
                              <a href="tablesMFCounters?profile=CED"><i class="fa fa-table fa-fw"></i>CPU Metrics by hour</a>
                              </li>
                               <li>
                                    <a href="chartsDispatcher?page=CPI"><i class="fa fa-bar-chart-o fa-fw"></i>CPI</a>
                                </li>
                               <li>
                                    <a href="chartsDispatcher?page=MIPS"><i class="fa fa-bar-chart-o fa-fw"></i>MIPS</a>
                                </li>
                            </ul>
                               <!-- /.nav-third-level -->
                            </li>
                              <li>
                              <a href="virtualTape.jsp"><i class="fa fa-database fa-fw"></i>Virtual Tapes</a>
                              </li>
                              <li>
                              <a href="volumiSMF.jsp"><i class="fa  fa-download fa-fw"></i>Volumi SMF</a>
                              </li>
                              <li>
                              <a href="scrt.jsp"><i class="fa fa-book fa-fw"></i>SCRT</a>
                              </li>
                                                            
                            </ul>
                               <!-- /.nav-second-level -->
                            </li>
                            <%} %>
                            <%if(profile.equals("CED")||profile.equals("REALE")){ %>
                         <li>
                            <a href="#"><i class="fa fa-institution fa-fw"></i> Reale Mutua<span class="fa arrow"></span></a>
                             <ul class="nav nav-second-level">
                             <li>
                             <a href="http://10.99.252.22/datifs/html/reale/HTM/START.HTML"><i class="fa fa-table fa-fw"></i>EPV</a>
                             </li>
                             <li> 
                              <a href="realeCharts"><i class="fa fa-bar-chart-o fa-fw"></i>Workload</a>
                              </li>
                              <li> 
                              <a href="volumeTimesReale.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>CICS eff.</a>
                              </li>
                              <li> 
                              <a href="test.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>RMF e R4H</a>
                              </li>
                              <li> 
                              <a href="dailyDetailReale.jsp"><i class="fa fa-file-excel-o fa-fw"></i>Export data</a>
                              </li>
                              <li> 
                              <a href="realeCPI.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>CPI</a>
                              </li>
                              <li>
                              <a href="cpiDetailReale.jsp"><i class="fa fa-file-excel-o fa-fw"></i>CPI exporter</a>
                              </li>
                              <li>
                              <a href="tablesMFCounters?profile=REALE"><i class="fa fa-table fa-fw"></i>CPU Metrics by hour</a>
                              </li>
                              <li>
                              <a href="joobleReale.jsp"><i class="fa fa-search fa-fw"></i>Jobs search</a>
                              </li>
                              <li> 
                              <a href="cicsAbendReale.jsp"><i class="fa fa-exclamation fa-fw"></i>Transactions ABEND</a>
                              </li>
                              <li>
                              <a href="batchAbendReale.jsp"><i class="fa fa-exclamation fa-fw"></i>Batch ABEND</a>
                              </li>
                            </ul>
                               <!-- /.nav-second-level -->
                            </li>
                            <%} %>
                            <%if(profile.equals("CED")||profile.equals("CREDEM")){ %>
                           <li>
                            <a href="#"><i class="fa fa-institution fa-fw"></i>Credem <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="http://10.99.252.22/datifs/html/credem/HTM/START.HTML"><i class="fa fa-table fa-fw"></i>EPV</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <%} %>
                        <%if(profile.equals("CED")||profile.equals("CARIGE")){ %>
                        <li>
                            <a href="#"><i class="fa fa-institution fa-fw"></i>Carige Assicurazioni<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="http://10.99.252.22/datifs/html/carigeass/HTM/START.HTML"><i class="fa fa-table fa-fw"></i>EPV</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                            </li>
                            <%} %>
                            <%if(profile.equals("CED")||profile.equals("ACCENTURE")){ %>
                        <li>
                            <a href="#"><i class="fa fa-institution fa-fw"></i>Accenture<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="accentureCalendar.jsp"><i class="fa fa-calendar fa-fw"></i>Calendario jobs</a>
                                </li>
                                <li>
                                    <a href="detailAccenture.jsp"><i class="fa fa-file-excel-o fa-fw"></i>Estrazione dettaglio</a>
                                </li>
                            </ul>
                        </li>
                            <%} %>
                            <%if(profile.equals("CED")){ %>
                            <li>
                            <a href="#"><i class="fa fa-certificate fa-fw"></i>Infocenter<span class="fa arrow"></span></a>
                             <ul class="nav nav-second-level">
                             <li> 
                              <a href="infocenter.jsp"><i class="fa fa-file-excel-o fa-fw"></i>Export to...</a>
                              <a href="infocenterDocs.jsp"><i class="fa fa-book fa-fw"></i>Documents</a>
                              </li>
                            </ul>
                               <!-- /.nav-second-level -->
                            </li>
                            <%} %>
                            <%if(profile.equals("CED")){ %>
                            
                         <li>
                            <a href="admin"><i class="fa fa-wrench fa-fw"></i> Admin Server & UI</a>
                        </li>
                        <%} %>
 				</ul>                     
             
                </div>
                <!-- /.sidebar-collapse -->
            </div>
    	
    	    <!-- /.navbar-static-side -->
        </nav>