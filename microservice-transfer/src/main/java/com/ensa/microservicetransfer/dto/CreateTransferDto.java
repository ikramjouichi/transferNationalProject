package com.ensa.microservicetransfer.dto;


import com.ensa.microservicetransfer.enums.FeesType;
import com.ensa.microservicetransfer.enums.TransferStatus;
import com.ensa.microservicetransfer.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransferDto {
    public String id;
    public TransferType transferType;
    public Double amount;
    public TransferStatus status;
    public TransferClient sender;
    public TransferClient recipient;
    public FeesType feesType;

    public class TransferClient {
        public TransferClientType type;
        public String id;
    }
    public enum TransferClientType {
        CLIENT,
        PROSPECT
    }
}
