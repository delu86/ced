package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalendarAccentureSuggestServlet
 */
public class CalendarAccentureSuggestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RESOURCE_PATH = "datalayer.db";
	private static final String SELECT = "SELECT SMF30JBN FROM smfacc.sla_accenture WHERE  SMF30JBN like ? "+
                                         " order by SMF30JBN LIMIT 20";
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarAccentureSuggestServlet() {
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
		String queryString=request.getParameter("query");
		response.setContentType("application/json");
		try {
			out = response.getWriter();
			String resString="[";
			response.setContentType("application/json");
			out = response.getWriter();
			ResourceBundle rb =   ResourceBundle.getBundle(RESOURCE_PATH);
			Connection conn=null;
			PreparedStatement pStatement=null;
			Class.forName(rb.getString("driver"));
			conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
			pStatement=conn.prepareStatement(SELECT);
			pStatement.setString(1, queryString+"%");
			ResultSet rSet = pStatement.executeQuery();
			int i=0;
			while(rSet.next()){
				if(i==0){
					resString=resString.concat("\""+rSet.getString(1)+"\"");
					i++;
				}
				else{
					resString=resString.concat(",\""+rSet.getString(1)+"\"");
				}
			}
			rSet.close();
			pStatement.close();
			conn.close();
			out.print(resString+"]");
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
