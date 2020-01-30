/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.dto;

import com.mycompany.bookaccounting.entity.Client;
import java.time.LocalDate;
import java.util.Collection;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class OrderDTO {

    private Long id;

    @NotNull
    private LocalDate creationDate;

    private LocalDate executionDate;

    private boolean executed;

    @NotNull
    private Client client;

    private Collection<BookDTO> books;

    public OrderDTO(Long id, LocalDate creationDate, LocalDate executionDate,
            boolean isExecuted, Client client, Collection<BookDTO> books) {
        this.id = id;
        this.creationDate = creationDate;
        this.executionDate = executionDate;
        this.executed = isExecuted;
        this.client = client;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public boolean isExecuted() {
        return executed;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public Collection<BookDTO> getBooks() {
        return books;
    }
}
