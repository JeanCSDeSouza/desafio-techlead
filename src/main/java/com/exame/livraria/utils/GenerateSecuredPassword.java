package com.exame.livraria.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateSecuredPassword {
	public static void main(String... args) {
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
		 System.out.println(passwordEncoder.encode("desafio-techlead").toString());
		 System.out.println(passwordEncoder.encode("desafio-techlead-su").toString());
	}
}
