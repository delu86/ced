package exporter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelExporter {


	public ExcelExporter() {

		}
	
	public static void getExcelFromDbQuery(OutputStream outputStream, String resourceDbPath,String queryString,String... args) throws IOException, RowsExceededException, WriteException, SQLException, ClassNotFoundException{
		WritableWorkbook workbook=Workbook.createWorkbook(outputStream);
        createWritableSheet(workbook, "Sheet", resourceDbPath, queryString, args); 
		workbook.write();
		workbook.close();
	}
	public static void getExcelFromDbQuery(OutputStream outputStream, String resourceDbPath,String titles[],String[]queryStrings,String[]...args) throws IOException, WriteException, ClassNotFoundException, SQLException{
		WritableWorkbook workbook=Workbook.createWorkbook(outputStream);
		int i=0;
		for(String query:queryStrings){
			createWritableSheet(workbook, titles[i], resourceDbPath, query, args[i]); 	
		i++;
		}
		workbook.write();
		workbook.close();
	}
	public static void getExcelFromIDAA(OutputStream outputStream,String driver, String urlConnection,String queryString,String... args) throws IOException, RowsExceededException, WriteException, SQLException, ClassNotFoundException{
		int numbOfSheet=1;
		WritableWorkbook workbook=Workbook.createWorkbook(outputStream);
		WritableSheet sheet=workbook.createSheet("SHEET"+numbOfSheet, 0);	
		Connection conn=null;
		PreparedStatement pStatement=null;
		Class.forName(driver);
		conn=DriverManager.getConnection(urlConnection);
		pStatement=conn.prepareStatement(queryString);
	    int i=1;
		for (String arg : args) {
		        pStatement.setString(i, arg);
		        i++;
		    }
		ResultSet rSet = pStatement.executeQuery();
		ResultSetMetaData rsMetaData=rSet.getMetaData();
		int columnCount=rsMetaData.getColumnCount();
		int types[]=new int[columnCount];		
		for(i=0;i<types.length;i++){
			types[i]=rsMetaData.getColumnType(i+1);
			WritableFont labelFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true); 
			WritableCellFormat labelFormat = new WritableCellFormat (labelFont);
			Label lab=new Label(i, 0,rsMetaData.getColumnName(i+1),labelFormat);
			sheet.addCell(lab);
			}
		int row=1;
		while(rSet.next()){
			for(int count=0;count<columnCount;count++){
			WritableCell writableCell=getWritableCell(rSet, types[count], row, count);
		    sheet.addCell(writableCell);
		    }
		   row++;	
		   if(row==65536-1){
	           row=1;
			   numbOfSheet++;
			   sheet=workbook.createSheet("SHEET"+numbOfSheet, numbOfSheet-1);
				for(i=0;i<types.length;i++){
					types[i]=rsMetaData.getColumnType(i+1);
					WritableFont labelFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true); 
					WritableCellFormat labelFormat = new WritableCellFormat (labelFont);
					Label lab=new Label(i, 0,rsMetaData.getColumnName(i+1),labelFormat);
					sheet.addCell(lab);
					}
		   }
		}
		rSet.close();
		pStatement.close();
		conn.close();
 
		workbook.write();
		workbook.close();		
	}
	private static WritableSheet createWritableSheet(WritableWorkbook workbook,
			String titleSheet,String resourceDbPath,String queryString,String... args) throws ClassNotFoundException, SQLException, RowsExceededException, WriteException{
		WritableSheet sheet=workbook.createSheet(titleSheet, 0);	
		Connection conn=null;
		PreparedStatement pStatement=null;
		ResourceBundle rb =   ResourceBundle.getBundle(resourceDbPath);
		Class.forName(rb.getString("driver"));
		conn=DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("password"));
		pStatement=conn.prepareStatement(queryString);
	    int i=1;
		for (String arg : args) {
		        pStatement.setString(i, arg);
		        i++;
		    }

		ResultSet rSet = pStatement.executeQuery();
		ResultSetMetaData rsMetaData=rSet.getMetaData();
		int columnCount=rsMetaData.getColumnCount();
		int types[]=new int[columnCount];		
		for(i=0;i<types.length;i++){
			types[i]=rsMetaData.getColumnType(i+1);
			WritableFont labelFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true); 
			WritableCellFormat labelFormat = new WritableCellFormat (labelFont);
			Label lab=new Label(i, 0,rsMetaData.getColumnName(i+1),labelFormat);
			sheet.addCell(lab);
			}
		int row=1;
		while(rSet.next()){
			for(int count=0;count<columnCount;count++){
			WritableCell writableCell=getWritableCell(rSet, types[count], row, count);
		    sheet.addCell(writableCell);
		    }
		   row++;	
		}
		rSet.close();
		pStatement.close();
		conn.close();
		return sheet;
	}
    private static WritableCell getWritableCell(ResultSet rSet, int type,int row,int column) throws SQLException {
		jxl.write.WritableCell writableCell;
    	switch (type) {
		case Types.INTEGER:
			writableCell=new jxl.write.Number(column, row, rSet.getInt(column+1));
			break;
	 
		case Types.FLOAT:	
			writableCell=new jxl.write.Number(column, row, rSet.getFloat(column+1));
			break;
        
		case Types.BIGINT:
			writableCell=new jxl.write.Number(column, row, rSet.getLong(column+1));
			break;
		
		case Types.DOUBLE:
			writableCell=new jxl.write.Number(column, row, rSet.getDouble(column+1));
			break;
		case Types.DATE:
			writableCell=new jxl.write.Label(column, row, rSet.getString(column+1));
			break;
		case Types.TIMESTAMP:
			writableCell=new jxl.write.Label(column, row, rSet.getString(column+1));
			break;
	
		default:
			writableCell=new jxl.write.Label(column, row, rSet.getString(column+1));
			break;
		}
    	return writableCell;
		
	}

}