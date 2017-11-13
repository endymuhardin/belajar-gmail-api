package com.muhardin.endy.belajar.belajargmailapi;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BelajarGmailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelajarGmailApiApplication.class, args);
	}

	@Bean
	public MustacheFactory mustacheFactory(){
		return new DefaultMustacheFactory();
	}
}
