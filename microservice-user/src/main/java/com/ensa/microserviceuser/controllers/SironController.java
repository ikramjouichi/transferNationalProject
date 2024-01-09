package com.ensa.microserviceuser.controllers;

import com.ensa.microserviceuser.entities.BlacklistedClient;
import com.ensa.microserviceuser.services.SironService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Remplacez cela par l'origine de votre application React
@RestController
@RequestMapping("/api/siron")
public class SironController {
    private final SironService sironService;

    public SironController(SironService sironService) {
        this.sironService = sironService;
    }

    @GetMapping("/getAllSironeList")
    public List<BlacklistedClient> gettallSirone(){
        return sironService.getAllBlackListed();
    }

    @GetMapping("/is-blacklisted/{id}")
    public boolean isBlacklisted(@PathVariable String id) {

        return this.sironService.checkBlacklist(id);
    }

    @PostMapping("/add-to-blacklist")
    public void addToBlacklisted(@RequestBody BlacklistedClient blacklistedClient) {
        this.sironService.addBlacklistedClient(blacklistedClient);
    }

    @DeleteMapping("/remove-from-blacklist/{id}")
    public void removeFromBlacklisted(@PathVariable String id) {
        this.sironService.removeBlacklistedCLient(id);
    }
}
