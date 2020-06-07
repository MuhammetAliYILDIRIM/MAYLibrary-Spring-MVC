package com.may.libraryMVC.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void listAuthorsTest() throws Exception {

        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> authorsPage = new PageImpl<Author>(authors);

        when(authorService.getAllAuthors(any(Pageable.class))).thenReturn(authorsPage);
        mockMvc.perform(get("/author/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/list"))
                .andExpect(model().attribute("authorsPage", instanceOf(Page.class)));
    }

    @Test
    public void newAuthorTest() throws Exception {
        mockMvc.perform(get("/author/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/authorform"))
                .andExpect(model().attribute("author", instanceOf(Author.class)));
    }

    @Test
    public void saveOrUpdateAuthorTest() throws Exception {
        Author author = new Author();
        author.setId(0);
        when(authorService.saveOrEditAuthor(any(Author.class))).thenReturn(author);
        String jsonRequest = om.writeValueAsString(author);
        mockMvc.perform(post("/author")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:author/show/" + author.getId()));
    }

    @Test
    public void getAuthorTest() throws Exception {
        Author author = new Author();
        author.setId(0);
        when(authorService.getAuthorById(author.getId())).thenReturn(author);
        mockMvc.perform(get("/author/show/" + author.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("author/show"))
                .andExpect(model().attribute("author", instanceOf(Author.class)));

    }

    @Test
    public void editAuthorTest() throws Exception {
        Author author = new Author();
        author.setId(0);
        when(authorService.getAuthorById(author.getId())).thenReturn(author);
        mockMvc.perform(post("/author")
                .param("edit", author.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("author/authorform"))
                .andExpect(model().attribute("author", instanceOf(Author.class)));
    }

    @Test
    public void deleteAuthorTest() throws Exception {
        Integer authorId = 0;
        mockMvc.perform(post("/author")
                .param("delete", authorId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/author/list"));
    }

}
