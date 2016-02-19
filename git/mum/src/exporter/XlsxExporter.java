/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exporter;


import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author CRE0260
 */
public class XlsxExporter {

    public static void writeXLSX(OutputStream outputStream, String resourceDbPath,String queryString,String... args){
            try {
                ResourceBundle rb =   ResourceBundle.getBundle(resourceDbPath);
		Class.forName(rb.getString("driver"));
		Connection conn =DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		PreparedStatement pStatement=conn.prepareStatement(queryString,java.sql.ResultSet.TYPE_FORWARD_ONLY, 
                java.sql.ResultSet.CONCUR_READ_ONLY);
                
	    int paramCount=1;
            
		for (String arg : args) {
		        pStatement.setString(paramCount++, arg);
		        
		    }
                ResultSet rSet = pStatement.executeQuery();
		ResultSetMetaData rsMetaData=rSet.getMetaData();
		int columnCount=rsMetaData.getColumnCount();
                SXSSFWorkbook workBook = new  SXSSFWorkbook();
                SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("cics");
                String currentLine=null;
                int rowNum=0;
                Row intestazione=sheet.createRow(rowNum);
               for(int i=0;i<columnCount;i++){
                   intestazione.createCell(i).setCellValue(rsMetaData.getColumnLabel(i+1));
                }
            rowNum++;
            while (rSet.next()) {
            rowNum++;
            Row currentRow=sheet.createRow(rowNum);
            for(int k=0;k<columnCount;k++){
                currentRow.createCell(k).setCellValue(rSet.getString(k+1));
            }
        }
        rSet.close();
        pStatement.close();
        conn.close();
        workBook.write(outputStream);
        
        System.out.println("Done");
    } catch (ClassNotFoundException ex) {
        System.out.println(ex.getMessage()+"Exception in try");
    }   catch (SQLException ex) {
        System.out.println(ex.getMessage()+"Exception in try");
        } catch (IOException ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
}
    
}
