package com.muhardin.endy.belajar.belajargmailapi.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
