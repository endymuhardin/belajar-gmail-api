package com.muhardin.endy.belajar.belajargmailapi.controller;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.muhardin.endy.belajar.belajargmailapi.dto.KirimEmailRequest;
import com.muhardin.endy.belajar.belajargmailapi.service.GmailApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class KirimEmailController {

    @Autowired private GmailApiService gmailApiService;
    @Autowired private MustacheFactory mustacheFactory;

    @PostMapping("/kirim")
    @ResponseStatus(HttpStatus.CREATED)
    public void kirim(@RequestBody @Valid KirimEmailRequest kirimEmailRequest) {
        Mustache templateEmail = mustacheFactory.compile("templates/welcome.html");
        Map<String, String> data = new HashMap<>();
        data.put("nama", kirimEmailRequest.getNama());
        data.put("pesan", kirimEmailRequest.getPesan());

        StringWriter output = new StringWriter();
        templateEmail.execute(output, data);

        gmailApiService.kirimEmail(
                kirimEmailRequest.getDari(),
                kirimEmailRequest.getUntuk(),
                kirimEmailRequest.getSubjek(),
                output.toString());
    }
}
