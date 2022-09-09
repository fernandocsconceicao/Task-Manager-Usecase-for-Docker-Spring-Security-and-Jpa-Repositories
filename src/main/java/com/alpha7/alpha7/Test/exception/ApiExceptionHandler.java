package com.alpha7.alpha7.Test.exception;

import jdk.jfr.StackTrace;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleDuplicatedDatabaseEntry() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Armazenamento duplicado");
    }
    @ExceptionHandler(StandardException.class)
    public ResponseEntity<String> handleStandardError(StandardException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }


}