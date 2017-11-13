package com.muhardin.endy.belajar.belajargmailapi.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Service
public class GmailApiService {

    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_SEND);

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${gmail.account.username}")
    private String gmailUsername;

    @Value("${gmail.credential}")
    private String credentialFile;

    @Value("${gmail.folder}")
    private String dataStoreFolder;

    private Gmail gmail;

    @PostConstruct
    public void inisialisasiOauth() throws Exception {
        JsonFactory jsonFactory =
                JacksonFactory.getDefaultInstance();

        FileDataStoreFactory fileDataStoreFactory =
                new FileDataStoreFactory(new File(dataStoreFolder));

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory,
                        new InputStreamReader(new FileInputStream(credentialFile)));

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, jsonFactory, clientSecrets, SCOPES)
                        .setDataStoreFactory(fileDataStoreFactory)
                        .setAccessType("offline")
                        .build();

        Credential gmailCredential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");

        gmail = new Gmail.Builder(httpTransport, jsonFactory, gmailCredential)
                .setApplicationName(applicationName)
                .build();

    }

    public void kirimEmail(String from, String to, String subject, String content){

    }
}
