package fr.gda.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecupererNbreJoursDuMois {

	public int getJours(int annee, int numeroMois) {
		Calendar cal = Calendar.getInstance();
		cal.set(annee, numeroMois - 1, 1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return maxDay;
	}

	public String getPremierJour(int annee, int numeroMois) {
		Calendar cal = Calendar.getInstance();
		cal.set(annee, numeroMois - 1, 1);

		Date maDate = cal.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		simpleDateFormat = new SimpleDateFormat("EEEE");

		String premierJour = simpleDateFormat.format(maDate);

		return premierJour;

	}

}
