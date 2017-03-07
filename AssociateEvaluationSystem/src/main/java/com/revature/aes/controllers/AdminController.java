package com.revature.aes.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.locator.MailServiceLocator;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.RestServices;
import com.revature.aes.service.RoleService;
import com.revature.aes.service.UserService;

/**
 * 
 * Controller for admins. Provides restful 
 * CRUD methods that can create, retrieve, update
 * or delete recruiters and trainers from the database. 
 * 
 * Access is limited to admin.
 * 
 * @author Michelle Slay, Ric Smith
 *
 */
@RestController
public class AdminController {

	@Autowired
	Logging log;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MailServiceLocator mailService;
	
	@Autowired
	private RestServices client;
	
	/**
	 * This method gets a list of candidates who reference
	 * this recruiter.
	 * 
	 * @param email
	 * 		the email of this recruiter
	 * @return 
	 * 		the list of users this recruiter added
	 */
	
	
	@RequestMapping(value="/admin/employees", method= RequestMethod.GET)
	public List<User> getEmployees(){
		
		List<User> users = new ArrayList<User>();
		
		users = userService.findAllUsers();
		
		
		
		
		return users;
	}
	
	
	
	/**
	 * This method changes details about a user in the database.
	 * 
	 * @param email
	 * 		The email of this recruiter
	 * @param index
	 * 		The candidate at the index of the list returned
	 * 		by getCandidates that needs updating 
	 * @param candidate
	 * 		The updated user object
	 * @return
	 * 		The newly saved User object
	 */
	@RequestMapping(value="/admin/{email}/employees/{index}", method= RequestMethod.PUT)
	public User updateEmployee(@PathVariable String email, @PathVariable int index, @RequestBody User user){
		return userService.updateEmployee(user, email, index);
	}
	
	/**
	 * This method removes the indexed user from the database
	 * 
	 * @param email
	 * 		The email of this recruiter
	 * @param index
	 * 		The index of this user in the list returned by
	 * getCandidates
	 */
	@RequestMapping(value="/admin/{email}/candidates/{index}", method= RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable String email, @PathVariable int index){
		userService.removeEmployee(email, index);
	}

	@RequestMapping(value="admin/recruiter/", method = RequestMethod.POST)
	public String initRecruiter(@PathVariable String email, @PathVariable String lastname, @PathVariable String firstname) {
		//userService.createRecruiter(email, lastname, firstname);
		String msg = "creating recruiter";
		return msg;
	}
	
	@RequestMapping(value="admin/trainer/{email}/{lastname}/{firstname}", method = RequestMethod.POST)
	public void initTrainer(@PathVariable String email, @PathVariable String lastname, @PathVariable String firstname) {
		userService.createTrainer(email, lastname, firstname);
	}
	
	/**

	 * This method creates a superuser for the system that can 
	 *   create recruiter and trainer accounts.
	 * This endpoint is currently disabled after creating the superuser
	 *   account so that no one else can create another superuser.
	 * 
	 * @param email
	 * 		The email of the superuser
	 * @param lastname
	 * 		lastname of the superuser
	 * @param firstname
	 * 		firstname of the superuser
	 */
//	@RequestMapping(value="admin/registerSuperuser/{email}/{lastname}/{firstname}", method = RequestMethod.POST)
//	public void initSuperuser(@PathVariable String email, @PathVariable String lastname, @PathVariable String firstname) {
//		userService.createAdmin(email, lastname, firstname);
//	}
	
}
