package com.ensa.microservicenotification.OTPsms;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VonageCnfig {
    @Value("f3147d8f")
    private String apiKey;

    @Value("EGiNoZ61CH0fuJLn")
    private String apiSecret;
    @Bean
    public VonageClient vonageClient() {
        return VonageClient.builder().apiKey(apiKey).apiSecret(apiSecret).build();
    }
}
