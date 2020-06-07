package com.may.libraryMVC;

import com.may.libraryMVC.model.entity.Role;
import com.may.libraryMVC.model.entity.User;
import com.may.libraryMVC.repositoy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class LibraryMvcApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(LibraryMvcApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Arrays.asList(new Role("ADMIN")));
        user.setEmail("yildirimmuha@gmail.com");
        userRepository.save(user);
    }
}
