package com.anaem.xpulsebo.utils;

public class Consts {
	private static final String password = "";
	private static final String className = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost/xpulsebo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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
