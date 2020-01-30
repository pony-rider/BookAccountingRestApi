/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.repository;

import com.mycompany.bookaccounting.entity.Book;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    List<Book> findAll();

    @Override
    List<Book> findAllById(Iterable<Long> ids);
}
