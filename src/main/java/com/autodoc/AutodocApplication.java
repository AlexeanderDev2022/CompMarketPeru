package com.autodoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class AutodocApplication {

	public static void main(String[] args) {
		   BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		   admin123
//		    System.out.println(encoder.encode("Vendedor123"));
		SpringApplication.run(AutodocApplication.class, args);
	}

}
