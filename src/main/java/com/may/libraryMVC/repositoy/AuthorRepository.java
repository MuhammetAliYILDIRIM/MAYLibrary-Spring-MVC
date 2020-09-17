package com.may.libraryMVC.repositoy;

import com.may.libraryMVC.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
