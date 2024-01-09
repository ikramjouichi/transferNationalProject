package com.ensa.microservicetransfer.dao;

import com.ensa.microservicetransfer.entities.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectDAO extends JpaRepository<Prospect, String> {
}
