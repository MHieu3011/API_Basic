package com.vccorp.exception;

import java.sql.SQLException;

import javax.naming.NameNotFoundException;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.vccorp.api.ResponseAPICustom;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseAPICustom handlerAllException(Exception ex, WebRequest request) {
		return new ResponseAPICustom(0, ex.getMessage(), 500, "");
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseAPICustom sqlException(SQLException ex, WebRequest request) {
		return new ResponseAPICustom(0, ex.getMessage(), ex.getErrorCode(), "");
	}

	@ExceptionHandler(MessageDescriptorFormatException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseAPICustom notEmptyException(MessageDescriptorFormatException ex, WebRequest request) {
		return new ResponseAPICustom(0, ex.getMessage(), 502, "");
	}

	@ExceptionHandler(NameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseAPICustom nameNotFoundException(NameNotFoundException ex, WebRequest request) {
		return new ResponseAPICustom(0, ex.getMessage(), 503, "");
	}

}
