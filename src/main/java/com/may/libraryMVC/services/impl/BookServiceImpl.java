package com.may.libraryMVC.services.impl;

import com.may.libraryMVC.model.entity.Book;
import com.may.libraryMVC.model.entity.User;
import com.may.libraryMVC.repositoy.AuthorRepository;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.repositoy.UserRepository;
import com.may.libraryMVC.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Book saveOrEditBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {

        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<Book> getBooksByAuthorId(Integer authorId, Pageable pageable) {
        return bookRepository.findBooksByAuthors(authorRepository.getOne(authorId), pageable);
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }


    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);

    }


    @Override
    public boolean borrowBook(String username, Integer bookId) {


        User borrowedUser = userRepository.findUserByUsername(username).get();
        if (bookRepository.findBooksByBorrowedUserId(borrowedUser.getId()).size() > 2) {
            return false;
        }
        Book book = bookRepository.getOne(bookId);
        book.setBorrowedUser(borrowedUser);
        book.setReturnDate((LocalDate.now().plusWeeks(4)));
        bookRepository.save(book);
        return true;
    }

    @Override
    public void returnBook(Integer bookId) {
        Book book = bookRepository.getOne(bookId);
        book.setReturnDate(null);
        book.setBorrowedUser(null);
        bookRepository.save(book);
    }


}
