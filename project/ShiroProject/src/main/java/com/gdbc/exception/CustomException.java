package com.gdbc.exception;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6378094865208252322L;
	// 异常信息
	private String message;

	public CustomException(String message) {
		super(message);
		this.message = message;

	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
