package com.jonasesteves.transfer_scheduling.api.exceptionhandler;

import com.jonasesteves.transfer_scheduling.api.exception.EntityNotFoundException;
import com.jonasesteves.transfer_scheduling.api.exception.InvalidAmountException;
import com.jonasesteves.transfer_scheduling.api.exception.PastScheduledDateException;
import com.jonasesteves.transfer_scheduling.api.exception.StartDateGreaterThanFinalDateException;
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
        problemDetail.setDetail("One or more arguments are missing or invalid.");
        problemDetail.setType(URI.create("/errors/bad-request"));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Entity not found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/entity-not-found"));
        return problemDetail;
    }

    @ExceptionHandler(PastScheduledDateException.class)
    protected ProblemDetail handlePastScheduledDate(PastScheduledDateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid scheduled date");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/invalid-scheduled-date"));
        return problemDetail;
    }

    @ExceptionHandler(InvalidAmountException.class)
    protected ProblemDetail handleInvalidAmount(InvalidAmountException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid amount value");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/invalid-amount-value"));
        return problemDetail;
    }

    @ExceptionHandler(StartDateGreaterThanFinalDateException.class)
    protected ProblemDetail handleInvalidDatesInterval(StartDateGreaterThanFinalDateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid dates interval");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/invalid-amount-value"));
        return problemDetail;
    }
}
