package com.may.libraryMVC.services;

import com.may.libraryMVC.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book saveOrEditBook(Book book);

    Page<Book> getAllBooks(Pageable pageable);

    Page<Book> getBooksByAuthorId(Integer authorId, Pageable pageable);

    Book getBookById(Integer bookId);

    void deleteBook(Integer bookId);

    boolean borrowBook(String username,Integer bookId);

    void returnBook(Integer bookId);


}
