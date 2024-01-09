package com.ensa.microservicetransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmailTransfer {
    private String email;
    private String urlPdf;
}
