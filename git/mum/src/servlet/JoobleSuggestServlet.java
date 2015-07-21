package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoobleSuggestServlet
 */
public class JoobleSuggestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RESOURCE_PATH = "datalayer.db";
	private static final String SELECT = "SELECT SMF30JBN FROM smfacc.r30jbn WHERE SYSTEM=? and SMF30JBN like ? "+
                                         " order by SMF30JBN LIMIT 20";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoobleSuggestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		PrintWriter out;
		String queryString=request.getParameter("query");
		String system=request.getParameter("system");
		String resString="[";
		response.setContentType("application/json");
		out = response.getWriter();
		ResourceBundle rb =   ResourceBundle.getBundle(RESOURCE_PATH);
		Connection conn=null;
		PreparedStatement pStatement=null;
		Class.forName(rb.getString("driver"));
		conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		pStatement=conn.prepareStatement(SELECT);
		pStatement.setString(1,system);
		pStatement.setString(2, queryString+"%");
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
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request,response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
