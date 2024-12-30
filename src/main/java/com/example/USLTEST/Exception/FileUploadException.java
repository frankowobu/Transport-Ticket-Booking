package com.example.USLTEST.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileUploadException extends RuntimeException { public FileUploadException(String message){
            super(message);
        };

}
