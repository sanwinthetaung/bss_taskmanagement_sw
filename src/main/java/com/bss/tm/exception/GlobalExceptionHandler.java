package com.bss.tm.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bss.tm.utils.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorResponse response = new ErrorResponse(404, "Not Found.");
    	return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorResponse response = new ErrorResponse(500, "Internal Server Occur.");
    	return new ResponseEntity<Object>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ErrorResponse response = new ErrorResponse(400, ex.getParameterName() + " parameter is missing");
    	return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
    }
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse response = new ErrorResponse(405, "MediaType Not Supported.");
    	return new ResponseEntity<Object>(response,HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse response = new ErrorResponse(400, "Request Method not supported.");
    	return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
	}
}
