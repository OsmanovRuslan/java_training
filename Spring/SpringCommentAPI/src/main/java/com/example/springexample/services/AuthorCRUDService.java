package com.example.springexample.services;

import com.example.springexample.dto.AuthorDto;
import com.example.springexample.entity.Author;
import com.example.springexample.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthorCRUDService implements CRUDService<AuthorDto> {

    private final AuthorRepository repository;

    @Override
    public AuthorDto getById(Integer id) {
        log.info("Get by ID: {}", id);
        Author author = repository.findById(id).orElseThrow();
        return mapToDto(author);
    }

    @Override
    public Collection<AuthorDto> getAll() {
        log.info("Get All");
        return repository.findAll()
                .stream()
                .map(AuthorCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(AuthorDto authorDto) {
        log.info("Create");
        repository.save(mapToEntity(authorDto));
    }

    @Override
    public void update(AuthorDto authorDto) {
        log.info("Update");
        repository.save(mapToEntity(authorDto));
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete");
        repository.deleteById(id);
    }

    public static AuthorDto mapToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setFirstName(author.getFirstName());
        authorDto.setLastName(author.getLastName());
        authorDto.setRating(author.getRating());
        authorDto.setComments(author.getComments()
                .stream()
                .map(CommentCRUDService::mapToDto)
                .toList());
        return authorDto;
    }

    public static Author mapToEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
        author.setRating(authorDto.getRating());
        author.setComments(authorDto.getComments().stream()
                .map(CommentCRUDService::mapToEntity)
                .toList());
        return author;
    }

}
