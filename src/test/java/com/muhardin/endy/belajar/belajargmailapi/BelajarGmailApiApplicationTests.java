package com.muhardin.endy.belajar.belajargmailapi;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.muhardin.endy.belajar.belajargmailapi.config.GmailApiConfiguration;
import com.muhardin.endy.belajar.belajargmailapi.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class BelajarGmailApiApplicationTests {

	@Value("${gmail.folder}")
	private String dataStoreFolder;

	@Autowired private EmailService emailService;
	@Autowired private MustacheFactory mustacheFactory;

	@Test
	public void testKirimEmail() {
		emailService.kirimEmail(
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

		emailService.kirimEmail(
				"Belajar GMail API",
				"endy.muhardin@gmail.com",
				"Percobaan Mustache Template",
				output.toString());
	}


	@Test
	public void testConvertStoredCredential() throws IOException {
		byte[] credentialFile = Files.readAllBytes(
				Paths.get(dataStoreFolder + File.separator +
						GmailApiConfiguration.STORED_CREDENTIAL_FILE));
		String base64Encoded
				= Base64.getEncoder()
				.encodeToString(credentialFile);

		System.out.println(base64Encoded);
	}

	@Test
	public void testConvertClientSecret() throws IOException {
		byte[] clientSecretJson = Files.readAllBytes(
				Paths.get(dataStoreFolder + File.separator +
						GmailApiConfiguration.CLIENT_SECRET_JSON_FILE));
		String base64Encoded
				= Base64.getEncoder()
				.encodeToString(clientSecretJson);

		System.out.println(base64Encoded);
	}

}
