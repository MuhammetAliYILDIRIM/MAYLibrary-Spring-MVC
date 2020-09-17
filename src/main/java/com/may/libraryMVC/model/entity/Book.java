package com.may.libraryMVC.model.entity;


import com.may.libraryMVC.model.constant.BookCategory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    @Column(unique = true)
    private String ISBN;
    private String releasesDate;
    private LocalDate returnDate;
    private BookCategory bookCategory;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Author> authors = new ArrayList<>();
    @ManyToOne
    private User borrowedUser;

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getReleasesDate() {
        return releasesDate;
    }

    public void setReleasesDate(String releasesDate) {
        this.releasesDate = releasesDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public User getBorrowedUser() {
        return borrowedUser;
    }

    public void setBorrowedUser(User borrowedUser) {
        this.borrowedUser = borrowedUser;
    }
}
