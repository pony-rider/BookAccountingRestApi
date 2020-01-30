/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 *
 */
@Entity
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private Integer birthYear;

    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    protected Author() {
    }

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public Author(String name, int birthYear, Set<Book> books) {
        this(name, birthYear);
        setBooks(books);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    
    public Set<Book> getBooks() {
        return books;
    }

    public boolean addBook(Book book) {
        books.add(book);
        return book.getAuthors().add(this);
    }

    public boolean removeBook(Book book) {
        books.remove(book);
        return book.getAuthors().remove(this);
    }

    public void setBooks(Set<Book> books) {
        this.books = new HashSet<>();
        for (Book book : books) {
            addBook(book);
        }
    }

    @Override
    public String toString() {
        return String.format("Author[id=%d, name=%s, birthYear=%d]", id, name, birthYear);
    }
}
