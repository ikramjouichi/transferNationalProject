package com.ensa.microservicetransfer.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @Column(name = "client_id")
    private String id;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;


    public Client() {
    }

    public Client(String clientId) {
        this.id = clientId;
    }

    public String getClientId() {
        return id;
    }

    public void setClientId(String clientId) {
        this.id = clientId;
    }
}
