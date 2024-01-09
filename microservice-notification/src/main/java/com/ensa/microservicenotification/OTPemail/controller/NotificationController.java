package com.ensa.microservicenotification.OTPemail.controller;

import com.ensa.microservicenotification.OTPemail.EmailSenderService;
import com.ensa.microservicenotification.OTPemail.dto.EmailDetailsDTO;
import com.ensa.microservicenotification.OTPemail.dto.TransferReferenceDTO;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000") // Remplacez cela par l'origine de votre application React
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final EmailSenderService emailService;

    public NotificationController(EmailSenderService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/password-notification")
    public void sendSignUpPasswordNotification(@RequestBody EmailDetailsDTO emailDetailsDTO) {
        this.emailService.sendEmail(emailDetailsDTO.getEmail(),emailDetailsDTO.getPassword());

    }
    @PostMapping("/TransferInfoPdf-notification")
    public void sendTransferInfoNotification(@RequestBody TransferReferenceDTO transferReferenceDTO){
        this.emailService.sendTransferRefernce(transferReferenceDTO.getEmail(),transferReferenceDTO.getUrlPdf());
    }
}
