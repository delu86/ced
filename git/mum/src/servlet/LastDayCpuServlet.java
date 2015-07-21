package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.SystemHourReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class LastDayCpuServlet
 */
public class LastDayCpuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LastDayCpuServlet() {
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
		DatabaseManager db=new DatabaseManager();
		try {
			Collection<SystemHourReport> coll=db.getYesterdaySystemsCPUnumbers();
			response.setContentType("application/json");
			PrintWriter out=response.getWriter();
			String json="[";
			String systems="[";
			int[] day={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			String actualSystem=null;
			String data="";
			for(SystemHourReport sys:coll){
				if(actualSystem==null){//primo elemento 
					actualSystem=sys.getSystem();
					day[Integer.valueOf(sys.getFrom_hour())]=sys.getNumberCPU();
					systems=systems.concat("\""+sys.getSystem()+"\"");
				    	}
				else{
					if(actualSystem.equals(sys.getSystem())){
						day[Integer.valueOf(sys.getFrom_hour())]=sys.getNumberCPU();
					}
					else{
						actualSystem=sys.getSystem();
						systems=systems.concat(","+"\""+sys.getSystem()+"\"");
						if(data.equals(""))
							data=data.concat("[");
						else
							data=data.concat("],[");
						for(int i=0;i<=23;i++){
							data=(i!=0)?data.concat(","+String.valueOf(day[i])):
								         data.concat(String.valueOf(day[i]));
						day[i]=0;	
						}
					   
					   day[Integer.valueOf(sys.getFrom_hour())]=sys.getNumberCPU();
					}
				}
			
			}
			data=data.concat("],[");
			for(int i=0;i<=23;i++)
				data=(i!=0)?data.concat(","+String.valueOf(day[i])):
					         data.concat(String.valueOf(day[i]));
			
		   data=data.concat("]");
			
			json=json.concat(systems+"],"+data+"]");
			out.print(json);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
	public static void main(String[] args){
		int[] data={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		System.out.println(data.toString());
	}

}
