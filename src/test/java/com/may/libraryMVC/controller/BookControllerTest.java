package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.model.entity.Book;
import com.may.libraryMVC.services.AuthorService;
import com.may.libraryMVC.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BookControllerTest {
    @Mock
    private BookService bookService;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private BookController bookController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc =
                MockMvcBuilders.standaloneSetup(bookController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void getAllBooksTest() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        Page<Book> booksPage = new PageImpl<>(books);

        when(bookService.getAllBooks(Mockito.any(Pageable.class))).thenReturn(booksPage);
        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", instanceOf(Page.class)));
    }

    @Test
    public void getBooksByAuthorIdTest() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        Page<Book> booksPage = new PageImpl<>(books);
        Integer authorId = 0;

        when(bookService.getBooksByAuthorId(eq(authorId), Mockito.any(Pageable.class))).thenReturn(booksPage);
        mockMvc.perform(get("/book/list/" + authorId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("books", instanceOf(Page.class)));
    }

    @Test
    public void getBookByIdTest() throws Exception {
        Integer bookId = 0;
        when(bookService.getBookById(bookId)).thenReturn(new Book());
        mockMvc.perform(get("/book/show/" + bookId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/show"))
                .andExpect(model().attribute("book", instanceOf(Book.class)));
    }

    @Test
    public void deleteBookTest() throws Exception {
        int bookId = 0;
        mockMvc.perform(post("/book/delete/" + bookId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/list"));
    }

    @Test
    public void editBookTest() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Integer bookId = 0;
        when(bookService.getBookById(bookId)).thenReturn(new Book());
        when(authorService.getAllAuthors()).thenReturn(authors);
        mockMvc.perform(get("/book/edit/" + bookId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attribute("book", instanceOf(Book.class)))
                .andExpect(model().attribute("allAuthors", hasItem(allOf(instanceOf(Author.class)))));
    }

    @Test
    public void newBookTest() throws Exception {
        mockMvc.perform(get("/book/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attribute("book", instanceOf(Book.class)));
    }

    @Test
    public void addAuthorTest() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Integer bookId = 0;
        when(bookService.getBookById(bookId)).thenReturn(new Book());
        when(authorService.getAllAuthors()).thenReturn(authors);
        mockMvc.perform(get("/book/bookform")
                .param("addAuthor", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attribute("book", instanceOf(Book.class)))
                .andExpect(model().attribute("allAuthors", hasItem(allOf(instanceOf(Author.class)))));
    }

    @Test
    public void saveBookTest() throws Exception {
        when(bookService.saveOrEditBook(Mockito.any(Book.class))).thenReturn(new Book());
        mockMvc.perform(post("/book/bookform")
                .param("save", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/list"));
    }

    @Test
    public void borrowBookTest() throws Exception {
        int borrowId = 0;
        Principal principal = () -> "TEST_PRINCIPAL";
        mockMvc.perform(get("/book/borrow/" + borrowId).principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
    }

    @Test
    public void userCannotBorrowBookTest() throws Exception {
        int borrowId = 0;
        Principal principal = () -> "TEST_PRINCIPAL";

        mockMvc.perform(get("/book/borrow/" + borrowId).principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
    }

    @Test
    public void returnBookTest() throws Exception {
        int returnId = 0;
        Principal principal = () -> "TEST_PRINCIPAL";

        mockMvc.perform(get("/book/borrow/" + returnId).principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
    }


}
