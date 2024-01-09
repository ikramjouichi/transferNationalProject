package com.ensa.microserviceuser.repository;

import com.ensa.microserviceuser.entities.BlacklistedClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedClientRepository extends JpaRepository<BlacklistedClient, String> {
}
