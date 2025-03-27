package co.srdejo.bff.domain.service;

import co.srdejo.bff.application.dto.UserDto;
import co.srdejo.bff.domain.exception.AuthenticationException;
import co.srdejo.bff.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateTokenSuccessfully() {
        // Arrange
        UserDto userDto = new UserDto("testUser", "password");
        authService.register(userDto);

        // Act
        String token = authService.createToken(userDto);

        // Assert
        assertNotNull(token);
    }

    @Test
    void shouldThrowExceptionWhenCredentialsAreInvalid() {
        // Arrange
        UserDto userDto = new UserDto("testUser", "password");
        authService.register(userDto);
        UserDto userDtoWrongPassword = new UserDto("testUser", "wrongPassword");
        // Act & Assert
        assertThrows(AuthenticationException.class, () -> authService.createToken(userDtoWrongPassword));
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        UserDto userDto = new UserDto("newUser", "password");

        // Act
        String token = authService.register(userDto);

        // Assert
        assertNotNull(token);
        assertTrue(userRepository.findByUsername("newUser").isPresent());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        // Arrange
        UserDto userDto = new UserDto("existingUser", "password");
        authService.register(userDto);

        // Act & Assert
        assertThrows(AuthenticationException.class, () -> authService.register(userDto));
    }

}