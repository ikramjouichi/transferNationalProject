package com.ensa.microservicetransfer.web;

import com.ensa.microservicetransfer.entities.Client;
import com.ensa.microservicetransfer.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("clients")
@RestController
public class ClientController {
    private ClientService clientService ;
    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    Client create(@RequestBody Client client) {
        return this.clientService.create(client);
    }
}

