package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.dto.UserDTO;
import com.may.libraryMVC.services.BookService;
import com.may.libraryMVC.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller

public class UserController {

    private final UserService userService;
    private final BookService bookService;

    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @RequestMapping({"admin", "admin/list"})
    public String getAllBooks(Model model, @PageableDefault(size = 10) @SortDefault("username") Pageable pageable) {


        Page<UserDTO> allUsers = userService.getAllUsers(pageable);

        model.addAttribute("users", allUsers);

        return "admin/list";
    }

    @RequestMapping(value = "admin", params = {"deleteUser"}, method = RequestMethod.POST)
    public String deleteUser(@RequestParam(value = "deleteUser") String username) {
        userService.deleteUser(username);
        return "redirect:/admin/list";
    }


    @RequestMapping(value = "admin", params = {"activateUser"}, method = RequestMethod.POST)
    public String activateUser(@RequestParam(value = "activateUser") String username) {
        userService.activateUser(username);
        return "redirect:/admin/list";
    }

    @RequestMapping(value = "admin", params = {"blockUser"}, method = RequestMethod.POST)
    public String blockUser(@RequestParam(value = "blockUser") String username) {
        userService.blockUser(username);
        return "redirect:/admin/list";

    }

    @RequestMapping(value = "admin", params = {"unBlockUser"}, method = RequestMethod.POST)
    public String unBlockUser(@RequestParam(value = "unBlockUser") String username) {
        userService.unBlockUser(username);
        return "redirect:/admin/list";

    }

    @RequestMapping(value = "admin", params = {"viewUser"}, method = RequestMethod.POST)
    public String viewUser(Model model, @RequestParam(value = "viewUser") String username) {
        model.addAttribute("profile", userService.getUserByUsername(username).get());
        return "profile";

    }


    @RequestMapping("profile")
    public String getUserProfile(Model model, Principal principal) {
        if (model.getAttribute("profile") == null) {
            model.addAttribute("profile", userService.getUserByUsername(principal.getName()).get());
        }
        return "profile";

    }


}
