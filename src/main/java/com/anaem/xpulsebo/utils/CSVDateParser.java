package com.anaem.xpulsebo.utils;

import java.sql.Date;
import java.util.regex.Pattern;

public class CSVDateParser {
	private enum MONTHS { ianuarie, februarie, martie, aprilie, mai, iunie, iulie, august, septembrie, octombrie, noiembrie, decembrie};
	 private static Pattern DATE_PATTERN = Pattern.compile(
		      "^29-02-((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
		      + "|^(0[1-9]|1[0-9]|2[0-8]))-02-(((19|2[0-9])[0-9]{2})$"
		      + "|^(0[1-9]|[12][0-9]|3[01]))-(0[13578]|10|12)-(((19|2[0-9])[0-9]{2})$"
		      + "|^(0[1-9]|[12][0-9]|30))-(0[469]|11)-(((19|2[0-9])[0-9]{2})$");
	
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
	
	public static boolean matches(String date) {
		return DATE_PATTERN.matcher(date).matches();
	}
	
	public static Date parseDate(String s) {
		String[] aux = s.split("-");
		return Date.valueOf(aux[2]+"-"+aux[1]+"-"+aux[0]);
	}
}
