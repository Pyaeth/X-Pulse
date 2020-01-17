package com.anaem.xpulsebo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaem.xpulsebo.model.User;

@Service
public class UserDAO {
	
    Connection con;
	
	public UserDAO() {
        try {
        	con = DBConnection.getDBConnection();
			String sql1 = "SELECT * FROM users WHERE username = ? AND password = ?";
			String sql2 = "INSERT INTO users (id, username, password) VALUES (NULL, ?, ?)";
			String sql3 = "UPDATE users SET firstname = ?, lastname = ?, role = ? WHERE username = ?";
			String sql4 = "UPDATE users SET username = ? WHERE username = ?";
			String sql5 = "UPDATE users SET password = ? WHERE username = ?";
			String sql6 = "DELETE FROM users WHERE username = ?";
			this.stmt1 = con.prepareStatement(sql1);
			this.stmt2 = con.prepareStatement(sql2);
			this.stmt3 = con.prepareStatement(sql3);
			this.stmt4 = con.prepareStatement(sql4);
			this.stmt5 = con.prepareStatement(sql5);
			this.stmt6 = con.prepareStatement(sql6);
		} catch (Exception e){
			e.printStackTrace();
		}
    }
	
	private PreparedStatement stmt1, stmt2, stmt3, stmt4, stmt5, stmt6;
	
	private void setUserDetails(ResultSet rs, User user) throws SQLException {
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstname(rs.getString("firstName"));
		user.setLastname(rs.getString("lastName"));
		user.setRole(rs.getString("role"));
	}
	
	public Optional<User> retrieveUser(String username, String password) throws SQLException {
		stmt1.setString(1, username);
		stmt1.setString(2, password);
		User user = new User();
		try (ResultSet rs = stmt1.executeQuery()){
			if (rs.next()) {
				setUserDetails(rs, user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}
	
	public Optional<User> addNewUser (User user) throws SQLException {
		stmt2.setString(1, user.getUsername());
		stmt2.setString(2, user.getPassword());
		System.out.println(user.getUsername() + " " + user.getPassword());
		stmt2.executeUpdate();
		return retrieveUser(user.getUsername(), user.getPassword());
	}
	
	public Optional<User> updateUserDetails (User user) throws SQLException {
		stmt3.setString(1, user.getFirstname());
		stmt3.setString(2, user.getLastname());
		stmt3.setString(3, user.getRole());
		stmt3.setString(4, user.getUsername());
		stmt3.executeUpdate();
		return retrieveUser(user.getUsername(), user.getPassword());
	}
	
	public Optional<User> changeUsername (User user) throws SQLException {
		stmt4.setString(1, user.getUsername());
		stmt4.setString(2, user.getUsername());
		stmt4.executeUpdate();
		return retrieveUser(user.getUsername(), user.getPassword());
	}
	
	public Optional<User> changePassword (User user) throws SQLException {
		stmt5.setString(1, user.getPassword());
		stmt5.setString(2, user.getUsername());
		stmt5.executeUpdate();
		return retrieveUser(user.getUsername(), user.getPassword());
	}
	
	public Optional<User> deleteUser (User user) throws SQLException {
		stmt6.setString(1, user.getUsername());
		stmt6.executeUpdate();
		stmt6.executeUpdate();
		return retrieveUser(user.getUsername(), user.getPassword());
	}
}
