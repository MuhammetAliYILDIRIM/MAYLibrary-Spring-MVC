package com.may.libraryMVC.services;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User saveOrEditUser(UserRegistrationDTO userDTO);


    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO getUserByUsername(String username);

    UserDTO convertUserToUserDTO(User user);

    UserDTO getUserByEmail(String email);

    User deleteUser(String username);

    User activateUser(String username);

    User blockUser(String username);

    User unBlockUser(String username);

    boolean isUsernameUsed(String username);

    boolean isEmailUsed(String email);

}
