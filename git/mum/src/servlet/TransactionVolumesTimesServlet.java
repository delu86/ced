package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.TransactionReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class TransactionVolumesTimesServlet
 */
public class TransactionVolumesTimesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String OFFSET_PARAMETER = "offset";
	private static final String SYSTEM_PARAMETER = "system";
	private static final String TRANSACTION_PARAMETER = "tname";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionVolumesTimesServlet() {
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
		int offset=Integer.parseInt(request.getParameter(OFFSET_PARAMETER));
		String system=request.getParameter(SYSTEM_PARAMETER);
		String transaction=request.getParameter(TRANSACTION_PARAMETER);
		DatabaseManager db=new DatabaseManager();
		PrintWriter out;
		try {
			String json="[";
			String dataTempi="[";
			String dataVolumi="[";
			response.setContentType("application/json");
			out = response.getWriter();
			SimpleDateFormat f1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			f1.setTimeZone(TimeZone.getTimeZone("UTC"));
			Collection<TransactionReport> collection=db.getVolumesTimesTransaction(transaction,system,offset);
			int i=0;
			for(TransactionReport el: collection){
				if(i==0){
					Date d=f1.parse(el.getDateString());
					dataVolumi=dataVolumi.concat("["+String.valueOf(d.getTime())+","+el.getTransactionCount()+"]");
					dataTempi=dataTempi.concat("["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",el.getCpuSecond())+"]");
					i++;
				}else{
					Date d=f1.parse(el.getDateString());
					dataVolumi=dataVolumi.concat(",["+String.valueOf(d.getTime())+","+el.getTransactionCount()+"]");
					dataTempi=dataTempi.concat(",["+String.valueOf(d.getTime())+","+String.format(Locale.US,"%.2f",el.getCpuSecond())+"]");
					
				}
			}
			dataVolumi=dataVolumi.concat("],\n");
			dataTempi=dataTempi.concat("]");
			json=json.concat(dataVolumi+dataTempi+"]");
			out.print(json);

		} catch (Exception e) {
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
