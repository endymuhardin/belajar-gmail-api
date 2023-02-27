package com.muhardin.endy.belajar.belajargmailapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KirimEmailRequest {

    @NotNull @NotEmpty
    private String dari;

    @NotNull @NotEmpty
    private String untuk;

    @NotNull @NotEmpty
    private String subjek;

    @NotNull @NotEmpty
    private String nama;

    @NotNull @NotEmpty
    private String pesan;
}
