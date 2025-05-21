package ru.sechko.homework.libraryapi.controller;

import ru.sechko.homework.libraryapi.entity.*;
import ru.sechko.homework.libraryapi.repository.AuthorRepository;
import ru.sechko.homework.libraryapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Author testAuthor;


    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        testAuthor = new Author();
        testAuthor.setName("И.О. Фамилия");
        testAuthor =  authorRepository.save(testAuthor);

    }


    @Test
    void testCreateBook() throws Exception {
        String jsonBook = """
            {
              "title": "Тест",
              "year": 2025,
              "authorId": %d
            }
            """.formatted(testAuthor.getId());

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Тест"))
                .andExpect(jsonPath("$.year").value(2025))
                .andExpect(jsonPath("$.author.name").value("И.О. Фамилия"));
    }

    void testGetBookById() throws Exception {
        var book = bookRepository.save(new Book(1L, "Тест 01", testAuthor, 2024));
        mockMvc.perform(get("/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Тест 01"));
    }
}
