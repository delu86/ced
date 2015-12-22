package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.UtilityDate;
import exporter.ExcelExporter;

/**
 * Servlet implementation class JoobleExcelExporterServlet
 */
public class JoobleExcelExporterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TOP_LIMIT_CHAR="l";
	private static final String MONTH_CHAR="m";
	private static final String JOBNAME_CHAR="j";
	private static final String USER_CHAR="u";
	private static final String FROM_CHAR="f";
	private static final String TO_CHAR="t";
	private static final String DAY_CHAR="d";
	private static final String CONDCODE_CHAR="a";
	private static final String CPUTIME_CHAR="c";
	private static final String CONDCODE_ERROR_CHAR="e";
	private static final String CONDCODE_WARNING_CHAR="w";
	private static final String JOBNAME_TOKEN=" and SMF30JBN like ?";
	private static final String CPU_TIME_TOKEN=" and CPUTIME>= ? ";
	private static final String CONDCODE_ERROR_TOKEN=" and ABEND is not NULL ";
	private static final String CONDCODE_WARNING_TOKEN=" and CONDCODE BETWEEN 3 and  11 ";
	private static final String CONDCODE_TOKE_STRING=" and CONDCODE=?";
	private static final String FROM_DATE_TOKEN_STRING=" and date(INITIALTIME)>=?";
	private static final String TO_DATE_TOKEN_STRING=" and date(INITIALTIME)<=?";
	private static final String DATE_TOKEN_STRING=" and date(INITIALTIME)=?";
	private static final String USER_TOKEN_STRING=" and SMF30RUD=?";
	private static final String MONTH_TOKEN_STRING=" and substr(INITIALTIME , 6 , 2)=? ";
	private static final String SELECT_JOB_BY_NAME = "SELECT SMF30JBN,JESNUM, SMF30RUD ,INITIALTIME,ENDTIME ,"+
			" ROUND(CPUTIME, 2) AS CPUTIME,ZIPTM, ELAPSED, DISKIO, DISKIOTM, CONDCODE ,SMF30CL8 as class, "+
			" SMF30PTY AS priority,SMF30SCN as serviceClass, SMF30RCN AS reportClass "+ 
			" FROM CR00515.EPV30_5_JOBTERM"+
			" WHERE SYSTEM = ? AND SMF30WID = 'JES2' AND SMF30JBN like ?  and CPUTIME>0";
	private static final String SELECT_TOP_CONSUMER="SELECT SMF30JBN,JESNUM, SMF30RUD ,INITIALTIME,ENDTIME , ROUND(CPUTIME, 2) AS CPUTIME,ZIPTM, ELAPSED, DISKIO, DISKIOTM, CONDCODE ,SMF30CL8 as class, "
			+ "  SMF30PTY AS priority, SMF30RCN AS reportClass  from CR00515.EPV30_5_JOBTERM"
			+ " where SYSTEM=? ";
			
    private static final String END_QUERY= " ORDER BY INITIALTIME DESC FETCH FIRST 2000 ROWS ONLY ";
	private static final String TOP_TOKEN_STRING = "TOP";
	private int limit;
	private static final String IDAA_RESOURCE_PATH = "datalayer.idaa";  
	private static final String SUFFIX_FILE_NAME = "export";
	private static final String  EXCEL_EXTENSION= ".xls";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoobleExcelExporterServlet() {
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
		try {
		ResourceBundle rb =   ResourceBundle.getBundle(IDAA_RESOURCE_PATH);
		String finalQuery="";
		String system=request.getParameter("system");
		String jobname=request.getParameter("jobname");
		ArrayList<String> params=new ArrayList<String>();
		finalQuery=createQuery(finalQuery,params,system, jobname);
     	
		response.setHeader("Content-Disposition", "attachment; filename="+SUFFIX_FILE_NAME+EXCEL_EXTENSION);
	    
		
			ExcelExporter.getExcelFromIDAA(response.getOutputStream(), rb.getString("driver"),rb.getString("url"), finalQuery, params.toArray(new String[params.size()]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		private String createQuery(String finalQuery,ArrayList<String> params, String system,String query) throws ParseException{
			String[] listParameter=query.split(" -");
			if(!listParameter[0].equals(TOP_TOKEN_STRING)){
			finalQuery=finalQuery.concat(SELECT_JOB_BY_NAME);
			params.add(system);
			params.add(listParameter[0].replace("*", "%"));
			for(int i=1;i<listParameter.length;i++){
			
				//elimina li spazi
				String trimmed=listParameter[i].trim();
				if(trimmed.startsWith(USER_CHAR)){
					params.add(trimmed.substring(1).trim());
					finalQuery=finalQuery.concat(USER_TOKEN_STRING);		
				}
				if(trimmed.startsWith(CONDCODE_CHAR)){
					params.add(trimmed.substring(1).trim());
					finalQuery=finalQuery.concat(CONDCODE_TOKE_STRING);		
				}
				if(trimmed.startsWith(DAY_CHAR)){
					params.add(UtilityDate.fromFormatToFormat(trimmed.substring(1).trim(), 
							UtilityDate.formatDateDatabase, UtilityDate.formatDateEpv));
					finalQuery=finalQuery.concat(DATE_TOKEN_STRING);
				}
				if(trimmed.startsWith(FROM_CHAR)){
					params.add(UtilityDate.fromFormatToFormat(trimmed.substring(1).trim(), 
							UtilityDate.formatDateDatabase, UtilityDate.formatDateEpv));
					finalQuery=finalQuery.concat(FROM_DATE_TOKEN_STRING);
				}
				if(trimmed.startsWith(CPUTIME_CHAR)){
					params.add(trimmed.substring(1).trim());
					finalQuery=finalQuery.concat(CPU_TIME_TOKEN);
				}
				if(trimmed.startsWith(TO_CHAR)){
					params.add(UtilityDate.fromFormatToFormat(trimmed.substring(1).trim(), 
							UtilityDate.formatDateDatabase, UtilityDate.formatDateEpv));
					finalQuery=finalQuery.concat(TO_DATE_TOKEN_STRING);
				}
				if(trimmed.startsWith(CONDCODE_ERROR_CHAR)){
					finalQuery=finalQuery.concat(CONDCODE_ERROR_TOKEN);
				}
				if(trimmed.startsWith(CONDCODE_WARNING_CHAR)){
					finalQuery=finalQuery.concat(CONDCODE_WARNING_TOKEN);
				}
			}
			finalQuery=finalQuery.concat(END_QUERY);}
			else{
				String trimmed=null;
				if(listParameter.length>1){
				 trimmed=listParameter[1].trim();}
				finalQuery=finalQuery.concat(SELECT_TOP_CONSUMER);
				params.add(system);
				boolean selectPeriod=false; //indica se è stato scelto il giorno o il mese in cui cercare i top consumer
				 limit=10;
				for(int i=1;i<listParameter.length;i++){
				if(trimmed.startsWith(TOP_LIMIT_CHAR)){
					limit=Integer.parseInt(trimmed.substring(1).trim());
				}
				if(trimmed.startsWith(CONDCODE_CHAR)){
					
					finalQuery=finalQuery.concat(CONDCODE_ERROR_TOKEN);}
				if(trimmed.startsWith(JOBNAME_CHAR)){
					selectPeriod=true;
					params.add(trimmed.substring(1).trim().replace("*", "%"));
					finalQuery=finalQuery.concat(JOBNAME_TOKEN);
				}
				if(trimmed.startsWith(MONTH_CHAR)){
					params.add(String.format("%02d", Integer.parseInt(trimmed.substring(1).trim())));
					finalQuery=finalQuery.concat(MONTH_TOKEN_STRING);
					selectPeriod=true;
				}
				if(trimmed.startsWith(DAY_CHAR)){
					finalQuery=finalQuery.concat(DATE_TOKEN_STRING);
					params.add(UtilityDate.fromFormatToFormat(trimmed.substring(1).trim(), 
							UtilityDate.formatDateDatabase, UtilityDate.formatDateEpv));
					selectPeriod=true;
				}
				}
				if(!selectPeriod){
					finalQuery=finalQuery.concat(DATE_TOKEN_STRING);
					params.add(UtilityDate.fromFormatToFormat(UtilityDate.conversionToDBformat(UtilityDate.getDate(-1)), 
							UtilityDate.formatDateDatabase, UtilityDate.formatDateEpv));
				
				}
				finalQuery=finalQuery.concat("order by CPUTIME DESC FETCH FIRST "+limit+" ROWS ONLY");
			
			}
			return finalQuery;
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
