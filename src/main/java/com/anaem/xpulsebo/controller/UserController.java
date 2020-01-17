package com.anaem.xpulsebo.controller;

import java.util.Optional;

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
    public ResponseEntity getValidLogin(@RequestBody User u) throws Exception {
		System.out.println("User " + u.getUsername() + " attempted to login.");
		Optional<User> checkUser = userService.retrieveUser(u.getUsername(), u.getPassword());
        if (checkUser.isPresent()) {
    		return ResponseEntity.ok(checkUser);
    	} else {
    		return ResponseEntity.badRequest().body(checkUser.get());
    	}
    	
    }
	
    @PostMapping(path="/create")
    public ResponseEntity createNewUser(@RequestBody User user) throws Exception { 
    	System.out.println("New user created: "+user.getUsername());
        Optional<User> checkUser = userService.addNewUser(user);
        if (checkUser.isPresent()) {
        	return ResponseEntity.ok(checkUser.get());
        } else {
            return ResponseEntity.badRequest().body(checkUser.get());
        }
	    
    }
	
}
