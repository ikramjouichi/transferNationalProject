package com.ensa.microserviceuser.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class KYC {

    @Id
    private String identityNumber;

    private String lastname;

    private String firstname;

    private String gender;

    private String identityType;

    private String birthdayDate;

    private String occupation;

    private String address;

    private String city;

    private String phoneNumber;

    private String nationality;

    private String country;

    private String emissionCountry;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;





}
