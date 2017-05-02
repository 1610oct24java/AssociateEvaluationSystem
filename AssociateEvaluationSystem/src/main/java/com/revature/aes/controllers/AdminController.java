package com.revature.aes.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.beans.Role;
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
	
	@RequestMapping(value="/admin/candidates")
	public List<User> getCandidates(){
		List<User> users = userService.findUsersByRole("candidate");
		return users;
	}
	
	@RequestMapping(value="/admin/employee/{userEmail:.+}", method = RequestMethod.GET)
	public User getEmployee(@PathVariable String userEmail){
		User user = userService.findUserByEmail(userEmail);
		return user;
	}
	
	@RequestMapping(value="/admin/employee/{userEmail}/getCandidates", method = RequestMethod.GET)
	public List<User> getCandidatesByRecruiter(@PathVariable String userEmail){
		List <User> candidates = userService.findUsersByRecruiter(userEmail);
		return candidates;
	}
	
//	@RequestMapping(value="/admin/employee/{userEmail}/updateCandidates", method = RequestMethod.PUT)
//	public List<User> updateCandidatesListByRecruiter(@PathVariable String userEmail, @RequestBody List<User> newCandidates){
//
//		return userService.updateCandidatesOnRecruiter(userEmail, newCandidates);
//				
//	}
//	@RequestMapping(value="/admin/employee/{id}", method = RequestMethod.GET)
//	public User getEmployeeById(@PathVariable String id){
//		User user = userService.getUserById(Integer.parseInt(id));
//		return user;
//	}

	/**
	 * This method registers an employee (trainer, recruiter)
	 *
	 * @author Ric Smith
	 * @param employee	(User object requiring lastname, firstname, email, roleType)
	 * @return String 	(message to front-end if registration is a success or fail)
	 */
	@RequestMapping(value="admin/employee/register", method = RequestMethod.POST)
	public String registerEmployee(@RequestBody User employee) {

		boolean passC = false;
		String pass = null;
		// check if there's a recruiter id associated with this registration
		if (employee.getRecruiterId() != null) {
			// if there is, it's a new candidate being added
			passC = userService.createCandidate(employee, userService.findEmailById(employee.getRecruiterId()));
			// if not, then it's a recruiter/trainer(/admin?)
		} else {
			pass = userService.createEmployee(employee);
		}
		boolean mailSentSuccess;
		if (pass != null || passC != false) {
			mailSentSuccess = mailService.sendTempPassword(employee.getEmail(), pass);
			if (mailSentSuccess) {
				return "{\"msg\":\"success\"}";
			} else {
				return "{\"msg\":\"fail\"}";
			}
		} else {
			return "{\"msg\":\"fail\"}";
		}
	}

	/**
	 * This method changes details about a user in the database.
	 *
	 * @param email
	 * 		The current email of this recruiter
	 */
	@RequestMapping(value="admin/employee/{currentEmail}/update", method= RequestMethod.PUT)
	public Boolean updateEmployee(@RequestBody UserUpdateHolder userUpdate, @PathVariable String currentEmail){
		User currentUser = userService.findUserByEmail(currentEmail);
		userUpdate.setNoOldPasswordCheck(true);
		return userService.updateEmployee(currentUser, userUpdate);
	}

	/**
	 * This method removes a user from the database
	 *
	 * @author Delete operation by Hajira Zahir edited by Ric Smith
	 *
	 * @param email		(email of user)
	 */
	@RequestMapping(value="/admin/employee/{email}/delete", method=RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable String email){
		userService.removeEmployee(email);
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
	
	/**
	 * Retrieves all user roles that are in the database.
	 *  
	 * @return
	 */
	@RequestMapping(value="/admin/employee/roles")
	public List<Role> getRoles() {
		return roleService.getRoles();
	}
	
	/**
	 * Retrieves all emails from users that are registered already
	 * 
	 * @return
	 */
	@RequestMapping(value="admin/employee/emails")
	public List<String> getEmails() {
		List<String> allEmails = new ArrayList<String>();
		for (User user : userService.findAllUsers()){
			allEmails.add(user.getEmail());
		}
		return allEmails;
	}
	
	/**
	 * Retrives all recruiters from the database
	 * @return
	 */
	@RequestMapping(value="admin/employee/recruiters")
	public List<User> getRecruiters(){
		List<User> allRecruiters = new ArrayList<User>();
		for (User user : userService.findAllUsers()){
			if (user.getRole().getRoleId() == 2){
				allRecruiters.add(user);
			}
		}
		return allRecruiters;
	}

}
