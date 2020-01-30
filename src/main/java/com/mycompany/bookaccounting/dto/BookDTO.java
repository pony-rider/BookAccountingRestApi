/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.dto;

import com.mycompany.bookaccounting.entity.Author;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 */
public class BookDTO {

    private Long id;
    @NotBlank
    private String title;
    private Integer publishingYear;
    @Size(max = 255)
    private String annotation; 
    private Collection<String> authors;

    protected BookDTO() {
    }

    public BookDTO(String title, int publishingYear, String annotation) {
        this.title = title;
        this.publishingYear = publishingYear;
        this.annotation = annotation;
    }

    public BookDTO(Long id, String title, Integer publishingYear, String annotation,
            Collection<String> authors) {
        this.id = id;
        this.title = title;
        this.publishingYear = publishingYear;
        this.annotation = annotation;
        this.authors = authors;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public String getAnnotation() {
        return annotation;
    }

    public Collection<String> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return String.format("Book[id=%d, title=%s, pubYear=%d]", id, title,
                publishingYear);
    }
}
