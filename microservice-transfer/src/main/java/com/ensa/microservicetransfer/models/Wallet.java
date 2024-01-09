package com.ensa.microservicetransfer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class Wallet {
    private Long id;
    private String number;
    private Float balance;
    private Float transferCeiling;
    private Float dailyCeiling;
    private LocalDateTime creationDate;

    public Wallet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getTransferCeiling() {
        return transferCeiling;
    }

    public void setTransferCeiling(Float transferCeiling) {
        this.transferCeiling = transferCeiling;
    }

    public Float getDailyCeiling() {
        return dailyCeiling;
    }

    public void setDailyCeiling(Float dailyCeiling) {
        this.dailyCeiling = dailyCeiling;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
