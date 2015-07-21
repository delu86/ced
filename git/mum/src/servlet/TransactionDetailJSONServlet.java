package servlet;


import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.TransactionReport;
import utility.UtilityDate;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class TransactionDetailJSONServlet
 */
public class TransactionDetailJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TIMEZONE = "UTC";
	private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionDetailJSONServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out;
		String system=request.getParameter("system");
		long dateInMillis=Long.parseLong(request.getParameter("day"));
		String daytime=UtilityDate.fromMilliSecondToDateTimeString(dateInMillis, TIMEZONE,FORMAT_DATETIME);
      DatabaseManager db=new DatabaseManager();
		response.setContentType("application/json");
		try {
			String jsonString="{\"data\":[";
			String dataJSON="";
			out = response.getWriter();
			Collection<TransactionReport> collection=db.getTransactionByDate(daytime, system);
			int i=0;
			for(TransactionReport report:collection){
				if(i==0){
					dataJSON=dataJSON.concat("["+
							"\""+report.getTransaction()+"\","+
							"\""+report.getUserID()+"\","+
							"\""+report.getTransactionCount()+"\","+
							"\""+report.getCpuSecond()+"\","+
							"\""+report.getElapsed()+"\","+
				         	"\""+report.getDb2req()+"\","+
				         	"\""+report.getJ8()+"\","+
				         	"\""+report.getK8()+"\","+
				         	"\""+report.getL8()+"\","+
				         	"\""+report.getMs()+"\","+
				         	"\""+report.getMem()+"\","+
				         	"\""+report.getQr()+"\","+
				         	"\""+report.getS8()+"\""+
				         	"]");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",["+
							"\""+report.getTransaction()+"\","+
							"\""+report.getUserID()+"\","+
							"\""+report.getTransactionCount()+"\","+
							"\""+report.getCpuSecond()+"\","+
							"\""+report.getElapsed()+"\","+
				         	"\""+report.getDb2req()+"\","+
				         	"\""+report.getJ8()+"\","+
				         	"\""+report.getK8()+"\","+
				         	"\""+report.getL8()+"\","+
				         	"\""+report.getMs()+"\","+
				         	"\""+report.getMem()+"\","+
				         	"\""+report.getQr()+"\","+
				         	"\""+report.getS8()+"\""+
							"]");
									}
			}
			jsonString=jsonString.concat(dataJSON+"]}");
			out.print(jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		processRequest(request,response);
	}

}
