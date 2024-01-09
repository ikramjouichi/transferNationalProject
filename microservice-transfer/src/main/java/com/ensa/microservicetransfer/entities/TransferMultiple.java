package com.ensa.microservicetransfer.entities;

import com.ensa.microservicetransfer.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransferMultiple {
    @Id
    private String transferId;

    private double amount;

    private Date transfer_date;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    @ManyToOne
    @JoinColumn(name = "sender_client_id")
    private Client senderClient;
    @OneToMany()
    @JoinColumn(name = "recipients_clients_id")
    private List<Client> recipientClients;
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;
}
