package de.studware.RezepteGenerator.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {
	private static SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss.SSS");
	
	public static String getDateTime() {
		Calendar calendar = Calendar.getInstance();
		return DATEFORMAT.format(calendar.getTime());
	}
	
}
