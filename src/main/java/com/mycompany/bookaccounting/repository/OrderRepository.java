/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.repository;

import com.mycompany.bookaccounting.entity.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Override
    List<Order> findAll();

    @Query("SELECT o FROM Order o where o.client.id=:clientId AND o.id=:orderId")
    Optional<Order> findOrderOfClient(@Param("clientId") Long clientId,
            @Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE o.creationDate BETWEEN :start AND :end")
    List<Order> findOrdersBetweenDates(@Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("SELECT o FROM Order o WHERE o.creationDate BETWEEN :start AND :end AND o.executed=TRUE")
    List<Order> findExecutedOrdersBetweenDates(@Param("start") LocalDate start,
            @Param("end") LocalDate end);

}
