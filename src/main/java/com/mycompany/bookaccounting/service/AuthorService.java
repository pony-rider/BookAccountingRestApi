/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.service;

import com.mycompany.bookaccounting.entity.Author;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.repository.AuthorRepository;
import com.mycompany.bookaccounting.repository.BookRepository;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    @Transactional
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthor(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("author not found: " + id));
    }

    @Transactional
    public Long save(Author author) {
        return authorRepository.save(author).getId();
    }

    @Transactional
    public Author update(Author updateAuthor, Long id) {
        Author author = getAuthor(id);
        author.setName(updateAuthor.getName());
        author.setBirthYear(updateAuthor.getBirthYear());
        return authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author a = getAuthor(id);
        authorRepository.delete(a);
    }

    @Transactional
    public void deleteBookFromAuthor(Long authorId, Long bookId) {
        Author a = getAuthor(authorId);
        Book b = bookService.getBook(bookId);
        if (!a.removeBook(b)) {
            throw new IllegalArgumentException(String.format(
                    "book {id=%d} does not belongs to author {id=%d} ", bookId, authorId));
        }
    }

    @Transactional
    public void addBookToAuthor(Long authorId, Long bookId) {
        Author a = getAuthor(authorId);
        Book b = bookService.getBook(bookId);
        if (!a.addBook(b)) {
            throw new IllegalArgumentException(String.format(
                    "book {id=%d} is already belongs to author {id=%d} ", bookId, authorId));
        }
    }

    @Transactional
    public Collection<Book> getBooksByAuthor(Long authorId) {
        Author a = getAuthor(authorId);
        return a.getBooks();
    }
}
