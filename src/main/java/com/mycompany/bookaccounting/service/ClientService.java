/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaccounting.service;

import com.mycompany.bookaccounting.entity.Client;
import com.mycompany.bookaccounting.repository.ClientRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
    
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("client not found: " + id));
    }
    
    public Long create(Client client) {
        return clientRepository.save(client).getId();
    }
    
    @Transactional
    public Client updateClient(Long id, Client updateClient) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("client not found: " + id));
        client.setName(updateClient.getName());
        client.setPhoneNumber(updateClient.getPhoneNumber());
        return clientRepository.save(client);
    }
    
    @Transactional
    public void deleteClient(Long id) {
        clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("client not found: " + id));
        clientRepository.deleteById(id);
    }
}
