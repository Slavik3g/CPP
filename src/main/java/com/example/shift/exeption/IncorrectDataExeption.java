package com.example.shift.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "invalid data")
public class IncorrectDataExeption extends Exception{

    public IncorrectDataExeption() {
    }
    public IncorrectDataExeption(String message) {
        super(message);
    }

    public IncorrectDataExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDataExeption(Throwable cause) {
        super(cause);
    }
}
