package com.ensa.microservicewallet.Repository;


import com.ensa.microservicewallet.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Client save(Client client);

    @Query("SELECT c.wallet.balance FROM Client c WHERE c.id = :id")
    Float findWalletBalanceById(Long id);
}