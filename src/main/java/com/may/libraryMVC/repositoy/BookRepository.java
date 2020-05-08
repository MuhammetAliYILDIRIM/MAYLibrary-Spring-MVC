package com.may.libraryMVC.repositoy;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Page<Book> findBooksByAuthors(Author author, Pageable pageable);
    List<Book> findBooksByAuthorsId(Integer authorId);
    List<Book> findBooksByBorrowedUserId(Integer userId);

}
