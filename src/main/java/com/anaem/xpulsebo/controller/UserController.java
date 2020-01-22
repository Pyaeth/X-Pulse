package com.anaem.xpulsebo.controller;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anaem.xpulsebo.model.User;
import com.anaem.xpulsebo.service.UserService;


@RestController
@EnableAutoConfiguration
@CrossOrigin(
        allowCredentials = "true",
        origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
)
public class UserController {
	
    UserService userService = new UserService();
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/login")
    public ResponseEntity getValidLogin(@RequestBody User user) throws Exception {
		System.out.println("User " + user.getUsername() + " attempted to login.");
		Optional<User> checkUser = userService.getUserLogin(user.getUsername(), user.getPassword());
        if (checkUser.isPresent()) {
    		return ResponseEntity.ok(checkUser);
    	} else {
    		return ResponseEntity.badRequest().body("invalid combination username/password!");
    	}
    	
    }
	
    @PostMapping(path="/create")
    public ResponseEntity createNewUser(@RequestBody User user) throws Exception { 
    	System.out.println("New user created: "+user.getUsername());
        Optional<User> checkUser = userService.addNewUser(user);
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body("user already exists!");
        }  
    }
    
    @PostMapping(path="/changeUsername")
    public ResponseEntity changeUsername(@RequestBody User user) throws Exception { 
        Optional<User> checkUser = userService.changeUsername(user);
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body("user already exists!");
        }
    }

    
    static class PassForm {
    	private String username;
    	private String oldPassword;
    	private String newPassword;
    	
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getOldPassword() {
			return oldPassword;
		}
		public void setOldPassword(String oldPass) {
			this.oldPassword = oldPass;
		}
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPass) {
			this.newPassword = DigestUtils.sha512Hex(newPass);
		}
		@Override
		public String toString() {
			return "{username: " + this.username+", oldPassword: "+ this.oldPassword + ", newPassword: "+this.newPassword+"}";
		}
    }
    
    @PostMapping(path="/changePassword")
    public ResponseEntity changePassword(@RequestBody PassForm p) throws Exception { 
    	User user = new User();
    	user.setUsername(p.username);
    	user.setPassword(p.getOldPassword());
    	System.out.println(p);
        Optional<User> checkUser = userService.changePassword(user, p.getNewPassword());
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body("Couldn't change password!");
        }
    }
    
    @PostMapping(path="/changeFirstName")
    public ResponseEntity changeFirstName(@RequestBody User user) throws Exception { 
        Optional<User> checkUser = userService.changeFirstName(user);
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body("user already exists!");
        }
    }
    
    @PostMapping(path="/changeLastName")
    public ResponseEntity changeLastName(@RequestBody User user) throws Exception { 
        Optional<User> checkUser = userService.changeLastName(user);
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body("user already exists!");
        }
    }
    
    @PostMapping(path="/changeRole")
    public ResponseEntity changeRole(@RequestBody User user) throws Exception { 
        Optional<User> checkUser = userService.changeRole(user);
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body("user already exists!");
        }
    }	
}
