package com.ensa.microservicenotification.OTPsms.controller;

import com.ensa.microservicenotification.OTPsms.model.MessageRequest;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venage")
public class VonageController {

    @Autowired
    private VonageClient vonageClient;
    //TODO-> you can change this brand name
    private static  String BRAND_NAME="Vonage APIs";

    @PostMapping("/sendMessage")
    public String sendVerificationCode(@RequestBody MessageRequest messageRequest) {


        String phoneNumber = messageRequest.getPhone();


        TextMessage message = new TextMessage(BRAND_NAME, phoneNumber,"votre code de verification est :"+generateVerificationCode() );

        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK)
            return "message sent secessfuly -> check vonage account  SMS Logs to se if the message was sent or not ";
        else return "message not sent check if the phone number was correct :  starts with +212   or check your Vonage balance  : create new account and change properties in your application.yaml" ;

    }
    private String generateVerificationCode() {
        int min = 1000;
        int max = 9999;
        int verificationCodeInt = min + (int) (Math.random() * (max - min + 1));
        return String.valueOf(verificationCodeInt);
    }


}
