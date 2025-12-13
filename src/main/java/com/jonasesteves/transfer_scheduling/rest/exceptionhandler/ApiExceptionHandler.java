package com.jonasesteves.transfer_scheduling.rest.exceptionhandler;

import com.jonasesteves.transfer_scheduling.rest.exception.PastScheduledDateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad request");
        problemDetail.setDetail("One or more arguments are invalid.");
        problemDetail.setType(URI.create("/errors/bad-request"));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(PastScheduledDateException.class)
    protected ProblemDetail handle(PastScheduledDateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid scheduled date");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/invalid-scheduled-date"));
        return problemDetail;
    }
}
