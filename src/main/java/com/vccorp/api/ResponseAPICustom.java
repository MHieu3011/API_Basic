package com.vccorp.api;

public class ResponseAPICustom {

	private Integer status;
	private String message;
	private Integer code;
	private Object data;

	public ResponseAPICustom() {
	}

	public ResponseAPICustom(Integer status, String message, Integer code, Object data) {
		this.status = status;
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getDate() {
		return data;
	}

	public void setDate(Object data) {
		this.data = data;
	}

}
