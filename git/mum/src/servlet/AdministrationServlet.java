package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datalayer.DatabaseManager;
import object.DiskInformation;
import object.ProcessInformation;

import utility.UtilityDate;

/**
 * Servlet implementation class AdministrationServlet
 */
public class AdministrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADMIN_PAGE = "admin.jsp";
	private static final String [] wkl_system_array={"GSY7","CSY3","ZSY5","BSY2","SIES","SIGE"};
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministrationServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		 String line;
		 String action=request.getParameter("action");
		 if(action!=null){
			 Process p = Runtime.getRuntime().exec(new String[]{"/bin/bash","-c","echo cedacrilinux| sudo -S mount -f /root/nfs/sy3uss	"});
			 BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    while ((line = input.readLine()) != null) {
			        request.setAttribute("message", line);
			    }

		 }
		 Process p = Runtime.getRuntime().exec("df -k");
		 
		 Process p3=Runtime.getRuntime().exec(new String[] { "bash", "-c", "ps axu | less | grep EPV" });
		 BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		 BufferedReader input3 =
		            new BufferedReader(new InputStreamReader(p3.getInputStream()));
		 Collection<DiskInformation> disks=new ArrayList<DiskInformation>();
		 Collection<ProcessInformation> process=new ArrayList<ProcessInformation>();
		 String result="";

		   int n=0;
		    while ((line = input.readLine()) != null){
		    	if(n==0) n++;
		    	else
		    	result=result.concat(line+" ");
		    }
		    String [] split=result.split("\\s+");
		    line="";
		    int i=0;
		    for(int count=0;count<split.length;count++){
		    	if(i<6){
		    		line=line.concat(split[count]+" ");
		    		i++;
		    	}
		    	else{
		    		i=1;
		    		DiskInformation d=new DiskInformation(line);
		    		disks.add(d);
		    		line=split[count]+" ";
		    		
		    	}
		    }
		 while ((line = input3.readLine()) != null) {
			 if(i==0){i++;}
			 else{
			 ProcessInformation el=new ProcessInformation(line);
			 process.add(el);}
			 }
		 i=0;
		 request.setAttribute("disksInformation", disks);
		 request.setAttribute("process",process);
		 
		 request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
