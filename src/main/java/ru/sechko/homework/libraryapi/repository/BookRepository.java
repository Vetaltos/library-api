package ru.sechko.homework.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sechko.homework.libraryapi.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
