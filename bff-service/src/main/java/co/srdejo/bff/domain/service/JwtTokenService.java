package co.srdejo.bff.domain.service;

public interface JwtTokenService {
    String generateToken(String username);
}
