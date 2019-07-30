package com.splitwise.splitwise;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SplitwiseApplication {


	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
		System.out.println("App is running");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
