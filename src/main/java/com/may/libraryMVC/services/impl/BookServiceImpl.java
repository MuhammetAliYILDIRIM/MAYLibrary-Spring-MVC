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
import java.util.Optional;

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
        return bookRepository.getOne(bookId);
    }


    @Override
    public void deleteBook(Integer bookId) {
        if (!bookRepository.findById(bookId).isPresent()) {
            throw new RuntimeException("Book cannot be founded!");
        }
        bookRepository.deleteById(bookId);
    }


    @Override
    public void borrowBook(String username, Integer bookId) {


        Optional<User> borrowedUser = userRepository.findUserByUsername(username);
        if (!borrowedUser.isPresent()) {
            throw new RuntimeException("User cannot be founded!");
        }
        if (bookRepository.findBooksByBorrowedUserId(borrowedUser.get().getId()).size() > 2) {
            throw new RuntimeException("You have already borrowed 3 books!");
        }
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new RuntimeException("Book cannot be founded!");
        }
        book.get().setBorrowedUser(borrowedUser.get());
        book.get().setReturnDate((LocalDate.now().plusWeeks(4)));
        bookRepository.save(book.get());

    }

    @Override
    public void returnBook(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new RuntimeException("Book cannot be founded!");
        }
        book.get().setReturnDate(null);
        book.get().setBorrowedUser(null);
        bookRepository.save(book.get());
    }


}
