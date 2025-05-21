package ru.sechko.homework.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sechko.homework.libraryapi.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
