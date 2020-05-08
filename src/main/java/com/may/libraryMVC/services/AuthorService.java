package com.may.libraryMVC.services;

import com.may.libraryMVC.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    Author saveOrEditAuthor(Author author);

    Page<Author> getAllAuthors(Pageable pageable);

    Author getAuthorById(Integer id);

    List<Author> getAllAuthors();

    void deleteAuthor(Integer id);

}
