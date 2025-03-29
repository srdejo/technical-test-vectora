package co.srdejo.account.infrastructure.controller;

import co.srdejo.account.application.dto.AccountDto;
import co.srdejo.account.application.dto.NewAccountDto;
import co.srdejo.account.domain.exception.AccountNotFoundException;
import co.srdejo.account.domain.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@ExtendWith(MockitoExtension.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createAccount_ShouldReturnAccount() throws Exception {
        // Arrange
        NewAccountDto newAccountDto = new NewAccountDto("Daniel", 1000D);
        AccountDto savedAccount = new AccountDto(1L, "Daniel", 1000);

        when(accountService.create(any(NewAccountDto.class))).thenReturn(savedAccount);

        // Act & Assert
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAccountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Daniel"))
                .andExpect(jsonPath("$.saldo").value(1000));
    }

    @Test
    void getAccount_ShouldReturnAccount() throws Exception {
        // Arrange
        AccountDto account = new AccountDto(1L, "Daniel", 1000);

        when(accountService.getAccount(1L)).thenReturn(account);

        // Act & Assert
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Daniel"))
                .andExpect(jsonPath("$.saldo").value(1000));
    }

    @Test
    void getAccount_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(accountService.getAccount(1L)).thenThrow(new AccountNotFoundException("Cuenta 1 invalida"));

        // Act & Assert
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isNotFound());
    }
}