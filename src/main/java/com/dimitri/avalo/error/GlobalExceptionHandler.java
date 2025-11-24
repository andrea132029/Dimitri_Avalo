package com.dimitri.avalo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dimitri.avalo.exception.Excepcion;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorAtributo> handleNumberFormatException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorAtributo("formato", "El valor ingresado no es válido. Use solo números enteros."));
    }

    @ExceptionHandler(Excepcion.class)
    public ResponseEntity<ErrorAtributo> handleCustomException(Excepcion ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorAtributo(ex.getAtributo(), ex.getMessage()));
    }
}
