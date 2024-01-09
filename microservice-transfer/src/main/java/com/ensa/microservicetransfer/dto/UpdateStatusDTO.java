package com.ensa.microservicetransfer.dto;


import com.ensa.microservicetransfer.enums.TransferStatus;

import java.io.Serializable;

public class UpdateStatusDTO implements Serializable {
    private String id;
    private TransferStatus transferStatus;

    public UpdateStatusDTO(String id, TransferStatus transferStatus) {
        this.id = id;
        this.transferStatus = transferStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }
}
