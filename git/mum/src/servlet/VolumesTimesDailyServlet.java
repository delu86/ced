package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datalayer.DatabaseManager;
import object.VolumeTimeInformation;

/**
 * Servlet implementation class VolumesTimesDailyServlet
 */
public class VolumesTimesDailyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VolumesTimesDailyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter outPrintWriter;	
		String system=request.getParameter("system");
		String date=request.getParameter("date");
		DatabaseManager db=new DatabaseManager();
		String json="[";
		String categories="[";
		String cpuTimes="[";
		String volumes="[";
		String efficiency="[";
		try {
			Collection<VolumeTimeInformation> collection=db.getDailyVolumesTimesInformation(system, date);
			response.setContentType("application/json");
			outPrintWriter=response.getWriter();
			int i=0;
			for(VolumeTimeInformation el: collection){
				if(i==0){
				volumes=volumes.concat(String.valueOf(el.getVolume()));
				cpuTimes=cpuTimes.concat(String.valueOf(el.getCpuTime()));
				efficiency=efficiency.concat(String.valueOf(el.getEfficienza()));
				categories=categories.concat("\""+el.getApplvtname()+"\"");
				i++;
				}
				else {
					volumes=volumes.concat(","+String.valueOf(el.getVolume()));
					cpuTimes=cpuTimes.concat(","+String.valueOf(el.getCpuTime()));
					efficiency=efficiency.concat(","+String.valueOf(el.getEfficienza()));
					categories=categories.concat(",\""+el.getApplvtname()+"\"");
				}
			}
			volumes=volumes.concat("]");
			cpuTimes=cpuTimes.concat("]");
			categories= categories.concat("]");
			efficiency=efficiency.concat("]");
			json=json.concat(categories+",["+volumes+","+cpuTimes+","+efficiency+"]]");
			outPrintWriter.print(json);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
