package com.anaem.xpulsebo.utils;

public class Consts {
	private static final String password = "aembopc1";
	private static final String className = "org.mariadb.jdbc.Driver";
	private static final String url = "jdbc:mariadb://10.10.0.3:3306/xpulsebo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String user = "root";
	public static String getPassword() {
		return password;
	}

	public static String getClassname() {
		return className;
	}

	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getSecretkey() {
		return secretKey;
	}

	private static final String secretKey = "annaAnnaANnaANNa";

	public static String getSecretKey() {
		return secretKey;
	}
}
