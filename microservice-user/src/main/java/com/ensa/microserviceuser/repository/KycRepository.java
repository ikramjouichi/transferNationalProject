package com.ensa.microserviceuser.repository;

import com.ensa.microserviceuser.entities.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRepository extends JpaRepository<KYC, String> {
}
