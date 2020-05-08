package com.may.libraryMVC.services.impl;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.repositoy.AuthorRepository;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public Author saveOrEditAuthor(Author author) {
        return   authorRepository.save(author);

    }

    @Override
    public Page<Author> getAllAuthors(Pageable pageable) {

        return authorRepository.findAll(pageable);
    }

    @Override
    public Author getAuthorById(Integer id) {

        return authorRepository.findById(id).get();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        Author author = authorRepository.getOne(authorId);
        bookRepository.findBooksByAuthorsId(authorId).forEach(book -> {
            book.getAuthors().remove(author);
        });
        authorRepository.delete(authorRepository.getOne(authorId));
    }



}
