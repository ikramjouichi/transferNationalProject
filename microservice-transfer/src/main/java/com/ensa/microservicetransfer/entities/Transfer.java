package com.ensa.microservicetransfer.entities;


import com.ensa.microservicetransfer.enums.TransferStatus;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Transfer {
    @Id
    private String transferId;

    private double amount;

    private Date transfer_date;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_client_id")
    private Client senderClient;

    @ManyToOne
    @JoinColumn(name = "recipient_client_id")
    private Client recipientClient;

    @ManyToOne
    @JoinColumn(name = "sender_prospect_id")
    private Prospect senderProspect;

    @ManyToOne
    @JoinColumn(name = "recipient_prospect_id")
    private Prospect recipientProspect;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

    public Transfer() {
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(Date transfer_date) {
        this.transfer_date = transfer_date;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public Client getSenderClient() {
        return senderClient;
    }

    public void setSenderClient(Client sender) {
        this.senderClient = sender;
    }

    public Client getRecipientClient() {
        return recipientClient;
    }

    public void setRecipientClient(Client recipient) {
        this.recipientClient = recipient;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Prospect getSenderProspect() {
        return senderProspect;
    }

    public void setSenderProspect(Prospect senderProspect) {
        this.senderProspect = senderProspect;
    }

    public Prospect getRecipientProspect() {
        return recipientProspect;
    }

    public void setRecipientProspect(Prospect recipientProspect) {
        this.recipientProspect = recipientProspect;
    }
}
