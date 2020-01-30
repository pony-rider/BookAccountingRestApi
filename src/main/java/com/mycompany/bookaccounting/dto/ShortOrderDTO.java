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
public class ShortOrderDTO extends ExecutedOrderDTO {

    private boolean executed;

    @NotNull
    private Client client;

    private Collection<BookDTO> books;

    public ShortOrderDTO(Long id, boolean isExecuted, Client client,
            Collection<BookDTO> books) {
        super(id, client, books);
        this.executed = isExecuted;
    }

    public boolean isExecuted() {
        return executed;
    }
}
