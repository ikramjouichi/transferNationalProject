package com.ensa.microservicewallet.Repository;


import com.ensa.microservicewallet.Entity.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, String> {
}
