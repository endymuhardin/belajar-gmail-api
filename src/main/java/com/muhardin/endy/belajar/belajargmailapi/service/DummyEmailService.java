package com.muhardin.endy.belajar.belajargmailapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service @Profile("default")
public class DummyEmailService implements EmailService {

    @Override
    public void kirimEmail(String from, String to, String subject, String content) {
        log.info("Mengirim email ke {}", to);
        log.info("Subject : {}", subject);
        log.info("Content : {}", content);
    }
}
