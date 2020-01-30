/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.controllers;

import com.mycompany.bookaccounting.dto.ID;
import com.mycompany.bookaccounting.dto.OrderDTO;
import com.mycompany.bookaccounting.entity.Client;
import com.mycompany.bookaccounting.service.ClientService;
import com.mycompany.bookaccounting.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Api(value = "Orders")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get list of all clients")
    @ApiResponses(value = @ApiResponse(code = 200, message = "clients retrieved"))
    @GetMapping(value = "/clients")
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @ApiOperation(value = "Get client by clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "client retrieved")
        ,    
        @ApiResponse(code = 404, message = "client not found")})
    @GetMapping(value = "/clients/{clientId}")
    public Client getClient(@PathVariable Long clientId) {
        return clientService.getClient(clientId);
    }

    @ApiOperation(value = "Create a client")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "client created")})
    @PostMapping(value = "/clients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ID createClient(@Valid @RequestBody Client client) {
        return ID.from(clientService.create(client));
    }

    @ApiOperation(value = "update an existing client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "client updated")
        ,    
        @ApiResponse(code = 404, message = "client not found")})
    @PutMapping(value = "/clients/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Client updateClient(@PathVariable Long clientId,
            @Valid @RequestBody Client client) {
        return clientService.updateClient(clientId, client);
    }

    @ApiOperation(value = "delete client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "client deleted")
        ,    
        @ApiResponse(code = 404, message = "client not found")})
    @Transactional
    @DeleteMapping(value = "/clients/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }

    @ApiOperation(value = "Get orders of client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "books retrieved")
        ,    
        @ApiResponse(code = 404, message = "client not found")})
    @GetMapping(value = "/clients/{clientId}/orders")
    public List<OrderDTO> getOrders(@PathVariable Long clientId) {
        return orderService.getOrdersDTOs(clientId);
    }

    @ApiOperation(value = "get order with orderId of client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "order retrieved")
        ,    
        @ApiResponse(code = 404, message = "client not found | order not found")})
    @GetMapping(value = "/clients/{clientId}/orders/{orderId}")
    public OrderDTO getOrder(@PathVariable Long clientId, @PathVariable Long orderId) {
        return orderService.getOrderByClientAndMapToDTO(clientId, orderId);
    }

    @ApiOperation(value = "create new order with for client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "order created")
        ,    
        @ApiResponse(code = 404, message = "client not found")})
    @PostMapping(value = "/clients{clientId}/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ID createOrder(@PathVariable Long clientId,
            @Valid @RequestBody List<Long> books) {
        return ID.from(orderService.createOrder(clientId, books));
    }

     @ApiOperation(value = "delete order with orderId of client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "order deleted")
        ,    
        @ApiResponse(code = 404, message = "client not found | order not found")})
    @DeleteMapping(value = "/clients{clientId}/orders/{orderId}")
    public void deleteOrder(@PathVariable Long clientId, @PathVariable Long orderId) {
        orderService.deleteOrderByClient(clientId, orderId);
    }

    
     @ApiOperation(value = "execute order with orderId of client with clientId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "order executed")
        ,    
        @ApiResponse(code = 404, message = "client not found | order not found")})
    @PostMapping(value = "/clients/{clientId}/orders/{orderId}")
    public OrderDTO executeOrder(@PathVariable Long clientId, @PathVariable Long orderId) {
        return orderService.executeOrderAndMapToDTO(clientId, orderId);
    }
}
