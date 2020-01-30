/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bookaccounting.dto;

/**
 *
 */
public class ID {
    private Long id;

    public static ID from(Long id) {
        return new ID(id);
    }

    protected ID() {
    }
    
    
    protected ID(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
