package com.anaem.xpulsebo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anaem.xpulsebo.model.User;

public class UserDAO {
	
    Connection con;
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    
	public UserDAO() {
        try {
        	con = DBConnection.getDBConnection();
			String sql1 = "SELECT * FROM users WHERE username = ? AND password = ?";
			String sql2 = "INSERT INTO users (id, username, password) VALUES (NULL, ?, ?)";
			String sql3f = "UPDATE users SET firstname = ? WHERE username = ?";
			String sql3l = "UPDATE users SET lastname = ? WHERE username = ?";
			String sql3r = "UPDATE users SET role = ? WHERE username = ?";
			String sql4 = "UPDATE users SET username = ? WHERE id = ?";
			String sql4i = "SELECT * FROM users WHERE id = ? AND password = ?";
			String sql5 = "UPDATE users SET password = ? WHERE username = ?";
			String sql6 = "DELETE FROM users WHERE username = ?";
			String sql7 = "SELECT * FROM users WHERE username = ?";
			this.stmt1 = con.prepareStatement(sql1);
			this.stmt2 = con.prepareStatement(sql2);
			this.stmt3f = con.prepareStatement(sql3f);
			this.stmt3l = con.prepareStatement(sql3l);
			this.stmt3r = con.prepareStatement(sql3r);
			this.stmt4 = con.prepareStatement(sql4);
			this.stmt4i = con.prepareStatement(sql4i);
			this.stmt5 = con.prepareStatement(sql5);
			this.stmt6 = con.prepareStatement(sql6);
			this.stmt7 = con.prepareStatement(sql7);
		} catch (Exception e){
			logger.error(e.getMessage());
		}
    }
	
	private PreparedStatement stmt1, stmt2, stmt3f, stmt3l, stmt3r, stmt4, stmt4i, stmt5, stmt6, stmt7;
	
	private void setUserDetails(ResultSet rs, User user) throws SQLException {
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(null);
		user.setFirstname(rs.getString("firstName"));
		user.setLastname(rs.getString("lastName"));
		user.setRole(rs.getString("role"));
	}
	
	public Optional<User> retrieveUser(String username) throws SQLException {
		stmt7.setString(1, username);
		User user = new User();
		try (ResultSet rs = stmt7.executeQuery()){
			if (rs.next()) {
				setUserDetails(rs, user);
			}
			else {
				logger.warn("No such username found!");
				return Optional.empty();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	public Optional<User> getUserLogin(String username, String password) throws SQLException {
		logger.debug(username + " " + password);
		stmt1.setString(1, username);
		stmt1.setString(2, password);
		User user = new User();
		try (ResultSet rs = stmt1.executeQuery()){
			if (rs.next()) {
				setUserDetails(rs, user);
			}
			else {
				logger.warn("Invalid combination of username and password.");
				return Optional.empty();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	public Optional<User> getLoginById(int id, String password) throws SQLException {
		stmt4i.setInt(1, id);
		stmt4i.setString(2, password);
		User user = new User();
		try (ResultSet rs = stmt4i.executeQuery()){
			if (rs.next()) {
				setUserDetails(rs, user);
			}
			else {
				logger.warn("Invalid combination of id and password.");
				return Optional.empty();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	public Optional<User> addNewUser (User user) throws SQLException {
		if (retrieveUser(user.getUsername()).isPresent()) {
			return Optional.empty();
		}
		stmt2.setString(1, user.getUsername());
		stmt2.setString(2, user.getPassword());
		stmt2.executeUpdate();
		return retrieveUser(user.getUsername());
	}
	
	public Optional<User> changeFirstName (User user) throws SQLException {
		
		stmt3f.setString(2, user.getUsername());
		stmt3f.setString(1, user.getFirstname());
		stmt3f.executeUpdate();
		return retrieveUser(user.getUsername());
	}
	
	public Optional<User> changeLastName (User user) throws SQLException {
		stmt3l.setString(2, user.getUsername());
		stmt3l.setString(1, user.getLastname());
		stmt3l.executeUpdate();
		return retrieveUser(user.getUsername());
	}
	
	public Optional<User> changeRole (User user) throws SQLException {
		stmt3r.setString(1, user.getUsername());
		stmt3r.setString(2, user.getRole());
		stmt3r.executeUpdate();
		return retrieveUser(user.getUsername());
	}
	
	public Optional<User> changeUsername (User user) throws SQLException {
		if (getLoginById(user.getId(), user.getPassword()).isPresent()) {
			logger.debug("getLoginById isPresent");
			if (!retrieveUser(user.getUsername()).isPresent()) {
				logger.debug("retrieveUser isPresent");
				stmt4.setInt(2, user.getId());
				stmt4.setString(1, user.getUsername());
				stmt4.executeUpdate();
			}
		}
		return retrieveUser(user.getUsername());
	}
	  
	public Optional<User> changePassword (User user, String nP) throws SQLException {
		if (getUserLogin(user.getUsername(),user.getPassword()).isPresent()) {
			stmt5.setString(1, nP);
			stmt5.setString(2, user.getUsername());
			stmt5.executeUpdate();
		}
		return retrieveUser(user.getUsername());
	}
	
	public Optional<User> deleteUser (User user) throws SQLException {
		stmt6.setString(1, user.getUsername());
		stmt6.executeUpdate();
		stmt6.executeUpdate();
		return retrieveUser(user.getUsername());
	}
}
