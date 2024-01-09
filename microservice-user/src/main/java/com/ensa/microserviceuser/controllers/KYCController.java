package com.ensa.microserviceuser.controllers;

import com.ensa.microserviceuser.entities.KYC;
import com.ensa.microserviceuser.services.KYCservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
@CrossOrigin(origins = "http://localhost:3000") // Remplacez cela par l'origine de votre application React
@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
@Slf4j
public class KYCController {

    private final KYCservice kycService;


    @PostMapping
    @ResponseStatus(CREATED)
    public KYC createKYC(@RequestBody KYC kyc) {
        log.info(" KYC : {} ", kyc.getIdentityNumber());
        return kycService.createKYC(kyc);

    }

    @GetMapping("/{idNumber}")
    public KYC getKYCById(@PathVariable String idNumber) {
        return kycService.getKYCById(idNumber);

    }

    @GetMapping
    public ResponseEntity<List<KYC>> getAllKYC() {
        List<KYC> allKYC = kycService.getAllKYC();
        return new ResponseEntity<>(allKYC, OK);
    }

    @PutMapping("/{idNumber}")
    public ResponseEntity<KYC> updateKYC(
            @PathVariable String idNumber,
            @RequestBody KYC updatedKYC
    ){
        KYC kyc = kycService.updateKYC(idNumber, updatedKYC);
        return new ResponseEntity<>(kyc, OK);
    }

    @DeleteMapping("/{idNumber}")
    public ResponseEntity<Void> deleteKYC(@PathVariable String idNumber) {
        kycService.deleteKYC(idNumber);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
