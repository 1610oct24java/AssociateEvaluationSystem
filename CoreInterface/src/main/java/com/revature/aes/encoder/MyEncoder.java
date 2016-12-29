package com.revature.aes.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyEncoder {

	public static String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		String encodedPassword = encoder.encode(password);
		return encodedPassword;
	}
}
