# Mengirim Email dangan GMail API #

Cara menjalankan:

1. Jalankan aplikasinya

    ```
    SPRING_PROFILES_ACTIVE=local mvn clean spring-boot:run  
    ```

2. Perhatikan console log, nanti ada perintah untuk copas url ke browser seperti ini

    [![Copas URL Google Auth](docs/setup-gmail-api.png)](docs/setup-gmail-api.png)

3. Buka di browser, berikan otorisasi untuk aplikasi

4. `POST` ke url `http://localhost:8080/kirim` dengan request body seperti ini

    ```json
    {
      "dari" : "Belajar GMail API",
      "untuk" : "user001@yopmail.com",
      "subjek" : "Email Percobaan GMail API",
      "nama" : "User 001",
      "pesan" : "Selamat datang di Aplikasi Notifikasi. Terima kasih atas partisipasi Anda."
    }
    ```

5. Cek inbox di [YopMail](http://yopmail.com), masukkan username `user001`

## Cara Deploy di Heroku ##

1. Jalankan `testConvertClientSecret`, copy outputnya

2. Masuk ke Heroku, pasang output di nomer 1 ke environment variable dengan nama `CLIENT_SECRET_JSON`

3. Jalankan `testConvertStoredCredential`, copy outputnya

4. Masuk ke Heroku, pasang output di nomer 3 ke environment variable dengan nama `STORED_CREDENTIAL`

5. Set environment variable dengan nama `SPRING_PROFILES_ACTIVE` dengan value `heroku`