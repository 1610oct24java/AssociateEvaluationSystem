package com.revature.aes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.User;
import com.revature.aes.beans.UserUpdateHolder;
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
	 *
	 * @return 
	 * 		the list of users this recruiter added
	 */
	
	
	@RequestMapping(value="/admin/employees", method= RequestMethod.GET)
	public List<User> getEmployees(){

		List<User> users = userService.findAllUsers();

		return users;
	}
	
	/**
	 * This method removes the indexed user from the database
	 * 
	 * @author Delete operation by Hajira Zahir
	 * 
	 * @param email
	 * 		The email of this recruiter
	 */
	//Delete operation by Hajira Zahir
	@RequestMapping(value="/admin/employees/Delete/{email}/", method= RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable String email){
		System.out.println(" \n====== AdminCtrl.updateEmployee: update employee by email: " + email);
		userService.removeEmployee(email);
		System.out.println(" \n====== AdminCtrl.updateEmployee: userService ran update");
	}
	

	/**
	 * This method changes details about a user in the database.
	 * 
	 * @param email
	 * 		The current email of this recruiter
	 */
	@RequestMapping(value="admin/employees/update/{email}/", method= RequestMethod.PUT)
	public void updateEmployee(@RequestBody UserUpdateHolder userUpdate, @PathVariable String email){
		System.out.println(" \n====== AdminCtrl.updateEmployee: update employee by email: " + email);
		System.out.println( "\n ====== new password = " + userUpdate.getNewPassword());
		User currentUser = userService.findUserByEmail(email);
		userService.updateEmployee(currentUser, userUpdate);
		System.out.println(" \n====== AdminCtrl.updateEmployee: userService ran update");
		
	}

	@RequestMapping(value="admin/recruiter/{email}/{lastname}/{firstname}", method = RequestMethod.POST)
	public void initRecruiter(@PathVariable String email, @PathVariable String lastname, @PathVariable String firstname) {
		System.out.println(" \n-------------- AdminController.initRecruiter: reached an endpoint...initRecruiter\n");
		String pass = userService.createRecruiter(email, lastname, firstname);
		System.out.println(" \n-------------- AdminController.initRecruiter: userService should have run...\n");
		
		boolean mailSentSuccess = mailService.sendTempPassword(email, pass);
		System.out.println(" \n-------------- AdminController.initRecruiter: Email sent? " + mailSentSuccess);
	}
	
	@RequestMapping(value="admin/trainer/{email}/{lastname}/{firstname}", method = RequestMethod.POST)
	public void initTrainer(@PathVariable String email, @PathVariable String lastname, @PathVariable String firstname) {
		System.out.println(" \n-------------- AdminController.initTrainer: reached an endpoint...");
		String pass = userService.createTrainer(email, lastname, firstname);
		System.out.println(" \n-------------- AdminController.initTrainer: userService should have run...");
	
		boolean mailSentSuccess = mailService.sendTempPassword(email, pass);
		System.out.println(" \n-------------- AdminController.initTrainer: Email sent? " + mailSentSuccess);
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
