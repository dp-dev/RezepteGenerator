package de.studware.rezeptegenerator.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {

	public static String getDateTime() {
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss.SSS");
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}
	
}
