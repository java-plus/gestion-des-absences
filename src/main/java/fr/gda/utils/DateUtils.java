package fr.gda.utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static LocalDate toLocalDate(Date d) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));

		return date;
	}

	// public static LocalDate toLocalDate(java.util.Date d) {
	//
	// return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	// }

}
