package com.anaem.xpulsebo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaem.xpulsebo.dao.UserDAO;
import com.anaem.xpulsebo.model.User;

@Service
public class UserService {

    UserDAO dao = new UserDAO();
        
    public Optional<User> addNewUser(User u) throws Exception {
        return dao.addNewUser(u);
    }
    
    public Optional<User> deleteUser(User u) throws Exception {
    	return dao.deleteUser(u);
    }
    
    public Optional<User> changeFirstName(User u) throws Exception {
    	return dao.changeFirstName(u);
    }
    
    public Optional<User> changeLastName(User u) throws Exception {
    	return dao.changeLastName(u);
    }
    
    public Optional<User> changeRole(User u) throws Exception {
    	return dao.changeRole(u);
    }
    
    public Optional<User> changeUsername(User u) throws Exception {
    	return dao.changeUsername(u);
    }
    
    public Optional<User> changePassword(User u, String nP)  throws Exception {
    	return dao.changePassword(u, nP);
    }
    
    public Optional<User> getUserLogin(String username, String password) throws Exception {
    	return dao.getUserLogin(username, password);
    }
}
