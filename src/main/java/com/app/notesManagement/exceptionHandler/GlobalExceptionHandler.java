package com.app.notesManagement.exceptionHandler;
import com.app.notesManagement.exception.*;
import com.app.notesManagement.model.Words;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

    @ControllerAdvice
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler({NotesNotFoundException.class,TextsNotFoundException.class, WordsNotFoundException.class})
        protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
            String path = request.getDescription(false); // Obtain the path dynamically
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex, path);
            return buildResponseEntity(errorResponse);
        }


        @ExceptionHandler({TitleAndTextContentRequiredException.class })
        protected ResponseEntity<Object> handleRequiredException(RuntimeException ex, WebRequest request) {
            String path = request.getDescription(false); // Obtain the path dynamically
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex, path);
            return buildResponseEntity(errorResponse);
        }

        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            String path = request.getDescription(false); // Obtain the path dynamically
            List<String> validationErrors = extractValidationErrors(ex);
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request payload", ex, path, validationErrors);
            return buildResponseEntity(errorResponse);
        }

        private ResponseEntity<Object> buildResponseEntity(ErrorResponse customExceptionResponse) {
            return new ResponseEntity<>(customExceptionResponse, HttpStatusCode.valueOf(customExceptionResponse.getStatus()));
        }

        private List<String> extractValidationErrors(HttpMessageNotReadableException ex) {
            List<String> validationErrors = new ArrayList<>();

            if (ex.getCause() instanceof InvalidFormatException) {
                InvalidFormatException invalidFormatException = (InvalidFormatException)     ex.getCause();

                // Extract field and validation message from InvalidFormatException
                String fieldName = invalidFormatException.getPath().get(0).getFieldName();
                String validationMessage = invalidFormatException.getOriginalMessage();

                validationErrors.add(fieldName + ": " + validationMessage);
            }

            return validationErrors;
        }

    }

