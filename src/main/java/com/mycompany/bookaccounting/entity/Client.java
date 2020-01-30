/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 *
 */
@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(length = 150)
    private String name;

    @Pattern(regexp = "\\d{11}")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "client", orphanRemoval = true)
    List<Order> orders = new ArrayList<>();

    protected Client() {
    }

    public Client(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.client = this;
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.client = null;
    }

    @Override
    public String toString() {
        return String.format("Client[id=%d, name=%s, phone=%s]", id, name, phoneNumber);
    }

}
