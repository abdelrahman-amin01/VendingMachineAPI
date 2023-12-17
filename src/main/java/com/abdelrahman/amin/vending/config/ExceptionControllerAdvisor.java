package com.abdelrahman.amin.vending.config;

import com.abdelrahman.amin.vending.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

    //TODO:Handel Each exception and response with descriptive error page
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handelAllExceptions(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse
                = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now(), request.getServletPath());
        return ResponseEntity.ok(errorResponse);
    }
}