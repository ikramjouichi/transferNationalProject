package com.ensa.microservicetransfer.models;

import com.ensa.microservicetransfer.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientModel {
    private String id;
    private User user;
    private Wallet wallet;

}
