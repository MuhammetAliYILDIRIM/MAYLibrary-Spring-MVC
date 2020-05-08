package com.may.libraryMVC.services;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    void saveOrEditUser(UserRegistrationDTO userDTO);


    Page<UserDTO> getAllUsers(Pageable pageable);

    Optional<UserDTO> getUserByUsername(String username);

    UserDTO convert(User user);

    Optional<UserDTO> getUserByEmail(String email);

    boolean deleteUser(String username);

    boolean activateUser(String username);

    boolean blockUser(String username);

    boolean unBlockUser(String username);

}
