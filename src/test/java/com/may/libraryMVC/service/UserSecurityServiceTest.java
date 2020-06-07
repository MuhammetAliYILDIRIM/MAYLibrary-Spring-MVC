package com.may.libraryMVC.service;

import com.may.libraryMVC.model.entity.Role;
import com.may.libraryMVC.model.entity.User;
import com.may.libraryMVC.repositoy.UserRepository;
import com.may.libraryMVC.services.impl.UserSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserSecurityServiceTest {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsernameTest() {
        String username = "username";
        User user = getUser();
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        assertTrue(userSecurityService.loadUserByUsername(username) instanceof UserDetails);
        UserDetails userDetail = userSecurityService.loadUserByUsername(username);
        assertAll("userDetail",
                () -> assertEquals(userDetail.getUsername(), username),
                () -> assertEquals(userDetail.getUsername(), user.getUsername()),
                () -> assertEquals(userDetail.getPassword(), user.getPassword())
        );
        assertTrue(userDetail.getAuthorities().stream().allMatch(authority -> authority instanceof GrantedAuthority));

    }

    @Test
    public void mapRolesToAuthoritiesTest() {
        User user = getUser();
        assertTrue(userSecurityService.mapRolesToAuthorities(user.getRoles()).stream().allMatch(grantedAuthority -> grantedAuthority instanceof GrantedAuthority));


    }


    public static User getUser() {
        User user = new User();
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("username");
        user.setId(0);
        user.setFirstName("user");
        user.setLastName("test");
        user.setEmail("user@mail.com");
        user.setRoles(Arrays.asList(new Role("USER")));
        user.setNonLocked(true);
        user.setNonDeleted(true);
        return user;
    }
}
