package com.may.libraryMVC.service;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.model.entity.Role;
import com.may.libraryMVC.model.entity.User;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.repositoy.UserRepository;
import com.may.libraryMVC.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class UserServiceImplTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;


    private UserServiceImpl userService;

    public static User getUser() {
        User user = new User();
        user.setUsername("username");
        user.setId(0);
        user.setFirstName("user");
        user.setLastName("test");
        user.setEmail("user@mail.com");
        user.setRoles(Collections.singletonList(new Role("USER")));
        user.setNonLocked(true);
        user.setNonDeleted(true);
        return user;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, bookRepository, passwordEncoder);

    }

    @Test
    public void saveOrEditUser() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("username");
        userRegistrationDTO.setFirstName("user");
        userRegistrationDTO.setLastName("test");
        userRegistrationDTO.setEmail("user@mail.com");
        userRegistrationDTO.setPassword("password");
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        assertThat(userService.saveOrEditUser(userRegistrationDTO))
                .returns(userRegistrationDTO.getUsername(), from(User::getUsername))
                .returns(userRegistrationDTO.getFirstName(), from(User::getFirstName))
                .returns(userRegistrationDTO.getLastName(), from(User::getLastName))
                .returns(userRegistrationDTO.getEmail(), from(User::getEmail));

        assertTrue(passwordEncoder.matches(userRegistrationDTO.getPassword(),
                userService.saveOrEditUser(userRegistrationDTO).getPassword()));
        assertEquals("USER", userService.saveOrEditUser(userRegistrationDTO).getRoles().stream().findFirst().get().getName());

    }

    @Test
    public void getAllUsersTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page pageUsers = new PageImpl<>(Collections.singletonList(getUser()));
        when(userRepository.findAll(pageable)).thenReturn(pageUsers);
        assertNotNull(userService.getAllUsers(pageable));
        assertTrue(userService.getAllUsers(pageable).stream().allMatch(Objects::nonNull));
    }

    @Test
    public void getUserByUsernameTest() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(getUser()));
        assertNotNull(userService.getUserByUsername(username));
    }

    @Test
    public void givenWrongUsernameGetUserByUsernameTest() {
        String username = "wrongusernmame";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserByUsername(username), "User cannot be founded!");
    }

    @Test
    public void getUserByEmailTest() {
        String email = "user@email.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(getUser()));
        assertNotNull(userService.getUserByEmail(email));
    }

    @Test
    public void givenWrongEmailGetUserByEmailTest() {
        String email = "wronguser@email.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserByEmail(email), "User cannot be founded!");
    }

    @Test
    public void deleteUserTest() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        assertFalse(userService.deleteUser(username).isNonDeleted());

    }

    @Test
    public void givenWrongUsernameDeleteUserTest() {
        String username = "wrongusername";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.deleteUser(username), "User cannot be founded!");

    }

    @Test
    public void activateUserTest() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        assertTrue(userService.activateUser(username).isNonDeleted());

    }

    @Test
    public void givenWrongUsernameActivateUserTest() {
        String username = "wrongusername";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.activateUser(username), "User cannot be founded!");

    }

    @Test
    public void blockUserTest() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        assertFalse(userService.blockUser(username).isNonLocked());

    }

    @Test
    public void givenWrongUsernameBlockUserTest() {
        String username = "wrongusername";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.blockUser(username), "User cannot be founded!");

    }

    @Test
    public void unBlockUserTest() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        assertTrue(userService.unBlockUser(username).isNonLocked());

    }

    @Test
    public void givenWrongUsernameUnBlockUserTest() {
        String username = "wrongusername";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.unBlockUser(username), "User cannot be founded!");

    }

    @Test
    public void isUsernameUsedTest() {
        String username = "username";
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(getUser()));
        assertTrue(userService.isUsernameUsed(username));
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
        assertFalse(userService.isUsernameUsed(username));

    }

    @Test
    public void isEmailUsedTest() {
        String email = "user@mail.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(getUser()));
        assertTrue(userService.isEmailUsed(email));
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());
        assertFalse(userService.isEmailUsed(email));

    }

    @Test
    public void convertUserToUserDTOTest() {
        User user = getUser();
        user.setPassword(passwordEncoder.encode("password"));
        assertNotNull(userService.convertUserToUserDTO(user));
        assertThat(userService.convertUserToUserDTO(user))
                .returns(user.getUsername(), UserDTO::getUsername)
                .returns(user.getFirstName(), UserDTO::getFirstName)
                .returns(user.getLastName(), UserDTO::getLastName)
                .returns(user.getEmail(), UserDTO::getEmail);
    }

}
