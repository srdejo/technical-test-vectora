package co.srdejo.account.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ExceptionDto {
    @JsonAlias("mensaje")
    private String message;
    @JsonAlias("codigoError")
    private String code;

    public ExceptionDto(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
