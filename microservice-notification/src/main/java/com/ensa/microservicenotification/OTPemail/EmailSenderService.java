package com.ensa.microservicenotification.OTPemail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;



    public String sendEmail(String toEmail, String body){
        try {
            SimpleMailMessage message= new SimpleMailMessage();
            message.setFrom("academyteamensa@gmail.com");
            message.setTo(toEmail);
            message.setText("Bonjour Monsieur/Madame , votre mots de passe est : "+body);
            message.setSubject("Récupérez votre mot de passe");

            mailSender.send(message);
            return "mail sent successfully";
        }catch (Exception e){
            return "Error while Sending Mail";
        }

    }
    public String sendTransferRefernce(String toEmail, String body){
        try{
            SimpleMailMessage message= new SimpleMailMessage();
            message.setFrom("academyteamensa@gmail.com");
            message.setTo(toEmail);
            message.setText("Bonjour Monsieur/Madame , Voici votre reçu de transfer : "+body);
            message.setSubject("Récupérez votre reçu de Transfer");
            mailSender.send(message);
            return "mail sent successfully";
        }catch (Exception e){
            return "Error while Sending Mail";
        }
    }

}
