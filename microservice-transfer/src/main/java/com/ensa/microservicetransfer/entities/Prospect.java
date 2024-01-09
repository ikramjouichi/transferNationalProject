package com.ensa.microservicetransfer.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prospect {
@Id
    private String id;

    private String firstName;
    private String LastName;
    private String phoneNumber;


}
