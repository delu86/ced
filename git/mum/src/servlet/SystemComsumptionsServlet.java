package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.SystemConsumptionsReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class SystemComsumptionsServlet
 */
public class SystemComsumptionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemComsumptionsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out;
		String system=request.getParameter("system");
		DatabaseManager db=new DatabaseManager();
		SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd HH");
		f1.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Collection<SystemConsumptionsReport> coll=db.get30DaysConsumptionsReport(system);
			response.setContentType("application/json");
			out = response.getWriter();
			String json="{\"series\":[";
			String dataMIPCPU="{\"name\":\"MIPS Cpu\" ,\"data\":[";
			String dataMIPIIP="{\"name\":\"MIPS zIIP\",\"data\":[";
            String dataMSU4HRA="{\"name\":\"MSU4HRA\",\"yAxis\": 1,\"color\":\"\" ,\"data\":[";
            int i=0;
            for(SystemConsumptionsReport el: coll){
            	Date d=f1.parse(el.getDate().concat(" "+String.valueOf(el.getHour())));
            	String dateInMillis=String.valueOf(d.getTime());
            	if(i==0){
            	dataMIPCPU=dataMIPCPU.concat("["+dateInMillis+" , "+String.valueOf(el.getMipsCpu())+"]");
            	dataMIPIIP=dataMIPIIP.concat("["+dateInMillis+" , "+String.valueOf(el.getMipsZiip())+"]");
            	dataMSU4HRA=dataMSU4HRA.concat("["+dateInMillis+" , "+String.valueOf(el.getMsu4hra())+"]");
            	i++;
            	}
            	else{
                	dataMIPCPU=dataMIPCPU.concat(",["+dateInMillis+" , "+String.valueOf(el.getMipsCpu())+"]");
                	dataMIPIIP=dataMIPIIP.concat(",["+dateInMillis+" , "+String.valueOf(el.getMipsZiip())+"]");
                	dataMSU4HRA=dataMSU4HRA.concat(",["+dateInMillis+" , "+String.valueOf(el.getMsu4hra())+"]");
            	}
            }
            dataMIPCPU=dataMIPCPU.concat("]},");
            dataMIPIIP=dataMIPIIP.concat("]},");
            dataMSU4HRA=dataMSU4HRA.concat("]}");
            json=json.concat(dataMIPCPU+dataMIPIIP+dataMSU4HRA+"]}");
            out.print(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
