package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/*
 * Funzioni di utilità per conversione date, calcolo del giorno, ecc
 * 
 */
public class UtilityDate {

	public static final String formatDateDatabase="yyyyMMdd";
	public static final String formatDateEpv="yyyy-MM-dd";
	public static final String formatDateForVisualization="dd-MM-yyyy";
	
	public UtilityDate() {
		// TODO Auto-generated constructor stub
	}
	
	public static Date getDate(int shiftAmount){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, shiftAmount);
		return calendar.getTime();
	}
	public static Date getDate(String date,String formatDate) throws ParseException{
		SimpleDateFormat format=new SimpleDateFormat(formatDate);
		Date d= format.parse(date);
		return d;
	}
	public static String fromMilliSecondToDateTimeString(long millis,String timezone,String formatDate){
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		calendar.setTimeInMillis(millis);
		Date date=calendar.getTime();
		SimpleDateFormat f1=new SimpleDateFormat(formatDate);
		f1.setTimeZone(TimeZone.getTimeZone(timezone));
		return f1.format(date);
	}
	public static String conversionToDBformat(Date day){
		SimpleDateFormat format=new SimpleDateFormat(formatDateDatabase);
		return format.format(day);
	}
	public static String conversionToEpvformat(Date day){
		SimpleDateFormat format=new SimpleDateFormat(formatDateEpv);
		return format.format(day);
	}
	public static String conversionToFormat(String formatString,Date day){
		SimpleDateFormat format=new SimpleDateFormat(formatString);
		return format.format(day);
	}
	public static String conversionToVisulaformat(Date day){
		SimpleDateFormat format=new SimpleDateFormat(formatDateForVisualization);
		return format.format(day);
	}
	public static String fromFormatToFormat(String date,String format1,String format2) throws ParseException
	{
		   SimpleDateFormat fromFormat = new SimpleDateFormat(format1);
		   SimpleDateFormat toFormat = new SimpleDateFormat(format2);
		   return toFormat.format(fromFormat.parse(date));
	}
	public static String conversionFromDBformatToVisualFormat(String dbDate){
		String visualDate=dbDate.substring(6, 8)+"-"+dbDate.substring(4, 6)+"-"+dbDate.substring(0, 4);
		return visualDate;
	}
	public static void main(String[] args) throws ParseException{
		System.out.println(UtilityDate.conversionToDBformat(UtilityDate.getDate(-1)));
	}
}
