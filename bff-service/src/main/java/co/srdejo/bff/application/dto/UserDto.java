package co.srdejo.bff.application.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank(message = "El usuario es obligatorio")
    private String username;
    @NotBlank(message = "La clave es obligatoria")
    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
