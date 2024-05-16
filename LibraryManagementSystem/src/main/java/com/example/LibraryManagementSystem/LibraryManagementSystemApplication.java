package com.example.LibraryManagementSystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
public class LibraryManagementSystemApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

}
