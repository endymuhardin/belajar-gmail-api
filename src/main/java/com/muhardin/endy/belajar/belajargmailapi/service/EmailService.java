package com.muhardin.endy.belajar.belajargmailapi.service;

public interface EmailService {
    void kirimEmail(String from, String to, String subject, String content);
}
