/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.controllers;

import com.mycompany.bookaccounting.dto.ExecutedOrderDTO;
import com.mycompany.bookaccounting.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Api(value = "Orders")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get orders between startDate and endDate")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "orders retrieved")})
    @GetMapping(value = "/orders")
    public List<? extends ExecutedOrderDTO> getOrders(
            @ApiParam(format = "yyyy-MM-dd")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ApiParam(format = "yyyy-MM-dd")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "true") boolean onlyExecuted) {
        if (onlyExecuted) {
            return orderService.findExecutedOrdersBetweenDates(startDate, endDate);
        } else {
            return orderService.findOrdersBetweenDates(startDate, endDate);
        }
    }
}
