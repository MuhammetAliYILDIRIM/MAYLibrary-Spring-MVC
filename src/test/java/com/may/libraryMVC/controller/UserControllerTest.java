package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO());
        users.add(new UserDTO());
        Page<UserDTO> usersPage = new PageImpl<UserDTO>(users);

        when(userService.getAllUsers(Mockito.any(Pageable.class))).thenReturn((Page) usersPage);
        mockMvc.perform(get("/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/list"))
                .andExpect(model().attribute("users", instanceOf(Page.class)));
    }

    @Test
    public void deleteUserTest() throws Exception {


        mockMvc.perform(post("/admin").param("deleteUser", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/list"));
    }

    @Test
    public void givenWrongUsernameDeleteUserTest() throws Exception {


        mockMvc.perform(post("/admin").param("deleteUser", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/list"));
    }

    @Test
    public void activateUserTest() throws Exception {


        mockMvc.perform(post("/admin").param("activateUser", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/list"));
    }


    @Test
    public void blockUserTest() throws Exception {


        mockMvc.perform(post("/admin").param("blockUser", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/list"));
    }


    @Test
    public void viewUserTest() throws Exception {

        when(userService.getUserByUsername(anyString())).thenReturn(new UserDTO());

        mockMvc.perform(post("/admin").param("viewUser", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("profileshow"))
                .andExpect(model().attribute("profile", instanceOf(UserDTO.class)));
    }

    @Test
    public void getUserProfileTest() throws Exception {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "username";
            }
        };
        when(userService.getUserByUsername(anyString())).thenReturn(new UserDTO());

        mockMvc.perform(get("/profile").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("profileshow"))
                .andExpect(model().attribute("profile", instanceOf(UserDTO.class)));

    }
}
