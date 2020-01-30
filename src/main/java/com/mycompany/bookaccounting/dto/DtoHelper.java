/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.dto;

import com.mycompany.bookaccounting.entity.Author;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.entity.Order;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class DtoHelper {

    public BookDTO createBookDTO(Book book) {
        if (book == null) {
            return null;
        }
        Collection<String> authorsNames = getAuthorsNames(book.getAuthors());
        BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(),
                book.getPublishingYear(), book.getAnnotation(), authorsNames);
        return bookDTO;
    }

    private Collection<String> getAuthorsNames(Collection<Author> authors) {
        return authors.stream().map(a -> a.getName()).collect(Collectors.toList());
    }

    public void copyProperties(Book book, BookDTO bookDTO) {
        book.setTitle(bookDTO.getTitle());
        book.setAnnotation(bookDTO.getAnnotation());
        book.setPublishingYear(bookDTO.getPublishingYear());
    }

    public OrderDTO createOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        Collection<BookDTO> bookDTOs = order.getBooks().stream()
                .map(b -> createBookDTO(b)).collect(Collectors.toList());
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getCreationDate(),
                order.getExecutionDate(), order.isExecuted(), order.getClient(), bookDTOs);
        return orderDTO;
    }

    public ExecutedOrderDTO createExecutedOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        Collection<BookDTO> bookDTOs = order.getBooks().stream()
                .map(b -> createBookDTO(b)).collect(Collectors.toList());
        ExecutedOrderDTO orderDTO = new ExecutedOrderDTO(order.getId(), order.getClient(),
                bookDTOs);
        return orderDTO;
    }

    public ShortOrderDTO createShortOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        Collection<BookDTO> bookDTOs = order.getBooks().stream()
                .map(b -> createBookDTO(b)).collect(Collectors.toList());
        ShortOrderDTO orderDTO = new ShortOrderDTO(order.getId(), order.isExecuted(),
                order.getClient(), bookDTOs);
        return orderDTO;
    }
}
