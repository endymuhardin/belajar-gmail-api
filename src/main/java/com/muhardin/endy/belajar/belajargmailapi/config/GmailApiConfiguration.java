package com.muhardin.endy.belajar.belajargmailapi.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Configuration
public class GmailApiConfiguration {
    public static final String CLIENT_SECRET_JSON_ENV = "CLIENT_SECRET_JSON";
    public static final String STORED_CREDENTIAL_ENV = "STORED_CREDENTIAL";
    public static final String CLIENT_SECRET_JSON_FILE = "client_secret.json";
    public static final String STORED_CREDENTIAL_FILE = "StoredCredential";

    @Value("${gmail.folder}")
    private String dataStoreFolder;

    @Autowired
    private Environment env;

    @Bean
    public JsonFactory jsonFactory(){
        return GsonFactory.getDefaultInstance();
    }

    @Bean @Profile("local")
    public GoogleClientSecrets localFileClientSecrets() throws Exception {
        return loadGoogleClientSecrets();
    }

    @Bean @Profile("heroku")
    public GoogleClientSecrets environmentVariableClientSecrets() throws Exception {
        restoreEnvironmentVariableToFile(CLIENT_SECRET_JSON_ENV, CLIENT_SECRET_JSON_FILE);
        restoreEnvironmentVariableToFile(STORED_CREDENTIAL_ENV, STORED_CREDENTIAL_FILE);
        return loadGoogleClientSecrets();
    }

    private GoogleClientSecrets loadGoogleClientSecrets() throws IOException {
        return GoogleClientSecrets.load(jsonFactory(),
                new InputStreamReader(new FileInputStream(dataStoreFolder + File.separator + CLIENT_SECRET_JSON_FILE)));
    }

    private void restoreEnvironmentVariableToFile(String environmentVariableName, String filename) throws IOException {
        Files.createDirectories(Paths.get(dataStoreFolder));
        Files.write(Paths.get(dataStoreFolder +
                        File.separator + filename),
                Base64.getDecoder().decode(env.getProperty(environmentVariableName)));
    }
}
