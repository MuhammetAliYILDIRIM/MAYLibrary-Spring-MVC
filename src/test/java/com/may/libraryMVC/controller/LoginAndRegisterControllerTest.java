package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginAndRegisterControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private LoginAndRegisterController loginAndRegisterController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginAndRegisterController).build();
    }

    @Test
    public void registerTest() throws Exception {

        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("user", instanceOf(UserRegistrationDTO.class)));
    }

    @Test
    public void saveTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("username", "newUsername")
                .param("firstName", "first_name")
                .param("lastName", "last_name")
                .param("password", "user_password")
                .param("confirmPassword", "user_password")
                .param("email", "email@mail.com")
                .param("confirmEmail", "email@mail.com")
                .param("terms", "true"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/registration?success"));

    }

    @Test
    public void givenWrongUsernameSaveTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("username", "short")
                .param("firstName", "first_name")
                .param("lastName", "last_name")
                .param("password", "user_password")
                .param("confirmPassword", "user_password")
                .param("email", "email@mail.com")
                .param("confirmEmail", "email@mail.com")
                .param("terms", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().hasErrors());

    }

    @Test
    public void givenWrongPasswordSaveTest() throws Exception {
        mockMvc.perform(post("/registration")
                .param("username", "newUsername")
                .param("firstName", "first_name")
                .param("lastName", "last_name")
                .param("password", "short")
                .param("confirmPassword", "user_password")
                .param("email", "email@mail.com")
                .param("confirmEmail", "email@mail.com")
                .param("terms", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().hasErrors());

    }

    @Test
    public void givenUsernameHasAlreadyExistSaveTest() throws Exception {
        when((userService.getUserByUsername(anyString()))).thenReturn(new UserDTO());
        mockMvc.perform(post("/registration")
                .param("username", "existUsername")
                .param("firstName", "first_name")
                .param("lastName", "last_name")
                .param("password", "short")
                .param("confirmPassword", "user_password")
                .param("email", "email@mail.com")
                .param("confirmEmail", "email@mail.com")
                .param("terms", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().hasErrors());

    }

    @Test
    public void givenUserEmailAlreadyExistSaveTest() throws Exception {
        when((userService.getUserByUsername(anyString()))).thenReturn(new UserDTO());
        mockMvc.perform(post("/registration")
                .param("username", "existUsername")
                .param("firstName", "first_name")
                .param("lastName", "last_name")
                .param("password", "short")
                .param("confirmPassword", "user_password")
                .param("email", "existed@mail.com")
                .param("confirmEmail", "email@mail.com")
                .param("terms", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().hasErrors());

    }

}
