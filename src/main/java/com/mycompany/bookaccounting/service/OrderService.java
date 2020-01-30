/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.service;

import com.mycompany.bookaccounting.dto.DtoHelper;
import com.mycompany.bookaccounting.dto.ExecutedOrderDTO;
import com.mycompany.bookaccounting.dto.OrderDTO;
import com.mycompany.bookaccounting.dto.ShortOrderDTO;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.entity.Client;
import com.mycompany.bookaccounting.entity.Order;
import com.mycompany.bookaccounting.repository.BookRepository;
import com.mycompany.bookaccounting.repository.OrderRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    private DtoHelper dtoHelper;

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("order not found: " + id));
    }

    @Transactional
    public List<Order> getOrdersByClient(Long clientId) {
        Client c = clientService.getClient(clientId);
        return c.getOrders();
    }

    @Transactional
    public Order getOrderByClient(Long clientId, Long orderId) {
        return orderRepository.findOrderOfClient(clientId, orderId).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "order {id=%d} not found by cliend {id=%d}", orderId, clientId)));
    }

    @Transactional
    public OrderDTO getOrderByClientAndMapToDTO(Long clientId, Long orderId) {
        Order o = getOrderByClient(clientId, orderId);
        return dtoHelper.createOrderDTO(o);
    }

    @Transactional
    public void deleteOrderByClient(Long clientId, Long orderId) {
        Order order = getOrderByClient(clientId, orderId);
        orderRepository.delete(order);
    }

    @Transactional
    public List<OrderDTO> getOrdersDTOs(Long clientId) {
        Client c = clientService.getClient(clientId);
        return c.getOrders().stream().map(dtoHelper::createOrderDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO getOrderDTO(Long clientId) {
        Order o = getOrderByClient(clientId, clientId);
        return dtoHelper.createOrderDTO(o);
    }

    @Transactional
    public Long createOrder(Long clientId, List<Long> bookIds) {
        if (bookIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "can't create empty order, no books specified");
        }
        Client client = clientService.getClient(clientId);
        Set<Book> books = new HashSet<>(bookRepository.findAllById(bookIds));
        System.out.println(books);
        List<Long> foundBooks = books.stream().map(b -> b.getId()).collect(Collectors.toList());
        if (foundBooks.size() != bookIds.size()) {
            bookIds.removeAll(foundBooks);
            throw new EntityNotFoundException("books not found: " + bookIds);
        }
        Order order = new Order(client, books);
        return orderRepository.save(order).getId();
    }

    @Transactional
    public Order executeOrder(Long clientId, Long orderId) {
        Order order = getOrderByClient(clientId, orderId);
        if (order.isExecuted()) {
            throw new IllegalStateException(String.format(
                    "order{%d} is already executed %s", orderId, order.getExecutionDate().
                            toString()));
        }
        order.setExecutionDate(LocalDate.now());
        return order;
    }

    @Transactional
    public OrderDTO executeOrderAndMapToDTO(Long clientId, Long orderId) {
        Order order = executeOrder(clientId, orderId);
        return dtoHelper.createOrderDTO(order);
    }

    private void checkPeriod(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("endDate is before startDate");
        }
    }

    @Transactional
    public List<ShortOrderDTO> findOrdersBetweenDates(LocalDate start, LocalDate end) {
        checkPeriod(start, end);
        return orderRepository.findOrdersBetweenDates(start, end).stream()
                .map(dtoHelper::createShortOrderDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<ExecutedOrderDTO> findExecutedOrdersBetweenDates(LocalDate start,
            LocalDate end) {
        checkPeriod(start, end);
        return orderRepository.findExecutedOrdersBetweenDates(start, end).stream().map(
                dtoHelper::createExecutedOrderDTO).collect(Collectors.toList());
    }

}
