package com.ensa.microservicewallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientDto {
    private String id;
    private String email;
    //private  String phone;
}
