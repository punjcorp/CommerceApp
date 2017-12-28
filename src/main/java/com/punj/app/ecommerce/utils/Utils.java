package com.punj.app.ecommerce.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {

	public static String encodePassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);

	}
	
	public static void main(String args[]) {
		System.out.println("password is"+ Utils.encodePassword("sneha"));
		
	}

}