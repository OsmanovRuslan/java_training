package org.example.controllers;

import org.example.dto.NewsDto;
import org.example.dto.NotFoundResponse;
import org.example.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsCRUDService newsCRUDService;

    public NewsController(NewsCRUDService newsCRUDService) {
        this.newsCRUDService = newsCRUDService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        NewsDto news = newsCRUDService.getById(id);
        if (news != null) {
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        NotFoundResponse response = new NotFoundResponse("Новость с ID ".concat(id.toString()).concat(" не найдена."));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public Collection<NewsDto> getAll() {
        return newsCRUDService.getAll();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody NewsDto item) {
        newsCRUDService.create(item);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody NewsDto item) {
        if (newsCRUDService.getById(id) == null){
            NotFoundResponse response = new NotFoundResponse("Новость с ID ".concat(id.toString()).concat(" не найдена."));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        newsCRUDService.update(id, item);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        NewsDto news = newsCRUDService.getById(id);
        if (news != null) {
            newsCRUDService.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            NotFoundResponse response = new NotFoundResponse("Новость с ID ".concat(id.toString()).concat(" не найдена."));
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }
}
