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

import object.VolumeSMF;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class VolumiSMFJSONServlet
 */
public class VolumiSMFJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VolumiSMFJSONServlet() {
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
		try{
			response.setContentType("application/json");
			PrintWriter out=response.getWriter();
			
			String data="[";
			DatabaseManager db=new DatabaseManager();
			Collection<VolumeSMF> coll=db.getVolumiSMF();
			int i=0;
			SimpleDateFormat f1=new SimpleDateFormat("yyyyMMdd");
			f1.setTimeZone(TimeZone.getTimeZone("UTC"));
			for(VolumeSMF el:coll){
				Date d=f1.parse(el.getDate());
				if(i==0){
					
					data=data.concat("["+d.getTime()+" , "+el.getVolumeGB()+"]");
					i++;
				}
				else{
					data=data.concat(",["+d.getTime()+" , "+el.getVolumeGB()+"]");
				}
			}
			data=data.concat("]");
			out.print(data);
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
