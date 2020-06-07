package com.may.libraryMVC.data;

import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.model.entity.Book;
import com.may.libraryMVC.services.AuthorService;
import com.may.libraryMVC.services.BookService;
import com.may.libraryMVC.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.may.libraryMVC.model.constant.BookCategory.*;

@Component
public class BootstrapTestData implements ApplicationListener<ContextRefreshedEvent> {

    private final BookService bookService;
    private final AuthorService authorService;
    private final UserService userService;


    public BootstrapTestData(BookService bookService, AuthorService authorService, UserService userService,
                             PasswordEncoder passwordEncoder) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.userService = userService;

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadAuthorsAndBooks();
    }

    public void loadUsers(){
        UserRegistrationDTO userOne = new UserRegistrationDTO();
        userOne.setUsername("johnsmith");
        userOne.setFirstName("John");
        userOne.setLastName("Smith");
        userOne.setEmail("johnsmith@gmail.com");
        userOne.setPassword("asd1234");
        userService.saveOrEditUser(userOne);


        UserRegistrationDTO userTwo = new UserRegistrationDTO();
        userTwo.setUsername("emily");
        userTwo.setFirstName("Emily");
        userTwo.setLastName("Brown");
        userTwo.setEmail("emilybrown@gmail.com");
        userTwo.setPassword("asd1234");
        userService.saveOrEditUser(userTwo);

    }

    public void loadAuthorsAndBooks(){
        Author authorOne = new Author();
        authorOne.setFirstName("William");
        authorOne.setLastName("Shakespeare");
        authorOne.setNationality("English");
        authorOne.setLanguage("English");
        authorService.saveOrEditAuthor(authorOne);

        Author authorTwo = new Author();
        authorTwo.setFirstName("Kathy");
        authorTwo.setLastName("Sierra");
        authorTwo.setNationality("American");
        authorTwo.setLanguage("English");
        authorService.saveOrEditAuthor(authorTwo);

        Author authorThree = new Author();
        authorThree.setFirstName("Eric");
        authorThree.setLastName("FreeMan");
        authorThree.setNationality("American");
        authorThree.setLanguage("English");
        authorService.saveOrEditAuthor(authorThree);

        Author authorFour = new Author();
        authorFour.setFirstName("Bert");
        authorFour.setLastName("Bates");
        authorFour.setNationality("American");
        authorFour.setLanguage("English");
        authorService.saveOrEditAuthor(authorFour);

        Author authorFive = new Author();
        authorFive.setFirstName("Leo");
        authorFive.setLastName("Tolstoy");
        authorFive.setNationality("Russian");
        authorFive.setLanguage("Russian");
        authorService.saveOrEditAuthor(authorFive);

        Book bookOne = new Book();
        bookOne.setTitle("Hamlet");
        bookOne.setISBN("9781481506205");
        bookOne.setReleasesDate("1609");
        bookOne.setBookCategory(CLASSIC);
        bookOne.setAuthors(Arrays.asList(authorOne));
        bookService.saveOrEditBook(bookOne);

        Book bookTwo = new Book();
        bookTwo.setTitle("Romeo and Juliet");
        bookTwo.setISBN("9781522609957");
        bookTwo.setReleasesDate("1606");
        bookTwo.setBookCategory(CLASSIC);
        bookTwo.setAuthors(Arrays.asList(authorOne));
        bookService.saveOrEditBook(bookTwo);


        Book bookThree = new Book();
        bookThree.setTitle("Macbeth");
        bookThree.setISBN("9780237535117");
        bookThree.setReleasesDate("1609");
        bookThree.setBookCategory(CLASSIC);
        bookThree.setAuthors(Arrays.asList(authorOne));
        bookService.saveOrEditBook(bookThree);

        Book bookFour = new Book();
        bookFour.setTitle("Head First Java Paperback");
        bookFour.setISBN("9780596229201");
        bookFour.setReleasesDate("1609");
        bookFour.setBookCategory(EDUCATION_BOOK);
        bookFour.setAuthors(Arrays.asList(authorTwo,authorFour));
        bookService.saveOrEditBook(bookFour);

        Book bookFive = new Book();
        bookFive.setTitle("Head First Design Patterns: A Brain-Friendly Guide");
        bookFive.setISBN("9780596009235");
        bookFive.setReleasesDate("1609");
        bookFive.setBookCategory(EDUCATION_BOOK);
        bookFive.setAuthors(Arrays.asList(authorTwo,authorThree,authorFour));
        bookService.saveOrEditBook(bookFive);

        Book bookSix = new Book();
        bookSix.setTitle("Head First Learn to Code: A Learner's Guide to Coding and Computational Thinking");
        bookSix.setISBN("9780596039212");
        bookSix.setReleasesDate("1609");
        bookSix.setBookCategory(EDUCATION_BOOK);
        bookSix.setAuthors(Arrays.asList(authorThree));
        bookService.saveOrEditBook(bookSix);

        Book bookSeven = new Book();
        bookSeven.setTitle("OCA/OCP Java SE 7 Programmer I & II Study Guide (Exams 1Z0-803 & 1Z0-804) (Certification Press)");
        bookSeven.setISBN("9780596009223");
        bookSeven.setReleasesDate("1609");
        bookSeven.setBookCategory(EDUCATION_BOOK);
        bookSeven.setAuthors(Arrays.asList(authorTwo,authorFour));
        bookService.saveOrEditBook(bookSeven);


        Book bookEight = new Book();
        bookEight.setTitle("War and Peace");
        bookEight.setISBN("9781481504412");
        bookEight.setReleasesDate("1869");
        bookEight.setBookCategory(CLASSIC);
        bookEight.setAuthors(Arrays.asList(authorFive));
        bookService.saveOrEditBook(bookEight);

        Book bookNine = new Book();
        bookNine.setTitle("Anna Karenina");
        bookNine.setISBN("9781431504441");
        bookNine.setReleasesDate("1877");
        bookNine.setBookCategory(CLASSIC);
        bookNine.setAuthors(Arrays.asList(authorFive));
        bookService.saveOrEditBook(bookNine);

        Book bookTen = new Book();
        bookTen.setTitle("The Death of Ivan Ilyich");
        bookTen.setISBN("9781431504411");
        bookTen.setReleasesDate("1886");
        bookTen.setBookCategory(CLASSIC);
        bookTen.setAuthors(Arrays.asList(authorFive));
        bookService.saveOrEditBook(bookTen);

        Book bookEleven = new Book();
        bookEleven.setTitle("V. Henry");
        bookEleven.setISBN("9781481536215");
        bookEleven.setReleasesDate("1609");
        bookEleven.setBookCategory(HISTORY);
        bookEleven.setAuthors(Arrays.asList(authorOne));
        bookService.saveOrEditBook(bookEleven);




    }
}
