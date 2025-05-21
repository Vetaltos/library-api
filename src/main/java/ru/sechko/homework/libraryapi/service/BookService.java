package ru.sechko.homework.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sechko.homework.libraryapi.dto.BookDto;
import ru.sechko.homework.libraryapi.entity.Author;
import ru.sechko.homework.libraryapi.entity.Book;
import ru.sechko.homework.libraryapi.repository.AuthorRepository;
import ru.sechko.homework.libraryapi.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Page<Book> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    public Book create(BookDto bookDto) {
        Author author = authorRepository.findById(bookDto.authorId())
                .orElseThrow(() -> new RuntimeException("Автор не найден"));
        Book book = new Book(null, bookDto.title(), author, bookDto.year());
        return bookRepository.save(book);
    }

    public Book update(Long id, BookDto bookDto) {
        Book book = getById(id);
        Author author = authorRepository.findById(bookDto.authorId())
                .orElseThrow(() -> new RuntimeException("Автор не найден"));
        book.setTitle(bookDto.title());
        book.setYear(bookDto.year());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }
}
