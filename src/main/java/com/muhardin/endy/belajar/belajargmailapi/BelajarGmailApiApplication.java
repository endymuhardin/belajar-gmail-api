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
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootApplication
public class BelajarGmailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BelajarGmailApiApplication.class, args);
	}

	private static final String CLIENT_SECRET_JSON_FILE = "client_secret.json";
	private static final String STORED_CREDENTIAL_FILE = "StoredCredential";

	@Value("${GMAIL_CREDENTIAL_FILE_SERVER}")
	private String credentialFileServer;

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

	@Bean @Profile("heroku")
	public GoogleClientSecrets downloadClientSecrets() throws Exception {
		downloadCredentialsFile(CLIENT_SECRET_JSON_FILE);
		downloadCredentialsFile(STORED_CREDENTIAL_FILE);
		return GoogleClientSecrets.load(jsonFactory(),
				new InputStreamReader(new FileInputStream(dataStoreFolder + File.separator + CLIENT_SECRET_JSON_FILE)));
	}

	private void downloadCredentialsFile(String filename) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new ByteArrayHttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(
				credentialFileServer + "/" + filename,
				HttpMethod.GET, entity, byte[].class, "1");

		if (response.getStatusCode() == HttpStatus.OK) {
			Files.createDirectories(Paths.get(dataStoreFolder));
			Files.write(Paths.get(dataStoreFolder + File.separator + filename), response.getBody());
		}
	}

}
