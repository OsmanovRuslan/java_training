package com.example.springexample;

import com.example.springexample.dto.CommentDto;
import com.example.springexample.entity.Author;
import com.example.springexample.entity.Comment;
import com.example.springexample.repositories.AuthorRepository;
import com.example.springexample.repositories.CommentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringCommentApplicationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private TestRestTemplate template = new TestRestTemplate();

    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    public void fillingDatabase() {
        Comment comment = new Comment();
        comment.setText("Комментарий");
        comment.setAuthor(authorRepository.save(new Author()));
        commentRepository.save(comment);
    }

    @AfterEach
    public void clearDatabase() {
        authorRepository.deleteAll();
    }

    @Test
    @DisplayName("Test get comment by ID")
    public void testGetCommentById() {
        ResponseEntity<CommentDto> response = template.getRestTemplate().getForEntity("http://localhost:" + port + "/comment/1", CommentDto.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(1, response.getBody().getId());
        assertEquals("Комментарий", response.getBody().getText());
    }

}
