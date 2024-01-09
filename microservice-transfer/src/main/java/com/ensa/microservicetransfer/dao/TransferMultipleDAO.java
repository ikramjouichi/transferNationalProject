package com.ensa.microservicetransfer.dao;

import com.ensa.microservicetransfer.entities.TransferMultiple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Transactional
@Repository
public interface TransferMultipleDAO extends JpaRepository<TransferMultiple, String> {
}
