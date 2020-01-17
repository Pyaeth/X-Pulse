package com.anaem.xpulsebo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class DBConnection {

	/*
	 * @Value("${jdbc.url}") private String url;
	 * 
	 * @Value("${jdbc.user}") private String user;
	 * 
	 * @Value("${jdbc.password}") private String password;
	 * 
	 * @Value("${jdbc.className}") private String className;
	 */
    
	private static String password = "";
	private static String className = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/xpulsebo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = "root";
	
    public static Connection getDBConnection() throws SQLException {

        try {
            Class.forName(className);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
        return null;

    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
