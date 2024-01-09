package com.ensa.microserviceuser.exceptions;

public class CustomException extends RuntimeException{


    @Override
    public String getMessage() {
        return this.getClass()
                .getSimpleName()
                .replaceAll("([A-Z])", " $1")
                .strip().toUpperCase(); // CLIENT NOT FOUND
    }

}
