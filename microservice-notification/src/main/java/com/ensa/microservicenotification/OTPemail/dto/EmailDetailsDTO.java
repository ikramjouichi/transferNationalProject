package com.ensa.microservicenotification.OTPemail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetailsDTO {
    private String email;
    private String password;
}
