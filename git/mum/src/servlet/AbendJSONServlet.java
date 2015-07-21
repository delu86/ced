package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import object.TransactionReport;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class AbendJSONServlet
 */
public class AbendJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SYSTEM_PARAMETER="system";
	private static final String OFFSET_PARAMETER="offset";
	private static final String WINDOW_PARAMETER="window";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AbendJSONServlet() {
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
			Collection<TransactionReport> collection=db.getTransactionInAbendInWindowTime(system, window+offset, offset);
			response.setContentType("application/json");
			out = response.getWriter();
			int i=0;
			for(TransactionReport el: collection){
				if(i!=0){
					category=category.concat(",\""+el.getDateString()+"\"");
					dataTotTrans=dataTotTrans.concat(","+el.getTransactionCount());
					dataCpuTimeString=dataCpuTimeString.concat(","+el.getCpuSecond());
					
				}else{
					category=category.concat("\""+el.getDateString()+"\"");
					dataTotTrans=dataTotTrans.concat(String.valueOf(el.getTransactionCount()));
					dataCpuTimeString=dataCpuTimeString.concat(String.valueOf(el.getCpuSecond()));
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
