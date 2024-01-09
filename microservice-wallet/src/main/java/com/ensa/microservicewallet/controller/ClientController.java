package com.ensa.microservicewallet.controller;


import com.ensa.microservicewallet.Entity.Client;
import com.ensa.microservicewallet.dto.CreateClientDto;
import com.ensa.microservicewallet.exception.WalletNotFoundException;
import com.ensa.microservicewallet.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000") // Remplacez cela par l'origine de votre application React
@RequestMapping("/api/client")
@Controller
public class ClientController {
    ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }



    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDto ccDto) {
        System.out.println("je suis dans ClientController et createClient mrthode");
        return new ResponseEntity<>(this.clientService.createClient(ccDto), HttpStatus.CREATED);
    }
    @GetMapping("/")
    @ResponseBody
    public String sayHello(){
        System.out.println("say hello methode client controller waletservice");
        return "hello from controller transfer";
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable String id) {
        Optional<Client> client =  this.clientService.findClientById(id);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/{ClientId}/balance")
    public ResponseEntity<?> getWalletBalanceByClientId(@PathVariable Long ClientId) throws WalletNotFoundException {
        Float amount = this.clientService.findWalletBalanceById(ClientId);
        HashMap<String, Float> map = new HashMap<>();
        map.put("Amount", amount);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
