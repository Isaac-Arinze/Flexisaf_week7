package com.flexisaf.week3And4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public class EmailAlreadyExistsException extends RuntimeException{
        private String message;

        public EmailAlreadyExistsException(String message){
            super(message);
        }
}
