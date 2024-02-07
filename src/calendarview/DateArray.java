package calendarview;

import java.sql.Date;
import java.util.Calendar;

public class DateArray {
	
	static String[] yearArray = new String[201];
	static String[] monthArray = new String[] {"1", "2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

	public static String[] getYear() {
		
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		for(int i=0; i<201; i++) {
			StringBuilder yearSt = new StringBuilder();
			yearSt.append(nowYear-100+i);
			yearArray[i] =yearSt.toString();
		}		
		return yearArray;
	}
	
	public static String[] getMonth() {
		return monthArray;
	}
}