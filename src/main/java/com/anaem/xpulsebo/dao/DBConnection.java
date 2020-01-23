package com.anaem.xpulsebo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.anaem.xpulsebo.utils.Consts;

@Configuration
@PropertySource("classpath:application.properties")
public class DBConnection {

	private static String password = Consts.getPassword();
	private static String className = Consts.getClassname();
	private static String url = Consts.getUrl();
	private static String user = Consts.getUser();
	
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
