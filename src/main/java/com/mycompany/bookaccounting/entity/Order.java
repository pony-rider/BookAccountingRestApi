/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDate creationDate;

    private LocalDate executionDate;

    private boolean executed;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    Client client;

    @NotEmpty
    @ManyToMany
    private Set<Book> books = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDate.now();
    }

    protected Order() {
    }

    public Order(Client client, Set<Book> books) {
        setClient(client);
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    void setClient(Client client) {
        this.client = client;
        client.getOrders().add(this);
    }

    public Client getClient() {
        return client;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public boolean isExecuted() {
        return executed;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
        executed = true;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    @Override
    public String toString() {
        return String.format("Order[id=%d, ex=%b, ", id, executed)
                + creationDate + " " + executionDate + " " + client.toString()
                + " " + books.toString();
    }
}
