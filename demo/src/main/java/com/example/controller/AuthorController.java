package com.example.controller;

import com.example.model.Author;
import com.example.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "author/list";
    }

    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "author/create";
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author) {
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/update/{id}")
    public String updateAuthorForm(@PathVariable("id") Long id, Model model) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author id: " + id));
        model.addAttribute("author", author);
        return "author/update";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute Author author) {
        author.setId(id);
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }
}
