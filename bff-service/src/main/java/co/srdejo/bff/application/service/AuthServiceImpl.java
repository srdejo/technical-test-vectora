package co.srdejo.bff.application.service;

import co.srdejo.bff.application.dto.UserDto;
import co.srdejo.bff.domain.service.AuthService;
import co.srdejo.bff.domain.exception.AuthenticationException;
import co.srdejo.bff.domain.model.AppUser;
import co.srdejo.bff.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenServiceImpl jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenServiceImpl jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String createToken(UserDto userDto) {
        AppUser appUser = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new AuthenticationException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(userDto.getPassword(), appUser.getPassword())) {
            throw new AuthenticationException("Credenciales incorrectas");
        }

        return jwtService.generateToken(userDto.getUsername());
    }


    @Override
    public String register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new AuthenticationException("El usuario ya existe.");
        }

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        AppUser newAppUser = new AppUser(userDto.getUsername(), hashedPassword);
        userRepository.save(newAppUser);

        return jwtService.generateToken(userDto.getUsername());
    }
}
