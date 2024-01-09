package com.ensa.microserviceuser.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlacklistedClient {

    @Id
    private String bclientId;
    @NonNull
    private Date blacklistingDate;
    @NonNull
    private String cause;
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

}
