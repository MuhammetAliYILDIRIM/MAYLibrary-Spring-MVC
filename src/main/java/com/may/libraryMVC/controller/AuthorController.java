package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.entity.Author;
import com.may.libraryMVC.services.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping({"", "list"})
    public String listAuthors(Model model,
                              @PageableDefault(size = 10) @SortDefault("firstName")Pageable pageable) {
       model.addAttribute("authorsPage",authorService.getAllAuthors(pageable));

        return "author/list";
    }

    @RequestMapping("/new")
    public String newBook(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);

        return "author/authorform";

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveOrUpdateBook(Author author) {
        Author savedAuthor = authorService.saveOrEditAuthor(author);
        return "redirect:author/show/" + savedAuthor.getId();
    }

    @RequestMapping(value = "show/{id}")
    public String getAuthor(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "author/show";
    }

    @RequestMapping( params = {"edit"}, method = RequestMethod.POST)
    public String editAuthor(Model model,@RequestParam(value = "edit") Integer authorId) {
        model.addAttribute("author",authorService.getAuthorById(authorId));
        return "author/authorform";

    }

    @RequestMapping( params = {"delete"}, method = RequestMethod.POST)
    public String deleteAuthor(@RequestParam(value = "delete") Integer authorId){
        authorService.deleteAuthor(authorId);

        return "redirect:/author/list";
    }



}
