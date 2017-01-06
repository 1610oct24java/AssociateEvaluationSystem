package com.revature.aes.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Role;
import com.revature.aes.beans.User;

@RestController
@RequestMapping("/rest")
public class RestControl {
	
	@RequestMapping(value = "/users")
	public List<User> getUsers() {
		
		List<User> listOfUsers = new ArrayList<User>();
		listOfUsers = createUserList();
		
		return listOfUsers;
	}
	
	// Utiliy method to create user list.
	public List<User> createUserList() {
		
		User user = new User();
		user.setUserId(1);
		user.setFirstName("First");
		user.setLastName("Last");
		user.setEmail("email@email.com");
		user.setRecruiterId(1);
		user.setSalesforce(1);
		Role role = new Role(1, "Title");
		user.setRole(role);
		String now = "";
		user.setDatePassIssued(now);
		
		List<User> listOfUsers = new ArrayList<User>();
		listOfUsers.add(user);
		return listOfUsers;
	}
	
}
