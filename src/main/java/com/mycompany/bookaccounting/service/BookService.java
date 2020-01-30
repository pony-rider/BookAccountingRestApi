/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.service;

import com.mycompany.bookaccounting.dto.BookDTO;
import com.mycompany.bookaccounting.dto.DtoHelper;
import com.mycompany.bookaccounting.entity.Author;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.repository.BookRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private DtoHelper dtoHelper;

    @Transactional
    public List<BookDTO> getBookDTOs() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(dtoHelper::createBookDTO).collect(Collectors.toList());
    }

    @Transactional
    public BookDTO getBookDTO(Long id) {
        return bookRepository.findById(id).map(dtoHelper::createBookDTO).get();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find book " + id));
    }
    
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public Long save(Book book) {
        return bookRepository.save(book).getId();
    }

    @Transactional
    public Book update(Book updateBook, Long id) {
        Book b = getBook(id);
        b.setPublishingYear(updateBook.getPublishingYear());
        b.setAnnotation(updateBook.getAnnotation());
        return bookRepository.save(b);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book b = getBook(id);        
        bookRepository.deleteById(id);
    }

    @Transactional
    public Collection<Author> getAuthorsOfBook(Long bookId) {        
        Book b = getBook(bookId);
        return b.getAuthors();
    }

    @Transactional
    public void deleteAuthorFromBook(Long bookId, Long authorId) {
        Book b = getBook(bookId);
        Author a = authorService.getAuthor(authorId);
        if (!b.removeAuthor(a)) {
            throw new IllegalArgumentException(String.format(
                    "book {id=%d} does not belongs to author {id=%d} ", bookId, authorId));
        }
    }

    @Transactional
    public void addAuthorToBook(Long bookId, Long authorId) {
        Book b = getBook(bookId);
        Author a = authorService.getAuthor(authorId);
        if (!b.addAuthor(a)) {
            throw new IllegalArgumentException(String.format(
                    "book {id=%d} is already belongs to author{id=%d} ", bookId, authorId));
        }
    }
}
