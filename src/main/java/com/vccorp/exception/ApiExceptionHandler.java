package com.vccorp.exception;

import java.sql.SQLException;

import javax.naming.NameNotFoundException;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handlerAllException(Exception ex, WebRequest request) {
		return new ErrorMessage(500, "Exception Handler");
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage sqlException(SQLException ex, WebRequest request) {
		return new ErrorMessage(501, "SQL Exception");
	}

	@ExceptionHandler(MessageDescriptorFormatException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage notEmptyException(MessageDescriptorFormatException ex, WebRequest request) {
		return new ErrorMessage(502, "name and address not empty, 1<age<100");
	}
	
	@ExceptionHandler(NameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage nameNotFoundException(NameNotFoundException ex, WebRequest request) {
		return new ErrorMessage(503, "No user");
	}

}
