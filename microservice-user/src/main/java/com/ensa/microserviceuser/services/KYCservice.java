package com.ensa.microserviceuser.services;

import com.ensa.microserviceuser.entities.BlacklistedClient;
import com.ensa.microserviceuser.entities.KYC;
import com.ensa.microserviceuser.exceptions.ClientNotFound;
import com.ensa.microserviceuser.repository.KycRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KYCservice {
    private KycRepository kycRepository;

    @Autowired
    public KYCservice(KycRepository kycRepository) {
        this.kycRepository = kycRepository;
    }

    public KYC createKYC(KYC kyc ) {
        System.out.println("voici service to create KYC : "+kyc);
        return kycRepository.save(kyc);
    }


    public void deleteKYC(String identityNumber) {
        kycRepository.deleteById(identityNumber);
    }

    public KYC updateKYC(String identityNumber, KYC updatedKYC) {
        if (kycRepository.existsById(identityNumber)) {
            updatedKYC.setIdentityNumber(identityNumber);
            return kycRepository.save(updatedKYC);
        } else {
            throw new RuntimeException("KYC with identityNumber " + identityNumber + " not found");
        }
    }

    /**
     * get the kyc instance by idNumber
     *
     * @param identityNumber the id number of each kyc
     * @return the kyc instance that contains detailed
     * informations about it
     * @see KYC
     */

    public KYC getKYCById(String identityNumber) {
        return kycRepository
                .findById(identityNumber)
                .orElseThrow(() -> new ClientNotFound(identityNumber));
    }

    public List<KYC> getAllKYC() {
        return kycRepository.findAll();
    }
}
