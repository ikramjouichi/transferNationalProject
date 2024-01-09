package com.ensa.microservicetransfer.dao;


import com.ensa.microservicetransfer.entities.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorDAO extends JpaRepository<Supervisor, Long> {

}
