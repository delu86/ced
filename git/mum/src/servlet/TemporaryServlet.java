package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class TemporaryServlet
 */
public class TemporaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TemporaryServlet() {
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
			HttpServletResponse response) throws IOException {
		 File downloadFile = new File("/work/SHARE/ds04ap1x/json/RESUME_r30.json");
		 FileInputStream inStream = new FileInputStream(downloadFile);
		 
		 response.setContentType("application/json");
		 OutputStream outStream = response.getOutputStream();
		 inStream.read(new byte[7]);  
		 byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        inStream.close();
	        outStream.close(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
