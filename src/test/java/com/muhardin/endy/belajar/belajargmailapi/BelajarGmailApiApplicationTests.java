package com.muhardin.endy.belajar.belajargmailapi;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.muhardin.endy.belajar.belajargmailapi.service.GmailApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BelajarGmailApiApplicationTests {

	@Autowired private GmailApiService gmailApiService;
	@Autowired private MustacheFactory mustacheFactory;

	@Test
	public void testKirimEmail() {
		gmailApiService.kirimEmail(
				"Belajar GMail API",
				"endy.muhardin@gmail.com",
				"Email Percobaan" + LocalDateTime.now(),
				"Ini email percobaan dikirim dari aplikasi"
				);
	}

	@Test
	public void testKirimEmailDenganTemplate(){
		Mustache templateEmail = mustacheFactory.compile("templates/welcome.html");
		Map<String, String> data = new HashMap<>();
		data.put("nama", "Endy Muhardin");
		data.put("pesan", "Anda telah terdaftar di Aplikasi Notifikasi. Silahkan tunggu instruksi selanjutnya");

		StringWriter output = new StringWriter();
		templateEmail.execute(output, data);

		gmailApiService.kirimEmail(
				"Belajar GMail API",
				"endy.muhardin@gmail.com",
				"Percobaan Mustache Template",
				output.toString());
	}

}
