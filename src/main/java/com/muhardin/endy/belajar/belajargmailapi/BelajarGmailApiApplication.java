package com.muhardin.endy.belajar.belajargmailapi;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class BelajarGmailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelajarGmailApiApplication.class, args);
	}

	private static final String CLIENT_SECRET_JSON_FILE = "client_secret.json";

	@Value("${gmail.folder}")
	private String dataStoreFolder;

	@Bean
	public MustacheFactory mustacheFactory(){
		return new DefaultMustacheFactory();
	}

	@Bean
	public JsonFactory jsonFactory(){
		return JacksonFactory.getDefaultInstance();
	}

	@Bean @Profile("!heroku")
	public GoogleClientSecrets localFileClientSecrets() throws Exception {
		return GoogleClientSecrets.load(jsonFactory(),
						new InputStreamReader(new FileInputStream(dataStoreFolder + File.separator + CLIENT_SECRET_JSON_FILE)));
	}
}
