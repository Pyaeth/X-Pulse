package com.anaem.xpulsebo.utils;

import java.sql.Date;

public class CSVDateParser {
	private enum MONTHS { ianuarie, februarie, martie, aprilie, mai, iunie, iulie, august, septembrie, octombrie, noiembrie, decembrie};
	
	public static Date parseCSVDate(String s) {
		String[] aux = s.split(" ");
		String month = aux[1];
		int months = 0;
		for (MONTHS m : MONTHS.values()) {
			if (m.name().equals(month)) {
				months = m.ordinal() + 1;
				break;
			}
		}
		month = months < 10 ? ("0" + months) : ("" + months);
		return Date.valueOf(aux[2] + "-" + month + "-" + aux[0]);
	}
}
