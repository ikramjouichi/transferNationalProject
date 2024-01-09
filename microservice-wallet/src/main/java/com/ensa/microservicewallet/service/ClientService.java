package com.ensa.microservicewallet.service;


import com.ensa.microservicewallet.Entity.Client;
import com.ensa.microservicewallet.Entity.User;
import com.ensa.microservicewallet.Entity.Wallet;
import com.ensa.microservicewallet.Repository.ClientRepository;
import com.ensa.microservicewallet.dto.CreateClientDto;
import com.ensa.microservicewallet.dto.SendEmailDto;
import com.ensa.microservicewallet.utility.GenerateWalletNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }



    public Client createClient(CreateClientDto ccDto) {
        String generatedPassword = UUID.randomUUID().toString().replace("-","").substring(0, 10);
        // save Client to keycloak before saving on db
         //il faut emplacer email par phone car on va utiliser OTP SMS
        User user = new User(ccDto.getEmail(), generatedPassword); // password not saved in db, only keycloak
        System.out.println("user created"+user);
        Wallet wallet = new Wallet(
                GenerateWalletNumber.generateWalletNumber(),
                0.00f,
                5000.00f,
                4000.00f,
                LocalDateTime.now()
        );
        System.out.println("wallet created"+wallet);
        Client client = new Client(ccDto.getId(), user, wallet);
        System.out.println("client created"+client);


        SendEmailDto seDto = new SendEmailDto(user.getEmail(), generatedPassword);
        //microservicenotification localhost:8070
        restTemplate.postForObject(
                "http://microservicenotification/api/notification/password-notification",
                seDto, SendEmailDto.class
                );

        return this.clientRepository.save(client);
    }

    public Optional<Client> findClientById(String id) {
        return this.clientRepository.findById(id);
    }

    public Float findWalletBalanceById(Long id) {
        return this.clientRepository.findWalletBalanceById(id);
    }
}
