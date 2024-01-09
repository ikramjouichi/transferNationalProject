package com.ensa.microservicetransfer.dao;


import com.ensa.microservicetransfer.entities.Client;
import com.ensa.microservicetransfer.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransferDAO extends JpaRepository<Transfer, String> {

    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.senderClient = :sender AND t.transfer_date >= :starting")
    Double getTranferSumBySenderAndStartingDate(Client sender, Date starting);
}
