package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.model.entity.Book;
import com.may.libraryMVC.services.AuthorService;
import com.may.libraryMVC.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("book")
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;


    @Autowired
    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @RequestMapping({"", "list"})
    public String getAllBooks(Model model, @PageableDefault(size = 10) @SortDefault("title") Pageable pageable) {
        model.addAttribute("books", bookService.getAllBooks(pageable));

        return "book/list";
    }

    @RequestMapping("list/{authorId}")
    public String getBooksByAuthorId(@PathVariable Integer authorId, Model model,
                                     @PageableDefault(size = 10) @SortDefault("title") Pageable pageable) {

        model.addAttribute("books", bookService.getBooksByAuthorId(authorId, pageable));

        return "book/list";
    }

    @RequestMapping("show/{bookId}")
    public String getBookById(Model model, @PathVariable Integer bookId) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "book/show";
    }

    @RequestMapping(value = "delete/{bookId}", method = RequestMethod.POST)
    public String deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);

        return "redirect:/book/list";
    }

    @RequestMapping("edit/{bookId}")
    public String editBook(Model model, @PathVariable Integer bookId) {
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        model.addAttribute("book", bookService.getBookById(bookId));
        return "book/bookform";
    }

    @RequestMapping("new")
    public String newBook(Book book) {

        return "book/bookform";
    }


    @RequestMapping(value = "bookform", params = {"addAuthor"})
    public String addAuthor(Model model, Book book, BindingResult bindingResult) {
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        book.getAuthors().add(new Author());

        return "book/bookform";
    }

    @RequestMapping(value = "bookform", params = {"removeAuthor"})
    public String removeAuthor(Book book, Model model, BindingResult bindingResult,
                               @RequestParam(value = "removeAuthor", required = false) Integer authorId) {

        book.getAuthors().remove(authorId.intValue());
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        return "book/bookform";
    }

    @RequestMapping(value = "bookform", params = {"save"}, method = RequestMethod.POST)
    public String saveBook(Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/bookform";
        }
        List<Author> authorList =
                book.getAuthors().stream().map(author -> authorService.getAuthorById(author.getId())).distinct().collect(Collectors.toList());
        book.setAuthors(authorList);
        bookService.saveOrEditBook(book);

        return "redirect:/book/list";
    }

    @RequestMapping("borrow/{bookId}")
    public String borrowBook(Principal principal, @PathVariable Integer bookId) {

        bookService.borrowBook(principal.getName(), bookId);
        return "redirect:/book";

    }

    @RequestMapping("return/{bookId}")
    public String returnBook(Principal principal, @PathVariable Integer bookId) {
        bookService.returnBook(bookId);
        return "redirect:/profile";
    }


}
