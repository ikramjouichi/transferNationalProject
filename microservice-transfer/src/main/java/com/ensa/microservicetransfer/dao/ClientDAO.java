package com.ensa.microservicetransfer.dao;

import com.ensa.microservicetransfer.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends JpaRepository<Client, String> {

}
