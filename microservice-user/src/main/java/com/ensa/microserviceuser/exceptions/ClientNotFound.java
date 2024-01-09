package com.ensa.microserviceuser.exceptions;

public class ClientNotFound extends CustomException{

    private final String id;

    public ClientNotFound(String id){
        this.id = id;
    }


    @Override
    public String getMessage() {
        return String.format("%s : %s",
            super.getMessage(), id
        );
    }
}
