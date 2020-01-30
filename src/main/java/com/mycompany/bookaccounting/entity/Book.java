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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 */
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String title;
    private Integer publishingYear;

    @Size(max = 255)
    private String annotation;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Author> authors = new HashSet<>();

    protected Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, int publishingYear) {
        this.title = title;
        this.publishingYear = publishingYear;
    }

    public Book(String title, int publishingYear, String annotation) {
        this.title = title;
        this.publishingYear = publishingYear;
        this.annotation = annotation;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public boolean addAuthor(Author author) {
        authors.add(author);
        return author.getBooks().add(this);
    }

    public boolean removeAuthor(Author author) {
        authors.remove(author);
        return author.getBooks().remove(this);
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = new HashSet<>();
        for (Author author : authors) {
            addAuthor(author);
        }
    }

    @Override
    public String toString() {
        return String.format("Book[id=%d, title=%s, pubYear=%d]", id, title,
                publishingYear);
    }

}
