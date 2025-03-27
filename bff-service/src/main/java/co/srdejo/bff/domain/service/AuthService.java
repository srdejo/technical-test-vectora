package co.srdejo.bff.domain.service;

import co.srdejo.bff.application.dto.UserDto;

public interface AuthService {

    String createToken(UserDto user);
    String register(UserDto user);
}
