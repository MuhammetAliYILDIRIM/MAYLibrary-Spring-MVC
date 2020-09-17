package com.may.libraryMVC.model.dto;

import com.may.libraryMVC.model.entity.Book;

import java.util.List;

public class UserDTO {
    public boolean isNonLocked;
    public boolean isNonDeleted;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private List<Book> books;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isNonLocked() {
        return isNonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        isNonLocked = nonLocked;
    }

    public boolean isNonDeleted() {
        return isNonDeleted;
    }

    public void setNonDeleted(boolean nonDeleted) {
        isNonDeleted = nonDeleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
