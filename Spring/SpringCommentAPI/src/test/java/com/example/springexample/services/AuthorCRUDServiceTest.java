package com.example.springexample.services;

import com.example.springexample.dto.AuthorDto;
import com.example.springexample.entity.Author;
import com.example.springexample.repositories.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorCRUDServiceTest {

    private final AuthorRepository authorRepository = Mockito.mock(AuthorRepository.class);

    private final AuthorCRUDService authorCRUDService = new AuthorCRUDService(authorRepository);

    @Test
    @DisplayName("Test getById")
    public void testGetById() {
        int authorId = 1;
        Author author = new Author();
        author.setId(authorId);
        author.setComments(List.of());
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorCRUDService.getById(authorId);
        assertEquals(authorId, authorDto.getId());
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    @DisplayName("Test getAll")
    public void testGetAll() {
        int authorId = 1;
        List<Author> authorList = new ArrayList<>();
        Author author = new Author();
        author.setId(authorId);
        author.setComments(List.of());
        authorList.add(author);
        when(authorRepository.findAll()).thenReturn(authorList);
        Collection<AuthorDto> authorDtos = authorCRUDService.getAll();
        assertEquals(authorDtos.size(), authorList.size());
        verify(authorRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Test create")
    public void testCreate() {
        AuthorDto authorDto = new AuthorDto();
        authorCRUDService.create(authorDto);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("Test update")
    public void testUpdate() {
        AuthorDto authorDto = new AuthorDto();
        authorCRUDService.update(authorDto);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("Test deleteById")
    public void testDeleteById() {
        int authorId = 1;
        authorCRUDService.delete(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }
}
