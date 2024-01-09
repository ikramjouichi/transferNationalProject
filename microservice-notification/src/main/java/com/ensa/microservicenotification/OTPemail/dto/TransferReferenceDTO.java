package com.ensa.microservicenotification.OTPemail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferReferenceDTO {
    private String email;
    private String urlPdf;
}
