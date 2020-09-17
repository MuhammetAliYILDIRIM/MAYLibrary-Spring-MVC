package com.may.libraryMVC.service;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.model.entity.Book;
import com.may.libraryMVC.model.entity.User;
import com.may.libraryMVC.repositoy.AuthorRepository;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.repositoy.UserRepository;
import com.may.libraryMVC.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static com.may.libraryMVC.model.constant.BookCategory.HISTORY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    public static Book getBook() {
        Book book = new Book();
        book.getAuthors().add(getAuthor());
        book.setTitle("title");
        book.setISBN("ISBN");
        book.setBookCategory(HISTORY);
        book.setReleasesDate("1900");

        return book;
    }

    public static Author getAuthor() {
        Author author = new Author();
        author.setFirstName("Author");
        author.setLastName("Test");
        author.setLanguage("language");
        author.setNationality("nationality");
        return author;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveOrEditBookTest() {
        Book book = getBook();
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.saveOrEditBook(book));
    }

    @Test
    public void getAllBooksTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> pageBooks = new PageImpl<>(new ArrayList<>());
        when(bookRepository.findAll(pageable)).thenReturn(pageBooks);
        assertEquals(pageBooks, bookService.getAllBooks(pageable));
    }

    @Test
    public void getBooksByAuthorIdTest() {
        Integer authorId = 0;
        Author author = getAuthor();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> pageBooks = new PageImpl<>(new ArrayList<>());
        when(authorRepository.getOne(authorId)).thenReturn(author);
        when(bookRepository.findBooksByAuthors(author, pageable)).thenReturn(pageBooks);
        assertEquals(pageBooks, bookService.getBooksByAuthorId(authorId, pageable));
    }

    @Test
    public void getBookByIdTest() {
        Integer bookId = 0;

        Book book = getBook();
        when(bookRepository.getOne(bookId)).thenReturn(book);
        assertEquals(book, bookService.getBookById(bookId));
    }

    @Test
    public void deleteBookTest() {
        Integer bookId = 0;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        assertDoesNotThrow(() -> bookService.deleteBook(bookId));
    }

    @Test
    public void givenWrongBookIdDeleteBookTest() {
        Integer bookId = 0;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookService.deleteBook(bookId), "Book cannot be founded!");
    }

    @Test
    public void borrowBookTest() {
        String username = "username";
        Integer bookId = 0;
        Book book = getBook();
        User user = new User();
        user.setId(0);
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        when(bookRepository.findBooksByBorrowedUserId(user.getId())).thenReturn(new ArrayList<>());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        assertDoesNotThrow(() -> bookService.borrowBook(username, bookId));

    }

    @Test
    public void givenUserHasAlreadyBorrowedThreeBooksBorrowBookTest() {
        String username = "username";
        Integer bookId = 0;
        Book book = getBook();
        User user = new User();
        user.setId(0);
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        borrowedBooks.add(new Book());
        borrowedBooks.add(new Book());
        borrowedBooks.add(new Book());
        when(bookRepository.findBooksByBorrowedUserId(user.getId())).thenReturn(borrowedBooks);
        when(bookRepository.getOne(bookId)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        assertThrows(RuntimeException.class, () -> bookService.borrowBook(username, bookId), "You have already " +
                "borrowed 3 books!");

    }

    @Test
    public void returnBookTest() {
        Integer bookId = 0;
        Book book = getBook();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        assertDoesNotThrow(() -> bookService.returnBook(bookId));
    }

    @Test
    public void givenWrongBookIdReturnBookTest() {
        Integer bookId = 0;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookService.returnBook(bookId), "Book cannot be founded!");
    }

}
