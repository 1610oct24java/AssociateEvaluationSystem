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

	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public Security createSecurity(User user) {
		// 
		Security security = new Security();
		security.setUserId(user.getUserId());
		security.setValid(1);
		String pass = new BigInteger(130,rando).toString(32);
		security.setPassword(MyEncoder.encodePassword(pass));
		
		//Call Wes's email service to email the user their password
		
		return dao.save(security);
	}
}
