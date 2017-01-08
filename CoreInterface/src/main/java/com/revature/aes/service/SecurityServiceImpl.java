package com.revature.aes.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.aes.beans.Security;
import com.revature.aes.beans.User;
import com.revature.aes.dao.SecurityDao;
import com.revature.aes.encoder.MyEncoder;

/**
 * Provides an implementation of the SecurityService interface.
 * This class interacts explicitly with the dao methods created
 * for our projects. If a different implementation is needed
 * be sure to securely generate random passwords for the candidates.
 * 
 * The Dao implementation used can be anything that provides access
 * to a database.
 * 
 * @author Michelle Slay
 */
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private SecurityDao dao;
	private SecureRandom rando = new SecureRandom();
	
	@Override
	public Security findSecurityByUserId(int id) {	
		return dao.findSecurityByUserId(id);
	}
	
	/**
	 * This method randomly generates a password for a given candidate.
	 * This could be generalized for any kind of user with a simple addition
	 * of a "password" parameter but I don't know why you would need to 
	 * edit use this method instead of just overloading another?
	 * 
	 * To each their own I guess.
	 * 
	 * Secure password generation is provided by java.security.SecureRandom
 	 * It becomes a random string using BigInteger's toString method.
 	 * 
 	 * You could make a separate class for generating passwords but I
 	 * didn't feel it was necessary.
 	 * 
 	 * MyEncoder encrypts the password somehow before storing it to the 
 	 * database.
	 */
	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public String createSecurity(User user) {
		// 
		
		int userId = user.getUserId();
		Security security = dao.findSecurityByUserId(userId);
		if(security == null) {
			security = new Security();
		}
		security.setUserId(userId);
		security.setValid(1);
		
		String pass = new BigInteger(130,rando).toString(32).substring(0,8);
		security.setPassword(MyEncoder.encodePassword(pass));
		
		dao.save(security);
		
		return pass;
	}

	@Override
	public void updateSecurity(Security security) {
		dao.save(security);
	}

	@Override
	public void invalidatePassword(User u) {
		// 
		Security s = dao.findSecurityByUserId(u.getUserId());
		s.setValid(0);
	}

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public String createKnownSecurity(User user) {
		// TODO Auto-generated method stub
		int userId = user.getUserId();
		Security security = dao.findSecurityByUserId(userId);
		if(security == null) {
			security = new Security();
		}
		security.setUserId(userId);
		security.setValid(1);
		

		security.setPassword(MyEncoder.encodePassword("password"));

		dao.save(security);
		
		return "password";
	}
}
