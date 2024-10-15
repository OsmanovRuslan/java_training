package com.example.springexample.controllers;

import com.example.springexample.dto.AuthorDto;
import com.example.springexample.services.AuthorCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/author")
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorCRUDService authorService;

    @GetMapping()
    public Collection<AuthorDto> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Integer id){
        return authorService.getById(id);
    }

    @PostMapping()
    public void create(@RequestBody AuthorDto authorDto) {
        authorService.create(authorDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        authorService.update(authorDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        authorService.delete(id);
    }
}
