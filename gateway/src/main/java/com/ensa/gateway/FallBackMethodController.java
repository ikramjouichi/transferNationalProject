package com.ensa.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {
    @GetMapping("/userServiceFallBack")
    public String userServiceFallBack(){
        return "user service is taking longer than Expected." +
                "Please try again later";
    }
    @GetMapping("/walletServiceFallBack")
    public String walletServiceFallBack(){
        return "wallet service is taking longer than Expected." +
                "Please try again later";
    }
    @GetMapping("/transferServiceFallBack")
    public String transferServiceFallBack(){
        return "transfer service is taking longer than Expected." +
                "Please try again later";
    }
    @GetMapping("/notificationServiceFallBack")
    public String notificationServiceFallBack(){
        return "notification service is taking longer than Expected." +
                "Please try again later";
    }
    @GetMapping("/sironeServiceFallBack")
    public String sironeServiceFallBack(){
        return "sirone service is taking longer than Expected." +
                "Please try again later";
    }
}
