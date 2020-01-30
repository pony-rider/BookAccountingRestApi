/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.dto;

import com.mycompany.bookaccounting.entity.Client;
import java.util.Collection;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class ExecutedOrderDTO {

    private Long id;
    
    @NotNull
    private Client client;

    private Collection<BookDTO> books;

    public ExecutedOrderDTO(Long id, Client client, Collection<BookDTO> books) {
        this.id = id;
        this.client = client;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Collection<BookDTO> getBooks() {
        return books;
    }
}
