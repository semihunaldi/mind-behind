package com.mindbehind.demo.exception;

import com.mindbehind.demo.model.BaseResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by semihunaldi on 29.06.2021
 */

@ControllerAdvice
public class MindBehindExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MindBehindCommentException.class})
    protected ResponseEntity<Object> handleMindBehindCommentException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, prepareBaseResult(ex, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> unknownException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, prepareUnknownBaseResult(ex, HttpStatus.INTERNAL_SERVER_ERROR), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private BaseResult prepareBaseResult(Exception exception, HttpStatus httpStatus) {
        return new BaseResult(httpStatus.value(), exception.getLocalizedMessage());
    }

    private BaseResult prepareUnknownBaseResult(Exception exception, HttpStatus httpStatus) {
        final String message = "Mind Behind: Unknown Error" + exception.getLocalizedMessage();
        return new BaseResult(httpStatus.value(), message);
    }
}
