/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.controllers;

import com.mycompany.bookaccounting.dto.ID;
import com.mycompany.bookaccounting.entity.Author;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Api(value = "Books")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Get list of all books")
    @ApiResponses(value = @ApiResponse(code = 200, message = "books retrieved"))
    @GetMapping(value = "/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @ApiOperation(value = "Get book by bookId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "book retrieved")
        ,    
        @ApiResponse(code = 404, message = "book not found")})
    @GetMapping(value = "/books/{bookId}")
    public Book getBook(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }

    @ApiOperation(value = "Create a book")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "book created")})
    @PostMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ID createBook(@Valid @RequestBody Book book) {
        return ID.from(bookService.save(book));
    }

    @ApiOperation(value = "update an existing book with bookId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "book updated")
        ,    
        @ApiResponse(code = 404, message = "book not found")})
    @PutMapping(value = "/books/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book updateBook(@Valid @RequestBody Book book,
            @PathVariable Long bookId) {
        return bookService.update(book, bookId);
    }

    @ApiOperation(value = "delete book with bookId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "book deleted")
        ,    
        @ApiResponse(code = 404, message = "book not found")})
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    
    @ApiOperation(value = "get authors of book with bookId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "authors retrieved")
        ,    
        @ApiResponse(code = 404, message = "book not found")})
    @GetMapping(value = "/books/{bookId}/authors")
    public Collection<Author> getAuthorsOfBook(@PathVariable Long bookId) {
        return bookService.getAuthorsOfBook(bookId);
    }

    @ApiOperation(value = "remove author with authorId from authors of the book with bookId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "author deleted")
        ,    
        @ApiResponse(code = 404, message = "book not found | author not found"),})
    @DeleteMapping(value = "/books/{bookId}/authors/{authorId}")            
    public void deleteAuthorFromBook(@PathVariable Long bookId,
            @PathVariable Long authorId) {
        bookService.deleteAuthorFromBook(bookId, authorId);
    }

    @ApiOperation(value = "add author with authorId to authors of the book with bookId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "author added")
        ,    
        @ApiResponse(code = 404, message = "book not found | author not found"),})
    @PostMapping(value = "/books/{bookId}/authors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addAuthorToBook(@PathVariable Long bookId,
            @NotBlank @RequestBody Long authorId) {
        bookService.addAuthorToBook(bookId, authorId);
    }
}
