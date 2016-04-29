 package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.BatchReport;
import utility.UtilityDate;
import datalayer.DatabaseManager;

/**
 * Servlet implementation class JoobleSearchJSONServlet
 */
public class JoobleSearchJSONServlet extends HttpServlet {
    
	
	private static final long serialVersionUID = 1L;
	private static final String TOP_LIMIT_CHAR="L";
	private static final String MONTH_CHAR="M";
	private static final String JOBNAME_CHAR="J";
	private static final String USER_CHAR="U";
	private static final String FROM_CHAR="F";
	private static final String TO_CHAR="T";
	private static final String DAY_CHAR="D";
	private static final String CONDCODE_CHAR="A";
	private static final String CPUTIME_CHAR="C";
	private static final String CONDCODE_ERROR_CHAR="E";
	private static final String CONDCODE_WARNING_CHAR="W";
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
			" SMF30PTY AS priority, SMF30RCN AS reportClass, SMF30SCN as serviceClass "+ 
			" FROM CR00515.EPV30_5_JOBTERM"+
			" WHERE SYSTEM = ? AND SMF30WID = 'JES2' AND SMF30JBN like ?  and CPUTIME>0";
	private static final String SELECT_TOP_CONSUMER="SELECT SMF30JBN,JESNUM, SMF30RUD ,INITIALTIME,ENDTIME , "
                + "ROUND(CPUTIME, 2) AS CPUTIME,ZIPTM, ELAPSED, DISKIO, DISKIOTM, CONDCODE ,SMF30CL8 as class, "
			+ "  SMF30PTY AS priority, SMF30RCN AS reportClass, SMF30SCN as serviceClass  from CR00515.EPV30_5_JOBTERM"
			+ " where SYSTEM=? ";
			
    private static final String END_QUERY= " ORDER BY INITIALTIME DESC FETCH FIRST 2000 ROWS ONLY ";
	private static final String TOP_TOKEN_STRING = "TOP";
	private int limit;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoobleSearchJSONServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		try {
		String finalQuery="";
	    ArrayList<String> params=new ArrayList<String>();
		DatabaseManager db=new DatabaseManager();
		String system=request.getParameter("system");
		String query=request.getParameter("jobname");
		finalQuery=createQuery(finalQuery,params,system, query);
     	PrintWriter out;
		response.setContentType("application/json");
		String jsonString="{\"data\":[";
		String dataJSON="";
		
			out = response.getWriter();
			Collection<BatchReport> reports=db.getJobByName(finalQuery,params);
			int i=0;
			for(BatchReport report:reports){
				if(i==0){
					
					dataJSON=dataJSON.concat("{"+
						"\"JOBname\":"+	"\""+report.getJobName()+"\","+
						"\"JESNUM\":"+	"\"<a href='#'>"+report.getJesNumber()+"</a>\","+
						"\"USER\":"+	"\""+report.getRacfUserIdString()+"\","+
						"\"READTIME\":"+		"\""+report.getReadTime()+"\","+
						"\"ENDTIME\":"+		"\""+report.getEndTime()+"\","+
						"\"CPUTIME\":"+		"\""+report.getCpuTime()+"\","+
						"\"ZIPTIME\":"+		"\""+report.getZipTime()+"\","+
						"\"ELAPSED\":"+		"\""+report.getElapsed()+"\","+
						"\"DISKIO\":"+		"\""+report.getDiskio()+"\","+
						"\"DISKIOTM\":"+		"\""+report.getDiskioTime()+"\","+
						"\"CONCODE\":"+		"\""+report.getConditionCode()+"\","+
						"\"CLASS\":"+		"\""+report.getClass8()+"\","+
						"\"PRIORITY\":"+		"\""+report.getJesInputPriorityString()+"\","+
						"\"REPORT_CLASS\":"+		"\""+report.getReportClassString()+"\","+
                                                "\"SERVICE_CLASS\":"+		"\""+report.getServiceClassString()+"\""+
				         	"}");
					i++;
				}
				else{
					dataJSON=dataJSON.concat(",{"+
							"\"JOBname\":"+	"\""+report.getJobName()+"\","+
							"\"JESNUM\":"+	"\"<a href='#'>"+report.getJesNumber()+"</a>\","+
							"\"USER\":"+	"\""+report.getRacfUserIdString()+"\","+
							"\"READTIME\":"+		"\""+report.getReadTime()+"\","+
							"\"ENDTIME\":"+		"\""+report.getEndTime()+"\","+
							"\"CPUTIME\":"+		"\""+report.getCpuTime()+"\","+
							"\"ZIPTIME\":"+		"\""+report.getZipTime()+"\","+
							"\"ELAPSED\":"+		"\""+report.getElapsed()+"\","+
							"\"DISKIO\":"+		"\""+report.getDiskio()+"\","+
							"\"DISKIOTM\":"+		"\""+report.getDiskioTime()+"\","+
							"\"CONCODE\":"+		"\""+report.getConditionCode()+"\","+
							"\"CLASS\":"+		"\""+report.getClass8()+"\","+
							"\"PRIORITY\":"+		"\""+report.getJesInputPriorityString()+"\","+
							"\"REPORT_CLASS\":"+		"\""+report.getReportClassString()+"\","+
						        "\"SERVICE_CLASS\":"+		"\""+report.getServiceClassString()+"\""	
                                                +"}");
									}
			}
			jsonString=jsonString.concat(dataJSON+"]}");
			out.print(jsonString);
		
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
                        
			finalQuery=finalQuery.concat(" order by CPUTIME DESC FETCH FIRST "+limit+" ROWS ONLY");
                        
		
		}
		return finalQuery;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
