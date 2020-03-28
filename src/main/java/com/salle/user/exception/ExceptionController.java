package com.salle.user.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.salle.user.controller.UserController;

@ControllerAdvice(assignableTypes = UserController.class)
public class ExceptionController {
    private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    
    /* Spring Exception */
    
    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex){
    	logger.error("handleMethodArgumentNotValidException", ex);
    	ExceptionResponse errResponse = ExceptionResponse.of(ExceptionCode.INVALID_INPUT_VALUE, ex.getBindingResult());
    	return new ResponseEntity<ExceptionResponse>(errResponse,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value=MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {
    	logger.error("handleMEthodArgumentTypeMismatchException", ex);
    	ExceptionResponse errResponse = ExceptionResponse.of(ex);
    	return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value=HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
    	logger.error("handleHttpRequestMethodNotSupportedException", ex);
    	ExceptionResponse errResponse = ExceptionResponse.of(ExceptionCode.METHOD_NOT_ALLOWED);
    	return new ResponseEntity<>(errResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(value=AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
    	logger.error("handleAccessDeniedException", ex);
    	ExceptionResponse errResponse = ExceptionResponse.of(ExceptionCode.HANDLE_ACCESS_DENIED);
    	return new ResponseEntity<>(errResponse, HttpStatus.valueOf(ExceptionCode.HANDLE_ACCESS_DENIED.getStatus()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
    	logger.error("handleEntityNotFoundException", ex);
    	ExceptionResponse errResponse = ExceptionResponse.of(ExceptionCode.INTERNAL_SERVER_ERROR);
    	return new ResponseEntity<>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /* Custom Exception */
    @ExceptionHandler(value=HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleNullRequestBody(HttpServletRequest req, HttpMessageNotReadableException ex) {
    	logger.error("handleNullRequestBody", ex);
    	
    	ExceptionResponse errResponse = ExceptionResponse.of(ExceptionCode.NULL_BODY);
    	return new ResponseEntity<ExceptionResponse>(errResponse,HttpStatus.BAD_REQUEST);
    }
    
    
}
