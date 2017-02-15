package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Token;
import com.revature.aes.beans.User;

@Repository
public interface TokenDAO extends JpaRepository<Token, Integer>{
	public Token findTokenByToken(String token);
}
