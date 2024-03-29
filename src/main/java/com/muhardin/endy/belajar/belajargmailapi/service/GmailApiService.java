package com.muhardin.endy.belajar.belajargmailapi.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import jakarta.annotation.PostConstruct;
import jakarta.mail.Session;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

@Service @Profile("!default")
public class GmailApiService implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailApiService.class);

    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_SEND);

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${gmail.account.username}")
    private String gmailUsername;

    @Value("${gmail.folder}")
    private String dataStoreFolder;

    @Autowired private GoogleClientSecrets clientSecrets;

    @Autowired private JsonFactory jsonFactory;

    private Gmail gmail;

    @PostConstruct
    public void inisialisasiOauth() throws Exception {

        Files.createDirectories(Paths.get(dataStoreFolder));

        FileDataStoreFactory fileDataStoreFactory =
                new FileDataStoreFactory(new File(dataStoreFolder));

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

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
        try {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);

            InternetAddress destination = new InternetAddress(to);
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(gmailUsername, from));
            email.addRecipient(RecipientType.TO, destination);
            email.setSubject(subject);
            email.setContent(content, "text/html; charset=utf-8");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail = Base64.getEncoder().encodeToString(bytes);
            Message message = new Message();
            message.setRaw(encodedEmail);

            message = gmail.users().messages().send("me", message).execute();
            LOGGER.info("Email {} from {} to {} with subject {}", message.getId(), from, destination, subject);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
