package com.may.libraryMVC.services.impl;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.model.entity.Role;
import com.may.libraryMVC.model.entity.User;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.repositoy.UserRepository;
import com.may.libraryMVC.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User saveOrEditUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(Arrays.asList(new Role("USER")));

        return userRepository.save(user);

    }


    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable).map(this::convertUserToUserDTO);
    }

    @Override
    public UserDTO getUserByUsername(String username) {

        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }
        return convertUserToUserDTO(user.get());
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (!user.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }

        return convertUserToUserDTO(user.get());
    }

    @Override
    public User deleteUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }
        user.get().setNonDeleted(false);
        return userRepository.save(user.get());

    }

    @Override
    public User activateUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }
        user.get().setNonDeleted(true);
        return userRepository.save(user.get());

    }

    @Override
    public User blockUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }
        user.get().setNonLocked(false);
        return userRepository.save(user.get());


    }

    @Override
    public User unBlockUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }
        user.get().setNonLocked(true);
        return userRepository.save(user.get());


    }

    @Override
    public boolean isUsernameUsed(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public boolean isEmailUsed(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setNonDeleted(user.isNonDeleted());
        userDTO.setNonLocked(user.isNonLocked());
        userDTO.setBooks(bookRepository.findBooksByBorrowedUserId(user.getId()));
        return userDTO;
    }


}
