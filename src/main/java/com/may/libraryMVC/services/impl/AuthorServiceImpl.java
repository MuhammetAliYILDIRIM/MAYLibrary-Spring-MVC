package com.may.libraryMVC.services.impl;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.repositoy.AuthorRepository;
import com.may.libraryMVC.repositoy.BookRepository;
import com.may.libraryMVC.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        return authorRepository.save(author);

    }

    @Override
    public Page<Author> getAllAuthors(Pageable pageable) {

        return authorRepository.findAll(pageable);
    }

    @Override
    public Author getAuthorById(Integer id) {

        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent())
            return author.get();
        else
            throw new RuntimeException("Author cannot be founded!");
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (!author.isPresent()) {
            throw new RuntimeException("Author cannot be founded!");
        }
        bookRepository.findBooksByAuthorsId(authorId).forEach(book -> book.getAuthors().remove(author.get()));
        authorRepository.delete(authorRepository.getOne(authorId));
    }


}
