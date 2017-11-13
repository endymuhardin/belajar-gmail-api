package com.muhardin.endy.belajar.belajargmailapi;

import com.muhardin.endy.belajar.belajargmailapi.service.GmailApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BelajarGmailApiApplicationTests {

	@Autowired private GmailApiService gmailApiService;

	@Test
	public void testKirimEmail() {
		gmailApiService.kirimEmail(
				"Belajar GMail API",
				"endy.muhardin@gmail.com",
				"Email Percobaan" + LocalDateTime.now(),
				"Ini email percobaan dikirim dari aplikasi"
				);
	}

}
