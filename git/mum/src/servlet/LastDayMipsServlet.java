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
 * Servlet implementation class LastDayMipsServlet
 */
public class LastDayMipsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SELECT_YESTERDAY_MIPS="SELECT SERIAL,SYSTEM,cast(MIPLPAR as UNSIGNED) as MIPLPAR,EPVHOUR ,cast(MIPS as UNSIGNED) FROM support.merge_cecmips_lparcpu where EPVDATE=subdate(current_date, 1) and EPVHOUR=(SELECT EPVHOUR from support.merge_cecmips_lparcpu where EPVDATE=subdate(current_date, 1) group by EPVHOUR order by SUM(MIPLPAR) DESC LIMIT 1) order by SERIAL";

	public LastDayMipsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
    private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {

			response.setContentType("application/json");
			out = response.getWriter();
			ResourceBundle rb =   ResourceBundle.getBundle("datalayer.db2");
			 Class.forName(rb.getString("driver"));
			 conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
			 ps=conn.prepareStatement(SELECT_YESTERDAY_MIPS);
			 rs=ps.executeQuery();
			 String json="[";
			 String info="[";
			 String categories="[";
			 String subCategories="[";
			 String data="[";
			 String actualMachine=null;
			 int residuo=0;
			 String residui="[";
			 while(rs.next()){
				 if(actualMachine==null){
					 residuo=rs.getInt(5)-rs.getInt(3);
					 actualMachine=rs.getString(1);
					 categories=categories.concat("\""+actualMachine+"\"");
					 subCategories=subCategories.concat("\""+rs.getString(2)+"\"");
					 data=data.concat(rs.getString(3));
					 info=info.concat(rs.getString(4)+"]");
				 }
				 else{
					 if(actualMachine.equals(rs.getString(1))){
						 subCategories=subCategories.concat(",\""+rs.getString(2)+"\"");
						 data=data.concat(","+rs.getString(3));
						 residuo+=-rs.getInt(3);
					 }
					 else{
						 residui=residui.concat(String.valueOf(residuo)+",");
						 residuo=rs.getInt(5)-rs.getInt(3);
						 actualMachine=rs.getString(1);
						 categories=categories.concat(",\""+actualMachine+"\"");
						 subCategories=subCategories.concat("],[\""+rs.getString(2)+"\"");
						 data=data.concat("],["+rs.getString(3));
					 }
				 }	 
			 }
			 residui=residui.concat(String.valueOf(residuo)+"],");
			 subCategories=subCategories.concat("],"+categories+"],");
			 categories=categories.concat(",\"Disponibili\"],");
			 data=data.concat("],"+residui);
			 json=json.concat(categories+subCategories+data+info+"]");
			 out.print(json);
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block

			request.setAttribute("error", e);
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		finally{
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
