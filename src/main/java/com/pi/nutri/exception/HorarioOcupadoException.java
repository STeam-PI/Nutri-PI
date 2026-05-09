package com.pi.nutri.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Isso retorna automaticamente o Erro 409
public class HorarioOcupadoException extends RuntimeException {
    public HorarioOcupadoException(String message) {
        super(message);
    }
}