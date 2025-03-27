package co.srdejo.bff.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {

    @Mock
    private JwtTokenService jwtTokenService;

    @Test
    void shouldGenerateTokenSuccessfully() {
        // Arrange
        String username = "testUser";
        String fakeToken = "jwt-token";
        when(jwtTokenService.generateToken(username)).thenReturn(fakeToken);

        // Act
        String token = jwtTokenService.generateToken(username);

        // Assert
        assertNotNull(token);
        assertEquals("jwt-token", token);
        verify(jwtTokenService).generateToken(username);
    }
}
