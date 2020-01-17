package com.anaem.xpulsebo.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class MainService {
	private Connection con;
	private String url = "jdbc:mysql://localhost/xpulseDB";
	private String user = "root";
	private String pw = "";

	private MainService() {
		try {
			con = DriverManager.getConnection(url, user, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final class SingletonHolder {
		private static final MainService SINGLETON = new MainService();
	}

	public static MainService getInstance() {
		return SingletonHolder.SINGLETON;
	}
	
}
