package servlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exporter.ExcelExporter;
/**
 * Servlet implementation class WorkloadByDayExporterServlet
 */
public class WorkloadByDayExporterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String TABLE_PARAMETER_STRING="$table_name";
	private static final String SYSTEM_PARAMETER = "system";
	private static final String DATE_PARAMETER = "date";
    private static final String SELECT="SELECT DATA_INT10 ,SYSTEM,WKLOADNAME, sum(CPUTIME)*1026.5/600 as MIPS from "+TABLE_PARAMETER_STRING+" where SYSTEM=? and" +
            "  date(DATA_INT10)=? group by DATA_INT10,WKLOADNAME,SYSTEM order by DATA_INT10 ASC,WKLOADNAME ";
	private static final String SUFFIX_FILE_NAME = "workload";
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String RESOURCE_DB_PATH = "datalayer.db";
        private static final String RESOURCE_DB_PATH_2 = "datalayer.db2";
	public static final HashMap<String, String> mapSystemWorloadTableHashMap=initializeMapSystemWorloadTable();
	
	private static HashMap<String, String> initializeMapSystemWorloadTable() {
		// TODO Auto-generated method stub
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("SIES", "realebis_ctrl.workload_view");
		map.put("SIGE", "realebis_ctrl.workload_view");
                map.put("ALL", "realebis_ctrl.workload_view");
                map.put("ASDN", "smfacc.workload_view_carige");
		map.put("ASSV", "smfacc.workload_view_carige");
		map.put("GSY7", "smfacc.workload_view_sy7");
		map.put("ZSY5", "smfacc.workload_view_sy5");
		map.put("CSY3", "smfacc.workload_view_sy5");
		map.put("BSY2", "smfacc.workload_view_sy2");
		return map;
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkloadByDayExporterServlet() {
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
		String system=request.getParameter(SYSTEM_PARAMETER);
		String queryString=SELECT.replace(TABLE_PARAMETER_STRING, mapSystemWorloadTableHashMap.get(system));
		String date=request.getParameter(DATE_PARAMETER);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+"_"+system+"_"+date+EXCEL_EXTENSION);
		try {
                    if(system.equals("SIES")||system.equals("SIGE")){
			ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH_2,queryString
					,new String[]{system,date});}
                    else{
                        if(system.equals("ALL"))
                            ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH_2,queryString.replace("SYSTEM=? and", "")
					,new String[]{date});
                        else
                        ExcelExporter.getExcelFromDbQuery(response.getOutputStream(),RESOURCE_DB_PATH,queryString
					,new String[]{system,date});
                    }
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
