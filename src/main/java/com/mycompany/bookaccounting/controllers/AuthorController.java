/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.controllers;

import com.mycompany.bookaccounting.dto.ID;
import com.mycompany.bookaccounting.entity.Author;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
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
@Api(value = "Authors")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "Get list of all authors")
    @ApiResponses(value = @ApiResponse(code = 200, message = "authors retrieved"))
    @GetMapping(value = "/authors")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @ApiOperation(value = "Get author by authorId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "author retrieved")
        ,    
        @ApiResponse(code = 404, message = "author not found")})
    @GetMapping(value = "/authors/{authorId}")
    public Author getAuthor(@PathVariable Long authorId) {
        return authorService.getAuthor(authorId);
    }

    @ApiOperation(value = "Create a author")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "author created")})
    @PostMapping(value = "/authors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ID createAuthor(@Valid @RequestBody Author author) {
        return ID.from(authorService.save(author));
    }

    @ApiOperation(value = "update an existing author with authorId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "author updated")
        ,    
        @ApiResponse(code = 404, message = "author not found")})
    @PutMapping(value = "/authors/{authorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Author updateAuthor(@Valid @RequestBody Author author,
            @PathVariable Long authorId) {
        return authorService.update(author, authorId);
    }

    @ApiOperation(value = "delete author with authorId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "author deleted")
        ,    
        @ApiResponse(code = 404, message = "author not found")})
    @DeleteMapping("/authors/{authorId}")
    public void deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
    }

    @ApiOperation(value = "Get books of author with authorId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "books retrieved")
        ,    
        @ApiResponse(code = 404, message = "author not found")})
    @GetMapping(value = "/authors/{authorId}/books")
    public Collection<Book> getBooksByAuthor(@PathVariable Long authorId) {
        return authorService.getBooksByAuthor(authorId);
    }

    @ApiOperation(value = "remove book with bookId from books of the author with authorId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "book deleted")
        ,    
        @ApiResponse(code = 404, message = "book not found | author not found")})
    @DeleteMapping(value = "/authors/{authorId}/books/{bookId}")
    public void deleteBookFromAuthor(@PathVariable Long authorId,
            @PathVariable Long bookId) {
        authorService.deleteBookFromAuthor(authorId, bookId);
    }

    @ApiOperation(value = "add book with bookId to books of the author with authorId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "book added")
        ,    
        @ApiResponse(code = 404, message = "book not found | author not found")})
    @PostMapping(value = "/authors/{authorId}/books/")
    public void addBookToAuthor(@PathVariable Long authorId, @RequestBody Long bookId) {
        authorService.addBookToAuthor(authorId, bookId);
    }
}
