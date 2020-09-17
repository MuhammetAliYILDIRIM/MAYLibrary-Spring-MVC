package com.may.libraryMVC.service;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.model.entity.Book;
import com.may.libraryMVC.repositoy.AuthorRepository;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.services.impl.AuthorServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static com.may.libraryMVC.model.constant.BookCategory.HISTORY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthorServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

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
    public void saveOrEditAuthorTest() {
        Author author = getAuthor();
        when(authorRepository.save(author)).thenReturn(author);
        assertEquals(author, authorService.saveOrEditAuthor(author));
    }

    @Test
    public void getAllAuthorsWithPageTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> pageAuthors = new PageImpl<>(new ArrayList<>());
        when(authorRepository.findAll(pageable)).thenReturn(pageAuthors);
        assertEquals(pageAuthors, authorService.getAllAuthors(pageable));
    }

    @Test
    public void getAuthorByIdTest() {
        Integer authorId = 0;
        Author author = getAuthor();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        assertEquals(author, authorService.getAuthorById(authorId));
    }

    @Test
    public void givenWrongAuthorIdGetAuthorByIdTest() {
        Integer authorId = 0;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> authorService.getAuthorById(authorId), "Author cannot be founded!");
    }

    @Test
    public void getAllAuthorsWithListTest() {
        ArrayList<Author> authors = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);
        assertEquals(authors, authorService.getAllAuthors());
    }

    @Test
    public void deleteAuthorTest() {
        Integer authorId = 0;
        Book book = getBook();
        Author author = book.getAuthors().get(0);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.findBooksByAuthorsId(authorId)).thenReturn(List.of(book));
        assertDoesNotThrow(() -> authorService.deleteAuthor(authorId));
        assertThat(book.getAuthors(), hasSize(0));
    }

    @Test
    public void givenWrongAuthorIdDeleteAuthorTest() {
        Integer authorId = 1;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authorService.deleteAuthor(authorId), "Author cannot be founded!");
    }
}
