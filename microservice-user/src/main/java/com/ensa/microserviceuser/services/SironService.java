package com.ensa.microserviceuser.services;

import com.ensa.microserviceuser.entities.BlacklistedClient;
import com.ensa.microserviceuser.repository.BlacklistedClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SironService {
    private final BlacklistedClientRepository blacklistedClientRepository;


    public SironService(BlacklistedClientRepository blacklistedClientRepository) {
        this.blacklistedClientRepository = blacklistedClientRepository;
    }
    public List<BlacklistedClient> getAllBlackListed(){
        List<BlacklistedClient> listSirone= blacklistedClientRepository.findAll();
        return listSirone;
    }

    public boolean checkBlacklist(String id) {
        Optional<BlacklistedClient> blacklistedClient = blacklistedClientRepository.findById(id);
        return blacklistedClient.isPresent();
    }

    public void addBlacklistedClient(BlacklistedClient client) {
        blacklistedClientRepository.save(client);
    }

    public void removeBlacklistedCLient(String id) {
         BlacklistedClient blacklistedClient= blacklistedClientRepository.findById(id).orElse(null);
         if(blacklistedClient != null){
             blacklistedClientRepository.delete(blacklistedClient);
         }else {
             System.out.println("client not found with this ID");
         }
    }
}
