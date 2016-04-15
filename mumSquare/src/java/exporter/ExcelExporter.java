/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author CRE0260
 */
public class ExcelExporter {

    public static void writeXlsx(OutputStream outputStream, String dbResource,String queryString,String... args) 
            throws NamingException{
        Context initContext = null;
        try {
            initContext = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(ExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
                Context envContext  = (Context)initContext.lookup("java:/comp/env/");  
                DataSource datasource=(DataSource) envContext.lookup(dbResource);
        try {
            PreparedStatement ps=
                    datasource.getConnection().prepareStatement(queryString);
            ResultSet rSet = ps.executeQuery();
		ResultSetMetaData rsMetaData=rSet.getMetaData();
		int columnCount=rsMetaData.getColumnCount();
                SXSSFWorkbook workBook = new  SXSSFWorkbook();
                SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("cics");
                String currentLine=null;
                int rowNum=0;
                int types[]=new int[columnCount];
                Row intestazione=sheet.createRow(rowNum);
               for(int i=0;i<columnCount;i++){
                   intestazione.createCell(i).setCellValue(rsMetaData.getColumnLabel(i+1));
                   types[i]=rsMetaData.getColumnType(i+1);
                }
            rowNum++;
            while (rSet.next()) {
            rowNum++;
            Row currentRow=sheet.createRow(rowNum);
            for(int k=0;k<columnCount;k++){
                switch (types[k]){
                       case Types.INTEGER: currentRow.createCell(k).setCellValue(rSet.getInt(k+1));
                                           break;
                       case Types.FLOAT: currentRow.createCell(k).setCellValue(rSet.getFloat(k+1));
                                           break;
                       case Types.BIGINT:
			currentRow.createCell(k).setCellValue(rSet.getInt(k+1));
			break;
		
		case Types.DOUBLE:
                    currentRow.createCell(k).setCellValue(rSet.getDouble(k+1));
                                           break;
		case Types.DATE:
			currentRow.createCell(k).setCellValue(rSet.getDate(k+1));
                        break;

		
		case Types.TIMESTAMP:
			currentRow.createCell(k).setCellValue(rSet.getTimestamp(k+1))
                                ;break;

	
		default:
			currentRow.createCell(k).setCellValue(rSet.getString(k+1))
                                ;break;

                           

                }
                currentRow.createCell(k).setCellValue(rSet.getString(k+1));
            }
        }
            try {
                workBook.write(outputStream);
            } catch (IOException ex) {
                Logger.getLogger(ExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
