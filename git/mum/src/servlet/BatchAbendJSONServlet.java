package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import object.BatchReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class BatchAbendJSONServlet
 */
public class BatchAbendJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String SYSTEM_PARAMETER="system";
	private static final String OFFSET_PARAMETER="offset";
	private static final String WINDOW_PARAMETER="window";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchAbendJSONServlet() {
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
		String system=request.getParameter(SYSTEM_PARAMETER);
		int offset=Integer.valueOf(request.getParameter(OFFSET_PARAMETER));
		int window=Integer.valueOf(request.getParameter(WINDOW_PARAMETER));
		PrintWriter out;
		DatabaseManager db=new DatabaseManager();
		String category="[";
		String dataTotTrans="[";
		String dataCpuTimeString="[";
		String jsonString="[";
		try{
			Collection<BatchReport> collection=db.getBatchInAbendInWindowTime(system, window+offset, offset);
			response.setContentType("application/json");
			out = response.getWriter();
			int i=0;
			for(BatchReport el: collection){
				if(i!=0){
					category=category.concat(",\""+el.getDateInterval()+"\"");
					dataTotTrans=dataTotTrans.concat(","+el.getCount());
					dataCpuTimeString=dataCpuTimeString.concat(","+el.getCpuTime());
				}else{
					category=category.concat("\""+el.getDateInterval()+"\"");
					dataTotTrans=dataTotTrans.concat(String.valueOf(el.getCount()));
					dataCpuTimeString=dataCpuTimeString.concat(String.valueOf(el.getCpuTime()));
				i++;
				}
			}
			jsonString=jsonString.concat(category+"],["+dataTotTrans+"],"+dataCpuTimeString+"]]]");
			out.write(jsonString);
		}
		catch(Exception e){
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
