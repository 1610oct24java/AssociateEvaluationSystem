package com.revature.aes.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Security;
import com.revature.aes.dao.SecurityDao;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private SecurityDao dao;
	
	@Override
	public Security findSecurityByUserId(int id) {	
		return dao.findSecurityByUserId(id);
	}

}
