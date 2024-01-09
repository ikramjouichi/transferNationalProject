package com.ensa.microservicewallet.controller;


import com.ensa.microservicewallet.Entity.Wallet;
import com.ensa.microservicewallet.exception.WalletNotFoundException;
import com.ensa.microservicewallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000") // Remplacez cela par l'origine de votre application React
@RestController
@RequestMapping("/api/wallet")
public class WalletController {


    final
    WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value="/all")
    public ResponseEntity<List<Wallet>> getAllWallets()
    {
       List<Wallet> wallets=walletService.findAllWallets();
       return new  ResponseEntity<>(wallets, HttpStatus.OK);
    }


    @GetMapping(value="/find/{Number}")
    public ResponseEntity<Wallet> getWalletByNumber(@PathVariable  String Number) throws WalletNotFoundException {
        return new ResponseEntity<>(walletService.findWalletByWallet_Number(Number),HttpStatus.OK);
    }

    @PostMapping("/addWallet")
    public ResponseEntity<Wallet> AddWallet(@RequestBody  Wallet wallet)
    {
        return new ResponseEntity<>(walletService.CreateWallet(wallet),HttpStatus.CREATED);
    }

    @PutMapping(value="/update/{id}/{sold}")
    public ResponseEntity<Wallet> UpdateWalletBalance(@PathVariable Long id,@PathVariable Float sold) throws WalletNotFoundException {
           System.out.println("update balance methode voici solde"+sold);
        return  new ResponseEntity<>(walletService.UpdateWalletBalance(id,sold),HttpStatus.OK);
    }

    @PutMapping(value="/debite/{id}/{amount}")
    public ResponseEntity<Wallet> debiteWallet(@PathVariable Long id,@PathVariable Float amount) throws WalletNotFoundException {
        System.out.println("update balance methode voici solde"+amount);
        return new ResponseEntity<>(walletService.debit_balance(id,amount),HttpStatus.OK);
    }

    @PutMapping(value="/credite/{id}/{amount}")
    public ResponseEntity<Wallet> crediteWallet(@PathVariable Long id,@PathVariable Float amount) throws WalletNotFoundException {
        return new ResponseEntity<>(walletService.credit_balance(id,amount),HttpStatus.OK);
    }

    @PutMapping("/debit/{id}")
    public ResponseEntity<Wallet> debitWallet(@PathVariable Long id, @RequestBody Float amount) {
        try {
            Wallet wallet = walletService.debitBalance(id, amount);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/credit/{id}")
    public ResponseEntity<Wallet> creditWallet(@PathVariable Long id, @RequestBody Float amount) {
        try {
            Wallet wallet = walletService.creditBalance(id, amount);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
