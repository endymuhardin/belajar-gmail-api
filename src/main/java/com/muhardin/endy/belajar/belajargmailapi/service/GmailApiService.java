package com.muhardin.endy.belajar.belajargmailapi.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GmailApiService {

    @PostConstruct
    public void inisialisasiOauth(){

    }

    public void kirimEmail(String from, String to, String subject, String content){

    }
}
