package com.vccorp.exception;

import java.sql.SQLException;
import java.util.zip.DataFormatException;

import javax.naming.NameNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.vccorp.api.ResponseAPICustom;

@RestControllerAdvice
public class APIExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseAPICustom handlerAllException(Exception ex, WebRequest request) {
		return new ResponseAPICustom(0, "Lỗi phát sinh khác", 500, "");
	}

	@ExceptionHandler(SQLException.class)
	public ResponseAPICustom sqlException(SQLException ex, WebRequest request) {
		return new ResponseAPICustom(0, "SQL EXCEPTION", 901, "");
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseAPICustom noResultException(NoResultException ex, WebRequest request) {
		return new ResponseAPICustom(0, "Không tìm thấy kết quả", 404, "");
	}

	@ExceptionHandler(DataFormatException.class)
	public ResponseAPICustom dataFormatException(DataFormatException ex, WebRequest request) {
		return new ResponseAPICustom(0, "Dữ liệu nhập vào không đúng ràng buộc", 900, "");
	}

	@ExceptionHandler(DataExistException.class)
	public ResponseAPICustom dataExistException(DataExistException ex, WebRequest request) {
		return new ResponseAPICustom(0, "Dữ liệu đã tồn tại trong hệ thống", 902, "");
	}

	@ExceptionHandler(NameNotFoundException.class)
	public ResponseAPICustom nameNotFoundException(NameNotFoundException ex, WebRequest request) {
		return new ResponseAPICustom(0, "Name not found EXCEPTION", 503, "");
	}

	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseAPICustom addressNotFoundException(AddressNotFoundException ex, WebRequest request) {
		return new ResponseAPICustom(0, "Address not found EXCEPTION", 504, "");
	}

	@ExceptionHandler(NotEnoughMoneyException.class)
	public ResponseAPICustom notEnoughMoneyException(NotEnoughMoneyException ex, WebRequest request) {
		return new ResponseAPICustom(0, "Not Enough Money EXCEPTION", 505, "");
	}

	@ExceptionHandler(MoneyAException.class)
	public ResponseAPICustom moneyAException(MoneyAException ex, WebRequest request) {
		return new ResponseAPICustom(0, "'Money A' smaller trans money EXCEPTION", 506, "");
	}

}
